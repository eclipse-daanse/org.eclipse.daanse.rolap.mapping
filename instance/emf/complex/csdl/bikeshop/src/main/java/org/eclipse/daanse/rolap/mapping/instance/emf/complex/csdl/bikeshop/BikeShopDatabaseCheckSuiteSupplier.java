/*
 * Copyright (c) 2026 Contributors to the Eclipse Foundation.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 */
package org.eclipse.daanse.rolap.mapping.instance.emf.complex.csdl.bikeshop;

import java.util.List;

import org.eclipse.daanse.cwm.testkit.api.DatabaseCheckSuiteSupplier;
import org.eclipse.daanse.cwm.testkit.api.dbcheck.DatabaseCheckSuite;
import org.eclipse.daanse.cwm.testkit.api.dbcheck.DatabaseColumnCheck;
import org.eclipse.daanse.cwm.testkit.api.dbcheck.DatabaseSchemaCheck;
import org.eclipse.daanse.cwm.testkit.api.dbcheck.DatabaseTableCheck;

public class BikeShopDatabaseCheckSuiteSupplier implements DatabaseCheckSuiteSupplier {

    @Override
    public DatabaseCheckSuite get() {
        return new DatabaseCheckSuite(
                "Database Schema Check for BikeShop",
                List.of(new DatabaseSchemaCheck("", List.of(
                        table("BikeSales",
                                col("RowNumber", "INTEGER"),
                                col("SalesOrderNumber", "VARCHAR"),
                                col("SalesOrderLineNumber", "INTEGER"),
                                col("RevisionNumber", "INTEGER"),
                                col("ProductKey", "INTEGER"),
                                col("CountryCode", "VARCHAR"),
                                col("CurrencyKey", "INTEGER"),
                                col("CalendarQuarter", "VARCHAR"),
                                col("SalesChannelCode", "VARCHAR"),
                                col("OrderQuantity", "INTEGER"),
                                col("UnitPrice", "DECIMAL"),
                                col("ExtendedAmount", "DECIMAL"),
                                col("UnitPriceDiscountPct", "DOUBLE"),
                                col("DiscountAmount", "DOUBLE"),
                                col("ProductStandardCost", "DECIMAL"),
                                col("TotalProductCost", "DECIMAL"),
                                col("SalesAmount", "DECIMAL"),
                                col("TaxAmt", "DECIMAL"),
                                col("Freight", "DECIMAL"),
                                col("CarrierTrackingNumber", "VARCHAR"),
                                col("CustomerPONumber", "VARCHAR"),
                                col("CustomerAccountNumber", "VARCHAR")),
                        table("Bike",
                                col("RowNumber", "INTEGER"),
                                col("ProductKey", "INTEGER"),
                                col("ProductAlternateKey", "VARCHAR"),
                                col("ProductSubcategoryKey", "INTEGER"),
                                col("ProductName", "VARCHAR"),
                                col("StandardCost", "DECIMAL"),
                                col("FinishedGoodsFlag", "BOOLEAN"),
                                col("Color", "VARCHAR"),
                                col("ListPrice", "DECIMAL"),
                                col("Size", "VARCHAR"),
                                col("SizeRange", "VARCHAR"),
                                col("Weight", "DOUBLE"),
                                col("DealerPrice", "DECIMAL"),
                                col("Class", "VARCHAR"),
                                col("Style", "VARCHAR"),
                                col("ModelName", "VARCHAR"),
                                col("Description", "VARCHAR"),
                                col("WeightUnitMeasureCode", "VARCHAR"),
                                col("SizeUnitMeasureCode", "INTEGER"),
                                col("SafetyStockLevel", "INTEGER"),
                                col("ReorderPoint", "INTEGER"),
                                col("DaysToManufacture", "INTEGER"),
                                col("ProductLine", "VARCHAR")),
                        table("BikeSubcategory",
                                col("RowNumber", "INTEGER"),
                                col("ProductSubcategoryKey", "INTEGER"),
                                col("Subcategory", "VARCHAR")),
                        table("CalendarQuarter",
                                col("RowNumber", "INTEGER"),
                                col("CalendarQuarter2", "VARCHAR")),
                        table("Country",
                                col("RowNumber", "INTEGER"),
                                col("CountryCode", "INTEGER"),
                                col("CountryName", "VARCHAR")),
                        table("Currency",
                                col("RowNumber", "INTEGER"),
                                col("CurrencyKey", "INTEGER"),
                                col("CurrencyAlternateKey", "VARCHAR"),
                                col("CurrencyName", "VARCHAR")),
                        table("SalesChannel",
                                col("RowNumber", "INTEGER"),
                                col("SalesChannelCode", "VARCHAR"),
                                col("SalesChannelName", "VARCHAR"))))),
                List.of());
    }

    private static DatabaseTableCheck table(String name, DatabaseColumnCheck... cols) {
        return new DatabaseTableCheck(name, List.of(cols));
    }

    private static DatabaseColumnCheck col(String name, String type) {
        return new DatabaseColumnCheck(name, type);
    }
}
