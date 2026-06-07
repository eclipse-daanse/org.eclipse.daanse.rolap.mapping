/*
 * Copyright (c) 2026 Contributors to the Eclipse Foundation.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 */
package org.eclipse.daanse.rolap.mapping.instance.emf.complex.population.jena;

import java.util.List;

import org.eclipse.daanse.cwm.testkit.api.DatabaseCheckSuiteSupplier;
import org.eclipse.daanse.cwm.testkit.api.dbcheck.DatabaseCheckSuite;
import org.eclipse.daanse.cwm.testkit.api.dbcheck.DatabaseColumnCheck;
import org.eclipse.daanse.cwm.testkit.api.dbcheck.DatabaseSchemaCheck;
import org.eclipse.daanse.cwm.testkit.api.dbcheck.DatabaseTableCheck;

public class PopulationJenaDatabaseCheckSuiteSupplier implements DatabaseCheckSuiteSupplier {

    @Override
    public DatabaseCheckSuite get() {
        return new DatabaseCheckSuite(
                "Database Schema Check for PopulationJena",
                List.of(new DatabaseSchemaCheck("", List.of(
                        table("einwohner",
                                col("JAHR", "INTEGER"),
                                col("STATBEZ", "INTEGER"),
                                col("KER_GESCH", "VARCHAR"),
                                col("AGE", "INTEGER"),
                                col("Anzahl", "INTEGER"),
                                col("GEOJSON", "VARCHAR")),
                        table("year",
                                col("year", "INTEGER"),
                                col("ordinal", "INTEGER")),
                        table("town",
                                col("id", "INTEGER"),
                                col("name", "VARCHAR"),
                                col("geojson", "VARCHAR")),
                        table("plraum",
                                col("gid", "INTEGER"),
                                col("plraum", "VARCHAR"),
                                col("uuid", "VARCHAR"),
                                col("geojson", "VARCHAR"),
                                col("townid", "INTEGER")),
                        table("statbez",
                                col("gid", "INTEGER"),
                                col("plraum", "INTEGER"),
                                col("statbez_name", "VARCHAR"),
                                col("uuid", "VARCHAR"),
                                col("geojson", "VARCHAR")),
                        table("gender",
                                col("key", "VARCHAR"),
                                col("name", "VARCHAR")),
                        table("AgeGroups",
                                col("Age", "INTEGER"),
                                col("H1", "VARCHAR"),
                                col("H1_Order", "INTEGER"),
                                col("H2", "VARCHAR"),
                                col("H2_Order", "INTEGER"),
                                col("H7", "VARCHAR"),
                                col("H7_Order", "INTEGER"),
                                col("H8", "VARCHAR"),
                                col("H8_Order", "INTEGER"),
                                col("H9", "VARCHAR"),
                                col("H9_Order", "INTEGER"))))),
                List.of());
    }

    private static DatabaseTableCheck table(String name, DatabaseColumnCheck... cols) {
        return new DatabaseTableCheck(name, List.of(cols));
    }

    private static DatabaseColumnCheck col(String name, String type) {
        return new DatabaseColumnCheck(name, type);
    }
}
