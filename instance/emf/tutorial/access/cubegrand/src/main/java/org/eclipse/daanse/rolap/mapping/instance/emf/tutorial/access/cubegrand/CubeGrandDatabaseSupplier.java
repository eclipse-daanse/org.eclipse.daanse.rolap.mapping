/*
 * Copyright (c) 2026 Contributors to the Eclipse Foundation.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 */
package org.eclipse.daanse.rolap.mapping.instance.emf.tutorial.access.cubegrand;

import org.eclipse.daanse.cwm.model.cwm.resource.relational.Schema;
import org.eclipse.daanse.cwm.testkit.api.DatabaseSupplier;

public class CubeGrandDatabaseSupplier implements DatabaseSupplier {

    @Override
    public Schema schema() {
        return new CatalogSupplier().get().getDbschemas().get(0);
    }
}
