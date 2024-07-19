/*
 * Copyright (c) 2024 Contributors to the Eclipse Foundation.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *
 */
package org.eclipse.daanse.rolap.mapping.pojo.tutorials;

import java.util.List;

import org.eclipse.daanse.rolap.mapping.api.model.CubeMapping;
import org.eclipse.daanse.rolap.mapping.api.model.DocumentationMapping;
import org.eclipse.daanse.rolap.mapping.pojo.DocumentationMappingImpl;
import org.eclipse.daanse.rolap.mapping.pojo.MeasureGroupMappingImpl;
import org.eclipse.daanse.rolap.mapping.pojo.PhysicalCubeMappingImpl;
import org.eclipse.daanse.rolap.mapping.pojo.SchemaMappingImpl;
import org.eclipse.daanse.rolap.mapping.pojo.SumMeasureMappingImpl;
import org.eclipse.daanse.rolap.mapping.pojo.TableQueryMappingImpl;

public class MinimalCubeWithOneMeasureSchema extends SchemaMappingImpl {

    private final static String description = "Schema of a minimal cube containing only one measurement but no other " +
        "dimensions";
    private final static String documentationValue = """
        A basic OLAP schema with a minimal cube
        Data cubes (<Cube>) are defined in an OLAP schema (<Schema>). Within the schema the name of each data cube must be unique.
        This example schema contains one cube named "CubeOneMeasure".

        A cube is based on a fact table (<Table>) which refers to a database table containing one or more measurements to be aggregated (and optionally further columns defining factual dimensions).
        In this case the database table representing the fact table is named "Fact" in the database, which is adressed in the name attribute within the <Table> tag.

        Each measurement of the cube is defined in a separate <Measure> element.
        The measurement in this example cube is named "Measure-Sum" (name attribute). It corresponds to the "VALUE" column (column attribute) in the database table "Fact" and is aggregated by summation (aggregator attribute).
        """;
    private final static String name = "01_Minimal_Cube_With_One_Measure";
    private final static DocumentationMappingImpl documentation = new DocumentationMappingImpl(documentationValue);
    private final static PhysicalCubeMappingImpl cube = new PhysicalCubeMappingImpl();
    private final static TableQueryMappingImpl table = new TableQueryMappingImpl();
    private final static MeasureGroupMappingImpl measureGroup = new MeasureGroupMappingImpl();
    private final static SumMeasureMappingImpl measure = new SumMeasureMappingImpl();

    static {
        table.setName("Fact");
        measure.setName("Measure-Sum");
        measure.setColumn("VALUE");
        measureGroup.setMeasures(List.of(measure));
        cube.setName("CubeOneMeasure");
        cube.setQuery(table);
        cube.setMeasureGroups(List.of(measureGroup));
    }

    @Override
    public DocumentationMapping getDocumentation() {
        return documentation;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public List<? extends CubeMapping> getCubes() {
        return List.of(cube);
    }

}
