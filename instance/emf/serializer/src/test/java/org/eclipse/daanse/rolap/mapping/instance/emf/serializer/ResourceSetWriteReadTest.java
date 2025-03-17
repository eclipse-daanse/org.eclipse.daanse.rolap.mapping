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
package org.eclipse.daanse.rolap.mapping.instance.emf.serializer;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.StandardOpenOption;
import java.nio.file.attribute.BasicFileAttributes;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

import org.eclipse.daanse.rolap.mapping.api.CatalogMappingSupplier;
import org.eclipse.daanse.rolap.mapping.api.model.CatalogMapping;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.AbstractElement;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.Catalog;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.Documentation;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.DocumentedElement;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.RolapMappingPackage;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.xmi.XMLResource;
import org.gecko.emf.osgi.annotation.require.RequireEMF;
import org.gecko.emf.osgi.constants.EMFNamespaces;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.test.common.annotation.InjectBundleContext;
import org.osgi.test.common.annotation.InjectService;
import org.osgi.test.junit5.cm.ConfigurationExtension;
import org.osgi.test.junit5.context.BundleContextExtension;
import org.osgi.test.junit5.service.ServiceExtension;

@ExtendWith(BundleContextExtension.class)
@ExtendWith(ServiceExtension.class)
@ExtendWith(ConfigurationExtension.class)
@RequireEMF
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ResourceSetWriteReadTest {

    static Path tempDir;

    @BeforeAll
    public static void beforeAll() throws IOException {
        tempDir = Path.of("./daansetutorials");
        deleteDirectory(tempDir);
        tempDir = Files.createDirectories(tempDir);
    }

    @Test
    @Order(1)
    public void writePopulation(@InjectBundleContext BundleContext bc,
            @InjectService(cardinality = 1, filter = "(" + EMFNamespaces.EMF_MODEL_NAME + "="
                    + RolapMappingPackage.eNAME + ")") ResourceSet resourceSet,
            @InjectService List<CatalogMappingSupplier> mappingSuppiers)
            throws SQLException, InterruptedException, IOException {

        try {

            for (CatalogMappingSupplier catalogMappingSupplier : mappingSuppiers) {

                try {
                    serializeCatalog(resourceSet, catalogMappingSupplier);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    Map<Documentation, EObject> map = new HashMap<Documentation, EObject>();

    private void serializeCatalog(ResourceSet resourceSet, CatalogMappingSupplier catalogMappingSupplier)
            throws IOException {

        String name = catalogMappingSupplier.getClass().getPackageName();
        name = name.substring(46);
        Path baseDir = Files.createDirectories(tempDir.resolve(name));
        Path dataDir = Files.createDirectories(baseDir.resolve("data"));
        Path keepFile = Files.createFile(dataDir.resolve(".keep"));

        Bundle b = FrameworkUtil.getBundle(catalogMappingSupplier.getClass());

        CatalogMapping cm = catalogMappingSupplier.get();

        Catalog c = (Catalog) cm;

        Path mappingDir = baseDir.resolve("mapping");
        Files.createDirectories(mappingDir);
        Path fileCatalog = Files.createFile(mappingDir.resolve("catalog.xmi"));

        URI uriCatalog = URI.createFileURI(fileCatalog.toAbsolutePath().toString());
        Resource resourceCatalog = resourceSet.createResource(uriCatalog);

        Set<EObject> set = new HashSet<>();

        set = allRef(set, c);

        // sort

        List<EObject> sortedList = set.stream().sorted(comparator).toList();

        List<Documentation> docs = lookupDocumentationms(sortedList);

        for (EObject eObject : sortedList) {

            if (eObject.eContainer() == null) {

                if (!(eObject instanceof Documentation)) {

                    resourceCatalog.getContents().add(eObject);
                }
            }

        }
        Map<Object, Object> options = new HashMap<>();
        options.put(XMLResource.OPTION_ENCODING, "UTF-8");
        resourceCatalog.save(options);

        System.out.println(baseDir);
        System.out.println(Files.readString(fileCatalog, StandardCharsets.UTF_8));
        System.out.println("=======");
        System.out.println(fileCatalog.toAbsolutePath());
        System.out.println(Files.readString(fileCatalog, StandardCharsets.UTF_8));
        System.out.println("-------");

        Path fileReadme = Files.createFile(baseDir.resolve("README.MD"));

        StringBuilder sbReadme = new StringBuilder();

        for (Documentation documentation : docs) {

            String title = documentation.getTitle();

            String body = documentation.getValue();

            int imaj = documentation.getOrderMajor();
            int imin = documentation.getOrderMinor();
            int imic = documentation.getOrderMicro();

            boolean schowCont = documentation.isShowContainer();

            int contain = documentation.getShowContainments();

            if (title != null) {
                sbReadme.append("#");

                if (imin != 0) {
                    sbReadme.append("#");
                }
                if (imic != 0) {
                    sbReadme.append("#");
                }
                sbReadme.append(title);
                sbReadme.append("\n");
                sbReadme.append("\n");

            }

            if (body != null) {
                sbReadme.append(body);
                sbReadme.append("\n");
                sbReadme.append("\n");

            }

            if (schowCont) {

                EObject eoc = EcoreUtil.copy(map.get(documentation));

                removeContentsOfLevel(eoc, contain);

                URI uro = URI.createFileURI("dummy.xml");

                Resource r = resourceSet.createResource(uro);
                r.getContents().add(eoc);
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                r.save(baos, null);

                String cleaned = baos.toString().replace("file:" + fileCatalog.toAbsolutePath().toString(), "");

                cleaned = cleaned.substring(cleaned.indexOf("\n") + 1);
                cleaned = cleaned.replace("xmlns:roma=\"https://www.daanse.org/spec/org.eclipse.daanse.rolap.mapping\"",
                        "");

                cleaned = cleaned.replace("roma:TableQuery #", "#");
                cleaned = cleaned.replace("roma:PhysicalTable #", "#");
                cleaned = cleaned.replace("roma:PhysicalColumn #", "#");
                cleaned = cleaned.replace("roma:Measure #", "#");



                cleaned = cleaned.replace("xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"", "");
                cleaned = cleaned.replace("dummy.xml#", "");
                sbReadme.append("```xmi");
                sbReadme.append("\n");
                sbReadme.append(cleaned);
                sbReadme.append("\n");
                sbReadme.append("```");
                sbReadme.append("\n");
                sbReadme.append("\n");

            }
        }
        sbReadme.append("\n");

        sbReadme.append("## Definition");
        sbReadme.append("\n");
        sbReadme.append("\n");
        sbReadme.append("This files represent the complete definition of the catalog.");

        sbReadme.append("\n");
        sbReadme.append("\n");

        sbReadme.append("```xmi");
        sbReadme.append("\n");
        sbReadme.append(Files.readString(fileCatalog, StandardCharsets.UTF_8));
        sbReadme.append("\n");
        sbReadme.append("```");
        sbReadme.append("\n");

        sbReadme.append("## csv data");
        sbReadme.append("\n");
        sbReadme.append("\n");

        sbReadme.append("\n");
        sbReadme.append("This files represent the data in the tables.");
        sbReadme.append("\n");

        sbReadme.append("\n");

        // List all XML files in the OSGI-INF directory and below
        Enumeration<URL> eCsvs = b.findEntries("data", "*.csv", true);

        if (eCsvs != null) {

            while (eCsvs.hasMoreElements()) {
                URL csvFile = eCsvs.nextElement();
                byte[] csv = csvFile.openStream().readAllBytes();

                Path p = baseDir.resolve(csvFile.getPath().substring(1));
                Files.createDirectories(p.getParent());
                Files.write(p, csv, StandardOpenOption.CREATE);

                String filename = p.getName(p.getNameCount() - 1).toString();
                sbReadme.append("- [" + filename.replace(".csv", "") + "](./data/" + filename + ")");
                sbReadme.append("\n");
                sbReadme.append("\n");

//            sbReadme.append("```csv");
//            sbReadme.append("\n");
//            sbReadme.append(new String(csv));
//            sbReadme.append("\n");
//            sbReadme.append("```");
//            sbReadme.append("\n");
//        System.out.println(fileDb.toAbsolutePath());
//        System.out.println(Files.readString(fileDb, StandardCharsets.UTF_8));
            }
        }
        Files.writeString(fileReadme, sbReadme);
    }

    private void removeContentsOfLevel(EObject eoc, int contain) {

        EList<EReference> it = eoc.eClass().getEAllContainments();

        for (EReference eReference : it) {

            if (contain > 0) {

                for (EObject eo : eReference.eCrossReferences()) {
                    removeContentsOfLevel(eo, contain - 1);
                }
            } else {

//                for (EObject eObject : eReference.eContents()) {
                eoc.eUnset(eReference);
//                }

            }
        }
    }

    private List<Documentation> lookupDocumentationms(List<EObject> sortedList) {

        List<Documentation> docs = new ArrayList<Documentation>();

        for (EObject eo : sortedList) {
            if (eo instanceof DocumentedElement de) {
                for (Documentation documentation : de.getDocumentations()) {
                    map.put(documentation, de);
                    docs.add(documentation);

                }
                de.getDocumentations().clear();
            }
            List<EObject> list = new ArrayList<EObject>();

            eo.eAllContents().forEachRemaining(list::add);
            for (EObject obj : list) {
                if (obj instanceof AbstractElement ae) {
                    for (Documentation documentation : ae.getDocumentations()) {
                        map.put(documentation, ae);
                        docs.add(documentation);
                    }
                    ae.getDocumentations().clear();
                }
            }
        }

        docs.sort(new Comparator<Documentation>() {

            @Override
            public int compare(Documentation o1, Documentation o2) {

                int imaj = o1.getOrderMajor() - o2.getOrderMajor();
                if (imaj != 0) {
                    return imaj;
                }

                int imin = o1.getOrderMinor() - o2.getOrderMinor();
                if (imin != 0) {
                    return imin;
                }

                int imic = o1.getOrderMicro() - o2.getOrderMicro();
                if (imic != 0) {
                    return imic;
                }
                return 0;
            }
        });
        return docs;
    }

    private Set<EObject> allRef(Set<EObject> set, EObject eObject) {

        if (set.add(eObject)) {

            TreeIterator<EObject> allContents = eObject.eAllContents();
            while (allContents.hasNext()) {
                EObject obj = allContents.next();

                set = allRef(set, obj);
            }

            for (EObject eObject2 : eObject.eCrossReferences()) {

                set = allRef(set, eObject2);

            }
            EObject eContainer = eObject.eContainer();

            if (eContainer != null) {
                set = allRef(set, eContainer);

            }

        }
        return set;
    }

    static EObjectComparator comparator = new EObjectComparator();

    static class EObjectComparator implements Comparator<EObject> {

        AtomicInteger COUNTER = new AtomicInteger(1);
        Map<EClass, Integer> map = new HashMap<EClass, Integer>();

        EObjectComparator() {
            add(RolapMappingPackage.Literals.CATALOG);
            add(RolapMappingPackage.Literals.DATABASE_CATALOG);

            add(RolapMappingPackage.Literals.DATABASE_SCHEMA);

            add(RolapMappingPackage.Literals.PHYSICAL_TABLE);
            add(RolapMappingPackage.Literals.VIEW_TABLE);
            add(RolapMappingPackage.Literals.SYSTEM_TABLE);
            add(RolapMappingPackage.Literals.SQL_VIEW);
            add(RolapMappingPackage.Literals.COLUMN);
            add(RolapMappingPackage.Literals.PHYSICAL_COLUMN);

            add(RolapMappingPackage.Literals.SQL_EXPRESSION_COLUMN);
            add(RolapMappingPackage.Literals.SQL_SELECT_QUERY);
            add(RolapMappingPackage.Literals.SQL_STATEMENT);

            add(RolapMappingPackage.Literals.TABLE_QUERY);
            add(RolapMappingPackage.Literals.IINLINE_TABLE_QUERY);
            add(RolapMappingPackage.Literals.JOIN_QUERY);
            add(RolapMappingPackage.Literals.JOINED_QUERY_ELEMENT);

            add(RolapMappingPackage.Literals.CALCULATED_MEMBER);

            add(RolapMappingPackage.Literals.LEVEL);
            add(RolapMappingPackage.Literals.HIERARCHY);
            add(RolapMappingPackage.Literals.STANDARD_DIMENSION);
            add(RolapMappingPackage.Literals.TIME_DIMENSION);

            add(RolapMappingPackage.Literals.NAMED_SET);

            add(RolapMappingPackage.Literals.ACTION);

            add(RolapMappingPackage.Literals.KPI);
            add(RolapMappingPackage.Literals.MEASURE);
            add(RolapMappingPackage.Literals.MEASURE_GROUP);

            add(RolapMappingPackage.Literals.PHYSICAL_CUBE);

            add(RolapMappingPackage.Literals.CUBE_CONNECTOR);

            add(RolapMappingPackage.Literals.VIRTUAL_CUBE);

            add(RolapMappingPackage.Literals.ACCESS_ROLE);
            add(RolapMappingPackage.Literals.ACCESS_CATALOG_GRANT);
            add(RolapMappingPackage.Literals.ACCESS_CUBE_GRANT);
            add(RolapMappingPackage.Literals.ACCESS_DIMENSION_GRANT);
            add(RolapMappingPackage.Literals.ACCESS_HIERARCHY_GRANT);
            add(RolapMappingPackage.Literals.ACCESS_MEMBER_GRANT);

            add(RolapMappingPackage.Literals.CELL_FORMATTER);

        }

        void add(EClass eClass) {
            map.put(eClass, COUNTER.incrementAndGet());
        }

        @Override
        public int compare(EObject o1, EObject o2) {

            EClass eClass1 = o1.eClass();
            EClass eClass2 = o2.eClass();
            int value = map.getOrDefault(eClass1, 0) - map.getOrDefault(eClass2, 0);

            if (value != 0) {
                return value;
            }

            Object s1 = "";
            Object s2 = "";
            EStructuralFeature eStructuralFeature1 = eClass1.getEStructuralFeature("id");
            if (eStructuralFeature1 != null) {

                s1 = o1.eGet(eStructuralFeature1);
            }
            EStructuralFeature eStructuralFeature2 = eClass2.getEStructuralFeature("id");
            if (eStructuralFeature2 != null) {

                s2 = o2.eGet(eStructuralFeature2);
            }
            if (s1 == null) {
                s1 = "";
            }
            if (s2 == null) {
                s2 = "";
            }

            return s1.toString().compareToIgnoreCase(s1.toString());
        }
    };

    public static void deleteDirectory(Path directory) throws IOException {
        if (!Files.exists(directory)) {
            return;
        }

        Files.walkFileTree(directory, new SimpleFileVisitor<Path>() {
            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                Files.delete(file);
                return FileVisitResult.CONTINUE;
            }

            @Override
            public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
                Files.delete(dir);
                return FileVisitResult.CONTINUE;
            }
        });
    }
}
