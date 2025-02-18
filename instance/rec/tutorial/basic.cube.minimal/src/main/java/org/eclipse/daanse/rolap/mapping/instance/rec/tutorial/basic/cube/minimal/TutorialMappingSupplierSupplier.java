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
package org.eclipse.daanse.rolap.mapping.instance.rec.tutorial.basic.cube.minimal;

import java.util.List;

import org.eclipse.daanse.rolap.mapping.api.CatalogMappingSupplier;
import org.eclipse.daanse.rolap.mapping.api.model.CatalogMapping;
import org.eclipse.daanse.rolap.mapping.api.model.enums.ColumnType;
import org.eclipse.daanse.rolap.mapping.api.model.enums.MeasureAggregatorType;
import org.eclipse.daanse.rolap.mapping.instance.api.Kind;
import org.eclipse.daanse.rolap.mapping.instance.api.MappingInstance;
import org.eclipse.daanse.rolap.mapping.instance.api.Source;
import org.eclipse.daanse.rolap.mapping.pojo.CatalogMappingImpl;
import org.eclipse.daanse.rolap.mapping.pojo.ColumnMappingImpl;
import org.eclipse.daanse.rolap.mapping.pojo.DatabaseSchemaMappingImpl;
import org.eclipse.daanse.rolap.mapping.pojo.MeasureGroupMappingImpl;
import org.eclipse.daanse.rolap.mapping.pojo.MeasureMappingImpl;
import org.eclipse.daanse.rolap.mapping.pojo.PhysicalCubeMappingImpl;
import org.eclipse.daanse.rolap.mapping.pojo.PhysicalTableMappingImpl;
import org.eclipse.daanse.rolap.mapping.pojo.PhysicalTableMappingImpl.Builder;
import org.eclipse.daanse.rolap.mapping.pojo.TableQueryMappingImpl;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.ServiceScope;

@MappingInstance(kind = Kind.TUTORIAL, source = Source.POJO, number = "2")
@Component(service =  CatalogMappingSupplier.class, scope = ServiceScope.PROTOTYPE)
public class TutorialMappingSupplierSupplier implements CatalogMappingSupplier {

    private final static String name = "Minimal Physical Cube";


    private final static ColumnMappingImpl VALUE_COLUMN = ColumnMappingImpl.builder().withName("VALUE").withType(ColumnType.INTEGER).build();
    private final static PhysicalTableMappingImpl factTable = ((Builder) PhysicalTableMappingImpl.builder().withName(name).withColumns(List.of(VALUE_COLUMN))).build();

    private final static TableQueryMappingImpl tableQuery = TableQueryMappingImpl.builder().withTable(factTable).build();

    private final static MeasureMappingImpl measure = MeasureMappingImpl.builder()
            .withName("Measure-Sum")
            .withColumn(VALUE_COLUMN)
            .withAggregatorType(MeasureAggregatorType.SUM)
            .build();

    private final static MeasureGroupMappingImpl measureGroup = MeasureGroupMappingImpl.builder()
            .withMeasures(List.of(measure))
            .build();

    private final static PhysicalCubeMappingImpl cube = PhysicalCubeMappingImpl.builder()
            .withName("CubeOneMeasure")
            .withQuery(tableQuery)
            .withMeasureGroups(List.of(measureGroup))
            .build();

    private final static CatalogMappingImpl schema = CatalogMappingImpl.builder()
            .withName("AnySchemaName")
            .withCubes(List.of(cube))
            .withDbSchemas(List.of(DatabaseSchemaMappingImpl.builder()
                    .withName(name)
                    .withTables(List.of(factTable))
                    .build()))
            .build();

    @Override
    public CatalogMapping get() {
        return schema;
    }

}
