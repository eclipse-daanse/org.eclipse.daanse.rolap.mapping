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
package org.eclipse.daanse.rolap.mapping.instance.emf.complex.school;

import org.eclipse.daanse.olap.check.runtime.api.OlapCheckSuiteSupplier;
import org.eclipse.daanse.rolap.mapping.model.provider.CatalogMappingSupplier;
import org.eclipse.daanse.rolap.mapping.instance.api.CatalogTestInstance;
import org.eclipse.daanse.cwm.testkit.api.DataSupplier;
import org.eclipse.daanse.cwm.testkit.api.DatabaseCheckSuiteSupplier;
import org.eclipse.daanse.cwm.testkit.api.DatabaseSupplier;

/**
 * Auto-discovered test instance for the complex.school catalog.
 * Pairs the existing {@link CatalogSupplier} + {@link CheckSuiteSupplier}
 * with the CSV files packaged under {@code ./data/} in this jar.
 */
public class SchoolTestInstance implements CatalogTestInstance {

    @Override
    public String name() {
        return "complex.school";
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
    public DatabaseCheckSuiteSupplier dbCheckSupplier() {
        return new SchoolDatabaseCheckSuiteSupplier();
    }

    @Override
    public DatabaseSupplier databaseSupplier() {
        return new SchoolDatabaseSupplier();
    }

    @Override
    public DataSupplier dataSupplier() {
        return new SchoolDataSupplier();
    }
}
