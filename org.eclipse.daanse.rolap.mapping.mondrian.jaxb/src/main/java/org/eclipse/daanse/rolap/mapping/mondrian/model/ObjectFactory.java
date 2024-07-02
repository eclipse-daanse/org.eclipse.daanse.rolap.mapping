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
 *   SmartCity Jena, Stefan Bischof - initial
 *
 */
package org.eclipse.daanse.rolap.mapping.mondrian.model;

import jakarta.xml.bind.annotation.XmlRegistry;

@XmlRegistry
public class ObjectFactory {

    public ObjectFactory() {
        // constructor
    }

    public AggColumnName createAggColumnName() {
        return new AggColumnName();
    }

    public Annotation createAnnotationsAnnotation() {
        return new Annotation();
    }

    public CalculatedMember createCalculatedMember() {
        return new CalculatedMember();
    }

    public CalculatedMemberProperty createCalculatedMemberProperty() {
        return new CalculatedMemberProperty();
    }

    public DimensionUsage createDimensionUsage() {
        return new DimensionUsage();
    }

    public DrillThroughAction createDrillThroughAction() {
        return new DrillThroughAction();
    }

    public DrillThroughAttribute createDrillThroughAttribute() {
        return new DrillThroughAttribute();
    }

    public DrillThroughMeasure createDrillThroughMeasure() {
        return new DrillThroughMeasure();
    }

    public ExpressionView createExpressionView() {
        return new ExpressionView();
    }

    public Hierarchy createHierarchy() {
        return new Hierarchy();
    }

    public InlineTable createHierarchyInlineTable() {
        return new InlineTable();
    }

    public ColumnDef createHierarchyInlineTableColumnDefsColumnDef() {
        return new ColumnDef();
    }

    public Row createHierarchyInlineTableRowsRow() {
        return new Row();
    }

    public Value createHierarchyInlineTableRowsRowValue() {
        return new Value();
    }

    public Join createHierarchyJoin() {
        return new Join();
    }

    public Level createHierarchyLevel() {
        return new Level();
    }

    public Closure createHierarchyLevelClosure() {
        return new Closure();
    }

    public Property createHierarchyLevelProperty() {
        return new Property();
    }

    public MemberReaderParameter createHierarchyMemberReaderParameter() {
        return new MemberReaderParameter();
    }

    public NamedSet createNamedSet() {
        return new NamedSet();
    }

    public PrivateDimension createPrivateDimension() {
        return new PrivateDimension();
    }

    public Schema createSchema() {
        return new Schema();
    }

    public Cube createSchemaCube() {
        return new Cube();
    }

    public Measure createSchemaCubeMeasure() {
        return new Measure();
    }

    public Parameter createSchemaParameter() {
        return new Parameter();
    }

    public Role createSchemaRole() {
        return new Role();
    }

    public SchemaGrant createSchemaRoleSchemaGrant() {
        return new SchemaGrant();
    }

    public CubeGrant createSchemaRoleSchemaGrantCubeGrant() {
        return new CubeGrant();
    }

    public DimensionGrant createSchemaRoleSchemaGrantCubeGrantDimensionGrant() {
        return new DimensionGrant();
    }

    public HierarchyGrant createSchemaRoleSchemaGrantCubeGrantHierarchyGrant() {
        return new HierarchyGrant();
    }

    public MemberGrant createSchemaRoleSchemaGrantCubeGrantHierarchyGrantMemberGrant() {
        return new MemberGrant();
    }

    public Union createSchemaRoleUnion() {
        return new Union();
    }

    public RoleUsage createSchemaRoleUnionRoleUsage() {
        return new RoleUsage();
    }

    public VirtualCube createSchemaVirtualCube() {
        return new VirtualCube();
    }

    public CubeUsage createSchemaVirtualCubeCubeUsagesCubeUsage() {
        return new CubeUsage();
    }

    public VirtualCubeDimension createSchemaVirtualCubeVirtualCubeDimension() {
        return new VirtualCubeDimension();
    }

    public VirtualCubeMeasure createSchemaVirtualCubeVirtualCubeMeasure() {
        return new VirtualCubeMeasure();
    }

    public SharedDimension createSharedDimension() {
        return new SharedDimension();
    }

    public SQL createSQL() {
        return new SQL();
    }

    public Table createTable() {
        return new Table();
    }

    public AggExclude createTableAggExclude() {
        return new AggExclude();
    }

    public AggName createTableAggName() {
        return new AggName();
    }

    public AggForeignKey createTableAggNameAggForeignKey() {
        return new AggForeignKey();
    }

    public AggLevel createTableAggNameAggLevel() {
        return new AggLevel();
    }

    public AggMeasure createTableAggNameAggMeasure() {
        return new AggMeasure();
    }

    public AggPattern createTableAggPattern() {
        return new AggPattern();
    }

    public AggExclude createTableAggPatternAggExclude() {
        return new AggExclude();
    }

    public AggForeignKey createTableAggPatternAggForeignKey() {
        return new AggForeignKey();
    }

    public AggLevel createTableAggPatternAggLevel() {
        return new AggLevel();
    }

    public AggMeasure createTableAggPatternAggMeasure() {
        return new AggMeasure();
    }

    /**
     * create an instance of {@link Hint }
     *
     */
    public Hint createTableHint() {
        return new Hint();
    }

    public View createView() {
        return new View();
    }

    public WritebackTable createWritebackTable() {
        return new WritebackTable();
    }

}
