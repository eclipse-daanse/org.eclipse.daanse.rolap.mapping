/*
 * Copyright (c) 2026 Contributors to the Eclipse Foundation.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 */
package org.eclipse.daanse.rolap.mapping.instance.emf.tutorial.cube.sqlview;

import java.net.URL;
import java.util.Map;

import org.eclipse.daanse.cwm.testkit.api.DataSupplier;

/**
 * Phase-2 DataSupplier for tutorial.cube.sqlview. The single FACT CSV is
 * header-only (no SQL-type row 2) — types come from the CWM columns of
 * {@link SqlViewDatabaseSupplier}. Package-scoped resource path avoids
 * classpath collisions with other tutorial jars shipping a /data/FACT.csv.
 */
public class SqlViewDataSupplier implements DataSupplier {

    @Override
    public Map<String, URL> csvResources() {
        return Map.of("FACT", getClass().getResource("data/FACT.csv"));
    }
}
