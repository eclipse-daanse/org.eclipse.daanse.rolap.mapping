/*
 * Copyright (c) 2026 Contributors to the Eclipse Foundation.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 */
package org.eclipse.daanse.rolap.mapping.instance.emf.tutorial.writeback.decimalandcommentandmultidim;

import java.util.List;

import org.eclipse.daanse.cwm.testkit.api.DatabaseCheckSuiteSupplier;
import org.eclipse.daanse.cwm.testkit.api.dbcheck.DatabaseCheckSuite;
import org.eclipse.daanse.cwm.testkit.api.dbcheck.DatabaseColumnCheck;
import org.eclipse.daanse.cwm.testkit.api.dbcheck.DatabaseSchemaCheck;
import org.eclipse.daanse.cwm.testkit.api.dbcheck.DatabaseTableCheck;

public class DecimalAndCommentAndMultiDimDatabaseCheckSuiteSupplier implements DatabaseCheckSuiteSupplier {

    @Override
    public DatabaseCheckSuite get() {
        return new DatabaseCheckSuite(
                "Database Schema Check for DecimalAndCommentAndMultiDim",
                List.of(new DatabaseSchemaCheck("", List.of(
                        table("FACT",
                                col("PRODUCT", "VARCHAR"),
                                col("CITY", "VARCHAR"),
                                col("AMOUNT", "DECIMAL")),
                        table("PRODUCT",
                                col("KEY", "VARCHAR"),
                                col("NAME", "VARCHAR")),
                        table("REGION",
                                col("COUNTRY_KEY", "VARCHAR"),
                                col("COUNTRY_NAME", "VARCHAR"),
                                col("CITY_KEY", "VARCHAR"),
                                col("CITY_NAME", "VARCHAR"))))),
                List.of());
    }

    private static DatabaseTableCheck table(String name, DatabaseColumnCheck... cols) {
        return new DatabaseTableCheck(name, List.of(cols));
    }

    private static DatabaseColumnCheck col(String name, String type) {
        return new DatabaseColumnCheck(name, type);
    }
}
