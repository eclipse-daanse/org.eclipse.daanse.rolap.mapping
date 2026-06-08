/*
 * Copyright (c) 2026 Contributors to the Eclipse Foundation.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 */
package org.eclipse.daanse.rolap.mapping.instance.emf.tutorial.cube.minimal;

import java.net.URL;
import java.util.Map;

import org.eclipse.daanse.olap.check.runtime.api.OlapCheckSuiteSupplier;
import org.eclipse.daanse.rolap.mapping.model.provider.CatalogMappingSupplier;
import org.eclipse.daanse.rolap.mapping.instance.api.CatalogTestInstance;
import org.eclipse.daanse.cwm.testkit.api.DatabaseSupplier;

/**
 * Auto-discovered test instance for tutorial.cube.minimal — pairs the
 * existing {@link CatalogSupplier} + {@link CheckSuiteSupplier} with the
 * single {@code Fact.csv} packaged under {@code /data/} in this jar.
 *
 * <p>Uses the Phase-1 csvResources path (CSV has its row-2 SQL-type row);
 * tables get created by the legacy {@code CsvDataImporter} from header + types.
 */
public class MinimalCubeTestInstance implements CatalogTestInstance {

    @Override
    public String name() {
        return "tutorial.cube.minimal";
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
        // Package-scoped path avoids classpath collision with other tutorial
        // jars that also ship a {@code /data/Fact.csv}.
        return Map.of("Fact", getClass().getResource("/data/Fact.csv"));
    }


    @Override
    public DatabaseSupplier databaseSupplier() {
        return new MinimalCubeDatabaseSupplier();
    }

}
