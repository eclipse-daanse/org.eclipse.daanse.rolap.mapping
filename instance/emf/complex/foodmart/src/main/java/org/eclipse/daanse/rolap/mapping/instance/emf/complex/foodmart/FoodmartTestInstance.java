/*
 * Copyright (c) 2026 Contributors to the Eclipse Foundation.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   SmartCity Jena, Stefan Bischof - initial
 */
package org.eclipse.daanse.rolap.mapping.instance.emf.complex.foodmart;

import java.net.URL;
import java.util.LinkedHashMap;
import java.util.Map;

import org.eclipse.daanse.olap.check.runtime.api.OlapCheckSuiteSupplier;
import org.eclipse.daanse.rolap.mapping.model.provider.CatalogMappingSupplier;
import org.eclipse.daanse.rolap.mapping.instance.api.CatalogTestInstance;
import org.eclipse.daanse.cwm.testkit.api.DatabaseCheckSuiteSupplier;
import org.eclipse.daanse.cwm.testkit.api.DatabaseSupplier;

/**
 * Auto-discovered test instance for the FoodMart catalog — Mondrian's
 * canonical demo cube. Ships 37 CSV resources under
 * {@code <package>/data/<table>.csv}, each header + row-2 SQL types +
 * data rows (Phase-1 csvResources path).
 *
 * <p>Load order is parent-before-child so referential integrity holds for
 * the inventory / sales fact tables and the aggregate cubes.
 */
public class FoodmartTestInstance implements CatalogTestInstance {

    /** Topologically-sorted table list: dimensions first, then facts. */
    private static final String[] LOAD_ORDER = {
            // dimensions / lookup tables (no FK in)
            "account", "category", "currency", "days", "department", "position",
            "product_class", "promotion", "region", "store_ragged", "time_by_day",
            "warehouse_class",
            // depend on dimensions above
            "product", "store", "warehouse", "employee_closure", "employee",
            "reserve_employee", "customer", "salary", "expense_fact",
            // fact tables
            "inventory_fact_1997", "inventory_fact_1998",
            "sales_fact_1997", "sales_fact_1998", "sales_fact_dec_1998",
            // aggregate tables
            "agg_c_10_sales_fact_1997", "agg_c_14_sales_fact_1997",
            "agg_c_special_sales_fact_1997", "agg_g_ms_pcat_sales_fact_1997",
            "agg_l_03_sales_fact_1997", "agg_l_04_sales_fact_1997",
            "agg_l_05_sales_fact_1997", "agg_lc_06_sales_fact_1997",
            "agg_lc_100_sales_fact_1997", "agg_ll_01_sales_fact_1997",
            "agg_pl_01_sales_fact_1997"
    };

    @Override
    public String name() {
        return "complex.foodmart";
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
        Map<String, URL> m = new LinkedHashMap<>();
        for (String t : LOAD_ORDER) {
            URL u = getClass().getResource("/data/" + t + ".csv");
            if (u != null) {
                m.put(t, u);
            }
        }
        return m;
    }

    @Override
    public DatabaseCheckSuiteSupplier dbCheckSupplier() {
        return new FoodmartDatabaseCheckSuiteSupplier();
    }



    @Override
    public DatabaseSupplier databaseSupplier() {
        return new FoodmartDatabaseSupplier();
    }

}
