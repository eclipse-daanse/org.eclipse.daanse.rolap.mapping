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
package org.eclipse.daanse.rolap.mapping.instance.api;

import java.net.URL;
import java.util.Map;

import org.eclipse.daanse.cwm.testkit.api.CsvAutoDetect;
import org.eclipse.daanse.cwm.testkit.api.DataSupplier;
import org.eclipse.daanse.cwm.testkit.api.DatabaseCheckSuiteSupplier;
import org.eclipse.daanse.cwm.testkit.api.DatabaseSupplier;
import org.eclipse.daanse.olap.check.runtime.api.OlapCheckSuiteSupplier;
import org.eclipse.daanse.rolap.mapping.model.provider.CatalogMappingSupplier;

/**
 * Uber SPI that bundles a catalog mapping, its check suite, and its CSV
 * data together. Each mapping-instance jar publishes one (or more) of
 * these via {@link java.util.ServiceLoader} so the testkit can auto-discover
 * and run them.
 *
 * <p>CSV data is auto-detected by default — implementers only override
 * {@link #csvResources()} when the default convention does not fit.
 */
public interface CatalogTestInstance {

    /**
     * Display name shown in JUnit dynamic-test reports, e.g. {@code "complex.school"}.
     */
    String name();

    /**
     * The mapping definition. Typically the same {@code CatalogSupplier}
     * instance already published by the mapping-instance module.
     */
    CatalogMappingSupplier mappingSupplier();

    /**
     * The check suite to execute against the catalog.
     */
    OlapCheckSuiteSupplier checkSuiteSupplier();

    /**
     * CSV files to load into the test database, keyed by target table name.
     *
     * <p>The default scans the classpath / jar for files matching
     * {@code <package-of-this-class>/data/*.csv}, sorted alphabetically.
     * Order matters: {@code CsvDataImporter} does not topologically sort by
     * foreign keys, so override this method to enforce parent-before-child
     * ordering if the data has FK dependencies.
     */
    default Map<String, URL> csvResources() {
        return CsvAutoDetect.detect(getClass(), "data");
    }

    // -----------------------------------------------------------------
    // Phase-2 layered SPIs. All optional. Returning null = "skip this layer."
    // -----------------------------------------------------------------

    /** Phase-2 Layer 1. Optional. Default: null = no database layer. */
    default DatabaseSupplier databaseSupplier() {
        return null;
    }

    /**
     * Phase-2 Layer 2. Optional. Default: a bridge that exposes
     * {@link #csvResources()} as a {@link DataSupplier} for backwards
     * compat. Override to provide programmatic data on top of (or instead
     * of) CSVs.
     */
    default DataSupplier dataSupplier() {
        Map<String, URL> csvs = csvResources();
        return new DataSupplier() {
            @Override public Map<String, URL> csvResources() { return csvs; }
        };
    }

    /** Phase-2 Layer 4b. Optional. Default: null = no database-shape checks. */
    default DatabaseCheckSuiteSupplier dbCheckSupplier() {
        return null;
    }
}
