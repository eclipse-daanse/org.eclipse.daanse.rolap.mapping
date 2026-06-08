/*
 * Copyright (c) 2026 Contributors to the Eclipse Foundation.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 */
package org.eclipse.daanse.rolap.mapping.instance.emf.tutorial.aggregation.aggregatetables;

import java.net.URL;
import java.util.Map;

import org.eclipse.daanse.olap.check.runtime.api.OlapCheckSuiteSupplier;
import org.eclipse.daanse.rolap.mapping.model.provider.CatalogMappingSupplier;
import org.eclipse.daanse.rolap.mapping.instance.api.CatalogTestInstance;
import org.eclipse.daanse.cwm.testkit.api.DatabaseSupplier;

public class AggregateTablesTestInstance implements CatalogTestInstance {

    @Override
    public String name() {
        return "tutorial.aggregation.aggregatetables";
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
            put("PRODUCT_CLASS", getClass().getResource("/data/PRODUCT_CLASS.csv"));
            put("PRODUCT", getClass().getResource("/data/PRODUCT.csv"));
            put("SALES_FACT_1997", getClass().getResource("/data/SALES_FACT_1997.csv"));
            put("AGG_C_14_SALES_FACT_1997", getClass().getResource("/data/AGG_C_14_SALES_FACT_1997.csv"));
            put("AGG_C_SPECIAL_SALES_FACT_1997", getClass().getResource("/data/AGG_C_SPECIAL_SALES_FACT_1997.csv"));
            put("AGG_LC_100_SALES_FACT_1997", getClass().getResource("/data/AGG_LC_100_SALES_FACT_1997.csv"));
            put("aGG_PL_01_SALES_FACT_1997", getClass().getResource("/data/aGG_PL_01_SALES_FACT_1997.csv"));
        }};
    }


    @Override
    public DatabaseSupplier databaseSupplier() {
        return new AggregateTablesDatabaseSupplier();
    }

}
