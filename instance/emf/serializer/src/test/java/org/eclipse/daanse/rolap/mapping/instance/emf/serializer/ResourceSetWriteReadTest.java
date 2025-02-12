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

import java.io.IOException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.sql.SQLException;
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
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.Catalog;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.RolapMappingPackage;
import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
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

import com.fasterxml.jackson.core.sym.Name1;

@ExtendWith(BundleContextExtension.class)
@ExtendWith(ServiceExtension.class)
@ExtendWith(ConfigurationExtension.class)
@RequireEMF
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ResourceSetWriteReadTest {

//    @TempDir
    static Path tempDir;

    @BeforeAll
    public static void beforeAll() throws IOException {
        tempDir = Files.createTempDirectory("tutorials");
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
                serializeCatalog(resourceSet, catalogMappingSupplier);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void serializeCatalog(ResourceSet resourceSet, CatalogMappingSupplier catalogMappingSupplier)
            throws IOException {

        CatalogMapping catalogMapping = catalogMappingSupplier.get();
        String name = "" + catalogMapping.getName();
        Path baseDir = Files.createDirectories(tempDir.resolve(name));
        Bundle b = FrameworkUtil.getBundle(catalogMappingSupplier.getClass());

        // List all XML files in the OSGI-INF directory and below
        Enumeration<URL> e = b.findEntries("data", "*.csv", true);

        if (e != null) {

            while (e.hasMoreElements()) {

                URL csvFile = e.nextElement();
                Path p = baseDir.resolve(csvFile.getPath().substring(1));
                Files.createDirectories(p.getParent());
                Files.write(p, csvFile.openStream().readAllBytes(), StandardOpenOption.CREATE);
            }
        }

        CatalogMapping cm = catalogMappingSupplier.get();

        Catalog c = (Catalog) cm;

        Path mappingDir = baseDir.resolve("mapping");
        Files.createDirectories(mappingDir);
        Path fileCatalog = Files.createFile(mappingDir.resolve("catalog.xmi"));
//        Path fileDb = Files.createFile(mappingDir.resolve("db.xmi"));

        URI uriCatalog = URI.createFileURI(fileCatalog.toAbsolutePath().toString());
//        URI uriDb = URI.createFileURI(fileDb.toAbsolutePath().toString());
//        Resource resourceDb = resourceSet.createResource(uriDb);
        Resource resourceCatalog = resourceSet.createResource(uriCatalog);

        Set<EObject> set = new HashSet<>();

        set = allRef(set, c);

        // sort

        List<EObject> sortedList = set.stream().sorted(comparator).toList();

        for (EObject eObject : sortedList) {

            if (eObject.eContainer() == null) {
                resourceCatalog.getContents().add(eObject);
            }

        }

//        resourceDb.getContents().addAll(c.getDbschemas());
//
//        resourceDb.save(Map.of());
        resourceCatalog.save(Map.of());

        System.out.println(baseDir);
        System.out.println(Files.readString(fileCatalog, StandardCharsets.UTF_8));
        System.out.println("=======");
        System.out.println(fileCatalog.toAbsolutePath());
        System.out.println(Files.readString(fileCatalog, StandardCharsets.UTF_8));
        System.out.println("-------");
//        System.out.println(fileDb.toAbsolutePath());
//        System.out.println(Files.readString(fileDb, StandardCharsets.UTF_8));
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

        AtomicInteger COUNTER = new AtomicInteger();
        Map<EClass, Integer> map = new HashMap<EClass, Integer>();

        EObjectComparator() {
            add(RolapMappingPackage.Literals.DATABASE_CATALOG);
            add(RolapMappingPackage.Literals.PHYSICAL_TABLE);
            add(RolapMappingPackage.Literals.VIEW_TABLE);
            add(RolapMappingPackage.Literals.SYSTEM_TABLE);
            add(RolapMappingPackage.Literals.SQL_VIEW);
            add(RolapMappingPackage.Literals.COLUMN);

            add(RolapMappingPackage.Literals.SQL_EXPRESSION);
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

            add(RolapMappingPackage.Literals.CATALOG);

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

}
