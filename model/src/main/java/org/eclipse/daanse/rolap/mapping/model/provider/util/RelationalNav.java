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

import java.util.stream.Stream;

import org.eclipse.daanse.cwm.model.cwm.objectmodel.core.ModelElement;
import org.eclipse.daanse.cwm.model.cwm.resource.relational.Column;
import org.eclipse.daanse.cwm.model.cwm.resource.relational.NamedColumnSet;
import org.eclipse.daanse.cwm.model.cwm.resource.relational.Schema;
import org.eclipse.daanse.cwm.model.cwm.resource.relational.Table;
import org.eclipse.daanse.cwm.model.cwm.resource.relational.View;

/**
 * Typed navigation helpers around the CWM relational containment model. CWM
 * models containment through the loosely-typed
 * {@code Namespace.ownedElement} and {@code Classifier.feature} features. This
 * helper filters those collections to the concrete sub-types needed by most
 * ROLAP traversals.
 *
 * <p>Prefer these helpers over direct {@code ownedElement.stream().filter(…)}
 * calls so that all downcasts live in one place and can be adjusted centrally
 * if the CWM surface changes.
 */
public final class RelationalNav {

    private RelationalNav() {
    }

    /** All {@link Table}s directly owned by {@code schema}. */
    public static Stream<Table> tablesOf(Schema schema) {
        return schema.getOwnedElement().stream()
                .filter(Table.class::isInstance)
                .map(Table.class::cast);
    }

    /** All {@link View}s directly owned by {@code schema}. */
    public static Stream<View> viewsOf(Schema schema) {
        return schema.getOwnedElement().stream()
                .filter(View.class::isInstance)
                .map(View.class::cast);
    }

    /** All {@link NamedColumnSet}s (Table or View) directly owned by {@code schema}. */
    public static Stream<NamedColumnSet> columnSetsOf(Schema schema) {
        return schema.getOwnedElement().stream()
                .filter(NamedColumnSet.class::isInstance)
                .map(NamedColumnSet.class::cast);
    }

    /** All {@link Column}s that belong to the given {@link NamedColumnSet}. */
    public static Stream<Column> columnsOf(NamedColumnSet columnSet) {
        return columnSet.getFeature().stream()
                .filter(Column.class::isInstance)
                .map(Column.class::cast);
    }

    /**
     * Finds a single owned element by name inside a schema. Returns the first
     * match or {@code null} if none. Intended for simple lookups; prefer
     * {@link #tablesOf(Schema)} or {@link #viewsOf(Schema)} for iteration.
     */
    public static ModelElement findByName(Schema schema, String name) {
        if (name == null) {
            return null;
        }
        for (ModelElement me : schema.getOwnedElement()) {
            if (name.equals(me.getName())) {
                return me;
            }
        }
        return null;
    }
}
