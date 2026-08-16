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

import java.util.List;

import org.eclipse.daanse.rolap.mapping.model.MdxExpression;
import org.eclipse.daanse.rolap.mapping.model.RolapMappingFactory;
import org.eclipse.daanse.rolap.mapping.model.database.source.SourceFactory;
import org.eclipse.daanse.rolap.mapping.model.database.source.SqlStatement;

/**
 * Builders for the CWM expression wrappers of the mapping model. MDX and SQL
 * text lives in the inherited {@code core::Expression.body}; the concrete
 * class ({@link MdxExpression} vs {@link SqlStatement}) is the language
 * marker, mirroring CWM's own QueryExpression/ProcedureExpression marker
 * subclasses.
 */
public final class Expressions {

    private Expressions() {
    }

    /** An MDX expression carrying {@code body}. */
    public static MdxExpression mdx(String body) {
        MdxExpression e = RolapMappingFactory.eINSTANCE.createMdxExpression();
        e.setBody(body);
        return e;
    }

    /** A SQL statement carrying {@code body} for the given dialects. */
    public static SqlStatement sql(String body, String... dialects) {
        SqlStatement s = SourceFactory.eINSTANCE.createSqlStatement();
        s.setBody(body);
        s.getDialects().addAll(List.of(dialects));
        return s;
    }
}
