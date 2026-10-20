/*
* Copyright (c) 2025 Contributors to the Eclipse Foundation.
*
* This program and the accompanying materials are made
* available under the terms of the Eclipse Public License 2.0
* which is available at https://www.eclipse.org/legal/epl-2.0/
*
* SPDX-License-Identifier: EPL-2.0
*
* Contributors:
*   SmartCity Jena - initial
*   Stefan Bischof (bipolis.org) - initial
*/
package org.eclipse.daanse.rolap.mapping.model.docgen.integration;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.daanse.rolap.mapping.model.docgen.EcoreExtension;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EEnum;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.xmi.impl.EcoreResourceFactoryImpl;
import org.eclipse.fennec.emf.osgi.annotation.require.RequireEMF;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.osgi.framework.BundleContext;
import org.osgi.test.common.annotation.InjectBundleContext;
import org.slf4j.Logger;

import io.pebbletemplates.pebble.PebbleEngine;
import io.pebbletemplates.pebble.loader.ClasspathLoader;
import io.pebbletemplates.pebble.template.PebbleTemplate;

@RequireEMF
public class GetDocumentationTest {

    private static final Logger LOGGER = org.slf4j.LoggerFactory.getLogger(GetDocumentationTest.class);
    private static String TEXT = """
            # 🧩 Introduction: Model Structure and Class Overview

            This chapter provides a comprehensive overview of all classes and enumerations defined in the EMF Ecore model. Each section focuses on a single class or enum, presenting its full structure, including:

            - Attributes and their types
            - References to other model elements
            - Cardinalities, Containsments and default values
            - Usage relationships across the model
            - A visual diagram for better understanding

            The documentation is generated directly from the Ecore/XMI representation of the model, ensuring consistency with the underlying formal definition.

            Unlike formats such as JSON or YAML, XMI is a model-oriented serialization designed for structural clarity and type safety. It captures the semantics and relationships of your model elements explicitly, making it the preferred format for precise and scalable domain modeling.

            Use this section to explore how the individual elements of your model are defined and how they interconnect — both structurally and semantically.
                        """;
    @TempDir
    Path tempDir;

    @InjectBundleContext
    BundleContext bc;

    @Test
    public void resourceSetExistsTest() throws SQLException, InterruptedException, IOException {
        Path path = Path.of("modelOutput");
        deleteRecursively(path);
        Files.createDirectories(path);

        ClasspathLoader loader = new ClasspathLoader(GetDocumentationTest.class.getClassLoader());
        loader.setPrefix("templates");

        // Pebble Engine erstellen
        PebbleEngine engine = new PebbleEngine.Builder().autoEscaping(false).loader(loader)
                .extension(new EcoreExtension()).build();

        // Alle Packages des Baums (inkl. Subpackages) einsammeln
        List<EPackage> packages = new ArrayList<>();
        for (EPackage ePackage : readPackages()) {
            collectPackages(ePackage, packages);
        }

        // Alle Klassen des gesamten Baums, damit "Used by" paketuebergreifend funktioniert
        List<EClass> allClasses = packages.stream().flatMap(p -> p.getEClassifiers().stream())
                .filter(EClass.class::isInstance).map(EClass.class::cast).toList();

        for (EPackage ePackage : packages) {
            LOGGER.debug("EPackage: " + qualifiedName(ePackage));
            createDoc(ePackage, engine, allClasses, path);
        }
    }

    private static void collectPackages(EPackage ePackage, List<EPackage> packages) {
        packages.add(ePackage);
        for (EPackage sub : ePackage.getESubpackages()) {
            collectPackages(sub, packages);
        }
    }

    private static String qualifiedName(EPackage ePackage) {
        EPackage parent = ePackage.getESuperPackage();
        return parent == null ? ePackage.getName() : qualifiedName(parent) + "." + ePackage.getName();
    }

    private static String pageName(EPackage ePackage) {
        return ePackage.getESuperPackage() == null ? "index" : "package-" + qualifiedName(ePackage);
    }

    private List<EPackage> readPackages() throws IOException {
        // Register .ecore file extension
        Resource.Factory.Registry.INSTANCE.getExtensionToFactoryMap().put("ecore", new EcoreResourceFactoryImpl());

        // Create a ResourceSet
        ResourceSet resourceSet = new ResourceSetImpl();

        // Load the .ecore model
        String ecoreFile = "model/rolap.mapping.ecore";

        URL url = bc.getBundle().getEntry(ecoreFile);
        Resource resource = resourceSet.getResource(URI.createURI(url.toString()), true);

        // Access the model
        resource.load(Collections.EMPTY_MAP);

        // Get EPackages

        List<EPackage> packages = new ArrayList<>();
        for (Object obj : resource.getContents()) {
            if (obj instanceof EPackage) {
                EPackage ePackage = (EPackage) obj;
                packages.add(ePackage);
            }
        }
        return packages;
    }

    private void createDoc(EPackage ePackage, PebbleEngine engine, List<EClass> allClasses, Path path)
            throws IOException {

        List<EClass> packageClasses = ePackage.getEClassifiers().stream().filter(EClass.class::isInstance)
                .map(EClass.class::cast).toList();

        List<EEnum> packageEnums = ePackage.getEClassifiers().stream().filter(EEnum.class::isInstance)
                .map(EEnum.class::cast).toList();

        createPackagePage(ePackage, engine, path);

        for (EClass eClass : packageClasses) {
            if (!eClass.isInterface()) {

                createClassPage(ePackage, eClass, engine, allClasses, path);
            }
        }

        for (EEnum eEnum : packageEnums) {
            createEnumPage(ePackage, eEnum, engine, path);
        }

    }

    private void createPackagePage(EPackage ePackage, PebbleEngine engine, Path path) throws IOException {

        PebbleTemplate template = engine.getTemplate("package.peb");
        Map<String, Object> context = packageContext(ePackage);
        List<Map<String, String>> subpackages = ePackage.getESubpackages().stream()
                .map(sub -> Map.of("name", qualifiedName(sub), "page", pageName(sub))).toList();
        context.put("subpackages", subpackages);
        EPackage parent = ePackage.getESuperPackage();
        if (parent != null) {
            context.put("parentName", qualifiedName(parent));
            context.put("parentPage", pageName(parent));
        }

        // In Datei schreiben
        try (Writer writer = new FileWriter(path.resolve(pageName(ePackage) + ".md").toFile())) {

            if (ePackage.getESuperPackage() == null) {
                writer.append(TEXT);
                writer.append("\n");
            }
            template.evaluate(writer, context);
        }
    }

    private static Map<String, Object> packageContext(EPackage ePackage) {
        Map<String, Object> context = new HashMap<>();
        context.put("package", ePackage);
        context.put("packageName", qualifiedName(ePackage));
        context.put("packagePage", pageName(ePackage));
        return context;
    }

    private void createClassPage(EPackage ePackage, EClass eClass, PebbleEngine engine, List<EClass> allClasses,
            Path path) throws IOException {

        PebbleTemplate template = engine.getTemplate("class.peb");
        Map<String, Object> context = packageContext(ePackage);
        context.put("class", eClass);
        context.put("allClasses", allClasses);

        String className = eClass.getName();
        // In Datei schreiben
        try (Writer writer = new FileWriter(path.resolve("class-" + className + ".md").toFile())) {
            template.evaluate(writer, context);
        }
    }

    private void createEnumPage(EPackage ePackage, EEnum eEnum, PebbleEngine engine, Path path) throws IOException {

        PebbleTemplate template = engine.getTemplate("enum.peb");
        Map<String, Object> context = packageContext(ePackage);
        context.put("enum", eEnum);

        String className = eEnum.getName();
        // In Datei schreiben
        try (Writer writer = new FileWriter(path.resolve("enum-" + className + ".md").toFile())) {
            template.evaluate(writer, context);
        }
    }

    public static void deleteRecursively(Path path) throws IOException {
        if (Files.exists(path)) {
            Files.walk(path)
                .sorted(Comparator.reverseOrder())
                .forEach(p -> {
                    try {
                        Files.deleteIfExists(p);
                    } catch (IOException e) {
                        LOGGER.debug("Fehler beim Löschen: " + p + " - " + e.getMessage());
                    }
                });
        }
    }

}
