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

import java.net.URL;
import java.util.Map;

import org.eclipse.daanse.olap.check.runtime.api.OlapCheckSuiteSupplier;
import org.eclipse.daanse.rolap.mapping.model.provider.CatalogMappingSupplier;
import org.eclipse.daanse.rolap.mapping.instance.api.CatalogTestInstance;
import org.eclipse.daanse.cwm.testkit.api.DatabaseCheckSuiteSupplier;
import org.eclipse.daanse.cwm.testkit.api.DatabaseSupplier;

public class BikeShopTestInstance implements CatalogTestInstance {

    @Override
    public String name() {
        return "complex.csdl.bikeshop";
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
            put("BikeSubcategory", getClass().getResource("data/BikeSubcategory.csv"));
            put("Bike", getClass().getResource("data/Bike.csv"));
            put("CalendarQuarter", getClass().getResource("data/CalendarQuarter.csv"));
            put("Country", getClass().getResource("data/Country.csv"));
            put("Currency", getClass().getResource("data/Currency.csv"));
            put("SalesChannel", getClass().getResource("data/SalesChannel.csv"));
            put("BikeSales", getClass().getResource("data/BikeSales.csv"));
        }};
    }

    @Override
    public DatabaseCheckSuiteSupplier dbCheckSupplier() {
        return new BikeShopDatabaseCheckSuiteSupplier();
    }



    @Override
    public DatabaseSupplier databaseSupplier() {
        return new BikeShopDatabaseSupplier();
    }

}
