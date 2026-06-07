/*
 * Copyright (c) 2026 Contributors to the Eclipse Foundation.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 */
package org.eclipse.daanse.rolap.mapping.instance.emf.complex.steelwheels;

import java.util.List;

import org.eclipse.daanse.cwm.testkit.api.DatabaseCheckSuiteSupplier;
import org.eclipse.daanse.cwm.testkit.api.dbcheck.DatabaseCheckSuite;
import org.eclipse.daanse.cwm.testkit.api.dbcheck.DatabaseColumnCheck;
import org.eclipse.daanse.cwm.testkit.api.dbcheck.DatabaseSchemaCheck;
import org.eclipse.daanse.cwm.testkit.api.dbcheck.DatabaseTableCheck;

public class SteelWheelsDatabaseCheckSuiteSupplier implements DatabaseCheckSuiteSupplier {

    @Override
    public DatabaseCheckSuite get() {
        return new DatabaseCheckSuite(
                "Database Schema Check for SteelWheels",
                List.of(new DatabaseSchemaCheck("", List.of(
                        table("orderfact",
                                col("CUSTOMERNUMBER", "INTEGER"),
                                col("PRODUCTCODE", "VARCHAR"),
                                col("TIME_ID", "VARCHAR"),
                                col("QUANTITYORDERED", "INTEGER"),
                                col("TOTALPRICE", "NUMERIC"),
                                col("STATUS", "VARCHAR"),
                                col("ORDERDATE", "TIMESTAMP"),
                                col("PRICEEACH", "NUMERIC"),
                                col("REQUIREDDATE", "TIMESTAMP"),
                                col("SHIPPEDDATE", "TIMESTAMP")),
                        table("customer_w_ter",
                                col("CUSTOMERNUMBER", "INTEGER"),
                                col("CUSTOMERNAME", "VARCHAR"),
                                col("TERRITORY", "VARCHAR"),
                                col("COUNTRY", "VARCHAR"),
                                col("STATE", "VARCHAR"),
                                col("CITY", "VARCHAR"),
                                col("CONTACTFIRSTNAME", "VARCHAR"),
                                col("CONTACTLASTNAME", "VARCHAR"),
                                col("PHONE", "VARCHAR"),
                                col("ADDRESSLINE1", "VARCHAR"),
                                col("CREDITLIMIT", "NUMERIC")),
                        table("products",
                                col("PRODUCTCODE", "VARCHAR"),
                                col("PRODUCTNAME", "VARCHAR"),
                                col("PRODUCTLINE", "VARCHAR"),
                                col("PRODUCTVENDOR", "VARCHAR"),
                                col("PRODUCTDESCRIPTION", "VARCHAR")),
                        table("time",
                                col("TIME_ID", "VARCHAR"),
                                col("YEAR_ID", "INTEGER"),
                                col("QTR_NAME", "VARCHAR"),
                                col("QTR_ID", "INTEGER"),
                                col("MONTH_NAME", "VARCHAR"),
                                col("MONTH_ID", "INTEGER"))))),
                List.of());
    }

    private static DatabaseTableCheck table(String name, DatabaseColumnCheck... cols) {
        return new DatabaseTableCheck(name, List.of(cols));
    }

    private static DatabaseColumnCheck col(String name, String type) {
        return new DatabaseColumnCheck(name, type);
    }
}
