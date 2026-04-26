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
package org.eclipse.daanse.rolap.mapping.instance.emf.serializer.integration;

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

import org.eclipse.daanse.rolap.mapping.instance.api.CatalogRef;
import org.eclipse.daanse.rolap.mapping.instance.api.DocSection;
import org.eclipse.daanse.rolap.mapping.instance.api.TutorialDescription;
import org.eclipse.daanse.rolap.mapping.instance.api.TutorialDescriptionSupplier;
import org.eclipse.daanse.rolap.mapping.model.provider.CatalogMappingSupplier;
import org.eclipse.daanse.olap.check.model.check.OlapCheckPackage;
import org.eclipse.daanse.olap.check.model.check.OlapCheckSuite;
import org.eclipse.daanse.olap.check.runtime.api.OlapCheckSuiteSupplier;
import org.eclipse.daanse.rolap.mapping.model.catalog.Catalog;
import org.eclipse.daanse.rolap.mapping.model.RolapMappingPackage;
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
import org.eclipse.fennec.emf.osgi.annotation.require.RequireEMF;
import org.eclipse.fennec.emf.osgi.constants.EMFNamespaces;
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

import org.eclipse.daanse.rolap.mapping.model.access.common.CommonPackage;
import org.eclipse.daanse.rolap.mapping.model.access.olap.OlapPackage;
import org.eclipse.daanse.rolap.mapping.model.catalog.CatalogPackage;
import org.eclipse.daanse.rolap.mapping.model.database.source.SourcePackage;
import org.eclipse.daanse.rolap.mapping.model.olap.cube.CubePackage;
import org.eclipse.daanse.rolap.mapping.model.olap.cube.action.ActionPackage;
import org.eclipse.daanse.rolap.mapping.model.olap.cube.CubePackage;
import org.eclipse.daanse.rolap.mapping.model.olap.cube.measure.MeasurePackage;
import org.eclipse.daanse.rolap.mapping.model.olap.dimension.DimensionPackage;
import org.eclipse.daanse.rolap.mapping.model.olap.dimension.hierarchy.HierarchyPackage;
import org.eclipse.daanse.rolap.mapping.model.olap.dimension.hierarchy.level.LevelPackage;
import org.eclipse.daanse.rolap.mapping.model.olap.format.FormatPackage;
import org.eclipse.daanse.rolap.mapping.model.olap.dimension.DimensionPackage;
import org.eclipse.daanse.cwm.util.resource.relational.SqlSimpleTypes;
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

            Download the full [Tutorial-Package](./zip/all-tutorials.zip) as zip.

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
            @InjectService(cardinality = 1, filter = "(" + EMFNamespaces.EMF_MODEL_NSURI + "="
                    + RolapMappingPackage.eNS_URI + ")") ResourceSet resourceSet,
            @InjectService ServiceAware<CatalogMappingSupplier> mappingSuppiersSA,
            @InjectService ServiceAware<OlapCheckSuiteSupplier> checkSuiteSuppliersSA)
            throws SQLException, InterruptedException, IOException {

        try {

            List<ServiceReference<CatalogMappingSupplier>> srs = mappingSuppiersSA.getServiceReferences();
            List<ServiceReference<OlapCheckSuiteSupplier>> chrs = checkSuiteSuppliersSA.getServiceReferences();
            StringBuilder parentReadme = new StringBuilder();
            parentReadme.append(TEXT);

            // Build check suite lookup map keyed by derived name
            Map<String, OlapCheckSuiteSupplier> checkSuiteByName = new HashMap<>();
            for (ServiceReference<OlapCheckSuiteSupplier> chs : chrs) {
                OlapCheckSuiteSupplier supplier = checkSuiteSuppliersSA.getService(chs);
                String csName = supplier.getClass().getPackageName().substring(46);
                checkSuiteByName.put(csName, supplier);
            }

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
                    String name = catalogMappingSupplier.getClass().getPackageName().substring(46);
                    OlapCheckSuiteSupplier matchingCheckSuite = checkSuiteByName.get(name);

                    parentReadme.append("\n");

                    serializeCatalog(resourceSet, parentReadme, catalogMappingSupplier, sr.getProperties(), combinedZos, matchingCheckSuite);
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

    Map<TutorialDescriptionSupplier, TutorialDescription> tutorialDescriptions = new HashMap<>();

    private void serializeCatalog(ResourceSet resourceSet, StringBuilder parentReadme,
            CatalogMappingSupplier catalogMappingSupplier, Dictionary<String, Object> dictionary, ZipOutputStream combinedZos, OlapCheckSuiteSupplier checkSuiteSupplier) throws IOException {

        String name = catalogMappingSupplier.getClass().getPackageName();
        name = name.substring(46);

        Path fileReadme = Files.createFile(tempDir.resolve(name + ".md"));

        Path zipDir = Files.createDirectories(tempDir.resolve("cubeserver/tutorial/zip"));
        ZipOutputStream zos = new ZipOutputStream(new FileOutputStream(zipDir.resolve(name + ".zip").toFile()));

        Bundle b = FrameworkUtil.getBundle(catalogMappingSupplier.getClass());

        Catalog cm = catalogMappingSupplier.get();

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
        parentReadme.append("\n");


        Catalog c = (Catalog) cm;

        URI uriCatalog = URI.createFileURI("catalog.xmi");
        Resource resourceCatalog = resourceSet.createResource(uriCatalog);

        Set<EObject> set = new HashSet<>();

        set = allRef(set, c);

        // sort

        List<EObject> sortedList = set.stream().sorted(comparator).toList();

        List<DocSection> docs = lookupDocSections(catalogMappingSupplier);

        // Deduplicate SQLSimpleType instances with identical semantics (same
        // name + length + precision + scale). CatalogSupplier code calls
        // SqlSimpleTypes.Sql99.varcharType() once per column, so many freshly-built
        // instances describe e.g. VARCHAR(255) redundantly. Collapse them to a
        // single shared instance per catalog before serialization.
        sortedList = deduplicateSqlTypes(sortedList);

        for (EObject eObject : sortedList) {
            if (eObject.eContainer() == null) {
                resourceCatalog.getContents().add(eObject);
            }
        }
        // Assign human-readable XMI IDs instead of EMF's default positional
        // fragment paths (e.g. /21/@ownedElement.18/@feature.0). IDs are derived
        // from the element type + name so cross-references render as
        // `xmi:id="_Table_TableOne"` / `type="_SQLSimpleType_VARCHAR_255"`.
        if (resourceCatalog instanceof org.eclipse.emf.ecore.xmi.XMIResource xmi) {
            assignXmiIds(xmi, sortedList);
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

        for (DocSection documentation : docs) {

            String title = documentation.title();

            String body = documentation.body();

            int imaj = documentation.orderMajor();
            int imin = documentation.orderMinor();
            int imic = documentation.orderMicro();

            boolean schowCont = documentation.elementRef() != null;

            int contain = documentation.showContainmentDepth();

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

                EObject eoc = EcoreUtil.copy(documentation.elementRef());
                removeContentsOfLevel(eoc, contain);

                // Pull cross-referenced "context leaves" (Columns, Tables,
                // Sources, Dimensions, Hierarchies, Levels, SQLSimpleTypes)
                // into the snippet so readers see the referenced definitions
                // inline, not just href="…" stubs. Fixed-point iteration so
                // e.g. PhysicalCube -> TableSource -> Table -> Column -> Type
                // all get pulled along.
                Map<EObject, EObject> pulled = new java.util.IdentityHashMap<>();
                Set<EObject> pulledCopies = java.util.Collections.newSetFromMap(new java.util.IdentityHashMap<>());
                for (int pass = 0; pass < 5; pass++) {
                    List<EObject> frontier = new ArrayList<>();
                    frontier.add(eoc);
                    for (EObject pc : pulled.values()) frontier.add(pc);
                    boolean added = false;
                    for (EObject node : frontier) {
                        List<EObject> walk = new ArrayList<>();
                        walk.add(node);
                        for (TreeIterator<EObject> ni = node.eAllContents(); ni.hasNext();) walk.add(ni.next());
                        for (EObject n : walk) {
                            for (org.eclipse.emf.ecore.EReference ref : n.eClass().getEAllReferences()) {
                                if (ref.isContainment() || ref.isContainer()) continue;
                                if (ref.isMany()) {
                                    @SuppressWarnings("unchecked")
                                    List<EObject> targets = (List<EObject>) n.eGet(ref);
                                    for (int i = 0; i < targets.size(); i++) {
                                        EObject t = targets.get(i);
                                        EObject swap = pullContextLeaf(t, pulled, pulledCopies);
                                        if (swap != null && swap != t) {
                                            targets.set(i, swap);
                                            added = true;
                                        }
                                    }
                                } else {
                                    Object v = n.eGet(ref);
                                    if (v instanceof EObject t) {
                                        EObject swap = pullContextLeaf(t, pulled, pulledCopies);
                                        if (swap != null && swap != t) {
                                            n.eSet(ref, swap);
                                            added = true;
                                        }
                                    }
                                }
                            }
                        }
                    }
                    if (!added) break;
                }

                URI uro = URI.createFileURI("dummy.xmi");

                Resource r = resourceSet.createResource(uro);
                r.getContents().add(eoc);
                for (EObject cpy : pulled.values()) {
                    if (cpy.eContainer() == null) {
                        r.getContents().add(cpy);
                    }
                }
                if (r instanceof org.eclipse.emf.ecore.xmi.XMIResource xmir) {
                    List<EObject> allIn = new ArrayList<>();
                    for (EObject root : r.getContents()) {
                        allIn.add(root);
                        TreeIterator<EObject> ait = root.eAllContents();
                        while (ait.hasNext()) allIn.add(ait.next());
                    }
                    assignXmiIds(xmir, allIn);
                }
                // Drop cross-refs pointing to detached objects (from pruning),
                // otherwise EMF throws "not contained in a resource" on save.
                Map<Object, Object> snippetOpts = new HashMap<>();
                snippetOpts.put(XMLResource.OPTION_PROCESS_DANGLING_HREF,
                        XMLResource.OPTION_PROCESS_DANGLING_HREF_DISCARD);
                ByteArrayOutputStream baosDummy = new ByteArrayOutputStream();
                try {
                    r.save(baosDummy, snippetOpts);
                } catch (Exception snippetEx) {
                    resourceSet.getResources().remove(r);
                    sbReadme.append("*<small>(Snippet not renderable: ")
                            .append(snippetEx.getClass().getSimpleName())
                            .append(")</small>*\n\n");
                    continue;
                }
                resourceSet.getResources().remove(r);

                String cleaned = baosDummy.toString();
                cleaned = cleaned.replace("catalog.xmi#", "");

                cleaned = cleaned.substring(cleaned.indexOf("\n") + 1);
                cleaned = cleaned.replace("xmlns:roma=\"https://www.daanse.org/spec/org.eclipse.daanse.rolap.mapping\"",
                        "");

                cleaned = cleaned.replace("roma:TableSource #", "#");
                cleaned = cleaned.replace("roma:JoinSource #", "#");
                cleaned = cleaned.replace("roma:InlineTableSource #", "#");
                cleaned = cleaned.replace("roma:SqlSelectSource #", "#");
                cleaned = cleaned.replace("roma:StandardDimension #", "#");
                cleaned = cleaned.replace("roma:Measure #", "#");
                // CWM relational types (prefix already shortened above)
                cleaned = cleaned.replace("cwmRel:Table #", "#");
                cleaned = cleaned.replace("cwmRel:View #", "#");
                cleaned = cleaned.replace("cwmRel:Column #", "#");
                cleaned = cleaned.replace("cwmRel:Schema #", "#");

                cleaned = cleaned.replace(" column=\"cwmRel:Column ", " column=\"");
                cleaned = cleaned.replace(" table=\"cwmRel:Table ", " table=\"");
                cleaned = cleaned.replace(" primaryKey=\"cwmRel:Column ", " primaryKey=\"");
                cleaned = cleaned.replace(" source=\"roma:TableSource ", " source=\"");
                cleaned = cleaned.replace(" source=\"roma:JoinSource ", " source=\"");
                cleaned = cleaned.replace(" key=\"cwmRel:Column ", " key=\"");
                cleaned = cleaned.replace(" nameColumn=\"cwmRel:Column ", " nameColumn=\"");

                cleaned = cleaned.replace("xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"", "");
                cleaned = cleaned.replace("dummy.xmi#", "");
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
        sbReadme.append("This file represents the complete definition of the catalog.");

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
        sbReadme.append("This file contains the data-tables as csv and the mapping as xmi file.");
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

        // Serialize check suite into the same ZIP if available
        if (checkSuiteSupplier != null) {
            OlapCheckSuite chs = checkSuiteSupplier.get();

            URI uriCheckSuite = URI.createFileURI("checkSuite.xmi");
            Resource resourceCheckSuite = resourceSet.createResource(uriCheckSuite);

            Set<EObject> checkSet = new HashSet<>();
            checkSet = allRef(checkSet, chs);

            List<EObject> sortedCheckList = checkSet.stream().sorted(checkSuiteComparator).toList();

            for (EObject eObject : sortedCheckList) {
                if (eObject.eContainer() == null) {
                    resourceCheckSuite.getContents().add(eObject);
                }
            }
            Map<Object, Object> checkOptions = new HashMap<>();
            checkOptions.put(XMLResource.OPTION_ENCODING, "UTF-8");
            ByteArrayOutputStream checkBaos = new ByteArrayOutputStream();
            resourceCheckSuite.save(checkBaos, checkOptions);

            ZipEntry checkEntry = new ZipEntry(name + "/check/checkSuite.xmi");
            zos.putNextEntry(checkEntry);
            zos.write(checkBaos.toByteArray());
            zos.closeEntry();

            ZipEntry combinedCheckEntry = new ZipEntry(name + "/check/checkSuite.xmi");
            combinedZos.putNextEntry(combinedCheckEntry);
            combinedZos.write(checkBaos.toByteArray());
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

    /**
     * Returns the tutorial's narrative sections. Looks the supplier up as
     * TutorialDescriptionSupplier (if it also implements that interface).
     * Returns an empty list if the supplier doesn't expose a description.
     */
    private List<DocSection> lookupDocSections(CatalogMappingSupplier catalogMappingSupplier) {
        if (!(catalogMappingSupplier instanceof TutorialDescriptionSupplier tds)) {
            return List.of();
        }
        TutorialDescription description = tds.describe();
        tutorialDescriptions.put(tds, description);
        List<DocSection> docs = new ArrayList<>(description.sections());
        docs.sort(Comparator
                .comparingInt(DocSection::orderMajor)
                .thenComparingInt(DocSection::orderMinor)
                .thenComparingInt(DocSection::orderMicro));
        return docs;
    }

    /**
     * Assigns stable, human-readable XMI IDs to elements that are serialized as
     * root entries of the Resource — and to their contained features — so that
     * the XMI's {@code xmi:id} / cross-reference {@code href} values are
     * readable (e.g. {@code _Table_Sales}) instead of EMF's default positional
     * fragment paths (e.g. {@code /21/@ownedElement.18}).
     */
    private void assignXmiIds(org.eclipse.emf.ecore.xmi.XMIResource xmi, List<EObject> elements) {
        Map<String, Integer> collisions = new HashMap<>();
        for (EObject eo : elements) {
            String id = idFor(eo);
            if (id == null) {
                continue;
            }
            Integer n = collisions.get(id);
            if (n != null) {
                n = n + 1;
                collisions.put(id, n);
                id = id + "_" + n;
            } else {
                collisions.put(id, 0);
            }
            xmi.setID(eo, id);
        }
    }

    /**
     * Classes whose instances are small enough to inline into a snippet when
     * cross-referenced from the displayed element. The returned copy (map of
     * original to its pulled copy) is what callers should rewire the ref to.
     */
    // Only types whose whole tree can be inlined with reasonable size.
    // Column is intentionally excluded — Columns are inlined via their
    // containing Table; pulling Column directly yields disconnected duplicates.
    private static final Set<String> CONTEXT_LEAF_TYPES = Set.of(
            "Table", "View", "SQLSimpleType",
            "InlineTable", "DialectSqlView",
            "TableSource", "SqlSelectSource", "JoinSource", "InlineTableSource",
            "StandardDimension", "TimeDimension",
            "ExplicitHierarchy", "ParentChildHierarchy",
            "Level");

    /** Returns a copy of {@code orig} (creating one if needed) when the type is
     *  a "context leaf" worth inlining; otherwise returns null. Also records
     *  contained-descendant original->copy pairs in {@code pulled} so later
     *  cross-refs to those descendants resolve to the pulled copy. Returns
     *  {@code orig} unchanged if it's already a pulled copy (prevents
     *  copy-of-copy duplicates in the fixed-point loop). */
    private EObject pullContextLeaf(EObject orig, Map<EObject, EObject> pulled,
            Set<EObject> copiesSet) {
        if (orig == null) return null;
        if (copiesSet.contains(orig)) return orig;
        EObject existing = pulled.get(orig);
        if (existing != null) return existing;
        if (!CONTEXT_LEAF_TYPES.contains(orig.eClass().getName())) return null;
        EcoreUtil.Copier copier = new EcoreUtil.Copier();
        EObject copy = copier.copy(orig);
        copier.copyReferences();
        for (Map.Entry<EObject, EObject> e : copier.entrySet()) {
            pulled.putIfAbsent(e.getKey(), e.getValue());
            copiesSet.add(e.getValue());
        }
        return copy;
    }

    /** Build a short human-readable, lowercase id for an EObject. */
    private String idFor(EObject eo) {
        // Prefer an explicit Daanse id attribute if present and non-blank.
        org.eclipse.emf.ecore.EStructuralFeature idFeat = eo.eClass().getEStructuralFeature("id");
        if (idFeat != null) {
            Object idVal = eo.eGet(idFeat);
            if (idVal instanceof String s && !s.isBlank()) {
                return (s.startsWith("_") ? s : "_" + s).toLowerCase();
            }
        }
        String typeName = eo.eClass().getName();
        org.eclipse.emf.ecore.EStructuralFeature nameFeat = eo.eClass().getEStructuralFeature("name");
        Object nameVal = nameFeat != null ? eo.eGet(nameFeat) : null;
        String suffix;
        if (nameVal instanceof String s && !s.isBlank()) {
            suffix = s;
        } else {
            suffix = anonymousSuffix(eo);
        }
        if (suffix == null || suffix.isBlank()) {
            return ("_" + typeName).toLowerCase();
        }
        // For Columns, prefix the suffix with the containing Table's (and its
        // Schema's) name so the id is unique across the catalog without
        // needing numeric collision suffixes: `_column_<schema>_<table>_<col>`.
        if ("Column".equals(typeName)) {
            suffix = qualifyWithContainers(eo, suffix);
        }
        // Strip the verbose "Daanse Tutorial - " / "Group - " prefix that
        // every tutorial catalog carries in its name, to keep xmi:id short.
        suffix = suffix.replaceFirst("(?i)^daanse\\s*tutorial\\s*-\\s*", "");
        suffix = suffix.replaceAll("[^A-Za-z0-9_]", "_");
        suffix = suffix.replaceAll("_+", "_").replaceAll("^_|_$", "");
        return ("_" + typeName + "_" + suffix).toLowerCase();
    }

    /** Prepends {@code <schema>_<table>_} (skipping anonymous containers)
     *  to the already-computed {@code suffix}, walking up the Column's
     *  containment chain until a Catalog is reached. */
    private String qualifyWithContainers(EObject eo, String suffix) {
        List<String> parts = new ArrayList<>();
        EObject ctr = eo.eContainer();
        while (ctr != null && !"Catalog".equals(ctr.eClass().getName())) {
            EStructuralFeature nf = ctr.eClass().getEStructuralFeature("name");
            if (nf != null && ctr.eGet(nf) instanceof String s && !s.isBlank()) {
                parts.add(0, s);
            }
            ctr = ctr.eContainer();
        }
        if (parts.isEmpty()) return suffix;
        return String.join("_", parts) + "_" + suffix;
    }

    /**
     * Derives a readable id suffix for an element without a `name`. Prefers a
     * meaningful cross-reference (e.g. a *Source's `table` → table name, a
     * DimensionConnector's `dimension` → dimension name), otherwise returns
     * null so the caller uses just the type name and lets the collision
     * handler disambiguate with a short numeric suffix (_schema, _schema_1…).
     */
    private String anonymousSuffix(EObject eo) {
        // Known-interesting name-like string attributes first.
        for (String attr : new String[]{"overrideDimensionName", "uniqueName", "title", "label"}) {
            EStructuralFeature f = eo.eClass().getEStructuralFeature(attr);
            if (f != null && eo.eGet(f) instanceof String s && !s.isBlank()) {
                return s;
            }
        }
        // Known-interesting single-valued references, in priority order.
        for (String refName : new String[]{
                "table", "view", "dimension", "hierarchy", "cube",
                "primaryKey", "foreignKey", "column", "key", "query"}) {
            EStructuralFeature refFeat = eo.eClass().getEStructuralFeature(refName);
            if (refFeat instanceof org.eclipse.emf.ecore.EReference ref
                    && !ref.isMany() && !ref.isContainment()) {
                Object v = eo.eGet(ref);
                if (v instanceof EObject target) {
                    EStructuralFeature tnf = target.eClass().getEStructuralFeature("name");
                    if (tnf != null && target.eGet(tnf) instanceof String s && !s.isBlank()) {
                        return s;
                    }
                }
            }
        }
        return null;
    }

    /**
     * Collapses semantically-identical {@link org.eclipse.daanse.cwm.model.cwm.resource.relational.SQLSimpleType}
     * instances in {@code sortedList} into a single shared instance per type
     * signature (name + length + precision + scale + radix). All columns that
     * referenced a duplicate are rewired to the canonical instance. The
     * returned list has the redundant SQLSimpleTypes removed.
     */
    private List<EObject> deduplicateSqlTypes(List<EObject> sortedList) {
        Map<String, org.eclipse.daanse.cwm.model.cwm.resource.relational.SQLSimpleType> canonical = new HashMap<>();
        Map<org.eclipse.daanse.cwm.model.cwm.resource.relational.SQLSimpleType,
                org.eclipse.daanse.cwm.model.cwm.resource.relational.SQLSimpleType> remap = new HashMap<>();
        for (EObject eo : sortedList) {
            if (!(eo instanceof org.eclipse.daanse.cwm.model.cwm.resource.relational.SQLSimpleType t)) {
                continue;
            }
            String key = t.getName() + "|" + t.getCharacterMaximumLength() + "|"
                    + t.getNumericPrecision() + "|" + t.getNumericScale() + "|"
                    + t.getNumericPrecisionRadix() + "|" + t.getDateTimePrecision();
            org.eclipse.daanse.cwm.model.cwm.resource.relational.SQLSimpleType c = canonical.get(key);
            if (c == null) {
                canonical.put(key, t);
            } else if (c != t) {
                remap.put(t, c);
            }
        }
        if (remap.isEmpty()) {
            return sortedList;
        }
        // Rewire Column.type (and any other cross-refs) to the canonical instance.
        for (EObject eo : sortedList) {
            for (org.eclipse.emf.ecore.EReference ref : eo.eClass().getEAllReferences()) {
                if (ref.isContainment() || ref.isContainer()) {
                    continue;
                }
                if (ref.isMany()) {
                    @SuppressWarnings("unchecked")
                    java.util.List<EObject> refList = (java.util.List<EObject>) eo.eGet(ref);
                    for (int i = 0; i < refList.size(); i++) {
                        EObject target = refList.get(i);
                        if (target instanceof org.eclipse.daanse.cwm.model.cwm.resource.relational.SQLSimpleType tt
                                && remap.containsKey(tt)) {
                            refList.set(i, remap.get(tt));
                        }
                    }
                } else {
                    Object target = eo.eGet(ref);
                    if (target instanceof org.eclipse.daanse.cwm.model.cwm.resource.relational.SQLSimpleType tt
                            && remap.containsKey(tt)) {
                        eo.eSet(ref, remap.get(tt));
                    }
                }
            }
        }
        List<EObject> filtered = new ArrayList<>(sortedList.size() - remap.size());
        for (EObject eo : sortedList) {
            if (!remap.containsKey(eo)) {
                filtered.add(eo);
            }
        }
        return filtered;
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
            add(CatalogPackage.Literals.CATALOG);
            add(CatalogPackage.Literals.DATABASE_CATALOG);

            // CWM relational types
            add(org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalPackage.Literals.SCHEMA);
            add(org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalPackage.Literals.TABLE);
            add(org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalPackage.Literals.VIEW);
            add(org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalPackage.Literals.COLUMN);
            add(org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalPackage.Literals.FOREIGN_KEY);
            add(org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalPackage.Literals.PRIMARY_KEY);

            // Daanse extensions
            add(org.eclipse.daanse.rolap.mapping.model.database.relational.RelationalPackage.Literals.DIALECT_SQL_VIEW);
            add(org.eclipse.daanse.rolap.mapping.model.database.relational.RelationalPackage.Literals.EXPRESSION_COLUMN);
            add(org.eclipse.daanse.rolap.mapping.model.database.relational.RelationalPackage.Literals.INLINE_TABLE);
            add(SourcePackage.Literals.SQL_STATEMENT);

            add(SourcePackage.Literals.TABLE_SOURCE);
            add(SourcePackage.Literals.INLINE_TABLE_SOURCE);
            add(SourcePackage.Literals.JOIN_SOURCE);
            add(SourcePackage.Literals.SQL_SELECT_SOURCE);
            add(SourcePackage.Literals.JOINED_QUERY_ELEMENT);

            add(LevelPackage.Literals.CALCULATED_MEMBER);

            add(LevelPackage.Literals.LEVEL);
            add(HierarchyPackage.Literals.HIERARCHY);
            add(HierarchyPackage.Literals.EXPLICIT_HIERARCHY);
            add(HierarchyPackage.Literals.PARENT_CHILD_HIERARCHY);

            add(DimensionPackage.Literals.STANDARD_DIMENSION);
            add(DimensionPackage.Literals.TIME_DIMENSION);

            add(DimensionPackage.Literals.NAMED_SET);

            add(ActionPackage.Literals.ACTION);

            add(CubePackage.Literals.KPI);
            add(MeasurePackage.Literals.SUM_MEASURE);
            add(MeasurePackage.Literals.MIN_MEASURE);
            add(MeasurePackage.Literals.MAX_MEASURE);
            add(MeasurePackage.Literals.AVG_MEASURE);
            add(MeasurePackage.Literals.COUNT_MEASURE);
            add(MeasurePackage.Literals.NONE_MEASURE);
            add(MeasurePackage.Literals.CUSTOM_MEASURE);
            add(MeasurePackage.Literals.TEXT_AGG_MEASURE);
            add(CubePackage.Literals.MEASURE_GROUP);

            add(CubePackage.Literals.PHYSICAL_CUBE);

            add(CubePackage.Literals.CUBE_CONNECTOR);

            add(CubePackage.Literals.VIRTUAL_CUBE);

            add(CommonPackage.Literals.ACCESS_ROLE);
            add(CommonPackage.Literals.ACCESS_CATALOG_GRANT);
            add(OlapPackage.Literals.ACCESS_CUBE_GRANT);
            add(OlapPackage.Literals.ACCESS_DIMENSION_GRANT);
            add(OlapPackage.Literals.ACCESS_HIERARCHY_GRANT);
            add(OlapPackage.Literals.ACCESS_MEMBER_GRANT);

            add(FormatPackage.Literals.CELL_FORMATTER);

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

    static CheckSuiteEObjectComparator checkSuiteComparator = new CheckSuiteEObjectComparator();

    static class CheckSuiteEObjectComparator implements Comparator<EObject> {

        AtomicInteger COUNTER = new AtomicInteger(1);
        Map<EClass, Integer> map = new HashMap<EClass, Integer>();

        CheckSuiteEObjectComparator() {
            add(OlapCheckPackage.Literals.CATALOG_CHECK);
            add(OlapCheckPackage.Literals.CATALOG_ATTRIBUTE_CHECK);
            add(OlapCheckPackage.Literals.DATABASE_SCHEMA_CHECK);
            add(OlapCheckPackage.Literals.DATABASE_SCHEMA_ATTRIBUTE_CHECK);
            add(OlapCheckPackage.Literals.DATABASE_TABLE_CHECK);
            add(OlapCheckPackage.Literals.DATABASE_TABLE_ATTRIBUTE_CHECK);
            add(OlapCheckPackage.Literals.DATABASE_COLUMN_CHECK);
            add(OlapCheckPackage.Literals.DATABASE_COLUMN_ATTRIBUTE_CHECK);
            add(OlapCheckPackage.Literals.CUBE_CHECK);
            add(OlapCheckPackage.Literals.CUBE_ATTRIBUTE_CHECK);
            add(OlapCheckPackage.Literals.DIMENSION_CHECK);
            add(OlapCheckPackage.Literals.DIMENSION_ATTRIBUTE_CHECK);
            add(OlapCheckPackage.Literals.HIERARCHY_CHECK);
            add(OlapCheckPackage.Literals.HIERARCHY_ATTRIBUTE_CHECK);
            add(OlapCheckPackage.Literals.LEVEL_CHECK);
            add(OlapCheckPackage.Literals.LEVEL_ATTRIBUTE_CHECK);
            add(OlapCheckPackage.Literals.MEASURE_CHECK);
            add(OlapCheckPackage.Literals.MEASURE_ATTRIBUTE_CHECK);
            add(OlapCheckPackage.Literals.QUERY_CHECK);
            add(OlapCheckPackage.Literals.QUERY_CHECK_RESULT);
            //TODO
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
