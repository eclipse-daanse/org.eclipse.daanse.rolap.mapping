/*
 * Copyright (c) 2022 Contributors to the Eclipse Foundation.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   SmartCity Jena, Stefan Bischof - initial
 *
 */
package org.eclipse.daanse.rolap.mapping.verifyer.basic;

import java.util.SortedSet;

import org.eclipse.daanse.rolap.mapping.api.model.JoinQueryMapping;
import org.eclipse.daanse.rolap.mapping.api.model.QueryMapping;
import org.eclipse.daanse.rolap.mapping.api.model.TableQueryMapping;

public class SchemaExplorer {

    private SchemaExplorer() {
        //constructor
    }

    public static String[] getTableNameForAlias(QueryMapping relation, String table) {
        String theTableName = table;
        String schemaName = null;

        // EC: Loops join tree and finds the table name for an alias.
        if (relation instanceof JoinQueryMapping join) {
            QueryMapping theRelOrJoinL = left(join);
            QueryMapping theRelOrJoinR = right(join);
            for (int i = 0; i < 2; i++) {
                // Searches first using the Left Join and then the Right.
                QueryMapping theCurrentRelOrJoin = (i == 0) ? theRelOrJoinL : theRelOrJoinR;
                if (theCurrentRelOrJoin instanceof TableQueryMapping theTable) {
                    if (theTable.getAlias() != null && theTable.getAlias()
                            .equals(table)) {
                        // If the alias was found get its table name and return
                        // it.
                        theTableName = theTable.getTable().getName();
                        schemaName = theTable.getTable().getSchema().getName();
                    }
                } else {
                    // otherwise continue down the join tree.
                    String[] result = getTableNameForAlias(theCurrentRelOrJoin, table);
                    schemaName = result[0];
                    theTableName = result[1];
                }
            }
        }
        return new String[] { schemaName, theTableName };
    }

    public static void getTableNamesForJoin(QueryMapping relation, SortedSet<String> joinTables) {
        // EC: Loops join tree and collects table names.
        if (relation instanceof JoinQueryMapping join) {
            QueryMapping theRelOrJoinL = left(join);
            QueryMapping theRelOrJoinR = right(join);
            for (int i = 0; i < 2; i++) {
                // Searches first using the Left Join and then the Right.
                QueryMapping theCurrentRelOrJoin = (i == 0) ? theRelOrJoinL : theRelOrJoinR;
                if (theCurrentRelOrJoin instanceof TableQueryMapping theTable) {
                    String theTableName = (theTable.getAlias() != null && theTable.getAlias()
                            .trim()
                            .length() > 0) ? theTable.getAlias() : theTable.getTable().getName();
                    joinTables.add(theTableName);
                } else {
                    // Calls recursively collecting all table names down the
                    // join tree.
                    getTableNamesForJoin(theCurrentRelOrJoin, joinTables);
                }
            }
        }

    }

    private static QueryMapping left(JoinQueryMapping join) {
        if (join != null && join.getLeft() != null) {
            return join.getLeft().getQuery();
        }
        throw new SchemaExplorerException("Join left error");
    }

    private static QueryMapping right(JoinQueryMapping join) {
        if (join != null && join.getRight() != null) {
            return join.getRight().getQuery();
        }
        throw new SchemaExplorerException("Join right error");
    }
}
