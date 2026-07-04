/*
 * Copyright (c) 2026 Contributors to the Eclipse Foundation.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 */
package org.eclipse.daanse.rolap.mapping.instance.emf.complex.csdl.bikeaccessories;

import java.net.URL;
import java.util.Map;

import org.eclipse.daanse.olap.check.runtime.api.OlapCheckSuiteSupplier;
import org.eclipse.daanse.rolap.mapping.model.provider.CatalogMappingSupplier;
import org.eclipse.daanse.rolap.mapping.instance.api.CatalogTestInstance;
import org.eclipse.daanse.cwm.testkit.api.DatabaseCheckSuiteSupplier;
import org.eclipse.daanse.cwm.testkit.api.DatabaseSupplier;

public class BikeAccessoriesTestInstance implements CatalogTestInstance {

    @Override
    public String name() {
        return "complex.csdl.bikeaccessories";
    }

    @Override
    public CatalogMappingSupplier mappingSupplier() {
        return new CatalogSupplier();
    }

    @Override
    public OlapCheckSuiteSupplier checkSuiteSupplier() {
        return new CheckSuiteSupplier();
    }

    @Override
    public Map<String, URL> csvResources() {
        return new java.util.LinkedHashMap<String, URL>() {{
            put("ProductCategory", getClass().getResource("data/ProductCategory.csv"));
            put("ProductSubcategory", getClass().getResource("data/ProductSubcategory.csv"));
            put("Product", getClass().getResource("data/Product.csv"));
            put("Customer", getClass().getResource("data/Customer.csv"));
            put("Employee", getClass().getResource("data/Employee.csv"));
            put("Geography", getClass().getResource("data/Geography.csv"));
            put("Store", getClass().getResource("data/Store.csv"));
            put("Time", getClass().getResource("data/Time.csv"));
            put("Fact", getClass().getResource("data/Fact.csv"));
        }};
    }

    @Override
    public DatabaseCheckSuiteSupplier dbCheckSupplier() {
        return new BikeAccessoriesDatabaseCheckSuiteSupplier();
    }



    @Override
    public DatabaseSupplier databaseSupplier() {
        return new BikeAccessoriesDatabaseSupplier();
    }

}
