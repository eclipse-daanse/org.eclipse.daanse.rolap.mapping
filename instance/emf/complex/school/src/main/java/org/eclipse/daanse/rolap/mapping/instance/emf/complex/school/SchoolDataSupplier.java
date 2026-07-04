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

import java.net.URL;
import java.util.LinkedHashMap;
import java.util.Map;

import org.eclipse.daanse.cwm.testkit.api.DataSupplier;

/**
 * Phase-2 DataSupplier for complex.school. Returns the 22 CSV resources
 * packaged under {@code ./data/} keyed by the matching table name in the
 * CWM Schema returned by {@link SchoolDatabaseSupplier}. CSVs are
 * header-only (no SQL-type row 2) — types come from the CWM columns.
 *
 * <p>Order is parent-before-child so the FK chain loads cleanly.
 */
public class SchoolDataSupplier implements DataSupplier {

    private static final String[] LOAD_ORDER = {
            "bundesland", "wohnort_landkreis", "alters_gruppe", "geschlecht",
            "schul_jahr", "schul_kategorie", "schul_art", "traeger_kategorie",
            "traeger_art", "traeger", "ganztags_art", "schule",
            "sonderpaed_foerderbedarf", "foerderung_art", "personal_art",
            "einschulung", "klassen_wiederholung", "migrations_hintergrund",
            "schul_abschluss", "fact_schulen", "fact_personal", "fact_schueler"
    };

    @Override
    public Map<String, URL> csvResources() {
        Map<String, URL> m = new LinkedHashMap<>();
        for (String t : LOAD_ORDER) {
            URL u = getClass().getResource("data/" + t + ".csv");
            if (u != null) {
                m.put(t, u);
            }
        }
        return m;
    }
}
