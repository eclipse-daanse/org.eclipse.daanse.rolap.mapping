/*
* Copyright (c) 2026 Contributors to the Eclipse Foundation.
*
* This program and the accompanying materials are made
* available under the terms of the Eclipse Public License 2.0
* which is available at https://www.eclipse.org/legal/epl-2.0/
*
* SPDX-License-Identifier: EPL-2.0
*/
package org.eclipse.daanse.rolap.mapping.model.provider.util;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import org.eclipse.daanse.cwm.model.cwm.objectmodel.core.ModelElement;
import org.eclipse.daanse.rolap.mapping.model.catalog.Catalog;
import org.eclipse.daanse.rolap.mapping.model.database.source.InlineTableSource;
import org.eclipse.daanse.rolap.mapping.model.database.source.JoinSource;
import org.eclipse.daanse.rolap.mapping.model.database.source.JoinedQueryElement;
import org.eclipse.daanse.rolap.mapping.model.database.source.SqlSelectSource;
import org.eclipse.daanse.rolap.mapping.model.database.source.TableSource;
import org.eclipse.daanse.rolap.mapping.model.olap.cube.PhysicalCube;
import org.eclipse.daanse.rolap.mapping.model.olap.cube.MeasureGroup;
import org.eclipse.daanse.rolap.mapping.model.olap.dimension.Dimension;
import org.eclipse.daanse.rolap.mapping.model.olap.dimension.DimensionConnector;
import org.eclipse.daanse.rolap.mapping.model.olap.dimension.hierarchy.Hierarchy;
import org.eclipse.daanse.rolap.mapping.model.olap.format.Formatter;
import org.eclipse.daanse.rolap.mapping.model.database.aggregation.ExplicitAggregationTable;
import org.eclipse.daanse.rolap.mapping.model.database.aggregation.PatternAggregationTable;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;

import org.eclipse.daanse.cwm.model.cwm.objectmodel.core.util.Packages;
import org.eclipse.daanse.cwm.model.cwm.resource.relational.Schema;
/**
 * Fills missing {@code ModelElement.name}s from context — never overwriting a
 * set name — so every serialized catalog satisfies the mandatory CWM
 * {@code name [1..1]} and the Diagnostician runs clean.
 *
 * <p>The derivation rules live here once, audited, instead of hundreds of
 * generated setName lines: a {@link DimensionConnector} is named after its
 * override name or its dimension, a {@link TableSource} after its table, a
 * {@link JoinSource} after its participants, a {@link SqlSelectSource} after
 * its alias, a {@link MeasureGroup} after its cube (plus index when the cube
 * has several), an explicit aggregation table after its aggregate table, a
 * {@link Formatter} after its implementation class. Where no context carries,
 * the class name plus a per-type counter steps in — ugly, honest, unique
 * within the run. Names are diagnostic labels; references run over xmi:id.
 * Deliberately never named: relational objects (schemas, tables, views,
 * columns — including their mapping subtypes) — their name is a SQL
 * identifier with runtime meaning, and an unnamed schema means the
 * connection default schema — and hierarchies, whose empty name is the
 * runtime default "named after the using dimension", context-dependent for
 * shared hierarchies and overridden dimension names.
 *
 * <p>Suppliers call {@link #complete(Catalog)} once, right before returning
 * the finished catalog; the walk covers the catalog tree and its referenced
 * schema roots and is idempotent.
 */
public final class Naming {

    private Naming() {
    }

    /** Fills every missing name reachable from {@code catalog}; returns it. */
    public static Catalog complete(Catalog catalog) {
        Set<EObject> roots = new LinkedHashSet<>();
        roots.add(EcoreUtil.getRootContainer(catalog));
        Packages.available(catalog, Schema.class).forEach(s -> roots.add(EcoreUtil.getRootContainer(s)));
        Map<String, Integer> counters = new HashMap<>();
        for (EObject root : roots) {
            maybeName(root, catalog, counters);
            for (Iterator<EObject> it = root.eAllContents(); it.hasNext();) {
                maybeName(it.next(), catalog, counters);
            }
        }
        return catalog;
    }

    private static void maybeName(EObject object, Catalog catalog, Map<String, Integer> counters) {
        if (!(object instanceof ModelElement element) || !isBlank(element)) {
            return;
        }
        if (isSqlIdentity(element)) {
            // Names of relational objects are SQL identifiers, not labels: a
            // schema without a name means the connection default schema, a
            // column or view name renders into queries. Never invent those.
            return;
        }
        if (element instanceof Hierarchy) {
            // An unnamed hierarchy is a runtime default: it takes the name of
            // the dimension USING it, per usage context — shared hierarchies
            // and overrideDimensionName make that unrepresentable as a static
            // name (a wrong pick changes MDX unique names). Never invent it.
            return;
        }
        String name = derive(element, catalog);
        if (name == null || name.isBlank()) {
            String type = element.eClass().getName();
            int n = counters.merge(type, 1, Integer::sum);
            name = type + "-" + n;
        }
        element.setName(name);
    }

    private static boolean isSqlIdentity(ModelElement element) {
        return element instanceof org.eclipse.daanse.cwm.model.cwm.resource.relational.Schema
                || element instanceof org.eclipse.daanse.cwm.model.cwm.resource.relational.ColumnSet
                || element instanceof org.eclipse.daanse.cwm.model.cwm.resource.relational.Column
                || element.eClass().getEPackage().getNsURI().startsWith("http://www.omg.org/spec/CWM");
    }

    private static boolean isBlank(ModelElement element) {
        return element.getName() == null || element.getName().isBlank();
    }

    private static String derive(ModelElement element, Catalog catalog) {
        if (element instanceof DimensionConnector connector) {
            if (connector.getOverrideDimensionName() != null && !connector.getOverrideDimensionName().isBlank()) {
                return connector.getOverrideDimensionName();
            }
            return connector.getDimension() != null ? connector.getDimension().getName() : null;
        }
        if (element instanceof TableSource source) {
            return source.getTable() != null ? source.getTable().getName() : null;
        }
        if (element instanceof InlineTableSource source) {
            return source.getTable() != null ? source.getTable().getName() : null;
        }
        if (element instanceof SqlSelectSource source) {
            return source.getAlias();
        }
        if (element instanceof JoinSource join) {
            String left = participant(join.getLeft());
            String right = participant(join.getRight());
            return left != null && right != null ? left + "-join-" + right : null;
        }
        if (element instanceof MeasureGroup group) {
            if (group.eContainer() instanceof PhysicalCube cube && cube.getName() != null) {
                int siblings = cube.getMeasureGroups().size();
                int index = cube.getMeasureGroups().indexOf(group);
                return siblings > 1 ? cube.getName() + "-" + (index + 1) : cube.getName();
            }
            return null;
        }
        if (element instanceof ExplicitAggregationTable aggregation) {
            return aggregation.getTable() != null ? aggregation.getTable().getName() : null;
        }
        if (element instanceof PatternAggregationTable aggregation) {
            return aggregation.getPattern();
        }
        if (element instanceof Formatter formatter) {
            String ref = formatter.getRef();
            if (ref != null && !ref.isBlank()) {
                int cut = ref.lastIndexOf('.');
                return cut >= 0 ? ref.substring(cut + 1) : ref;
            }
            return null;
        }
        return null;
    }

    private static String participant(JoinedQueryElement side) {
        if (side == null || side.getSource() == null) {
            return null;
        }
        ModelElement source = side.getSource();
        return source.getName() != null && !source.getName().isBlank() ? source.getName() : null;
    }
}
