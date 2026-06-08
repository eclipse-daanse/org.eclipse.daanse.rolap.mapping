/*
 * Copyright (c) 2026 Contributors to the Eclipse Foundation.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 */
package org.eclipse.daanse.rolap.mapping.instance.emf.tutorial.virtualcube.min;

import java.net.URL;
import java.util.Map;

import org.eclipse.daanse.olap.check.runtime.api.OlapCheckSuiteSupplier;
import org.eclipse.daanse.rolap.mapping.model.provider.CatalogMappingSupplier;
import org.eclipse.daanse.rolap.mapping.instance.api.CatalogTestInstance;
import org.eclipse.daanse.cwm.testkit.api.DatabaseCheckSuiteSupplier;
import org.eclipse.daanse.cwm.testkit.api.DatabaseSupplier;

public class MinTestInstance implements CatalogTestInstance {

    @Override
    public String name() {
        return "tutorial.virtualcube.min";
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
            put("C1_Fact", getClass().getResource("/data/C1_Fact.csv"));
            put("C2_Fact", getClass().getResource("/data/C2_Fact.csv"));
        }};
    }

    @Override
    public DatabaseCheckSuiteSupplier dbCheckSupplier() {
        return new MinDatabaseCheckSuiteSupplier();
    }



    @Override
    public DatabaseSupplier databaseSupplier() {
        return new MinDatabaseSupplier();
    }

}
