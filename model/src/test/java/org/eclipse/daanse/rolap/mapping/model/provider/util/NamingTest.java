/*
* Copyright (c) 2026 Contributors to the Eclipse Foundation.
*
* This program and the accompanying materials are made
* available under the terms of the Eclipse Public License 2.0
* which is available at https://www.eclipse.org/legal/epl-2.0/
*
* SPDX-License-Identifier: EPL-2.0
*/
package org.eclipse.daanse.rolap.mapping.model.provider.util;

import static org.assertj.core.api.Assertions.assertThat;

import org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory;
import org.eclipse.daanse.cwm.model.cwm.resource.relational.Schema;
import org.eclipse.daanse.cwm.model.cwm.resource.relational.Table;
import org.eclipse.daanse.rolap.mapping.model.catalog.Catalog;
import org.eclipse.daanse.rolap.mapping.model.catalog.CatalogFactory;
import org.eclipse.daanse.rolap.mapping.model.database.source.SourceFactory;
import org.eclipse.daanse.rolap.mapping.model.database.source.TableSource;
import org.eclipse.daanse.rolap.mapping.model.olap.cube.CubeFactory;
import org.eclipse.daanse.rolap.mapping.model.olap.cube.MeasureGroup;
import org.eclipse.daanse.rolap.mapping.model.olap.cube.PhysicalCube;
import org.eclipse.daanse.rolap.mapping.model.olap.dimension.DimensionConnector;
import org.eclipse.daanse.rolap.mapping.model.olap.dimension.DimensionFactory;
import org.eclipse.daanse.rolap.mapping.model.olap.dimension.StandardDimension;
import org.eclipse.daanse.rolap.mapping.model.olap.dimension.hierarchy.ExplicitHierarchy;
import org.eclipse.daanse.rolap.mapping.model.olap.dimension.hierarchy.HierarchyFactory;
import org.junit.jupiter.api.Test;

class NamingTest {

    @Test
    void derivesNamesFromContextAndNeverOverwrites() {
        Schema schema = RelationalFactory.eINSTANCE.createSchema();
        schema.setName("DWH");
        Table fact = RelationalFactory.eINSTANCE.createTable();
        fact.setName("FACT");
        schema.getOwnedElement().add(fact);

        TableSource source = SourceFactory.eINSTANCE.createTableSource();
        source.setTable(fact);

        StandardDimension dim = DimensionFactory.eINSTANCE.createStandardDimension();
        dim.setName("Zeit");
        ExplicitHierarchy hierarchy = HierarchyFactory.eINSTANCE.createExplicitHierarchy();
        dim.getHierarchies().add(hierarchy);

        DimensionConnector connector = DimensionFactory.eINSTANCE.createDimensionConnector();
        connector.setDimension(dim);

        PhysicalCube cube = CubeFactory.eINSTANCE.createPhysicalCube();
        cube.setName("Umsatz");
        cube.setSource(source);
        cube.getDimensionConnectors().add(connector);
        MeasureGroup group = CubeFactory.eINSTANCE.createMeasureGroup();
        cube.getMeasureGroups().add(group);

        Catalog catalog = CatalogFactory.eINSTANCE.createCatalog();
        catalog.setName("Sales");
        catalog.getImportedElement().add(schema);
        catalog.getOwnedElement().add(source);
        catalog.getOwnedElement().add(dim);
        catalog.getOwnedElement().add(hierarchy);
        catalog.getOwnedElement().add(cube);

        Naming.complete(catalog);

        assertThat(source.getName()).isEqualTo("FACT");
        assertThat(connector.getName()).isEqualTo("Zeit");
        // hierarchies stay unnamed on purpose: the empty name is the runtime
        // default "named after the using dimension", context-dependent
        assertThat(hierarchy.getName()).isNull();
        assertThat(group.getName()).isEqualTo("Umsatz");
        // set names stay untouched
        assertThat(cube.getName()).isEqualTo("Umsatz");
        assertThat(dim.getName()).isEqualTo("Zeit");
    }

    @Test
    void overrideNameWinsAndFallbackCounts() {
        Catalog catalog = CatalogFactory.eINSTANCE.createCatalog();
        catalog.setName("Sales");

        StandardDimension dim = DimensionFactory.eINSTANCE.createStandardDimension();
        dim.setName("Zeit");
        DimensionConnector withOverride = DimensionFactory.eINSTANCE.createDimensionConnector();
        withOverride.setDimension(dim);
        withOverride.setOverrideDimensionName("Bestelldatum");
        DimensionConnector bare = DimensionFactory.eINSTANCE.createDimensionConnector();

        PhysicalCube cube = CubeFactory.eINSTANCE.createPhysicalCube();
        cube.setName("Umsatz");
        cube.getDimensionConnectors().add(withOverride);
        cube.getDimensionConnectors().add(bare);
        catalog.getOwnedElement().add(cube);
        catalog.getOwnedElement().add(dim);

        Naming.complete(catalog);

        assertThat(withOverride.getName()).isEqualTo("Bestelldatum");
        // no context at all: honest class-plus-counter fallback
        assertThat(bare.getName()).isEqualTo("DimensionConnector-1");
    }

    @Test
    void completeIsIdempotent() {
        Catalog catalog = CatalogFactory.eINSTANCE.createCatalog();
        catalog.setName("Sales");
        PhysicalCube cube = CubeFactory.eINSTANCE.createPhysicalCube();
        cube.setName("Umsatz");
        MeasureGroup group = CubeFactory.eINSTANCE.createMeasureGroup();
        cube.getMeasureGroups().add(group);
        catalog.getOwnedElement().add(cube);

        Naming.complete(catalog);
        String first = group.getName();
        Naming.complete(catalog);

        assertThat(group.getName()).isEqualTo(first).isEqualTo("Umsatz");
    }
}
