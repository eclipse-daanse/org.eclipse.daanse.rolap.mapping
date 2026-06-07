/*
 * Copyright (c) 2026 Contributors to the Eclipse Foundation.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 */
package org.eclipse.daanse.rolap.mapping.instance.emf.complex.expressivenames;

import java.util.List;

import org.eclipse.daanse.cwm.testkit.api.DatabaseCheckSuiteSupplier;
import org.eclipse.daanse.cwm.testkit.api.dbcheck.DatabaseCheckSuite;
import org.eclipse.daanse.cwm.testkit.api.dbcheck.DatabaseColumnCheck;
import org.eclipse.daanse.cwm.testkit.api.dbcheck.DatabaseSchemaCheck;
import org.eclipse.daanse.cwm.testkit.api.dbcheck.DatabaseTableCheck;

public class ExpressiveNamesDatabaseCheckSuiteSupplier implements DatabaseCheckSuiteSupplier {

    @Override
    public DatabaseCheckSuite get() {
        return new DatabaseCheckSuite(
                "Database Schema Check for ExpressiveNames",
                List.of(new DatabaseSchemaCheck("", List.of(
                        table("Cube1Fact",
                                col("D1", "VARCHAR"),
                                col("D2", "VARCHAR"),
                                col("D3", "VARCHAR"),
                                col("M1", "INTEGER")),
                        table("D1H1L1Table",
                                col("D1H1L1", "INTEGER"),
                                col("D1H1L1_NAME", "VARCHAR"),
                                col("D1H1L1_Ordinal", "INTEGER")),
                        table("D2H1L1Table",
                                col("D2H1L1", "INTEGER"),
                                col("D2H1L1_NAME", "VARCHAR"),
                                col("D2H1L1_Ordinal", "INTEGER")),
                        table("D2H2L2Table",
                                col("D2H2L2", "INTEGER"),
                                col("D2H2L1", "INTEGER"),
                                col("D2H2L2_NAME", "VARCHAR"),
                                col("D2H2L1_NAME", "VARCHAR"),
                                col("D2H2L2_Ordinal", "INTEGER"),
                                col("D2H2L1_Ordinal", "INTEGER")),
                        table("D3H1L1Table",
                                col("D3H1L1", "INTEGER"),
                                col("D3H1L1_NAME", "VARCHAR"),
                                col("D3H1L1_Ordinal", "INTEGER")),
                        table("D3H2L2Table",
                                col("D3H2L2", "INTEGER"),
                                col("D3H2L2_id", "INTEGER"),
                                col("D3H2L1_id", "INTEGER"),
                                col("D3H2L2_NAME", "VARCHAR"),
                                col("D3H2L2_Ordinal", "INTEGER")),
                        table("D3H2L1Table",
                                col("D3H2L1", "INTEGER"),
                                col("D3H2L1_NAME", "VARCHAR"),
                                col("D3H2L1_Ordinal", "INTEGER")),
                        table("D3H3L3Table",
                                col("D3H3L3", "INTEGER"),
                                col("D3H3L2_id", "INTEGER"),
                                col("D3H3L3_NAME", "VARCHAR"),
                                col("D3H3L3_Ordinal", "INTEGER")),
                        table("D3H3L2Table",
                                col("D3H3L2", "INTEGER"),
                                col("D3H3L1_id", "INTEGER"),
                                col("D3H3L2_NAME", "VARCHAR"),
                                col("D3H3L2_Ordinal", "INTEGER")),
                        table("D3H3L1Table",
                                col("D3H3L1", "INTEGER"),
                                col("D3H3L1_NAME", "VARCHAR"),
                                col("D3H3L1_Ordinal", "INTEGER"))))),
                List.of());
    }

    private static DatabaseTableCheck table(String name, DatabaseColumnCheck... cols) {
        return new DatabaseTableCheck(name, List.of(cols));
    }

    private static DatabaseColumnCheck col(String name, String type) {
        return new DatabaseColumnCheck(name, type);
    }
}
