/*
 * Copyright (c) 2026 Contributors to the Eclipse Foundation.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 */
package org.eclipse.daanse.rolap.mapping.instance.emf.tutorial.cube.dimension.intro;

import java.net.URL;
import java.util.Map;

import org.eclipse.daanse.olap.check.runtime.api.OlapCheckSuiteSupplier;
import org.eclipse.daanse.rolap.mapping.model.provider.CatalogMappingSupplier;
import org.eclipse.daanse.rolap.mapping.instance.api.CatalogTestInstance;
import org.eclipse.daanse.cwm.testkit.api.DatabaseSupplier;

/**
 * Auto-discovered test instance for tutorial.cube.dimension.intro — a
 * single cube with one dimension (theDimension → theHierarchy → theLevel)
 * and one measure ('theMeasure') backed by an 8-row Fact.csv summing to 378.
 */
public class DimensionIntroTestInstance implements CatalogTestInstance {

    @Override
    public String name() {
        return "tutorial.cube.dimension.intro";
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
        return Map.of("Fact", getClass().getResource("data/Fact.csv"));
    }


    @Override
    public DatabaseSupplier databaseSupplier() {
        return new DimensionIntroDatabaseSupplier();
    }

}
