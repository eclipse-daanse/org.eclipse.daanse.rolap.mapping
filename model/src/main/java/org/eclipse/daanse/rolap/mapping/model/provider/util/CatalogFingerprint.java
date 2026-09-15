/*
* Copyright (c) 2026 Contributors to the Eclipse Foundation.
*
* This program and the accompanying materials are made
* available under the terms of the Eclipse Public License 2.0
* which is available at https://www.eclipse.org/legal/epl-2.0/
*
* SPDX-License-Identifier: EPL-2.0
*
* Contributors:
*   Stefan Bischof (bipolis.org) - initial
*/
package org.eclipse.daanse.rolap.mapping.model.provider.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.HexFormat;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import org.eclipse.daanse.cwm.model.cwm.resource.relational.SQLSimpleType;
import org.eclipse.daanse.rolap.mapping.model.access.common.CommonPackage;
import org.eclipse.daanse.rolap.mapping.model.access.olap.OlapPackage;
import org.eclipse.daanse.rolap.mapping.model.catalog.Catalog;
import org.eclipse.daanse.rolap.mapping.model.catalog.CatalogPackage;
import org.eclipse.daanse.rolap.mapping.model.database.source.SourcePackage;
import org.eclipse.daanse.rolap.mapping.model.olap.cube.CubePackage;
import org.eclipse.daanse.rolap.mapping.model.olap.cube.action.ActionPackage;
import org.eclipse.daanse.rolap.mapping.model.olap.cube.measure.MeasurePackage;
import org.eclipse.daanse.rolap.mapping.model.olap.dimension.DimensionPackage;
import org.eclipse.daanse.rolap.mapping.model.olap.dimension.hierarchy.HierarchyPackage;
import org.eclipse.daanse.rolap.mapping.model.olap.dimension.hierarchy.level.LevelPackage;
import org.eclipse.daanse.rolap.mapping.model.olap.format.FormatPackage;
import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.xmi.XMLResource;
import org.eclipse.emf.ecore.xmi.XMIResource;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceImpl;

/**
 * Content identity of a catalog mapping: the reachable model graph is copied,
 * canonically serialized into one in-memory XMI document and hashed with
 * SHA-256. Stable for graphs built by the same construction path (same
 * supplier, or save/reload); the served model is never mutated. Unresolved
 * proxies void the identity ({@link IllegalStateException}).
 */
public final class CatalogFingerprint {

    /** Pipeline version marker; changing the canonicalization rules requires a new marker. */
    static final byte[] SALT = "daanse-fp-v1\0".getBytes(StandardCharsets.UTF_8);

    // the importer opposite would pull every importing catalog into the closure
    private static final Set<String> SKIPPED_REFERENCES = Set.of("importer");

    private CatalogFingerprint() {
    }

    /** 32 raw bytes. */
    public static byte[] sha256(Catalog catalog) {
        MessageDigest digest = sha256Digest();
        digest.update(SALT);
        digest.update(canonicalXmiBytes(catalog));
        return digest.digest();
    }

    public static String sha256Hex(Catalog catalog) {
        return HexFormat.of().formatHex(sha256(catalog));
    }

    /** The canonical XMI document the fingerprint is computed over; exposed for tests and diffing. */
    public static byte[] canonicalXmiBytes(Catalog catalog) {
        Objects.requireNonNull(catalog, "catalog");

        EcoreUtil.resolveAll(catalog);
        Set<EObject> closure = closure(new HashSet<>(), catalog);
        failOnProxies(closure);
        List<EObject> originalRoots = roots(closure);

        // canonicalization mutates, therefore: detached copy only
        EcoreUtil.Copier copier = new EcoreUtil.Copier(true, false);
        List<EObject> copiedRoots = new ArrayList<>(copier.copyAll(originalRoots));
        copier.copyReferences();

        List<EObject> allCopied = new ArrayList<>();
        for (EObject root : copiedRoots) {
            allCopied.add(root);
            for (TreeIterator<EObject> it = root.eAllContents(); it.hasNext();) {
                allCopied.add(it.next());
            }
        }
        allCopied.sort(COMPARATOR);
        List<EObject> canonical = deduplicateSqlTypes(allCopied);

        XMIResource resource = new XMIResourceImpl(URI.createFileURI("catalog.xmi"));
        for (EObject eObject : canonical) {
            if (eObject.eContainer() == null) {
                resource.getContents().add(eObject);
            }
        }
        assignXmiIds(resource, canonical);

        Map<Object, Object> options = new HashMap<>();
        options.put(XMLResource.OPTION_ENCODING, StandardCharsets.UTF_8.name());
        options.put(Resource.OPTION_LINE_DELIMITER, "\n");
        // a discarded dangling ref would let two different models hash equal
        options.put(XMLResource.OPTION_PROCESS_DANGLING_HREF, XMLResource.OPTION_PROCESS_DANGLING_HREF_THROW);

        ByteArrayOutputStream baos = new ByteArrayOutputStream(64 * 1024);
        try {
            resource.save(baos, options);
        } catch (IOException e) {
            throw new IllegalStateException(
                    "catalog '" + catalog.getName() + "' has no canonical serialization", e);
        }
        return baos.toByteArray();
    }

    private static MessageDigest sha256Digest() {
        try {
            return MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException("SHA-256 unavailable", e);
        }
    }

    private static Set<EObject> closure(Set<EObject> set, EObject eObject) {
        if (set.add(eObject)) {
            for (TreeIterator<EObject> allContents = eObject.eAllContents(); allContents.hasNext();) {
                closure(set, allContents.next());
            }
            for (EReference reference : eObject.eClass().getEAllReferences()) {
                if (reference.isContainment() || !eObject.eIsSet(reference)
                        || SKIPPED_REFERENCES.contains(reference.getName())) {
                    continue;
                }
                Object value = eObject.eGet(reference);
                if (value instanceof List<?> list) {
                    for (Object o : new ArrayList<>(list)) {
                        if (o instanceof EObject ref) {
                            closure(set, ref);
                        }
                    }
                } else if (value instanceof EObject ref) {
                    closure(set, ref);
                }
            }
            EObject eContainer = eObject.eContainer();
            if (eContainer != null) {
                closure(set, eContainer);
            }
        }
        return set;
    }

    private static void failOnProxies(Set<EObject> closure) {
        List<String> proxies = new ArrayList<>();
        for (EObject eObject : closure) {
            if (eObject.eIsProxy()) {
                proxies.add(eObject.eClass().getName() + " " + EcoreUtil.getURI(eObject));
            }
        }
        if (!proxies.isEmpty()) {
            throw new IllegalStateException(
                    "catalog graph contains unresolved proxies, identity undefined: " + proxies);
        }
    }

    // every root container of the closure is embedded: the catalog, foreign
    // catalogs (import), Schemas, SQLDataTypes, and freestanding referenced
    // elements (in-code catalogs commonly leave dimensions etc. uncontained);
    // over-inclusion only over-invalidates, never corrupts the identity
    private static List<EObject> roots(Set<EObject> closure) {
        Set<EObject> roots = new LinkedHashSet<>();
        for (EObject eObject : closure) {
            roots.add(EcoreUtil.getRootContainer(eObject));
        }
        List<EObject> ordered = new ArrayList<>(roots);
        ordered.sort(COMPARATOR);
        return ordered;
    }

    // construction code creates one SQLSimpleType instance per column; identical
    // graphs only reach identical XMI after collapsing them
    private static List<EObject> deduplicateSqlTypes(List<EObject> sorted) {
        Map<String, SQLSimpleType> canonical = new HashMap<>();
        Map<SQLSimpleType, SQLSimpleType> remap = new HashMap<>();
        for (EObject eo : sorted) {
            if (!(eo instanceof SQLSimpleType t)) {
                continue;
            }
            String key = t.getName() + "|" + t.getCharacterMaximumLength() + "|" + t.getNumericPrecision() + "|"
                    + t.getNumericScale() + "|" + t.getNumericPrecisionRadix() + "|" + t.getDateTimePrecision();
            SQLSimpleType c = canonical.get(key);
            if (c == null) {
                canonical.put(key, t);
            } else if (c != t) {
                remap.put(t, c);
            }
        }
        if (remap.isEmpty()) {
            return sorted;
        }
        for (EObject eo : sorted) {
            for (EReference ref : eo.eClass().getEAllReferences()) {
                if (ref.isContainment() || ref.isContainer()) {
                    continue;
                }
                if (ref.isMany()) {
                    @SuppressWarnings("unchecked")
                    List<EObject> refList = (List<EObject>) eo.eGet(ref);
                    for (int i = 0; i < refList.size(); i++) {
                        if (refList.get(i) instanceof SQLSimpleType tt && remap.containsKey(tt)) {
                            refList.set(i, remap.get(tt));
                        }
                    }
                } else if (eo.eGet(ref) instanceof SQLSimpleType tt && remap.containsKey(tt)) {
                    eo.eSet(ref, remap.get(tt));
                }
            }
        }
        List<EObject> filtered = new ArrayList<>(sorted.size() - remap.size());
        for (EObject eo : sorted) {
            if (!(eo instanceof SQLSimpleType t) || !remap.containsKey(t)) {
                filtered.add(eo);
            }
        }
        return filtered;
    }

    // ids derive from type + name, so they depend on content, not insertion order
    private static void assignXmiIds(XMIResource xmi, List<EObject> elements) {
        Set<String> used = new HashSet<>();
        for (EObject eo : elements) {
            String base = idFor(eo);
            if (base == null) {
                continue;
            }
            String id = base;
            int n = 1;
            while (!used.add(id)) {
                id = base + "_" + n++;
            }
            xmi.setID(eo, id);
        }
    }

    private static String idFor(EObject eo) {
        String typeName = eo.eClass().getName();
        EStructuralFeature nameFeat = eo.eClass().getEStructuralFeature("name");
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
        if ("Column".equals(typeName)) {
            suffix = qualifyWithContainers(eo, suffix);
        }
        suffix = suffix.replaceAll("[^A-Za-z0-9_]", "_");
        suffix = suffix.replaceAll("_+", "_").replaceAll("^_|_$", "");
        return ("_" + typeName + "_" + suffix).toLowerCase();
    }

    private static String qualifyWithContainers(EObject eo, String suffix) {
        List<String> parts = new ArrayList<>();
        EObject ctr = eo.eContainer();
        while (ctr != null && !"Catalog".equals(ctr.eClass().getName())) {
            EStructuralFeature nf = ctr.eClass().getEStructuralFeature("name");
            if (nf != null && ctr.eGet(nf) instanceof String s && !s.isBlank()) {
                parts.add(0, s);
            }
            ctr = ctr.eContainer();
        }
        if (parts.isEmpty()) {
            return suffix;
        }
        return String.join("_", parts) + "_" + suffix;
    }

    private static String anonymousSuffix(EObject eo) {
        for (String attr : new String[] { "overrideDimensionName", "uniqueName", "title", "label" }) {
            EStructuralFeature f = eo.eClass().getEStructuralFeature(attr);
            if (f != null && eo.eGet(f) instanceof String s && !s.isBlank()) {
                return s;
            }
        }
        for (String refName : new String[] { "table", "view", "dimension", "hierarchy", "cube", "primaryKey",
                "foreignKey", "column", "key", "query" }) {
            EStructuralFeature refFeat = eo.eClass().getEStructuralFeature(refName);
            if (refFeat instanceof EReference ref && !ref.isMany() && !ref.isContainment()
                    && eo.eGet(ref) instanceof EObject target) {
                EStructuralFeature tnf = target.eClass().getEStructuralFeature("name");
                if (tnf != null && target.eGet(tnf) instanceof String s && !s.isBlank()) {
                    return s;
                }
            }
        }
        return null;
    }

    // total order: EClass rank (unranked first), then name; locale-independent only
    private static final Comparator<EObject> COMPARATOR = new EClassRankComparator();

    private static final class EClassRankComparator implements Comparator<EObject> {

        private final Map<EClass, Integer> rank = new HashMap<>();
        private int counter = 1;

        EClassRankComparator() {
            add(CatalogPackage.Literals.CATALOG);

            add(org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalPackage.Literals.SCHEMA);
            add(org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalPackage.Literals.TABLE);
            add(org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalPackage.Literals.VIEW);
            add(org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalPackage.Literals.COLUMN);
            add(org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalPackage.Literals.FOREIGN_KEY);
            add(org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalPackage.Literals.PRIMARY_KEY);

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

        private void add(EClass eClass) {
            rank.put(eClass, ++counter);
        }

        @Override
        public int compare(EObject o1, EObject o2) {
            int value = rank.getOrDefault(o1.eClass(), 0) - rank.getOrDefault(o2.eClass(), 0);
            if (value != 0) {
                return value;
            }
            String s1 = nameOf(o1);
            String s2 = nameOf(o2);
            int byName = s1.compareToIgnoreCase(s2);
            if (byName != 0) {
                return byName;
            }
            return s1.compareTo(s2);
        }

        private static String nameOf(EObject o) {
            EStructuralFeature nameFeat = o.eClass().getEStructuralFeature("name");
            if (nameFeat != null && o.eGet(nameFeat) instanceof String s) {
                return s;
            }
            return "";
        }
    }
}
