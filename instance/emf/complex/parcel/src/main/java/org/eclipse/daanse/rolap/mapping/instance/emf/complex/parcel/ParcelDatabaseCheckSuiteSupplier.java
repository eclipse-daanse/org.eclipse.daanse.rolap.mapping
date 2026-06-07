/*
 * Copyright (c) 2026 Contributors to the Eclipse Foundation.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 */
package org.eclipse.daanse.rolap.mapping.instance.emf.complex.parcel;

import java.util.List;

import org.eclipse.daanse.cwm.testkit.api.DatabaseCheckSuiteSupplier;
import org.eclipse.daanse.cwm.testkit.api.dbcheck.DatabaseCheckSuite;
import org.eclipse.daanse.cwm.testkit.api.dbcheck.DatabaseColumnCheck;
import org.eclipse.daanse.cwm.testkit.api.dbcheck.DatabaseSchemaCheck;
import org.eclipse.daanse.cwm.testkit.api.dbcheck.DatabaseTableCheck;

public class ParcelDatabaseCheckSuiteSupplier implements DatabaseCheckSuiteSupplier {

    @Override
    public DatabaseCheckSuite get() {
        return new DatabaseCheckSuite(
                "Database Schema Check for Parcel",
                List.of(new DatabaseSchemaCheck("", List.of(
                        table("parcels",
                                col("parcel_id", "INTEGER"),
                                col("width", "DECIMAL"),
                                col("depth", "DECIMAL"),
                                col("height", "DECIMAL"),
                                col("type_id", "INTEGER"),
                                col("defect_id", "INTEGER"),
                                col("deliverable", "VARCHAR"),
                                col("customs", "VARCHAR"),
                                col("return_status", "VARCHAR"),
                                col("sender_id", "INTEGER"),
                                col("receiver_id", "INTEGER"),
                                col("drop_off_id", "INTEGER"),
                                col("delivery_id", "INTEGER"),
                                col("postage", "DECIMAL"),
                                col("insurance_value", "DECIMAL"),
                                col("weight", "DECIMAL")),
                        table("parcel_types",
                                col("type_id", "INTEGER"),
                                col("type_name", "VARCHAR")),
                        table("defects",
                                col("defect_id", "INTEGER"),
                                col("defect_name", "VARCHAR")),
                        table("addresses",
                                col("address_id", "INTEGER"),
                                col("continent", "VARCHAR"),
                                col("country", "VARCHAR"),
                                col("city", "VARCHAR"),
                                col("postal_code", "VARCHAR"),
                                col("street", "VARCHAR"))))),
                List.of());
    }

    private static DatabaseTableCheck table(String name, DatabaseColumnCheck... cols) {
        return new DatabaseTableCheck(name, List.of(cols));
    }

    private static DatabaseColumnCheck col(String name, String type) {
        return new DatabaseColumnCheck(name, type);
    }
}
