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

import org.eclipse.daanse.rolap.mapping.api.model.CalculatedMemberMapping;
import org.eclipse.daanse.rolap.mapping.api.model.MeasureMapping;
import org.eclipse.daanse.rolap.mapping.api.model.PhysicalCubeMapping;
import org.eclipse.daanse.rolap.mapping.api.model.RowMapping;
import org.eclipse.daanse.rolap.mapping.api.model.RowValueMapping;
import org.eclipse.daanse.rolap.mapping.api.model.enums.AccessCatalog;
import org.eclipse.daanse.rolap.mapping.api.model.enums.AccessCube;
import org.eclipse.daanse.rolap.mapping.api.model.enums.AccessDimension;
import org.eclipse.daanse.rolap.mapping.api.model.enums.AccessHierarchy;
import org.eclipse.daanse.rolap.mapping.api.model.enums.AccessMember;
import org.eclipse.daanse.rolap.mapping.api.model.enums.InternalDataType;
import org.eclipse.daanse.rolap.mapping.api.model.enums.HideMemberIfType;
import org.eclipse.daanse.rolap.mapping.api.model.enums.LevelType;
import org.eclipse.daanse.rolap.mapping.api.model.enums.RollupPolicyType;
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
import org.eclipse.daanse.rolap.mapping.pojo.AccessCatalogGrantMappingImpl;
import org.eclipse.daanse.rolap.mapping.pojo.AccessCubeGrantMappingImpl;
import org.eclipse.daanse.rolap.mapping.pojo.AccessDimensionGrantMappingImpl;
import org.eclipse.daanse.rolap.mapping.pojo.AccessHierarchyGrantMappingImpl;
import org.eclipse.daanse.rolap.mapping.pojo.AccessMemberGrantMappingImpl;
import org.eclipse.daanse.rolap.mapping.pojo.AccessRoleMappingImpl;
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
import org.eclipse.daanse.rolap.mapping.pojo.DatabaseSchemaMappingImpl;
import org.eclipse.daanse.rolap.mapping.pojo.DimensionConnectorMappingImpl;
import org.eclipse.daanse.rolap.mapping.pojo.DimensionMappingImpl;
import org.eclipse.daanse.rolap.mapping.pojo.EnviromentMappingImpl;
import org.eclipse.daanse.rolap.mapping.pojo.HierarchyMappingImpl;
import org.eclipse.daanse.rolap.mapping.pojo.InlineTableMappingImpl;
import org.eclipse.daanse.rolap.mapping.pojo.InlineTableQueryMappingImpl;
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
import org.eclipse.daanse.rolap.mapping.pojo.PhysicalColumnMappingImpl;
import org.eclipse.daanse.rolap.mapping.pojo.PhysicalCubeMappingImpl;
import org.eclipse.daanse.rolap.mapping.pojo.PhysicalTableMappingImpl;
import org.eclipse.daanse.rolap.mapping.pojo.QueryMappingImpl;
import org.eclipse.daanse.rolap.mapping.pojo.RowMappingImpl;
import org.eclipse.daanse.rolap.mapping.pojo.RowValueMappingImpl;
import org.eclipse.daanse.rolap.mapping.pojo.SQLExpressionMappingColumnImpl;
import org.eclipse.daanse.rolap.mapping.pojo.SqlSelectQueryMappingImpl;
import org.eclipse.daanse.rolap.mapping.pojo.SqlStatementMappingImpl;
import org.eclipse.daanse.rolap.mapping.pojo.SqlViewMappingImpl;
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
    private AtomicInteger counterAggregationName = new AtomicInteger();
    private AtomicInteger counterAggregationPattern = new AtomicInteger();
    private AtomicInteger counterAggregationExclude = new AtomicInteger();

    private Schema mondrianSchema;
    private EnviromentMappingImpl catalog;

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

    private Optional<DimensionMappingImpl> findDimensionByCubeNameByDimensionName(
        String cubeName,
        String dimensionName
    ) {
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

    private Optional<HierarchyMappingImpl> findHierarchyInDimensionConnectorByName(
        DimensionConnectorMappingImpl dc,
        String hierarchyName
    ) {
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

    EnviromentMappingImpl transform() {

        catalog = EnviromentMappingImpl.builder().build();
        CatalogMappingImpl s = CatalogMappingImpl.builder().build();

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
        catalog.setSchemas(List.of(s));

        return catalog;
    }

    private List<VirtualCubeMappingImpl> transformVirtualCubes(
        List<org.eclipse.daanse.rolap.mapping.mondrian.model.VirtualCube> virtualCubes
    ) {
        return virtualCubes.stream().map(this::transformVirtualCube).toList();
    }

    private VirtualCubeMappingImpl transformVirtualCube(
        org.eclipse.daanse.rolap.mapping.mondrian.model.VirtualCube virtualCube
    ) {
        VirtualCubeMappingImpl vc = VirtualCubeMappingImpl.builder().build();
        vc.setId("vc_" + counterVirtualCube.incrementAndGet());
        vc.setName(virtualCube.name());
        vc.setDescription(virtualCube.description());
        vc.setCubeUsages(transformCubeConnector(virtualCube.cubeUsages()));
        vc.setDimensionConnectors(transformVirtualCubeDimensionConnectors(virtualCube.virtualCubeDimensions()));
        vc.setCalculatedMembers(
            transformCalculatedMembers(null, vc.getDimensionConnectors(), virtualCube.calculatedMembers()));
        vc.setNamedSets(transformNamedSets(virtualCube.namedSets()));
        vc.setKpis(transformKpis(virtualCube.kpis()));
        Optional<MeasureMappingImpl> oMeasure = findMeasure(virtualCube.defaultMeasure());
        oMeasure.ifPresent(m -> vc.setDefaultMeasure(m));
        vc.setEnabled(virtualCube.enabled());
        vc.setVisible(virtualCube.visible());
        vc.setReferencedMeasures(transformReferencedMeasures(virtualCube.virtualCubeMeasures()));
        vc.setReferencedCalculatedMembers(transformReferencedCalculatedMembers(virtualCube.virtualCubeMeasures()));
        return vc;
    }

    private List<CalculatedMemberMappingImpl> transformReferencedCalculatedMembers(
        List<VirtualCubeMeasure> virtualCubeMeasures
    ) {
        if (virtualCubeMeasures != null) {
            return virtualCubeMeasures.stream().map(this::transformReferencedCalculatedMember).toList();
        }
        return List.of();
    }

    private CalculatedMemberMappingImpl transformReferencedCalculatedMember(VirtualCubeMeasure virtualCubeMeasure) {
        if (virtualCubeMeasure != null && virtualCubeMeasure.cubeName() != null) {
            Optional<? extends CubeMappingImpl> oCube = findCubeByName(virtualCubeMeasure.cubeName());
            if (oCube.isPresent()) {
                CubeMappingImpl c = oCube.get();
                if (c instanceof PhysicalCubeMapping pc && pc.getCalculatedMembers() != null) {
                    Optional<? extends CalculatedMemberMapping> oM =
                        pc.getCalculatedMembers().stream().filter(m -> m.getName().equals(getMeasureName(virtualCubeMeasure.name()))).findAny();
                    if (oM.isPresent()) {
                        return (CalculatedMemberMappingImpl) oM.get();
                    }
                }
            }
        }
        return null;
    }

    private List<MeasureMappingImpl> transformReferencedMeasures(List<VirtualCubeMeasure> virtualCubeMeasures) {
        if (virtualCubeMeasures != null) {
            return virtualCubeMeasures.stream().map(this::transformReferencedMeasure).toList();
        }
        return List.of();
    }

    private MeasureMappingImpl transformReferencedMeasure(VirtualCubeMeasure virtualCubeMeasure) {
        if (virtualCubeMeasure != null && virtualCubeMeasure.cubeName() != null) {
            Optional<? extends CubeMappingImpl> oCube = findCubeByName(virtualCubeMeasure.cubeName());
            if (oCube.isPresent()) {
                CubeMappingImpl c = oCube.get();
                if (c instanceof PhysicalCubeMapping pc && pc.getMeasureGroups() != null) {
                    List<? extends MeasureMapping> mList =
                        pc.getMeasureGroups().stream().flatMap(mg -> mg.getMeasures().stream()).toList();
                    Optional<? extends MeasureMapping> oM =
                        mList.stream().filter(m -> m.getName().equals(getMeasureName(virtualCubeMeasure.name()))).findAny();
                    if (oM.isPresent()) {
                        return (MeasureMappingImpl) oM.get();
                    }
                }
            }
        }
        return null;
    }

    private String getMeasureName(String name) {
        if (name != null) {
            String n = name.replace("[", "").replace("]", "");
            String[] arr = n.split("\\.");
            if (arr.length > 1) {
                return arr[1];
            }
        }
        return null;
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

    private Optional<AccessRoleMappingImpl> findAccessRole(
        List<AccessRoleMappingImpl> accessRoles,
        String accessRoleName
    ) {
        return accessRoles.stream().filter(ar -> ar.getName().equals(accessRoleName)).findAny();
    }

    private AccessCubeGrantMappingImpl transformAccessCubeGrant(CubeGrant cubeGrant) {
        AccessCubeGrantMappingImpl accessCubeGrant = AccessCubeGrantMappingImpl.builder().build();
        accessCubeGrant.setAccess(cubeGrant.access() != null ? AccessCube.fromValue(cubeGrant.access()) : null);
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
        accessDimensionGrant.setAccess(dimensionGrant.access() != null ?
            AccessDimension.fromValue(dimensionGrant.access().toString()) : null);
        Optional<DimensionMappingImpl> oDim = findDimension(dimensionGrant.dimension());
        oDim.ifPresent(d -> accessDimensionGrant.setDimension(d));
        return accessDimensionGrant;

    }

    private List<AccessDimensionGrantMappingImpl> transformAccessDimensionGrants(List<DimensionGrant> dimensionGrants) {
        return dimensionGrants.stream().map(this::transformAccessDimensionGrant).toList();
    }

    private AccessHierarchyGrantMappingImpl transformAccessHierarchyGrant(HierarchyGrant hierarchyGrant) {
        AccessHierarchyGrantMappingImpl accessHierarchyGrant = AccessHierarchyGrantMappingImpl.builder().build();
        accessHierarchyGrant.setAccess(hierarchyGrant.access() != null ?
            AccessHierarchy.fromValue(hierarchyGrant.access().toString()) : null);
        Optional<LevelMappingImpl> oLvl = findLevel(prepareLevel(hierarchyGrant.bottomLevel()));
        oLvl.ifPresent(l -> accessHierarchyGrant.setBottomLevel(l));
        Optional<HierarchyMappingImpl> oHier = findHierarchy(hierarchyGrant.hierarchy());
        oHier.ifPresent(h -> accessHierarchyGrant.setHierarchy(h));
        accessHierarchyGrant.setRollupPolicyType(RollupPolicyType.fromValue(hierarchyGrant.rollupPolicy()));
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
        accessMemberGrant.setAccess(memberGrant.access() != null ?
            AccessMember.valueOf(memberGrant.access().toString()) : null);
        accessMemberGrant.setMember(memberGrant.member());
        return accessMemberGrant;

    }

    private List<AccessMemberGrantMappingImpl> transformAccessMemberGrants(List<MemberGrant> memberGrants) {
        return memberGrants.stream().map(this::transformAccessMemberGrant).toList();
    }

    private AccessCatalogGrantMappingImpl transformAccessSchemaGrant(SchemaGrant schemaGrant) {
        AccessCatalogGrantMappingImpl accessSchemaGrant = AccessCatalogGrantMappingImpl.builder().build();
        accessSchemaGrant.setAccess(schemaGrant.access() != null ?
            AccessCatalog.fromValue(schemaGrant.access().toString()) : null);
        accessSchemaGrant.setCubeGrant(transformAccessCubeGrants(schemaGrant.cubeGrants()));
        return accessSchemaGrant;
    }

    private List<AccessCatalogGrantMappingImpl> transformAccessSchemaGrants(List<SchemaGrant> schemaGrants) {
        return schemaGrants.stream().map(this::transformAccessSchemaGrant).toList();
    }

    private AnnotationMappingImpl transformAnnotation(
        org.eclipse.daanse.rolap.mapping.mondrian.model.Annotation annotation
    ) {
        AnnotationMappingImpl a = AnnotationMappingImpl.builder().build();
        a.setName(annotation.name());
        a.setValue(annotation.content());
        return a;
    }

    private List<AnnotationMappingImpl> transformAnnotations(
        List<org.eclipse.daanse.rolap.mapping.mondrian.model.Annotation> annotations
    ) {
        return annotations.stream().map(this::transformAnnotation).toList();
    }

    private DimensionMappingImpl transformDimension(
        org.eclipse.daanse.rolap.mapping.mondrian.model.Dimension dimension
    ) {

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
        VirtualCubeDimension virtualCubeDimension
    ) {
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
        //TODO
        //dc.setForeignKey(virtualCubeDimension.foreignKey());
        return dc;
    }

    private DimensionConnectorMappingImpl transformDimensionConnector(
        DimensionOrDimensionUsage dimensionUsageOrDimensions
    ) {

        DimensionConnectorMappingImpl dc = DimensionConnectorMappingImpl.builder().build();
//        dc.setId("dc_" + counterDimensionConnector.incrementAndGet());
        if (dimensionUsageOrDimensions instanceof org.eclipse.daanse.rolap.mapping.mondrian.model.Dimension d) {
            DimensionMappingImpl dim = transformDimension(d);
            catalog.getDimensions().add(dim);
            dc.setDimension(dim);
            //TODO
            //dc.setForeignKey(d.foreignKey());
        } else if (dimensionUsageOrDimensions instanceof DimensionUsage du) {
            Optional<DimensionMappingImpl> oDim = findDimension(du.source());
            oDim.ifPresent(d -> dc.setDimension(d));
            //TODO
            //dc.setForeignKey(du.foreignKey());
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
        List<VirtualCubeDimension> dimensionUsageOrDimensions
    ) {
        return dimensionUsageOrDimensions.stream().map(this::transformVirtualCubeDimensionConnector).toList();
    }

    private List<DimensionConnectorMappingImpl> transformDimensionConnectors(
        List<DimensionOrDimensionUsage> dimensionUsageOrDimensions
    ) {
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
        //TODO
        //h.setPrimaryKey(hierarchy.primaryKey());
        //h.setPrimaryKeyTable(hierarchy.primaryKeyTable());
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
        List<org.eclipse.daanse.rolap.mapping.mondrian.model.MemberReaderParameter> memberReaderParameters
    ) {
        return memberReaderParameters.stream().map(this::transformtransformMemberReaderParameter).toList();
    }

    private MemberReaderParameterMappingImpl transformtransformMemberReaderParameter(
        org.eclipse.daanse.rolap.mapping.mondrian.model.MemberReaderParameter memberReaderParameter
    ) {
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
        //TODO
        //l.setCaptionColumn(level.captionColumn());
        l.setCaptionColumn(transformSQLExpressionOfExpressionView(level.captionExpression()));
        //TODO
        //l.setColumn(level.column());
        l.setHideMemberIfType(HideMemberIfType.fromValue(level.hideMemberIf().getValue()));
        if (level.internalType() != null) {
            l.setDataType(InternalDataType.fromValue(level.internalType().getValue()));
        }
        l.setColumn(transformSQLExpressionOfExpressionView(level.keyExpression()));
        l.setLevelType(LevelType.fromValue(level.levelType().getValue()));
        l.setMemberFormatter(transformMemberFormatter(level.memberFormatter()));
        //TODO
        //l.setNameColumn(level.nameColumn());
        l.setNameColumn(transformSQLExpressionOfExpressionView(level.nameExpression()));
        l.setNullParentValue(level.nullParentValue());
        //TODO
        //l.setOrdinalColumn(level.ordinalColumn());
        l.setOrdinalColumn(transformSQLExpressionOfExpressionView(level.ordinalExpression()));
        l.setParentChildLink(transformParentChildLink(level.closure()));
        //TODO
        //l.setParentColumn(level.parentColumn());
        l.setParentColumn(transformSQLExpressionOfExpressionView(level.parentExpression()));
        //TODO
        //l.setTable(level.table());
        l.setDataType(InternalDataType.fromValue(level.type().getValue()));
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
            //TODO
            //mp.setColumn(property.column());
            mp.setDependsOnLevelValue(property.dependsOnLevelValue());
            mp.setDataType(InternalDataType.fromValue(property.type() != null ? property.type().getValue() :
                PropertyTypeEnum.STRING.getValue()));

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
            //TODO
            //pchl.setChildColumn(closure.childColumn());
            //TODO
            //pchl.setParentColumn(closure.parentColumn());
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

    private SQLExpressionMappingColumnImpl transformSQLExpressionOfExpressionView(ExpressionView expressionView) {
        if (expressionView != null) {
            SQLExpressionMappingColumnImpl sqlExpression = SQLExpressionMappingColumnImpl.builder().build();
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
        //TODO
        //m.setAggregatorType(MeasureAggregatorType.fromValue(measure.aggregator()));
        m.setId("m_" + counterMeasure.incrementAndGet());
        m.setBackColor(measure.backColor());
        m.setCellFormatter(transformCellFormatter(measure.cellFormatter()));
        //TODO
        //m.setColumn(measure.column());
        if (measure.datatype() != null) {
            m.setDatatype(InternalDataType.fromValue(measure.datatype().toString()));
        }
        m.setDisplayFolder(measure.displayFolder());
        m.setFormatString(measure.formatString());
        m.setName(measure.name());
        m.setVisible(measure.visible());
        if (measure.measureExpression() != null) {
            // redefine as column
            // m.setMeasureExpression(transformSqlExpression(measure.measureExpression().sqls()));
        }
        return m;
    }

    private SQLExpressionMappingColumnImpl transformSqlExpression(
        List<org.eclipse.daanse.rolap.mapping.mondrian.model.SQL> sqls
    ) {
        SQLExpressionMappingColumnImpl sqlExpression = SQLExpressionMappingColumnImpl.builder().build();
        sqlExpression.setSqls(transformSqls(sqls));
        return sqlExpression;
    }

    private MeasureGroupMappingImpl transformMeasureGroup(PhysicalCubeMappingImpl pc, List<Measure> measures) {
        List<MeasureMappingImpl> ms = transformMeasures(measures);
        catalog.getMeasures().addAll(ms);
        MeasureGroupMappingImpl measureGroup = MeasureGroupMappingImpl.builder().build();
        measureGroup.setName("");
        measureGroup.setMeasures(ms);
        measureGroup.setPhysicalCube(pc);
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
        pc.setMeasureGroups(List.of(transformMeasureGroup(pc, cube.measures())));
        pc.setDimensionConnectors(transformDimensionConnectors(cube.dimensionUsageOrDimensions()));

        pc.setCalculatedMembers(transformCalculatedMembers(pc, pc.getDimensionConnectors(), cube.calculatedMembers()));
        pc.setNamedSets(transformNamedSets(cube.namedSets()));
        pc.setKpis(transformKpis(cube.kpis()));
        Optional<MeasureMappingImpl> oMeasure = findMeasure(cube.defaultMeasure());
        oMeasure.ifPresent(m -> pc.setDefaultMeasure(m));
        pc.setWritebackTable(transformWritebackTable(pc.getDimensionConnectors(), cube.writebackTable()));
        return pc;
    }

    private WritebackTableMappingImpl transformWritebackTable(
        List<DimensionConnectorMappingImpl> dimensionConnectors,
        Optional<org.eclipse.daanse.rolap.mapping.mondrian.model.WritebackTable> oWritebackTable
    ) {
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
        List<org.eclipse.daanse.rolap.mapping.mondrian.model.WritebackColumn> column
    ) {
        return column.stream()
            .filter(org.eclipse.daanse.rolap.mapping.mondrian.model.WritebackMeasure.class::isInstance)
            .map(wbm -> transformWritebackMeasure(
                (org.eclipse.daanse.rolap.mapping.mondrian.model.WritebackMeasure) wbm))
            .toList();
    }

    private WritebackMeasureMappingImpl transformWritebackMeasure(
        org.eclipse.daanse.rolap.mapping.mondrian.model.WritebackMeasure writebackMeasure
    ) {
        WritebackMeasureMappingImpl wbm = WritebackMeasureMappingImpl.builder().build();
        //TODO
        //wbm.setColumn(writebackMeasure.column());
        wbm.setName(writebackMeasure.name());
        return wbm;
    }

    private List<WritebackAttributeMappingImpl> transformWritebackAttributes(
        List<DimensionConnectorMappingImpl> dimensionConnectors,
        List<org.eclipse.daanse.rolap.mapping.mondrian.model.WritebackColumn> column
    ) {
        return column.stream()
            .filter(org.eclipse.daanse.rolap.mapping.mondrian.model.WritebackAttribute.class::isInstance)
            .map(wba -> transformWritebackAttribute(dimensionConnectors,
                (org.eclipse.daanse.rolap.mapping.mondrian.model.WritebackAttribute) wba))
            .toList();
    }

    private WritebackAttributeMappingImpl transformWritebackAttribute(
        List<DimensionConnectorMappingImpl> dimensionConnectors,
        org.eclipse.daanse.rolap.mapping.mondrian.model.WritebackAttribute writebackAttribute
    ) {
        WritebackAttributeMappingImpl wba = WritebackAttributeMappingImpl.builder().build();
        //TODO
        //wba.setColumn(writebackAttribute.column());
        if (dimensionConnectors != null) {
            Optional<DimensionConnectorMappingImpl> oDimC = dimensionConnectors.stream()
                .filter(dc -> dc.getOverrideDimensionName().equals(writebackAttribute.dimension()))
                .findAny();
            oDimC.ifPresent(dc -> wba.setDimensionConnector(dc));
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
        //TODO
        //kpi.setParentKpi(kpiM.parentKpiID());
        return kpi;
    }

    private List<TranslationMappingImpl> transformTranslations(
        List<org.eclipse.daanse.rolap.mapping.mondrian.model.Translation> translations
    ) {
        if (translations != null) {
            return translations.stream().map(this::transformTranslation).toList();
        }
        return List.of();
    }

    private TranslationMappingImpl transformTranslation(
        org.eclipse.daanse.rolap.mapping.mondrian.model.Translation translation
    ) {
        TranslationMappingImpl t = TranslationMappingImpl.builder().build();
        t.setDescription(translation.description());
        t.setAnnotations(transformAnnotations(translation.annotations()));
        t.setLanguage(translation.language());
        t.setCaption(translation.caption());
        t.setDisplayFolder(translation.displayFolder());
        return t;
    }

    private List<NamedSetMappingImpl> transformNamedSets(
        List<org.eclipse.daanse.rolap.mapping.mondrian.model.NamedSet> namedSets
    ) {
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
        PhysicalCubeMappingImpl pc,
        List<DimensionConnectorMappingImpl> dimensionConnectors,
        List<org.eclipse.daanse.rolap.mapping.mondrian.model.CalculatedMember> calculatedMembers
    ) {
        return calculatedMembers.stream().map(cm -> transformCalculatedMember(pc, dimensionConnectors, cm)).toList();
    }

    private CalculatedMemberMappingImpl transformCalculatedMember(
        PhysicalCubeMappingImpl pc,
        List<DimensionConnectorMappingImpl> dimensionConnectors,
        org.eclipse.daanse.rolap.mapping.mondrian.model.CalculatedMember calculatedMember
    ) {
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
                Optional<HierarchyMappingImpl> oHier = findHierarchyInDimensionConnectorByName(oDimCon.get(),
                    res.getValue());
                oHier.ifPresent(d -> cm.setHierarchy(d));
            }
        }
        cm.setPhysicalCube(pc);
        return cm;
    }

    private List<CalculatedMemberPropertyMappingImpl> transformCalculatedMemberProperties(
        List<org.eclipse.daanse.rolap.mapping.mondrian.model.CalculatedMemberProperty> calculatedMemberProperties
    ) {
        return calculatedMemberProperties.stream().map(this::transformCalculatedMemberProperty).toList();
    }

    private CalculatedMemberPropertyMappingImpl transformCalculatedMemberProperty(
        org.eclipse.daanse.rolap.mapping.mondrian.model.CalculatedMemberProperty calculatedMemberProperty
    ) {
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
        org.eclipse.daanse.rolap.mapping.mondrian.model.CellFormatter cellFormatter
    ) {
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
        List<org.eclipse.daanse.rolap.mapping.mondrian.model.Cube> cubes
    ) {
        return cubes.stream().map(this::transformPhysicalCube).toList();
    }

    private QueryMappingImpl transformQuery(
        org.eclipse.daanse.rolap.mapping.mondrian.model.RelationOrJoin relationOrJoin
    ) {

        if (relationOrJoin instanceof Table t) {
            return transformTableQuery(t);
        }
        if (relationOrJoin instanceof Join j) {
            return transformJoinQuery(j);
        }
        if (relationOrJoin instanceof InlineTable it) {
            return transformInlineTableQuery(it);
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
            sqlSelectQuery.setSql(transformSqls(v));
            return sqlSelectQuery;
        }
        return null;
    }

    private InlineTableQueryMappingImpl transformInlineTableQuery(InlineTable it) {
        if (it != null) {
            InlineTableQueryMappingImpl inlineTableQuery = InlineTableQueryMappingImpl.builder().build();
            inlineTableQuery.setAlias(it.alias());
            inlineTableQuery.setTable(transformInlineTable(it));
            return inlineTableQuery;
        }
        return null;
    }

    private InlineTableMappingImpl transformInlineTable(InlineTable it) {
        //TODO
        List<RowMapping> rows = transformRows(it.rows());
        InlineTableMappingImpl inlineTable = InlineTableMappingImpl.builder()
            .withRows(rows)
            .build();
        return inlineTable;
    }

    private List<RowMapping> transformRows(List<Row> rows) {
        if (rows != null) {
            rows.stream().map(r -> transformRow(r));
        }
        return List.of();
    }

    private RowMapping transformRow(Row r) {
        List<RowValueMapping> rowValues = transformRowValues(r.values());
        return RowMappingImpl.builder().withRowValues(rowValues).build();
    }

    private List<RowValueMapping> transformRowValues(List<Value> values) {
        if (values != null) {
            values.stream().map(v -> transformValue(v));
        }
        return List.of();
    }

    private RowValueMapping transformValue(Value v) {
        PhysicalColumnMappingImpl column = PhysicalColumnMappingImpl.builder().withName(v.column()).build();
        return RowValueMappingImpl.builder().withColumn(column).withValue(v.content()).build();
    }

    private PhysicalTableMappingImpl transformPhysicalTable(Table t) {
        DatabaseSchemaMappingImpl databaseSchema = DatabaseSchemaMappingImpl.builder()
            .withName(t.schema())
            .build();
        PhysicalTableMappingImpl table = ((PhysicalTableMappingImpl.Builder) PhysicalTableMappingImpl.builder()
            .withName(t.name())
            .withsSchema(databaseSchema))
            .build();
        return table;
    }

    private SqlViewMappingImpl transformSqls(View v) {
        // TODO Auto-generated method stub
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
            tableQuery.setTable(transformPhysicalTable(t));
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

    private List<SqlStatementMappingImpl> transformSqls(List<org.eclipse.daanse.rolap.mapping.mondrian.model.SQL> sqls) {
        return sqls.stream().map(this::transformSql).toList();
    }

    private JoinedQueryElementMappingImpl transformJoinedQueryElement(String alias, String key, RelationOrJoin roj) {
        JoinedQueryElementMappingImpl jqe = JoinedQueryElementMappingImpl.builder().build();
        jqe.setAlias(alias);
        //TODO
        //jqe.setKey(key);
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
            //TODO
            //an.setName(aggName.name());
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
        List<AggMeasureFactCount> aggMeasureFactCount
    ) {
        if (aggMeasureFactCount != null) {
            return aggMeasureFactCount.stream().map(this::transformAggregationMeasureFactCount).toList();
        }
        return List.of();
    }

    private AggregationMeasureFactCountMappingImpl transformAggregationMeasureFactCount(
        AggMeasureFactCount aggMeasureFactCount
    ) {
        AggregationMeasureFactCountMappingImpl amfc = AggregationMeasureFactCountMappingImpl.builder().build();
        //TODO
        //amfc.setColumn(aggMeasureFactCount.column());
        //TODO
        //amfc.setFactColumn(aggMeasureFactCount.factColumn());
        return amfc;
    }

    private List<AggregationLevelMappingImpl> transformAggregationLevels(List<AggLevel> aggLevels) {
        return aggLevels.stream().map(this::transformAggregationLevel).toList();
    }

    private AggregationLevelMappingImpl transformAggregationLevel(AggLevel aggLevel) {
        AggregationLevelMappingImpl al = AggregationLevelMappingImpl.builder().build();
        al.setAggregationLevelProperties(transformAggregationLevelProperties(aggLevel.properties()));
        //TODO
        //al.setCaptionColumn(aggLevel.captionColumn());
        al.setCollapsed(aggLevel.collapsed());
        //TODO
        //al.setColumn(aggLevel.column());
        al.setName(aggLevel.name());
        //TODO
        //al.setNameColumn(aggLevel.nameColumn());
        //TODO
        //al.setOrdinalColumn(aggLevel.ordinalColumn());
        return al;
    }

    private List<AggregationLevelPropertyMappingImpl> transformAggregationLevelProperties(
        List<AggLevelProperty> aggLevelProperties
    ) {
        return aggLevelProperties.stream().map(this::transformAggregationLevelProperty).toList();
    }

    private AggregationLevelPropertyMappingImpl transformAggregationLevelProperty(AggLevelProperty aggLevelProperty) {
        AggregationLevelPropertyMappingImpl ap = AggregationLevelPropertyMappingImpl.builder().build();
        //TODO
        //ap.setColumn(aggLevelProperty.column());
        ap.setName(aggLevelProperty.name());
        return ap;
    }

    private List<AggregationMeasureMappingImpl> transformAggregationMeasures(List<AggMeasure> aggregationMeasures) {
        return aggregationMeasures.stream().map(this::transformAggregationMeasure).toList();
    }

    private AggregationMeasureMappingImpl transformAggregationMeasure(AggMeasure aggMeasure) {
        AggregationMeasureMappingImpl am = AggregationMeasureMappingImpl.builder().build();
        //TODO
        //am.setColumn(aggMeasure.column());
        am.setName(aggMeasure.name());
        am.setRollupType(aggMeasure.rollupType());
        return am;
    }

    private List<AggregationForeignKeyMappingImpl> transformAggregationForeignKeys(List<AggForeignKey> aggForeignKeys) {
        return aggForeignKeys.stream().map(this::transformAggregationForeignKey).toList();
    }

    private AggregationForeignKeyMappingImpl transformAggregationForeignKey(AggForeignKey aggForeignKey) {
        AggregationForeignKeyMappingImpl afk = AggregationForeignKeyMappingImpl.builder().build();
        //TODO
        //afk.setAggregationColumn(aggForeignKey.aggColumn());
        //TODO
        //afk.setFactColumn(aggForeignKey.factColumn());
        return afk;
    }

    private List<AggregationColumnNameMappingImpl> transformAggregationColumnNames(List<AggColumnName> aggColumnNames) {
        return aggColumnNames.stream().map(this::transformAggregationColumnName).toList();
    }

    private AggregationColumnNameMappingImpl transformAggregationColumnName(AggColumnName aggColumnName) {
        AggregationColumnNameMappingImpl acn = AggregationColumnNameMappingImpl.builder().build();
        //TODO
        //acn.setColumn(aggColumnName.column());
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
        accessRole.setAccessCatalogGrants(transformAccessSchemaGrants(role.schemaGrants()));
        accessRole.setAnnotations(transformAnnotations(role.annotations()));
        accessRole.getReferencedAccessRoles();
        return accessRole;
    }

    private List<AccessRoleMappingImpl> transformRoles(List<Role> roles) {
        return roles.stream().map(this::transformRole).toList();
    }

    private List<DimensionMappingImpl> transformSharedDimensions(
        List<org.eclipse.daanse.rolap.mapping.mondrian.model.Dimension> sharedDimensions
    ) {
        return sharedDimensions.stream().map(this::transformDimension).toList();
    }

    private SqlStatementMappingImpl transformSql(org.eclipse.daanse.rolap.mapping.mondrian.model.SQL sql) {
        return SqlStatementMappingImpl.builder()
            .withSql(sql.content())
            .withDialects(List.of(sql.dialect()))
            .build();
    }

}
