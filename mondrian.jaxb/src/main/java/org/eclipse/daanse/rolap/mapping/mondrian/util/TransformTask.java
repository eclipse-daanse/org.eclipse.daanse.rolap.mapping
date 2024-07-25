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
package org.eclipse.daanse.rolap.mapping.mondrian.util;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Stream;

import org.eclipse.daanse.rolap.mapping.mondrian.model.AggColumnName;
import org.eclipse.daanse.rolap.mapping.mondrian.model.AggExclude;
import org.eclipse.daanse.rolap.mapping.mondrian.model.AggForeignKey;
import org.eclipse.daanse.rolap.mapping.mondrian.model.AggLevel;
import org.eclipse.daanse.rolap.mapping.mondrian.model.AggLevelProperty;
import org.eclipse.daanse.rolap.mapping.mondrian.model.AggMeasure;
import org.eclipse.daanse.rolap.mapping.mondrian.model.AggMeasureFactCount;
import org.eclipse.daanse.rolap.mapping.mondrian.model.AggName;
import org.eclipse.daanse.rolap.mapping.mondrian.model.AggPattern;
import org.eclipse.daanse.rolap.mapping.mondrian.model.AggTable;
import org.eclipse.daanse.rolap.mapping.mondrian.model.Closure;
import org.eclipse.daanse.rolap.mapping.mondrian.model.ColumnDef;
import org.eclipse.daanse.rolap.mapping.mondrian.model.CubeGrant;
import org.eclipse.daanse.rolap.mapping.mondrian.model.CubeUsage;
import org.eclipse.daanse.rolap.mapping.mondrian.model.DimensionGrant;
import org.eclipse.daanse.rolap.mapping.mondrian.model.DimensionOrDimensionUsage;
import org.eclipse.daanse.rolap.mapping.mondrian.model.DimensionTypeEnum;
import org.eclipse.daanse.rolap.mapping.mondrian.model.DimensionUsage;
import org.eclipse.daanse.rolap.mapping.mondrian.model.ElementFormatter;
import org.eclipse.daanse.rolap.mapping.mondrian.model.ExpressionView;
import org.eclipse.daanse.rolap.mapping.mondrian.model.Hierarchy;
import org.eclipse.daanse.rolap.mapping.mondrian.model.HierarchyGrant;
import org.eclipse.daanse.rolap.mapping.mondrian.model.Hint;
import org.eclipse.daanse.rolap.mapping.mondrian.model.InlineTable;
import org.eclipse.daanse.rolap.mapping.mondrian.model.Join;
import org.eclipse.daanse.rolap.mapping.mondrian.model.Level;
import org.eclipse.daanse.rolap.mapping.mondrian.model.Measure;
import org.eclipse.daanse.rolap.mapping.mondrian.model.MemberGrant;
import org.eclipse.daanse.rolap.mapping.mondrian.model.Property;
import org.eclipse.daanse.rolap.mapping.mondrian.model.PropertyTypeEnum;
import org.eclipse.daanse.rolap.mapping.mondrian.model.RelationOrJoin;
import org.eclipse.daanse.rolap.mapping.mondrian.model.Role;
import org.eclipse.daanse.rolap.mapping.mondrian.model.Row;
import org.eclipse.daanse.rolap.mapping.mondrian.model.SQL;
import org.eclipse.daanse.rolap.mapping.mondrian.model.Schema;
import org.eclipse.daanse.rolap.mapping.mondrian.model.SchemaGrant;
import org.eclipse.daanse.rolap.mapping.mondrian.model.Table;
import org.eclipse.daanse.rolap.mapping.mondrian.model.Value;
import org.eclipse.daanse.rolap.mapping.mondrian.model.View;
import org.eclipse.daanse.rolap.mapping.mondrian.model.VirtualCubeDimension;
import org.eclipse.daanse.rolap.mapping.mondrian.model.VirtualCubeMeasure;
import org.eclipse.daanse.rolap.mapping.pojo.AccessCubeGrantMappingImpl;
import org.eclipse.daanse.rolap.mapping.pojo.AccessDimensionGrantMappingImpl;
import org.eclipse.daanse.rolap.mapping.pojo.AccessHierarchyGrantMappingImpl;
import org.eclipse.daanse.rolap.mapping.pojo.AccessMemberGrantMappingImpl;
import org.eclipse.daanse.rolap.mapping.pojo.AccessRoleMappingImpl;
import org.eclipse.daanse.rolap.mapping.pojo.AccessSchemaGrantMappingImpl;
import org.eclipse.daanse.rolap.mapping.pojo.AggregationColumnNameMappingImpl;
import org.eclipse.daanse.rolap.mapping.pojo.AggregationExcludeMappingImpl;
import org.eclipse.daanse.rolap.mapping.pojo.AggregationForeignKeyMappingImpl;
import org.eclipse.daanse.rolap.mapping.pojo.AggregationLevelMappingImpl;
import org.eclipse.daanse.rolap.mapping.pojo.AggregationLevelPropertyMappingImpl;
import org.eclipse.daanse.rolap.mapping.pojo.AggregationMeasureFactCountMappingImpl;
import org.eclipse.daanse.rolap.mapping.pojo.AggregationMeasureMappingImpl;
import org.eclipse.daanse.rolap.mapping.pojo.AggregationNameMappingImpl;
import org.eclipse.daanse.rolap.mapping.pojo.AggregationPatternMappingImpl;
import org.eclipse.daanse.rolap.mapping.pojo.AggregationTableMappingImpl;
import org.eclipse.daanse.rolap.mapping.pojo.AnnotationMappingImpl;
import org.eclipse.daanse.rolap.mapping.pojo.CalculatedMemberMappingImpl;
import org.eclipse.daanse.rolap.mapping.pojo.CalculatedMemberPropertyMappingImpl;
import org.eclipse.daanse.rolap.mapping.pojo.CatalogMappingImpl;
import org.eclipse.daanse.rolap.mapping.pojo.CellFormatterMappingImpl;
import org.eclipse.daanse.rolap.mapping.pojo.CubeConnectorMappingImpl;
import org.eclipse.daanse.rolap.mapping.pojo.CubeMappingImpl;
import org.eclipse.daanse.rolap.mapping.pojo.DimensionConnectorMappingImpl;
import org.eclipse.daanse.rolap.mapping.pojo.DimensionMappingImpl;
import org.eclipse.daanse.rolap.mapping.pojo.DocumentationMappingImpl;
import org.eclipse.daanse.rolap.mapping.pojo.HierarchyMappingImpl;
import org.eclipse.daanse.rolap.mapping.pojo.InlineTableColumnDefinitionMappingImpl;
import org.eclipse.daanse.rolap.mapping.pojo.InlineTableQueryMappingImpl;
import org.eclipse.daanse.rolap.mapping.pojo.InlineTableRowCellMappingImpl;
import org.eclipse.daanse.rolap.mapping.pojo.InlineTableRowMappingImpl;
import org.eclipse.daanse.rolap.mapping.pojo.JoinQueryMappingImpl;
import org.eclipse.daanse.rolap.mapping.pojo.JoinedQueryElementMappingImpl;
import org.eclipse.daanse.rolap.mapping.pojo.KpiMappingImpl;
import org.eclipse.daanse.rolap.mapping.pojo.LevelMappingImpl;
import org.eclipse.daanse.rolap.mapping.pojo.MeasureGroupMappingImpl;
import org.eclipse.daanse.rolap.mapping.pojo.MeasureMappingImpl;
import org.eclipse.daanse.rolap.mapping.pojo.MemberFormatterMappingImpl;
import org.eclipse.daanse.rolap.mapping.pojo.MemberPropertyFormatterMappingImpl;
import org.eclipse.daanse.rolap.mapping.pojo.MemberPropertyMappingImpl;
import org.eclipse.daanse.rolap.mapping.pojo.MemberReaderParameterMappingImpl;
import org.eclipse.daanse.rolap.mapping.pojo.NamedSetMappingImpl;
import org.eclipse.daanse.rolap.mapping.pojo.ParentChildLinkMappingImpl;
import org.eclipse.daanse.rolap.mapping.pojo.PhysicalCubeMappingImpl;
import org.eclipse.daanse.rolap.mapping.pojo.QueryMappingImpl;
import org.eclipse.daanse.rolap.mapping.pojo.SQLExpressionMappingImpl;
import org.eclipse.daanse.rolap.mapping.pojo.SQLMappingImpl;
import org.eclipse.daanse.rolap.mapping.pojo.SchemaMappingImpl;
import org.eclipse.daanse.rolap.mapping.pojo.SqlSelectQueryMappingImpl;
import org.eclipse.daanse.rolap.mapping.pojo.StandardDimensionMappingImpl;
import org.eclipse.daanse.rolap.mapping.pojo.TableQueryMappingImpl;
import org.eclipse.daanse.rolap.mapping.pojo.TableQueryOptimizationHintMappingImpl;
import org.eclipse.daanse.rolap.mapping.pojo.TimeDimensionMappingImpl;
import org.eclipse.daanse.rolap.mapping.pojo.TranslationMappingImpl;
import org.eclipse.daanse.rolap.mapping.pojo.VirtualCubeMappingImpl;
import org.eclipse.daanse.rolap.mapping.pojo.WritebackAttributeMappingImpl;
import org.eclipse.daanse.rolap.mapping.pojo.WritebackMeasureMappingImpl;
import org.eclipse.daanse.rolap.mapping.pojo.WritebackTableMappingImpl;

public class TransformTask {

    private AtomicInteger counterMeasure = new AtomicInteger();
    private AtomicInteger counterPhysicalCube = new AtomicInteger();
    private AtomicInteger counterVirtualCube = new AtomicInteger();
    private AtomicInteger counterDimension = new AtomicInteger();
    private AtomicInteger counterHierarchy = new AtomicInteger();
    private AtomicInteger counterLevel = new AtomicInteger();
    private AtomicInteger counterAccessRole = new AtomicInteger();
    private AtomicInteger counterCalculatedMember = new AtomicInteger();
    private AtomicInteger counterCalculatedMemberProperty = new AtomicInteger();
    private AtomicInteger counterCellFormatter = new AtomicInteger();
    private AtomicInteger counterMemberFormatter = new AtomicInteger();
    private AtomicInteger counterNamedSet = new AtomicInteger();
    private AtomicInteger counterKpi = new AtomicInteger();
    private AtomicInteger counterProperty = new AtomicInteger();
    private AtomicInteger counterDimensionConnector = new AtomicInteger();
    private AtomicInteger counterAggregationName = new AtomicInteger();
    private AtomicInteger counterAggregationPattern = new AtomicInteger();
    private AtomicInteger counterAggregationExclude = new AtomicInteger();

    private Schema mondrianSchema;
    private CatalogMappingImpl catalog;

    private TransformTask() {
        // none
    }

    TransformTask(Schema schema) {
        this();
        this.mondrianSchema = schema;
    }

    private Optional<? extends CubeMappingImpl> findCubeByName(String cubeNameAsIdent) {
        return catalog.getCubes().stream().filter(c -> c.getName().equals(cubeNameAsIdent)).findAny();
    }

    private Optional<DimensionMappingImpl> findDimension(String name) {
        return catalog.getDimensions().stream().filter(d -> d.getName().equals(name)).findAny();
    }

    private Optional<LevelMappingImpl> findLevel(String name) {
        return catalog.getLevels().stream().filter(l -> l.getName().equals(name)).findAny();
    }

    private Optional<HierarchyMappingImpl> findHierarchy(String name) {
        return catalog.getHierarchies()
                .stream()
                .filter(h -> (h.getName() != null && h.getName().equals(name)))
                .findAny();
    }

    private Optional<MeasureMappingImpl> findMeasure(String name) {
        return catalog.getMeasures().stream().filter(m -> m.getName().equals(name)).findAny();
    }

    private Optional<DimensionMappingImpl> findDimensionByCubeNameByDimensionName(String cubeName,
            String dimensionName) {
        Optional<? extends CubeMappingImpl> oCube = findCubeByName(cubeName);
        if (oCube.isPresent()) {
            return findDimensionInCube(oCube.get(), dimensionName);
        }
        return Optional.empty();
    }

    private Optional<DimensionMappingImpl> findDimensionInCube(CubeMappingImpl cube, String name) {
        if (cube != null && cube.getDimensionConnectors() != null) {
            Optional<DimensionConnectorMappingImpl> oDimensionConnector = cube.getDimensionConnectors()
                    .stream()
                    .filter(dc -> dc.getDimension().getName().equals(name))
                    .findAny();
            if (oDimensionConnector.isPresent()) {
                return Optional.ofNullable(oDimensionConnector.get().getDimension());
            }
        }
        return Optional.empty();
    }

    private Optional<MeasureMappingImpl> findMeasureByCubeNameByMeasureName(String cubeName, String measureName) {
        Optional<? extends CubeMappingImpl> oCube = findCubeByName(cubeName);
        if (oCube.isPresent()) {
            return findMeasureInCube(oCube.get(), measureName);
        }
        return Optional.empty();
    }

    private Optional<MeasureMappingImpl> findMeasureInCube(CubeMappingImpl cube, String measureName) {
        if (cube != null && cube.getMeasureGroups() != null) {
            Optional<MeasureGroupMappingImpl> oMeasureGroup = cube.getMeasureGroups()
                    .stream()
                    .filter(mg -> mg.getMeasures().stream().anyMatch(m -> m.getName().equals(measureName)))
                    .findAny();
            if (oMeasureGroup.isPresent()) {
                return oMeasureGroup.get()
                        .getMeasures()
                        .stream()
                        .filter(m -> m.getName().equals(measureName))
                        .findAny();
            }
        }
        return Optional.empty();
    }

    private Optional<HierarchyMappingImpl> findHierarchyInDimensionConnectorByName(DimensionConnectorMappingImpl dc,
            String hierarchyName) {
        if (dc != null && dc.getDimension() != null && dc.getDimension().getHierarchies() != null) {
            return dc.getDimension().getHierarchies().stream().filter(h -> h.getName().equals(hierarchyName)).findAny();
        }
        return Optional.empty();
    }

    private Optional<Entry<String, String>> resolveDimensionConnectorNameHierarchyName(String unicalName) {
        if (unicalName != null) {
            String name = unicalName.replace("[", "").replace("]", "");
            String[] arr = name.split("\\.");
            if (arr.length > 1) {
                return Optional.of(Map.entry(arr[0], arr[1]));
            }
        }
        return Optional.empty();
    }

    CatalogMappingImpl transform() {

        catalog = CatalogMappingImpl.builder().build();
        SchemaMappingImpl s = SchemaMappingImpl.builder().build();

        List<DimensionMappingImpl> dimensionsShared = transformSharedDimensions(mondrianSchema.dimensions());
        catalog.getDimensions().addAll(dimensionsShared);
        List<PhysicalCubeMappingImpl> physicalCubes = transformPhysicalCubes(mondrianSchema.cubes());
        List<VirtualCubeMappingImpl> virtualCubes = transformVirtualCubes(mondrianSchema.virtualCubes());
        List<? extends CubeMappingImpl> allCubes = Stream.concat(physicalCubes.stream(), virtualCubes.stream())
                .toList();
        catalog.setCubes(allCubes);
        List<AccessRoleMappingImpl> accessRoles = transformRoles(mondrianSchema.roles());
        catalog.setAccessRoles(accessRoles);
        String accessRoleName = mondrianSchema.defaultRole();
        Optional<AccessRoleMappingImpl> oDefaultAccessRole = findAccessRole(accessRoles, accessRoleName);
        oDefaultAccessRole.ifPresent(ar -> s.setDefaultAccessRole(ar));

        s.setId("s_" + 1);
        s.setName(mondrianSchema.name());
        s.setDescription(mondrianSchema.description());
        s.setAccessRoles(accessRoles);
        s.setAnnotations(transformAnnotations(mondrianSchema.annotations()));
        s.setMeasuresDimensionName(mondrianSchema.measuresCaption());
        s.setCubes(allCubes);
        s.setDocumentation(transformDocumentation(mondrianSchema.documentation()));
        catalog.setSchemas(List.of(s));

        return catalog;
    }

    private DocumentationMappingImpl transformDocumentation(
            Optional<org.eclipse.daanse.rolap.mapping.mondrian.model.Documentation> documentation) {
        if (documentation.isPresent()) {
            return new DocumentationMappingImpl(documentation.get().documentation());
        }
        return null;
    }

    private List<VirtualCubeMappingImpl> transformVirtualCubes(
            List<org.eclipse.daanse.rolap.mapping.mondrian.model.VirtualCube> virtualCubes) {
        return virtualCubes.stream().map(this::transformVirtualCube).toList();
    }

    private VirtualCubeMappingImpl transformVirtualCube(
            org.eclipse.daanse.rolap.mapping.mondrian.model.VirtualCube virtualCube) {
        VirtualCubeMappingImpl vc = VirtualCubeMappingImpl.builder().build();
        vc.setId("vc_" + counterVirtualCube.incrementAndGet());
        vc.setName(virtualCube.name());
        vc.setDescription(virtualCube.description());
        vc.setCubeUsages(transformCubeConnector(virtualCube.cubeUsages()));
        vc.setDimensionConnectors(transformVirtualCubeDimensionConnectors(virtualCube.virtualCubeDimensions()));
        vc.setCalculatedMembers(
                transformCalculatedMembers(vc.getDimensionConnectors(), virtualCube.calculatedMembers()));
        vc.setNamedSets(transformNamedSets(virtualCube.namedSets()));
        vc.setKpis(transformKpis(virtualCube.kpis()));
        Optional<MeasureMappingImpl> oMeasure = findMeasure(virtualCube.defaultMeasure());
        oMeasure.ifPresent(m -> vc.setDefaultMeasure(m));
        vc.setEnabled(virtualCube.enabled());
        vc.setVisible(virtualCube.visible());
        vc.setMeasureGroups(transformVirtualCubeMeasureGroups(virtualCube.virtualCubeMeasures()));
        return vc;
    }

    private List<CubeConnectorMappingImpl> transformCubeConnector(List<CubeUsage> cubeUsages) {
        if (cubeUsages != null) {
            return cubeUsages.stream().map(this::transformCubeConnector).toList();
        }
        return List.of();
    }

    private CubeConnectorMappingImpl transformCubeConnector(CubeUsage virtualCubeMeasure) {
        CubeConnectorMappingImpl cubeConnector = CubeConnectorMappingImpl.builder().build();
        Optional<? extends CubeMappingImpl> oCube = findCubeByName(virtualCubeMeasure.cubeName());
        oCube.ifPresent(c -> cubeConnector.setCube(c));
        cubeConnector.setIgnoreUnrelatedDimensions(virtualCubeMeasure.ignoreUnrelatedDimensions());
        return cubeConnector;

    }

    private List<MeasureGroupMappingImpl> transformVirtualCubeMeasureGroups(
            List<VirtualCubeMeasure> virtualCubeMeasures) {
        return virtualCubeMeasures.stream().map(this::transformVirtualCubeMeasureGroup).toList();
    }

    private MeasureGroupMappingImpl transformVirtualCubeMeasureGroup(VirtualCubeMeasure virtualCubeMeasure) {
        MeasureGroupMappingImpl measureGroup = MeasureGroupMappingImpl.builder().build();
        Optional<MeasureMappingImpl> oMeasure = findMeasureByCubeNameByMeasureName(virtualCubeMeasure.cubeName(),
                virtualCubeMeasure.name());
        oMeasure.ifPresent(m -> measureGroup.getMeasures().add(m));
        measureGroup.setName(virtualCubeMeasure.name());
        return measureGroup;
    }

    private Optional<AccessRoleMappingImpl> findAccessRole(List<AccessRoleMappingImpl> accessRoles,
            String accessRoleName) {
        return accessRoles.stream().filter(ar -> ar.getName().equals(accessRoleName)).findAny();
    }

    private AccessCubeGrantMappingImpl transformAccessCubeGrant(CubeGrant cubeGrant) {
        AccessCubeGrantMappingImpl accessCubeGrant = AccessCubeGrantMappingImpl.builder().build();
        accessCubeGrant.setAccess(cubeGrant.access());
        String cubeNameAsIdent = cubeGrant.cube();
        Optional<? extends CubeMappingImpl> oCcube = findCubeByName(cubeNameAsIdent);
        oCcube.ifPresent(c -> accessCubeGrant.setCube(c));
        accessCubeGrant.setDimensionGrants(transformAccessDimensionGrants(cubeGrant.dimensionGrants()));
        accessCubeGrant.setHierarchyGrants(transformAccessHierarchyGrants(cubeGrant.hierarchyGrants()));
        return accessCubeGrant;
    }

    private List<AccessCubeGrantMappingImpl> transformAccessCubeGrants(List<CubeGrant> cubeGrants) {
        return cubeGrants.stream().map(this::transformAccessCubeGrant).toList();
    }

    private AccessDimensionGrantMappingImpl transformAccessDimensionGrant(DimensionGrant dimensionGrant) {
        AccessDimensionGrantMappingImpl accessDimensionGrant = AccessDimensionGrantMappingImpl.builder().build();
        accessDimensionGrant.setAccess(dimensionGrant.access().toString());
        Optional<DimensionMappingImpl> oDim = findDimension(dimensionGrant.dimension());
        oDim.ifPresent(d -> accessDimensionGrant.setDimension(d));
        return accessDimensionGrant;

    }

    private List<AccessDimensionGrantMappingImpl> transformAccessDimensionGrants(List<DimensionGrant> dimensionGrants) {
        return dimensionGrants.stream().map(this::transformAccessDimensionGrant).toList();
    }

    private AccessHierarchyGrantMappingImpl transformAccessHierarchyGrant(HierarchyGrant hierarchyGrant) {
        AccessHierarchyGrantMappingImpl accessHierarchyGrant = AccessHierarchyGrantMappingImpl.builder().build();
        accessHierarchyGrant.setAccess(hierarchyGrant.access() != null ? hierarchyGrant.access().toString() : null);
        Optional<LevelMappingImpl> oLvl = findLevel(prepareLevel(hierarchyGrant.bottomLevel()));
        oLvl.ifPresent(l -> accessHierarchyGrant.setBottomLevel(l));
        Optional<HierarchyMappingImpl> oHier = findHierarchy(hierarchyGrant.hierarchy());
        oHier.ifPresent(h -> accessHierarchyGrant.setHierarchy(h));
        accessHierarchyGrant.setRollupPolicy(hierarchyGrant.rollupPolicy());
        oLvl = findLevel(prepareLevel(hierarchyGrant.topLevel()));
        oLvl.ifPresent(l -> accessHierarchyGrant.setTopLevel(l));
        accessHierarchyGrant.setMemberGrants(transformAccessMemberGrants(hierarchyGrant.memberGrants()));
        return accessHierarchyGrant;

    }

    private String prepareLevel(String level) {
        if (level != null) {
            return level.replace("[", "").replace("]", "");
        }
        return null;
    }

    private List<AccessHierarchyGrantMappingImpl> transformAccessHierarchyGrants(List<HierarchyGrant> dimensionGrants) {
        return dimensionGrants.stream().map(this::transformAccessHierarchyGrant).toList();
    }

    private AccessMemberGrantMappingImpl transformAccessMemberGrant(MemberGrant memberGrant) {
        AccessMemberGrantMappingImpl accessMemberGrant = AccessMemberGrantMappingImpl.builder().build();
        accessMemberGrant.setAccess(memberGrant.access() != null ? memberGrant.access().toString() : null);
        accessMemberGrant.setMember(memberGrant.member());
        return accessMemberGrant;

    }

    private List<AccessMemberGrantMappingImpl> transformAccessMemberGrants(List<MemberGrant> memberGrants) {
        return memberGrants.stream().map(this::transformAccessMemberGrant).toList();
    }

    private AccessSchemaGrantMappingImpl transformAccessSchemaGrant(SchemaGrant schemaGrant) {
        AccessSchemaGrantMappingImpl accessSchemaGrant = AccessSchemaGrantMappingImpl.builder().build();
        accessSchemaGrant.setAccess(schemaGrant.access() != null ? schemaGrant.access().toString() : null);
        accessSchemaGrant.setCubeGrant(transformAccessCubeGrants(schemaGrant.cubeGrants()));
        return accessSchemaGrant;
    }

    private List<AccessSchemaGrantMappingImpl> transformAccessSchemaGrants(List<SchemaGrant> schemaGrants) {
        return schemaGrants.stream().map(this::transformAccessSchemaGrant).toList();
    }

    private AnnotationMappingImpl transformAnnotation(
            org.eclipse.daanse.rolap.mapping.mondrian.model.Annotation annotation) {
        AnnotationMappingImpl a = AnnotationMappingImpl.builder().build();
        a.setName(annotation.name());
        a.setValue(annotation.content());
        return a;
    }

    private List<AnnotationMappingImpl> transformAnnotations(
            List<org.eclipse.daanse.rolap.mapping.mondrian.model.Annotation> annotations) {
        return annotations.stream().map(this::transformAnnotation).toList();
    }

    private DimensionMappingImpl transformDimension(
            org.eclipse.daanse.rolap.mapping.mondrian.model.Dimension dimension) {

        DimensionMappingImpl dim = null;
        if (DimensionTypeEnum.TIME_DIMENSION.equals(dimension.type())) {
            dim = TimeDimensionMappingImpl.builder().build();
        } else {
            dim = StandardDimensionMappingImpl.builder().build();
        }
        dim.setId("d_" + counterDimension.incrementAndGet());
        dim.setName(dimension.name());
        dim.setDescription(dimension.description());
        dim.setUsagePrefix(dimension.usagePrefix());
        dim.setVisible(dimension.visible());
        dim.setAnnotations(transformAnnotations(dimension.annotations()));
        List<HierarchyMappingImpl> hierarchies = transformHierarchies(dimension.hierarchies());
        catalog.getHierarchies().addAll(hierarchies);
        dim.setHierarchies(hierarchies);
        return dim;
    }

    private DimensionConnectorMappingImpl transformVirtualCubeDimensionConnector(
            VirtualCubeDimension virtualCubeDimension) {
        DimensionConnectorMappingImpl dc = DimensionConnectorMappingImpl.builder().build();
//        dc.setId("dc_" + counterDimensionConnector.incrementAndGet());
        if (virtualCubeDimension.cubeName() != null) {
            Optional<DimensionMappingImpl> oDim = findDimensionByCubeNameByDimensionName(
                    virtualCubeDimension.cubeName(), virtualCubeDimension.name());
            oDim.ifPresent(d -> dc.setDimension(d));
        } else {
            Optional<DimensionMappingImpl> oDim = findDimension(virtualCubeDimension.name());
            oDim.ifPresent(d -> dc.setDimension(d));
        }
        dc.setVisible(virtualCubeDimension.visible());
        dc.setForeignKey(virtualCubeDimension.foreignKey());
        return dc;
    }

    private DimensionConnectorMappingImpl transformDimensionConnector(
            DimensionOrDimensionUsage dimensionUsageOrDimensions) {

        DimensionConnectorMappingImpl dc = DimensionConnectorMappingImpl.builder().build();
//        dc.setId("dc_" + counterDimensionConnector.incrementAndGet());
        if (dimensionUsageOrDimensions instanceof org.eclipse.daanse.rolap.mapping.mondrian.model.Dimension d) {
            DimensionMappingImpl dim = transformDimension(d);
            catalog.getDimensions().add(dim);
            dc.setDimension(dim);
            dc.setForeignKey(d.foreignKey());
        } else if (dimensionUsageOrDimensions instanceof DimensionUsage du) {
            Optional<DimensionMappingImpl> oDim = findDimension(du.source());
            oDim.ifPresent(d -> dc.setDimension(d));
            dc.setForeignKey(du.foreignKey());
            if (du.level() != null) {
                Optional<LevelMappingImpl> oLvl = findLevel(du.level());
                oLvl.ifPresent(l -> dc.setLevel(l));
            }
            dc.setOverrideDimensionName(du.name());
            dc.setVisible(du.visible());
            dc.setUsagePrefix(du.usagePrefix());
        }
        return dc;
    }

    private List<DimensionConnectorMappingImpl> transformVirtualCubeDimensionConnectors(
            List<VirtualCubeDimension> dimensionUsageOrDimensions) {
        return dimensionUsageOrDimensions.stream().map(this::transformVirtualCubeDimensionConnector).toList();
    }

    private List<DimensionConnectorMappingImpl> transformDimensionConnectors(
            List<DimensionOrDimensionUsage> dimensionUsageOrDimensions) {
        return dimensionUsageOrDimensions.stream().map(this::transformDimensionConnector).toList();
    }

    private List<HierarchyMappingImpl> transformHierarchies(List<Hierarchy> hierarchies) {
        return hierarchies.stream().map(this::transformHierarchy).toList();
    }

    private HierarchyMappingImpl transformHierarchy(Hierarchy hierarchy) {
        HierarchyMappingImpl h = HierarchyMappingImpl.builder().build();
        h.setId("h_" + counterHierarchy.incrementAndGet());
        h.setName(hierarchy.name());
        h.setAllLevelName(hierarchy.allLevelName());
        h.setAllMemberCaption(hierarchy.allMemberCaption());
        h.setAllMemberName(hierarchy.allMemberName());
        h.setDefaultMember(hierarchy.defaultMember());
        h.setDescription(hierarchy.description());
        h.setDisplayFolder(hierarchy.displayFolder());
        h.setHasAll(hierarchy.hasAll());
        h.setMemberReaderClass(hierarchy.memberReaderClass());
        h.setOrigin(hierarchy.origin());
        h.setPrimaryKey(hierarchy.primaryKey());
        h.setPrimaryKeyTable(hierarchy.primaryKeyTable());
        h.setQuery(transformQuery(hierarchy.relation()));
        h.setUniqueKeyLevelName(hierarchy.uniqueKeyLevelName());
        h.setVisible(hierarchy.visible());
        List<LevelMappingImpl> lvls = transformLevels(hierarchy.levels());
        catalog.getLevels().addAll(lvls);
        h.setLevels(lvls);
        List<MemberReaderParameterMappingImpl> mrps = transformMemberReaderParameters(
                hierarchy.memberReaderParameters());
        h.setMemberReaderParameters(mrps);

        return h;
    }

    private List<MemberReaderParameterMappingImpl> transformMemberReaderParameters(
            List<org.eclipse.daanse.rolap.mapping.mondrian.model.MemberReaderParameter> memberReaderParameters) {
        return memberReaderParameters.stream().map(this::transformtransformMemberReaderParameter).toList();
    }

    private MemberReaderParameterMappingImpl transformtransformMemberReaderParameter(
            org.eclipse.daanse.rolap.mapping.mondrian.model.MemberReaderParameter memberReaderParameter) {
        if (memberReaderParameter != null) {
            MemberReaderParameterMappingImpl mrp = MemberReaderParameterMappingImpl.builder().build();
            mrp.setName(memberReaderParameter.name());
            mrp.setValue(memberReaderParameter.value());
            return mrp;
        }
        return null;

    }

    private LevelMappingImpl transformLevel(Level level) {
        LevelMappingImpl l = LevelMappingImpl.builder().build();

        l.setId("l_" + counterLevel.incrementAndGet());
        l.setName(level.name());
        l.setDescription(level.description());
        l.setApproxRowCount(level.approxRowCount());
        l.setCaptionColumn(level.captionColumn());
        l.setCaptionExpression(transformSQLExpressionOfExpressionView(level.captionExpression()));
        l.setColumn(level.column());
        l.setHideMemberIf(level.hideMemberIf().getValue());
        if (level.internalType() != null) {
            l.setInternalType(level.internalType().getValue());
        }
        l.setKeyExpression(transformSQLExpressionOfExpressionView(level.keyExpression()));
        l.setLevelType(level.levelType().getValue());
        l.setMemberFormatter(transformMemberFormatter(level.memberFormatter()));
        l.setNameColumn(level.nameColumn());
        l.setNameExpression(transformSQLExpressionOfExpressionView(level.nameExpression()));
        l.setNullParentValue(level.nullParentValue());
        l.setOrdinalColumn(level.ordinalColumn());
        l.setOrdinalExpression(transformSQLExpressionOfExpressionView(level.ordinalExpression()));
        TableQueryMappingImpl tableQuery = TableQueryMappingImpl.builder().build();
        tableQuery.setName(level.table());
        l.setParentChildLink(transformParentChildLink(level.closure()));
        l.setParentColumn(level.parentColumn());
        l.setParentExpression(transformSQLExpressionOfExpressionView(level.parentExpression()));
//        l.setTable(level.table());
        l.setType(level.type().getValue());
        l.setUniqueMembers(level.uniqueMembers());
        l.setVisible(level.visible());
        l.setMemberProperties(transformMemberProperties(level.properties()));

        return l;
    }

    private List<MemberPropertyMappingImpl> transformMemberProperties(List<Property> properties) {
        return properties.stream().map(this::transformMemberProperty).toList();
    }

    private MemberPropertyMappingImpl transformMemberProperty(Property property) {
        if (property != null) {
            MemberPropertyMappingImpl mp = MemberPropertyMappingImpl.builder().build();
            mp.setAnnotations(transformAnnotations(property.annotations()));
            mp.setId("p_" + counterProperty.incrementAndGet());
            mp.setDescription(property.description());
            mp.setName(property.name());
            mp.setFormatter(transformMemberPropertyFormatter(property.formatter()));
            mp.setColumn(property.column());
            mp.setDependsOnLevelValue(property.dependsOnLevelValue());
            mp.setType(property.type() != null ? property.type().getValue() : PropertyTypeEnum.STRING.getValue());

            return mp;
        }
        return null;
    }

    private MemberPropertyFormatterMappingImpl transformMemberPropertyFormatter(String formatter) {
        if (formatter != null) {
            MemberPropertyFormatterMappingImpl mpf = MemberPropertyFormatterMappingImpl.builder().build();
            mpf.setRef(formatter);
        }
        return null;
    }

    private ParentChildLinkMappingImpl transformParentChildLink(Closure closure) {
        if (closure != null) {
            ParentChildLinkMappingImpl pchl = ParentChildLinkMappingImpl.builder().build();
            pchl.setTable(transformTableQuery(closure.table()));
            pchl.setChildColumn(closure.childColumn());
            pchl.setParentColumn(closure.parentColumn());
            return pchl;
        }
        return null;
    }

    private MemberFormatterMappingImpl transformMemberFormatter(ElementFormatter memberFormatter) {
        if (memberFormatter != null) {
            MemberFormatterMappingImpl mf = MemberFormatterMappingImpl.builder().build();
            if (memberFormatter.className() != null) {
                mf.setRef(memberFormatter.className());
            }
            mf.setId("mf_" + counterMemberFormatter.incrementAndGet());
            catalog.getFormatters().add(mf);
            return mf;
        }
        return null;
    }

    private SQLExpressionMappingImpl transformSQLExpressionOfExpressionView(ExpressionView expressionView) {
        if (expressionView != null) {
            SQLExpressionMappingImpl sqlExpression = SQLExpressionMappingImpl.builder().build();
            sqlExpression.setSqls(transformSqls(expressionView.sqls()));
            return sqlExpression;
        }
        return null;
    }

    private List<LevelMappingImpl> transformLevels(List<Level> levels) {
        return levels.stream().map(this::transformLevel).toList();
    }

    private MeasureMappingImpl transformMeasure(Measure measure) {
        MeasureMappingImpl m = MeasureMappingImpl.builder().build();
        m.setType(measure.aggregator());
        m.setId("m_" + counterMeasure.incrementAndGet());
        m.setBackColor(measure.backColor());
        m.setCellFormatter(transformCellFormatter(measure.cellFormatter()));
        m.setColumn(measure.column());
        if (measure.datatype() != null) {
            m.setDatatype(measure.datatype().toString());
        }
        m.setDisplayFolder(measure.displayFolder());
        m.setFormatString(measure.formatString());
        m.setName(measure.name());
        m.setVisible(measure.visible());
        if (measure.measureExpression() != null) {
            m.setMeasureExpression(transformSqlExpression(measure.measureExpression().sqls()));
        }
        return m;
    }

    private SQLExpressionMappingImpl transformSqlExpression(
            List<org.eclipse.daanse.rolap.mapping.mondrian.model.SQL> sqls) {
        SQLExpressionMappingImpl sqlExpression = SQLExpressionMappingImpl.builder().build();
        sqlExpression.setSqls(transformSqls(sqls));
        return sqlExpression;
    }

    private MeasureGroupMappingImpl transformMeasureGroup(List<Measure> measures) {
        List<MeasureMappingImpl> ms = transformMeasures(measures);
        catalog.getMeasures().addAll(ms);
        MeasureGroupMappingImpl measureGroup = MeasureGroupMappingImpl.builder().build();
        measureGroup.setName("");
        measureGroup.setMeasures(ms);
        return measureGroup;
    }

    private List<MeasureMappingImpl> transformMeasures(List<Measure> measures) {
        return measures.stream().map(this::transformMeasure).toList();
    }

    private PhysicalCubeMappingImpl transformPhysicalCube(org.eclipse.daanse.rolap.mapping.mondrian.model.Cube cube) {
        PhysicalCubeMappingImpl pc = PhysicalCubeMappingImpl.builder().build();
        pc.setId("pc_" + counterPhysicalCube.incrementAndGet());
        pc.setName(cube.name());
        pc.setCache(cube.cache());
        pc.setDescription(cube.description());
        pc.setEnabled(cube.enabled());

        QueryMappingImpl query = transformQuery(cube.fact());
        pc.setQuery(query);
        pc.setVisible(cube.visible());
        pc.setAnnotations(transformAnnotations(cube.annotations()));
        pc.setMeasureGroups(List.of(transformMeasureGroup(cube.measures())));
        pc.setDimensionConnectors(transformDimensionConnectors(cube.dimensionUsageOrDimensions()));

        pc.setCalculatedMembers(transformCalculatedMembers(pc.getDimensionConnectors(), cube.calculatedMembers()));
        pc.setNamedSets(transformNamedSets(cube.namedSets()));
        pc.setKpis(transformKpis(cube.kpis()));
        Optional<MeasureMappingImpl> oMeasure = findMeasure(cube.defaultMeasure());
        oMeasure.ifPresent(m -> pc.setDefaultMeasure(m));
        pc.setWritebackTable(transformWritebackTable(pc.getDimensionConnectors(), cube.writebackTable()));
        return pc;
    }

    private WritebackTableMappingImpl transformWritebackTable(List<DimensionConnectorMappingImpl> dimensionConnectors,
            Optional<org.eclipse.daanse.rolap.mapping.mondrian.model.WritebackTable> oWritebackTable) {
        if (oWritebackTable.isPresent()) {
            org.eclipse.daanse.rolap.mapping.mondrian.model.WritebackTable writebackTable = oWritebackTable.get();
            WritebackTableMappingImpl wbt = WritebackTableMappingImpl.builder().build();
            wbt.setWritebackAttribute(transformWritebackAttributes(dimensionConnectors, writebackTable.columns()));
            wbt.setWritebackMeasure(transformWritebackMeasures(writebackTable.columns()));
            wbt.setName(writebackTable.name());
            wbt.setSchema(writebackTable.schema());
            return wbt;
        }
        return null;
    }

    private List<WritebackMeasureMappingImpl> transformWritebackMeasures(
            List<org.eclipse.daanse.rolap.mapping.mondrian.model.WritebackColumn> column) {
        return column.stream()
                .filter(org.eclipse.daanse.rolap.mapping.mondrian.model.WritebackMeasure.class::isInstance)
                .map(wbm -> transformWritebackMeasure(
                        (org.eclipse.daanse.rolap.mapping.mondrian.model.WritebackMeasure) wbm))
                .toList();
    }

    private WritebackMeasureMappingImpl transformWritebackMeasure(
            org.eclipse.daanse.rolap.mapping.mondrian.model.WritebackMeasure writebackMeasure) {
        WritebackMeasureMappingImpl wbm = WritebackMeasureMappingImpl.builder().build();
        wbm.setColumn(writebackMeasure.column());
        wbm.setName(writebackMeasure.name());
        return wbm;
    }

    private List<WritebackAttributeMappingImpl> transformWritebackAttributes(
            List<DimensionConnectorMappingImpl> dimensionConnectors,
            List<org.eclipse.daanse.rolap.mapping.mondrian.model.WritebackColumn> column) {
        return column.stream()
                .filter(org.eclipse.daanse.rolap.mapping.mondrian.model.WritebackAttribute.class::isInstance)
                .map(wba -> transformWritebackAttribute(dimensionConnectors,
                        (org.eclipse.daanse.rolap.mapping.mondrian.model.WritebackAttribute) wba))
                .toList();
    }

    private WritebackAttributeMappingImpl transformWritebackAttribute(
            List<DimensionConnectorMappingImpl> dimensionConnectors,
            org.eclipse.daanse.rolap.mapping.mondrian.model.WritebackAttribute writebackAttribute) {
        WritebackAttributeMappingImpl wba = WritebackAttributeMappingImpl.builder().build();
        wba.setColumn(writebackAttribute.column());
        if (dimensionConnectors != null) {
            Optional<DimensionConnectorMappingImpl> oDimC = dimensionConnectors.stream()
                    .filter(dc -> dc.getOverrideDimensionName().equals(writebackAttribute.dimension()))
                    .findAny();
            oDimC.ifPresent(d -> wba.setDimension(d.getDimension()));
        }
        return wba;
    }

    private List<KpiMappingImpl> transformKpis(List<org.eclipse.daanse.rolap.mapping.mondrian.model.Kpi> kpis) {
        if (kpis != null) {
            return kpis.stream().map(this::transformKpi).toList();
        }
        return List.of();
    }

    private KpiMappingImpl transformKpi(org.eclipse.daanse.rolap.mapping.mondrian.model.Kpi kpiM) {
        KpiMappingImpl kpi = KpiMappingImpl.builder().build();
        kpi.setId("kpi_" + counterKpi.incrementAndGet());
        kpi.setDescription(kpiM.description());
        kpi.setName(kpiM.name());
        kpi.setAnnotations(transformAnnotations(kpiM.annotations()));
        kpi.setTranslations(transformTranslations(kpiM.translations()));
        kpi.setDisplayFolder(kpiM.displayFolder());
        kpi.setAssociatedMeasureGroupID(kpiM.associatedMeasureGroupID());
        kpi.setValue(kpiM.value());
        kpi.setGoal(kpiM.goal());
        kpi.setStatus(kpiM.status());
        kpi.setTrend(kpiM.trend());
        kpi.setWeight(kpiM.weight());
        kpi.setTrendGraphic(kpiM.trend());
        kpi.setStatusGraphic(kpiM.statusGraphic());
        kpi.setCurrentTimeMember(kpiM.currentTimeMember());
        kpi.setParentKpiID(kpiM.parentKpiID());
        return kpi;
    }

    private List<TranslationMappingImpl> transformTranslations(
            List<org.eclipse.daanse.rolap.mapping.mondrian.model.Translation> translations) {
        if (translations != null) {
            return translations.stream().map(this::transformTranslation).toList();
        }
        return List.of();
    }

    private TranslationMappingImpl transformTranslation(
            org.eclipse.daanse.rolap.mapping.mondrian.model.Translation translation) {
        TranslationMappingImpl t = TranslationMappingImpl.builder().build();
        t.setDescription(translation.description());
        t.setAnnotations(transformAnnotations(translation.annotations()));
        t.setLanguage(translation.language());
        t.setCaption(translation.caption());
        t.setDisplayFolder(translation.displayFolder());
        return t;
    }

    private List<NamedSetMappingImpl> transformNamedSets(
            List<org.eclipse.daanse.rolap.mapping.mondrian.model.NamedSet> namedSets) {
        return namedSets.stream().map(this::transformNamedSet).toList();
    }

    private NamedSetMappingImpl transformNamedSet(org.eclipse.daanse.rolap.mapping.mondrian.model.NamedSet namedSet) {
        NamedSetMappingImpl ns = NamedSetMappingImpl.builder().build();
        ns.setId("ns_" + counterNamedSet.incrementAndGet());
        ns.setDescription(namedSet.description());
        ns.setName(namedSet.name());
        ns.setAnnotations(transformAnnotations(namedSet.annotations()));
        ns.setDisplayFolder(namedSet.displayFolder());
        ns.setFormula(namedSet.formula());
        if (namedSet.formulaElement() != null) {
            ns.setFormula(namedSet.formulaElement().cdata());
        }
        return ns;
    }

    private List<CalculatedMemberMappingImpl> transformCalculatedMembers(
            List<DimensionConnectorMappingImpl> dimensionConnectors,
            List<org.eclipse.daanse.rolap.mapping.mondrian.model.CalculatedMember> calculatedMembers) {
        return calculatedMembers.stream().map(cm -> transformCalculatedMember(dimensionConnectors, cm)).toList();
    }

    private CalculatedMemberMappingImpl transformCalculatedMember(
            List<DimensionConnectorMappingImpl> dimensionConnectors,
            org.eclipse.daanse.rolap.mapping.mondrian.model.CalculatedMember calculatedMember) {
        CalculatedMemberMappingImpl cm = CalculatedMemberMappingImpl.builder().build();
        cm.setId("cm_" + counterCalculatedMember.incrementAndGet());
        cm.setName(calculatedMember.name());
        cm.setDescription(calculatedMember.description());
        cm.setCalculatedMemberProperties(
                transformCalculatedMemberProperties(calculatedMember.calculatedMemberProperties()));
        cm.setCellFormatter(transformCellFormatter(calculatedMember.cellFormatter()));
        cm.setFormula(calculatedMember.formula());
        if (calculatedMember.formulaElement() != null) {
            cm.setFormula(calculatedMember.formulaElement().cdata());
        }
        cm.setDisplayFolder(calculatedMember.displayFolder());
        cm.setFormatString(calculatedMember.formatString());

        cm.setParent(calculatedMember.parent());
//        cm.setVisible(calculatedMember.visible());
        // calculatedMember.dimension() is a deprecated pattern we do not support.
        // if hierarchy is null server must take default hierarchy of measure dimension
        Optional<Entry<String, String>> oRes = resolveDimensionConnectorNameHierarchyName(calculatedMember.hierarchy());
        if (oRes.isPresent()) {
            Entry<String, String> res = oRes.get();
            Optional<DimensionConnectorMappingImpl> oDimCon = dimensionConnectors.stream()
                    .filter(dc -> dc.getOverrideDimensionName().equals(res.getKey()))
                    .findAny();
            if (oDimCon.isPresent()) {
                cm.setDimensionConector(oDimCon.get());
                Optional<HierarchyMappingImpl> oHier = findHierarchyInDimensionConnectorByName(oDimCon.get(),
                        res.getValue());
                oHier.ifPresent(d -> cm.setHierarchy(d));
            }
        }
        return cm;
    }

    private List<CalculatedMemberPropertyMappingImpl> transformCalculatedMemberProperties(
            List<org.eclipse.daanse.rolap.mapping.mondrian.model.CalculatedMemberProperty> calculatedMemberProperties) {
        return calculatedMemberProperties.stream().map(this::transformCalculatedMemberProperty).toList();
    }

    private CalculatedMemberPropertyMappingImpl transformCalculatedMemberProperty(
            org.eclipse.daanse.rolap.mapping.mondrian.model.CalculatedMemberProperty calculatedMemberProperty) {
        CalculatedMemberPropertyMappingImpl cmp = CalculatedMemberPropertyMappingImpl.builder().build();
        cmp.setId("cmp_" + counterCalculatedMemberProperty.incrementAndGet());
        cmp.setDescription(calculatedMemberProperty.description());
        cmp.setName(calculatedMemberProperty.name());
        cmp.setAnnotations(transformAnnotations(calculatedMemberProperty.annotations()));
        cmp.setValue(calculatedMemberProperty.value());
        cmp.setExpression(calculatedMemberProperty.expression());
        return cmp;
    }

    private CellFormatterMappingImpl transformCellFormatter(
            org.eclipse.daanse.rolap.mapping.mondrian.model.CellFormatter cellFormatter) {
        if (cellFormatter != null) {
            CellFormatterMappingImpl cf = CellFormatterMappingImpl.builder().build();
            cf.setId("cf_" + counterCellFormatter.incrementAndGet());
            cf.setAnnotations(List.of());
            if (cellFormatter.className() != null) {
                cf.setRef(cellFormatter.className());
            }
            catalog.getFormatters().add(cf);
            return cf;
        }
        return null;
    }

    private List<PhysicalCubeMappingImpl> transformPhysicalCubes(
            List<org.eclipse.daanse.rolap.mapping.mondrian.model.Cube> cubes) {
        return cubes.stream().map(this::transformPhysicalCube).toList();
    }

    private QueryMappingImpl transformQuery(
            org.eclipse.daanse.rolap.mapping.mondrian.model.RelationOrJoin relationOrJoin) {

        if (relationOrJoin instanceof Table t) {
            return transformTableQuery(t);
        }
        if (relationOrJoin instanceof Join j) {
            return transformJoinQuery(j);
        }
        if (relationOrJoin instanceof InlineTable it) {
            return transformInlineTable(it);
        }
        if (relationOrJoin instanceof View v) {
            return transformSqlSelectQuery(v);
        }
        return null;

    }

    private SqlSelectQueryMappingImpl transformSqlSelectQuery(View v) {
        if (v != null) {
            SqlSelectQueryMappingImpl sqlSelectQuery = SqlSelectQueryMappingImpl.builder().build();
            sqlSelectQuery.setAlias(v.alias());
            sqlSelectQuery.setSQL(transformSqls(v.sqls()));
            return sqlSelectQuery;
        }
        return null;
    }

    private InlineTableQueryMappingImpl transformInlineTable(InlineTable it) {
        if (it != null) {
            InlineTableQueryMappingImpl inlineTableQuery = InlineTableQueryMappingImpl.builder().build();
            inlineTableQuery.setAlias(it.alias());
            inlineTableQuery.setColumnDefinitions(transformInlineTableColumnDefinitions(it.columnDefs()));
            inlineTableQuery.setRows(transformInlineTableRows(it.rows()));
            return inlineTableQuery;
        }
        return null;
    }

    private JoinQueryMappingImpl transformJoinQuery(Join j) {
        if (j != null) {
            JoinQueryMappingImpl joinQuery = JoinQueryMappingImpl.builder().build();
            RelationOrJoin rojl = j.relations() != null && j.relations().size() > 0 ? j.relations().get(0) : null;
            RelationOrJoin rojr = j.relations() != null && j.relations().size() > 1 ? j.relations().get(1) : null;
            joinQuery.setLeft(transformJoinedQueryElement(j.leftAlias(), j.leftKey(), rojl));
            joinQuery.setRight(transformJoinedQueryElement(j.rightAlias(), j.rightKey(), rojr));
            return joinQuery;
        }
        return null;
    }

    private TableQueryMappingImpl transformTableQuery(Table t) {
        if (t != null) {
            TableQueryMappingImpl tableQuery = TableQueryMappingImpl.builder().build();
            tableQuery.setAlias(t.alias());
            tableQuery.setName(t.name());
            tableQuery.setSchema(t.schema());
            SQL sql = t.sql();
            if (sql != null) {
                tableQuery.setSqlWhereExpression(transformSql(sql));
            }
            tableQuery.setAggregationExcludes(transformAggregationExcludes(t.aggExcludes()));
            tableQuery.setAggregationTables(transformAggregationTables(t.aggTables()));
            tableQuery.setOptimizationHints(transformTableQueryOptimizationHints(t.hints()));
            return tableQuery;
        }
        return null;
    }

    private List<SQLMappingImpl> transformSqls(List<org.eclipse.daanse.rolap.mapping.mondrian.model.SQL> sqls) {
        return sqls.stream().map(this::transformSql).toList();
    }

    private List<InlineTableColumnDefinitionMappingImpl> transformInlineTableColumnDefinitions(
            List<ColumnDef> columnDefs) {
        return columnDefs.stream().map(this::transformInlineTableColumnDefinition).toList();
    }

    private List<InlineTableRowMappingImpl> transformInlineTableRows(List<Row> rows) {
        return rows.stream().map(this::transformInlineTableRow).toList();
    }

    private InlineTableColumnDefinitionMappingImpl transformInlineTableColumnDefinition(ColumnDef columnDef) {
        InlineTableColumnDefinitionMappingImpl itcd = InlineTableColumnDefinitionMappingImpl.builder().build();
        itcd.setName(columnDef.name());
        if (columnDef.type() != null) {
            itcd.setType(columnDef.type().getValue());
        }
        return itcd;
    }

    private InlineTableRowMappingImpl transformInlineTableRow(Row row) {
        InlineTableRowMappingImpl itr = InlineTableRowMappingImpl.builder().build();
        itr.setCells(transformInlineTableRowCells(row.values()));
        return itr;
    }

    private List<InlineTableRowCellMappingImpl> transformInlineTableRowCells(List<Value> values) {
        return values.stream().map(this::transformInlineTableRowCell).toList();
    }

    private InlineTableRowCellMappingImpl transformInlineTableRowCell(Value value) {
        InlineTableRowCellMappingImpl itrc = InlineTableRowCellMappingImpl.builder().build();
        itrc.setValue(value.content());
        itrc.setColumnName(value.column());
        return itrc;
    }

    private JoinedQueryElementMappingImpl transformJoinedQueryElement(String alias, String key, RelationOrJoin roj) {
        JoinedQueryElementMappingImpl jqe = JoinedQueryElementMappingImpl.builder().build();
        jqe.setAlias(alias);
        jqe.setKey(key);
        jqe.setQuery(transformQuery(roj));
        return jqe;
    }

    private List<TableQueryOptimizationHintMappingImpl> transformTableQueryOptimizationHints(List<Hint> hints) {
        return hints.stream().map(this::transformTableQueryOptimizationHint).toList();
    }

    private TableQueryOptimizationHintMappingImpl transformTableQueryOptimizationHint(Hint aggTable) {
        TableQueryOptimizationHintMappingImpl h = TableQueryOptimizationHintMappingImpl.builder().build();
        h.setValue(aggTable.content());
        h.setType(aggTable.type());
        return h;
    }

    private List<AggregationTableMappingImpl> transformAggregationTables(List<AggTable> aggExcludes) {
        return aggExcludes.stream().map(this::transformAggregationTable).toList();
    }

    private AggregationTableMappingImpl transformAggregationTable(AggTable aggTable) {
        if (aggTable instanceof AggName aggName) {
            AggregationNameMappingImpl an = AggregationNameMappingImpl.builder().build();
            an.setId("an_" + counterAggregationName.incrementAndGet());
            an.setAggregationFactCount(transformAggregationColumnName(aggName.aggFactCount()));
            an.setAggregationIgnoreColumns(transformAggregationColumnNames(aggName.aggIgnoreColumns()));
            an.setAggregationForeignKeys(transformAggregationForeignKeys(aggName.aggForeignKeys()));
            an.setAggregationMeasures(transformAggregationMeasures(aggName.aggMeasures()));
            an.setAggregationLevels(transformAggregationLevels(aggName.aggLevels()));
            an.setAggregationMeasureFactCounts(transformAggregationMeasureFactCounts(aggName.measuresFactCounts()));
            an.setIgnorecase(aggName.ignorecase());
            an.setApproxRowCount(aggName.approxRowCount());
            an.setName(aggName.name());
            catalog.getAggregationTables().add(an);
            return an;
        }
        if (aggTable instanceof AggPattern aggPattern) {
            AggregationPatternMappingImpl ap = AggregationPatternMappingImpl.builder().build();
            ap.setId("ap_" + counterAggregationPattern.incrementAndGet());
            ap.setAggregationFactCount(transformAggregationColumnName(aggPattern.aggFactCount()));
            ap.setAggregationIgnoreColumns(transformAggregationColumnNames(aggPattern.aggIgnoreColumns()));
            ap.setAggregationForeignKeys(transformAggregationForeignKeys(aggPattern.aggForeignKeys()));
            ap.setAggregationMeasures(transformAggregationMeasures(aggPattern.aggMeasures()));
            ap.setAggregationLevels(transformAggregationLevels(aggPattern.aggLevels()));
            ap.setAggregationMeasureFactCounts(transformAggregationMeasureFactCounts(aggPattern.measuresFactCounts()));
            ap.setIgnorecase(aggPattern.ignorecase());
            ap.setPattern(aggPattern.pattern());
            ap.setExcludes(transformAggregationExcludes(aggPattern.aggExcludes()));
            catalog.getAggregationTables().add(ap);
            return ap;
        }
        return null;
    }

    private List<AggregationMeasureFactCountMappingImpl> transformAggregationMeasureFactCounts(
            List<AggMeasureFactCount> aggMeasureFactCount) {
        if (aggMeasureFactCount != null) {
            return aggMeasureFactCount.stream().map(this::transformAggregationMeasureFactCount).toList();
        }
        return List.of();
    }

    private AggregationMeasureFactCountMappingImpl transformAggregationMeasureFactCount(
            AggMeasureFactCount aggMeasureFactCount) {
        AggregationMeasureFactCountMappingImpl amfc = AggregationMeasureFactCountMappingImpl.builder().build();
        amfc.setColumn(aggMeasureFactCount.column());
        amfc.setFactColumn(aggMeasureFactCount.factColumn());
        return amfc;
    }

    private List<AggregationLevelMappingImpl> transformAggregationLevels(List<AggLevel> aggLevels) {
        return aggLevels.stream().map(this::transformAggregationLevel).toList();
    }

    private AggregationLevelMappingImpl transformAggregationLevel(AggLevel aggLevel) {
        AggregationLevelMappingImpl al = AggregationLevelMappingImpl.builder().build();
        al.setAggregationLevelProperties(transformAggregationLevelProperties(aggLevel.properties()));
        al.setCaptionColumn(aggLevel.captionColumn());
        al.setCollapsed(aggLevel.collapsed());
        al.setColumn(aggLevel.column());
        al.setName(aggLevel.name());
        al.setNameColumn(aggLevel.nameColumn());
        al.setOrdinalColumn(aggLevel.ordinalColumn());
        return al;
    }

    private List<AggregationLevelPropertyMappingImpl> transformAggregationLevelProperties(
            List<AggLevelProperty> aggLevelProperties) {
        return aggLevelProperties.stream().map(this::transformAggregationLevelProperty).toList();
    }

    private AggregationLevelPropertyMappingImpl transformAggregationLevelProperty(AggLevelProperty aggLevelProperty) {
        AggregationLevelPropertyMappingImpl ap = AggregationLevelPropertyMappingImpl.builder().build();
        ap.setColumn(aggLevelProperty.column());
        ap.setName(aggLevelProperty.name());
        return ap;
    }

    private List<AggregationMeasureMappingImpl> transformAggregationMeasures(List<AggMeasure> aggregationMeasures) {
        return aggregationMeasures.stream().map(this::transformAggregationMeasure).toList();
    }

    private AggregationMeasureMappingImpl transformAggregationMeasure(AggMeasure aggMeasure) {
        AggregationMeasureMappingImpl am = AggregationMeasureMappingImpl.builder().build();
        am.setColumn(aggMeasure.column());
        am.setName(aggMeasure.name());
        am.setRollupType(aggMeasure.rollupType());
        return am;
    }

    private List<AggregationForeignKeyMappingImpl> transformAggregationForeignKeys(List<AggForeignKey> aggForeignKeys) {
        return aggForeignKeys.stream().map(this::transformAggregationForeignKey).toList();
    }

    private AggregationForeignKeyMappingImpl transformAggregationForeignKey(AggForeignKey aggForeignKey) {
        AggregationForeignKeyMappingImpl afk = AggregationForeignKeyMappingImpl.builder().build();
        afk.setAggregationColumn(aggForeignKey.aggColumn());
        afk.setFactColumn(aggForeignKey.factColumn());
        return afk;
    }

    private List<AggregationColumnNameMappingImpl> transformAggregationColumnNames(List<AggColumnName> aggColumnNames) {
        return aggColumnNames.stream().map(this::transformAggregationColumnName).toList();
    }

    private AggregationColumnNameMappingImpl transformAggregationColumnName(AggColumnName aggColumnName) {
        AggregationColumnNameMappingImpl acn = AggregationColumnNameMappingImpl.builder().build();
        acn.setColumn(aggColumnName.column());
        return acn;
    }

    private List<AggregationExcludeMappingImpl> transformAggregationExcludes(List<AggExclude> aggExcludes) {
        return aggExcludes.stream().map(this::transformAggregationExclude).toList();
    }

    private AggregationExcludeMappingImpl transformAggregationExclude(AggExclude aggExclude) {
        AggregationExcludeMappingImpl ae = AggregationExcludeMappingImpl.builder().build();
        ae.setId("ae_" + counterAggregationExclude.incrementAndGet());
        ae.setIgnorecase(aggExclude.ignorecase());
        ae.setName(aggExclude.name());
        ae.setPattern(aggExclude.pattern());
        catalog.getAggregationExcludes().add(ae);
        return ae;
    }

    private AccessRoleMappingImpl transformRole(Role role) {
        AccessRoleMappingImpl accessRole = AccessRoleMappingImpl.builder().build();
        accessRole.setName(role.name());
        accessRole.setId("ar_" + counterAccessRole.incrementAndGet());
        accessRole.setDescription(role.description());
        accessRole.setAccessSchemaGrants(transformAccessSchemaGrants(role.schemaGrants()));
        accessRole.setAnnotations(transformAnnotations(role.annotations()));
        accessRole.getReferencedAccessRoles();
        return accessRole;
    }

    private List<AccessRoleMappingImpl> transformRoles(List<Role> roles) {
        return roles.stream().map(this::transformRole).toList();
    }

    private List<DimensionMappingImpl> transformSharedDimensions(
            List<org.eclipse.daanse.rolap.mapping.mondrian.model.Dimension> sharedDimensions) {
        return sharedDimensions.stream().map(this::transformDimension).toList();
    }

    private SQLMappingImpl transformSql(org.eclipse.daanse.rolap.mapping.mondrian.model.SQL sql) {
        SQLMappingImpl s = SQLMappingImpl.builder()
                .withStatement(sql.content())
                .withDialects(List.of(sql.dialect()))
                .build();

        return s;
    }

}
