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
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Dictionary;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

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
import org.osgi.framework.ServiceReference;
import org.osgi.test.common.annotation.InjectBundleContext;
import org.osgi.test.common.annotation.InjectService;
import org.osgi.test.common.service.ServiceAware;
import org.osgi.test.junit5.cm.ConfigurationExtension;
import org.osgi.test.junit5.context.BundleContextExtension;
import org.osgi.test.junit5.service.ServiceExtension;

@ExtendWith(BundleContextExtension.class)
@ExtendWith(ServiceExtension.class)
@ExtendWith(ConfigurationExtension.class)
@RequireEMF
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ResourceSetWriteReadTest {

    public static final String TEXT = """
            # 📘 Introduction: Data Cube Modeling Tutorials

            Welcome to our comprehensive tutorial series on data cube modeling. This collection is designed to guide you step by step through the core concepts and techniques used in modeling complex analytical data structures. Whether you're building a business intelligence solution or working on an OLAP engine, a solid understanding of the data cube, catalogs, schemas, and OLAP elements is essential.

            We start with the relational foundation – modeling catalogs, schemas, tables, and columns in a way that mirrors typical database systems. This layer serves as the cornerstone for everything that follows.

            From there, we transition into the world of OLAP modeling:
            You’ll learn how to define cubes, and how to enrich them with dimensions, hierarchies, and levels that reflect your business structure and enable powerful multidimensional queries.

            We strongly recommend beginning with the database tutorial, as it introduces the core data model that most of the other tutorials build upon. Before diving into advanced topics, it’s useful to revisit the introductions in each section, as they often highlight key transitions and modeling decisions.

            The Tutorials in the `unstructured` section agre bare Mapping descriptions and example files. They are designed for advaned users who are already familiar with the basics of data cube modeling. These tutorials provide a deeper dive into specific topics, showcasing advanced techniques and best practices for creating efficient and effective data models. Feel free to add Tutorial description and structure to this Tutotials.

            A recommended reading order is provided below to help you build your understanding progressively and systematically.

                        """;
    static int i = 0;
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
            @InjectService ServiceAware<CatalogMappingSupplier> mappingSuppiersSA)
            throws SQLException, InterruptedException, IOException {

        try {

            List<ServiceReference<CatalogMappingSupplier>> srs = mappingSuppiersSA.getServiceReferences();
            StringBuilder parentReadme = new StringBuilder();
            parentReadme.append(TEXT);

            // Create combined ZIP directory structure
            Path zipDir = Files.createDirectories(tempDir.resolve("cubeserver/tutorial/zip"));
            ZipOutputStream combinedZos = new ZipOutputStream(new FileOutputStream(zipDir.resolve("all-tutorials.zip").toFile()));

            srs.sort((o1, o2) -> {
                Object s1 = o1.getProperty("number");
                Object s2 = o2.getProperty("number");

                String ss1 = s1 == null ? "9999.9.9" : s1.toString();
                String ss2 = s2 == null ? "9999.9.9" : s2.toString();
                return ss1.compareToIgnoreCase(ss2);
            });
            for (ServiceReference<CatalogMappingSupplier> sr : srs) {

                try {
                    CatalogMappingSupplier catalogMappingSupplier = mappingSuppiersSA.getService(sr);

                    parentReadme.append("\n");

                    serializeCatalog(resourceSet, parentReadme, catalogMappingSupplier, sr.getProperties(), combinedZos);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            
            // Close combined ZIP
            combinedZos.close();
            
            Path rootReadmeFile = Files.createFile(tempDir.resolve("index.md"));
            Files.writeString(rootReadmeFile, parentReadme);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    Map<Documentation, EObject> map = new HashMap<Documentation, EObject>();

    private void serializeCatalog(ResourceSet resourceSet, StringBuilder parentReadme,
            CatalogMappingSupplier catalogMappingSupplier, Dictionary<String, Object> dictionary, ZipOutputStream combinedZos) throws IOException {

        String name = catalogMappingSupplier.getClass().getPackageName();
        name = name.substring(46);

        Path fileReadme = Files.createFile(tempDir.resolve(name + ".md"));

        Path zipDir = Files.createDirectories(tempDir.resolve("cubeserver/tutorial/zip"));
        ZipOutputStream zos = new ZipOutputStream(new FileOutputStream(zipDir.resolve(name + ".zip").toFile()));

        Bundle b = FrameworkUtil.getBundle(catalogMappingSupplier.getClass());

        CatalogMapping cm = catalogMappingSupplier.get();

        StringBuilder sbReadme = new StringBuilder();

        sbReadme.append("---");
        sbReadme.append("\n");

        String grp = (String) dictionary.get("group");
        grp = grp == null ? "Unstrutured" : grp;

        String catName = cm.getName();

        catName = catName.replaceFirst(grp + " - ", "").replaceFirst("Daanse Tutorial - ", "");
        sbReadme.append("title: " + catName);
        sbReadme.append("\n");

        sbReadme.append("group: " + grp);
        sbReadme.append("\n");

        String kind = (String) dictionary.get("kind");
        kind = kind == null ? "other" : kind;
        sbReadme.append("kind: " + kind);
        sbReadme.append("\n");

        String nr = (String) dictionary.get("number");
        nr = nr == null ? "z" + i : nr;
        sbReadme.append("number: " + nr);
        sbReadme.append("\n");

//        sbReadme.append("source: " + dictionary.get("source"));
//        sbReadme.append("\n");

        sbReadme.append("---");
        sbReadme.append("\n");

        parentReadme.append("\n");
        parentReadme.append("[" + cm.getName().replaceFirst("Daanse Tutorial - ", "") + "](./" + name + ".md)");

        Catalog c = (Catalog) cm;

        URI uriCatalog = URI.createFileURI("catalog.xmi");
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
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        resourceCatalog.save(baos, options);

        ZipEntry entry = new ZipEntry(name + "/mapping/catalog.xmi");
        zos.putNextEntry(entry);
        zos.write(baos.toByteArray());
        zos.closeEntry();
        
        // Add to combined ZIP
        ZipEntry combinedEntry = new ZipEntry(name + "/mapping/catalog.xmi");
        combinedZos.putNextEntry(combinedEntry);
        combinedZos.write(baos.toByteArray());
        combinedZos.closeEntry();
        
        Files.createDirectories(zipDir);

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
                sbReadme.append(" ");
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
                ByteArrayOutputStream baosDummy = new ByteArrayOutputStream();
                r.save(baosDummy, null);

                String cleaned = baosDummy.toString();
                cleaned = cleaned.replace("catalog.xmi#", "");

                cleaned = cleaned.substring(cleaned.indexOf("\n") + 1);
                cleaned = cleaned.replace("xmlns:roma=\"https://www.daanse.org/spec/org.eclipse.daanse.rolap.mapping\"",
                        "");

                cleaned = cleaned.replace("roma:TableQuery #", "#");
                cleaned = cleaned.replace("roma:PhysicalTable #", "#");
                cleaned = cleaned.replace("roma:PhysicalColumn #", "#");
                cleaned = cleaned.replace("roma:JoinQuery #", "#");
                cleaned = cleaned.replace("roma:StandardDimension #", "#");
                cleaned = cleaned.replace("roma:Measure #", "#");

                cleaned = cleaned.replace(" column=\"roma:PhysicalColumn ", " column=\"");
                cleaned = cleaned.replace(" table=\"roma:PhysicalTable ", " table=\"");
                cleaned = cleaned.replace(" primaryKey=\"roma:PhysicalColumn ", " primaryKey=\"");
                cleaned = cleaned.replace(" query=\"roma:TableQuery ", " query=\"");
                cleaned = cleaned.replace(" query=\"roma:JoinQuery ", " query=\"");
                cleaned = cleaned.replace(" key=\"roma:PhysicalColumn ", " key=\"");
                cleaned = cleaned.replace(" nameColumn=\"roma:PhysicalColumn ", " nameColumn=\"");

                cleaned = cleaned.replace(" query=\"roma:JoinQuery ", " query=\"");

                cleaned = cleaned.replace("xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"", "");
                cleaned = cleaned.replace("dummy.xml#", "");
                cleaned = cleaned.replace("\"#", "\"");
                cleaned = cleaned.replace(" #", " ");

                sbReadme.append("```xml");
                sbReadme.append("\n");
                sbReadme.append(cleaned);
                sbReadme.append("\n");
                sbReadme.append("```");
                sbReadme.append("\n");
                sbReadme.append(
                        "*<small>Note: This is only a symbolic example. For the exact definition, see the [Definition](#definition) section.</small>*");

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

        sbReadme.append("```xml");
        sbReadme.append("\n");
        sbReadme.append(baos.toString(StandardCharsets.UTF_8));
        sbReadme.append("\n");
        sbReadme.append("```");
        sbReadme.append("\n");

        sbReadme.append("\n");

        sbReadme.append("\n");

        sbReadme.append("\n");

        sbReadme.append("## Tutorial Zip");
        sbReadme.append("\n");
        sbReadme.append("This files contaisn the data-tables as csv and the mapping as xmi file.");
        sbReadme.append("\n");
        sbReadme.append("\n");
        sbReadme.append("<a href=\"./zip/" + name + ".zip\" download>Download Zip File</a>");
        sbReadme.append("\n");

        // List all XML files in the OSGI-INF directory and below
        Enumeration<URL> eCsvs = b.findEntries("data", "*.csv", true);

        if (eCsvs != null) {

            while (eCsvs.hasMoreElements()) {
                URL csvFile = eCsvs.nextElement();
                byte[] csv = csvFile.openStream().readAllBytes();

                ZipEntry entryCsv = new ZipEntry(name + csvFile.getPath().substring(0));
                zos.putNextEntry(entryCsv);
                zos.write(csv);
                zos.closeEntry();
                
                // Add to combined ZIP
                ZipEntry combinedEntryCsv = new ZipEntry(name + csvFile.getPath().substring(0));
                combinedZos.putNextEntry(combinedEntryCsv);
                combinedZos.write(csv);
                combinedZos.closeEntry();
            }
        }
        Enumeration<URL> keepCsvs = b.findEntries("data", "*.keep", true);
        if (keepCsvs != null) {
            URL keepFile = keepCsvs.nextElement();
            ZipEntry entryKeep = new ZipEntry(name + keepFile.getPath().substring(0));
            zos.putNextEntry(entryKeep);
            zos.closeEntry();
            
            // Add to combined ZIP
            ZipEntry combinedEntryKeep = new ZipEntry(name + keepFile.getPath().substring(0));
            combinedZos.putNextEntry(combinedEntryKeep);
            combinedZos.closeEntry();
        }

        Files.writeString(fileReadme, sbReadme);
        zos.close();
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
            add(RolapMappingPackage.Literals.EXPLICIT_HIERARCHY);
            add(RolapMappingPackage.Literals.PARENT_CHILD_HIERARCHY);

            add(RolapMappingPackage.Literals.STANDARD_DIMENSION);
            add(RolapMappingPackage.Literals.TIME_DIMENSION);

            add(RolapMappingPackage.Literals.NAMED_SET);

            add(RolapMappingPackage.Literals.ACTION);

            add(RolapMappingPackage.Literals.KPI);
            add(RolapMappingPackage.Literals.SUM_MEASURE);
            add(RolapMappingPackage.Literals.MIN_MEASURE);
            add(RolapMappingPackage.Literals.MAX_MEASURE);
            add(RolapMappingPackage.Literals.AVG_MEASURE);
            add(RolapMappingPackage.Literals.COUNT_MEASURE);
            add(RolapMappingPackage.Literals.NONE_MEASURE);
            add(RolapMappingPackage.Literals.CUSTOM_MEASURE);
            add(RolapMappingPackage.Literals.TEXT_AGG_MEASURE);
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

            return s1.toString().compareToIgnoreCase(s2.toString());
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
