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

import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.AccessCubeGrant;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.AccessDimensionGrant;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.AccessHierarchyGrant;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.AccessMemberGrant;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.AccessRole;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.AccessSchemaGrant;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.AggregationColumnName;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.AggregationExclude;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.AggregationForeignKey;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.AggregationLevel;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.AggregationLevelProperty;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.AggregationMeasure;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.AggregationMeasureFactCount;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.AggregationName;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.AggregationPattern;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.AggregationTable;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.Annotation;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.CalculatedMember;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.CalculatedMemberProperty;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.CellFormatter;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.Cube;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.CubeConnector;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.Dimension;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.DimensionConnector;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.Documentation;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.EmfRolapMappingFactory;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.InlineTableColumnDefinition;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.InlineTableQuery;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.InlineTableRow;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.InlineTableRowCell;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.JoinQuery;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.JoinedQueryElement;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.Kpi;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.MeasureGroup;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.MemberFormatter;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.MemberProperty;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.MemberPropertyFormatter;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.MemberReaderParameter;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.NamedSet;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.ParentChildLink;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.PhysicalCube;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.Query;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.RolapContext;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.SQL;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.SQLExpression;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.SqlSelectQuery;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.TableQuery;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.TableQueryOptimizationHint;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.Translation;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.VirtualCube;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.WritebackAttribute;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.WritebackMeasure;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.WritebackTable;
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
import org.eclipse.daanse.rolap.mapping.mondrian.model.Schema;
import org.eclipse.daanse.rolap.mapping.mondrian.model.SchemaGrant;
import org.eclipse.daanse.rolap.mapping.mondrian.model.Table;
import org.eclipse.daanse.rolap.mapping.mondrian.model.Value;
import org.eclipse.daanse.rolap.mapping.mondrian.model.View;
import org.eclipse.daanse.rolap.mapping.mondrian.model.VirtualCubeDimension;
import org.eclipse.daanse.rolap.mapping.mondrian.model.VirtualCubeMeasure;

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
    private RolapContext rolapContext;

    private TransformTask() {
        // none
    }

    TransformTask(Schema schema) {
        this();
        this.mondrianSchema = schema;
    }

    private Optional<Cube> findCubeByName(String cubeNameAsIdent) {
        return rolapContext.getCubes().stream().filter(c -> c.getName().equals(cubeNameAsIdent)).findAny();
    }

    private Optional<Dimension> findDimension(String name) {
        return rolapContext.getDimensions().stream().filter(d -> d.getName().equals(name)).findAny();
    }

    private Optional<org.eclipse.daanse.rolap.mapping.emf.rolapmapping.Level> findLevel(String name) {
        return rolapContext.getLevels().stream().filter(l -> l.getName().equals(name)).findAny();
    }

    private Optional<org.eclipse.daanse.rolap.mapping.emf.rolapmapping.Hierarchy> findHierarchy(String name) {
        return rolapContext.getHierarchies().stream().filter(h -> (h.getName() != null && h.getName().equals(name)))
                .findAny();
    }

    private Optional<org.eclipse.daanse.rolap.mapping.emf.rolapmapping.Measure> findMeasure(String name) {
        return rolapContext.getMeasures().stream().filter(m -> m.getName().equals(name)).findAny();
    }

    private Optional<Dimension> findDimensionByCubeNameByDimensionName(String cubeName, String dimensionName) {
        Optional<Cube> oCube = findCubeByName(cubeName);
        if (oCube.isPresent()) {
            return findDimensionInCube(oCube.get(), dimensionName);
        }
        return Optional.empty();
    }

    private Optional<Dimension> findDimensionInCube(Cube cube, String name) {
        if (cube != null && cube.getDimensionConnectors() != null) {
            Optional<DimensionConnector> oDimensionConnector = cube.getDimensionConnectors().stream()
                    .filter(dc -> dc.getDimension().getName().equals(name)).findAny();
            if (oDimensionConnector.isPresent()) {
                return Optional.ofNullable(oDimensionConnector.get().getDimension());
            }
        }
        return Optional.empty();
    }

    private Optional<org.eclipse.daanse.rolap.mapping.emf.rolapmapping.Measure> findMeasureByCubeNameByMeasureName(
            String cubeName, String measureName) {
        Optional<Cube> oCube = findCubeByName(cubeName);
        if (oCube.isPresent()) {
            return findMeasureInCube(oCube.get(), measureName);
        }
        return Optional.empty();
    }

    private Optional<org.eclipse.daanse.rolap.mapping.emf.rolapmapping.Measure> findMeasureInCube(Cube cube,
            String measureName) {
        if (cube != null && cube.getMeasureGroups() != null) {
            Optional<MeasureGroup> oMeasureGroup = cube.getMeasureGroups().stream()
                    .filter(mg -> mg.getMeasures().stream().anyMatch(m -> m.getName().equals(measureName))).findAny();
            if (oMeasureGroup.isPresent()) {
                return oMeasureGroup.get().getMeasures().stream().filter(m -> m.getName().equals(measureName))
                        .findAny();
            }
        }
        return Optional.empty();
    }

    private Optional<org.eclipse.daanse.rolap.mapping.emf.rolapmapping.Hierarchy> findHierarchyInDimensionConnectorByName(
            DimensionConnector dc, String hierarchyName) {
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

    org.eclipse.daanse.rolap.mapping.emf.rolapmapping.RolapContext transform() {

        rolapContext = EmfRolapMappingFactory.eINSTANCE.createRolapContext();
        org.eclipse.daanse.rolap.mapping.emf.rolapmapping.Schema s = EmfRolapMappingFactory.eINSTANCE.createSchema();

        s.setId("s_" + 1);
        List<Dimension> dimensionsShared = transformSharedDimensions(mondrianSchema.dimensions());
        rolapContext.getDimensions().addAll(dimensionsShared);
        List<PhysicalCube> physicalCubes = transformPhysicalCubes(mondrianSchema.cubes());
        rolapContext.getCubes().addAll(physicalCubes);
        List<VirtualCube> virtualCubes = transformVirtualCubes(mondrianSchema.virtualCubes());
        rolapContext.getCubes().addAll(virtualCubes);
        List<AccessRole> accessRoles = transformRoles(mondrianSchema.roles());
        rolapContext.getAccessRoles().addAll(accessRoles);
        String accessRoleName = mondrianSchema.defaultRole();
        Optional<AccessRole> oDefaultAccessRole = findAccessRole(accessRoles, accessRoleName);
        oDefaultAccessRole.ifPresent(ar -> s.setDefaultAccessRole(ar));

        s.setName(mondrianSchema.name());
        s.setDescription(mondrianSchema.description());
        s.getAccessRoles().addAll(accessRoles);
        s.getAnnotations().addAll(transformAnnotations(mondrianSchema.annotations()));
        s.setMeasuresDimensionName(mondrianSchema.measuresCaption());
        s.getCubes().addAll(physicalCubes);
        s.setDocumentation(transformDocumentation(mondrianSchema.documentation()));
        rolapContext.getSchemas().add(s);

        return rolapContext;
    }

    private Documentation transformDocumentation(
            Optional<org.eclipse.daanse.rolap.mapping.mondrian.model.Documentation> documentation) {
        if (documentation.isPresent()) {
            Documentation doc = EmfRolapMappingFactory.eINSTANCE.createDocumentation();
            doc.setValue(documentation.get().documentation());
            return doc;
        }
        return null;
    }

    private List<VirtualCube> transformVirtualCubes(
            List<org.eclipse.daanse.rolap.mapping.mondrian.model.VirtualCube> virtualCubes) {
        return virtualCubes.stream().map(this::transformVirtualCube).toList();
    }

    private VirtualCube transformVirtualCube(org.eclipse.daanse.rolap.mapping.mondrian.model.VirtualCube virtualCube) {
        VirtualCube vc = EmfRolapMappingFactory.eINSTANCE.createVirtualCube();
        vc.setId("vc_" + counterVirtualCube.incrementAndGet());
        vc.setName(virtualCube.name());
        vc.setDescription(virtualCube.description());
        vc.getCubeUsages().addAll(transformCubeConnector(virtualCube.cubeUsages()));
        vc.getDimensionConnectors()
                .addAll(transformVirtualCubeDimensionConnectors(virtualCube.virtualCubeDimensions()));
        vc.getCalculatedMembers()
                .addAll(transformCalculatedMembers(vc.getDimensionConnectors(), virtualCube.calculatedMembers()));
        vc.getNamedSets().addAll(transformNamedSets(virtualCube.namedSets()));
        vc.getKpis().addAll(transformKpis(virtualCube.kpis()));
        Optional<org.eclipse.daanse.rolap.mapping.emf.rolapmapping.Measure> oMeasure = findMeasure(
                virtualCube.defaultMeasure());
        oMeasure.ifPresent(m -> vc.setDefaultMeasure(m));
        vc.setEnabled(virtualCube.enabled());
        vc.setVisible(virtualCube.visible());
        vc.getMeasureGroups().addAll(transformVirtualCubeMeasureGroups(virtualCube.virtualCubeMeasures()));
        return vc;
    }

    private List<CubeConnector> transformCubeConnector(List<CubeUsage> cubeUsages) {
        if (cubeUsages != null) {
            return cubeUsages.stream().map(this::transformCubeConnector).toList();
        }
        return List.of();
    }

    private CubeConnector transformCubeConnector(CubeUsage virtualCubeMeasure) {
        CubeConnector cubeConnector = EmfRolapMappingFactory.eINSTANCE.createCubeConnector();
        Optional<Cube> oCube = findCubeByName(virtualCubeMeasure.cubeName());
        oCube.ifPresent(c -> cubeConnector.setCube(c));
        cubeConnector.setIgnoreUnrelatedDimensions(virtualCubeMeasure.ignoreUnrelatedDimensions());
        return cubeConnector;

    }

    private List<MeasureGroup> transformVirtualCubeMeasureGroups(List<VirtualCubeMeasure> virtualCubeMeasures) {
        return virtualCubeMeasures.stream().map(this::transformVirtualCubeMeasureGroup).toList();
    }

    private MeasureGroup transformVirtualCubeMeasureGroup(VirtualCubeMeasure virtualCubeMeasure) {
        MeasureGroup measureGroup = EmfRolapMappingFactory.eINSTANCE.createMeasureGroup();
        Optional<org.eclipse.daanse.rolap.mapping.emf.rolapmapping.Measure> oMeasure = findMeasureByCubeNameByMeasureName(
                virtualCubeMeasure.cubeName(), virtualCubeMeasure.name());
        oMeasure.ifPresent(m -> measureGroup.getMeasures().add(m));
        measureGroup.setName(virtualCubeMeasure.name());
        return measureGroup;
    }

    private Optional<AccessRole> findAccessRole(List<AccessRole> accessRoles, String accessRoleName) {
        Optional<AccessRole> oDefaultAccessRole = accessRoles.stream().filter(ar -> ar.getName().equals(accessRoleName))
                .findAny();
        return oDefaultAccessRole;
    }

    private AccessCubeGrant transformAccessCubeGrant(CubeGrant cubeGrant) {
        AccessCubeGrant accessCubeGrant = EmfRolapMappingFactory.eINSTANCE.createAccessCubeGrant();
        accessCubeGrant.setAccess(cubeGrant.access());
        String cubeNameAsIdent = cubeGrant.cube();
        Optional<Cube> oCcube = findCubeByName(cubeNameAsIdent);
        oCcube.ifPresent(c -> accessCubeGrant.setCube(c));
        accessCubeGrant.getDimensionGrants().addAll(transformAccessDimensionGrants(cubeGrant.dimensionGrants()));
        accessCubeGrant.getHierarchyGrants().addAll(transformAccessHierarchyGrants(cubeGrant.hierarchyGrants()));
        return accessCubeGrant;
    }

    private List<AccessCubeGrant> transformAccessCubeGrants(List<CubeGrant> cubeGrants) {
        return cubeGrants.stream().map(this::transformAccessCubeGrant).toList();
    }

    private AccessDimensionGrant transformAccessDimensionGrant(DimensionGrant dimensionGrant) {
        AccessDimensionGrant accessDimensionGrant = EmfRolapMappingFactory.eINSTANCE.createAccessDimensionGrant();
        accessDimensionGrant.setAccess(dimensionGrant.access().toString());
        Optional<Dimension> oDim = findDimension(dimensionGrant.dimension());
        oDim.ifPresent(d -> accessDimensionGrant.setDimension(d));
        return accessDimensionGrant;

    }

    private List<AccessDimensionGrant> transformAccessDimensionGrants(List<DimensionGrant> dimensionGrants) {
        return dimensionGrants.stream().map(this::transformAccessDimensionGrant).toList();
    }

    private AccessHierarchyGrant transformAccessHierarchyGrant(HierarchyGrant hierarchyGrant) {
        AccessHierarchyGrant accessHierarchyGrant = EmfRolapMappingFactory.eINSTANCE.createAccessHierarchyGrant();
        accessHierarchyGrant.setAccess(hierarchyGrant.access() != null ? hierarchyGrant.access().toString() : null);
        Optional<org.eclipse.daanse.rolap.mapping.emf.rolapmapping.Level> oLvl = findLevel(
                prepareLevel(hierarchyGrant.bottomLevel()));
        oLvl.ifPresent(l -> accessHierarchyGrant.setBottomLevel(l));
        Optional<org.eclipse.daanse.rolap.mapping.emf.rolapmapping.Hierarchy> oHier = findHierarchy(
                hierarchyGrant.hierarchy());
        oHier.ifPresent(h -> accessHierarchyGrant.setHierarchy(h));
        accessHierarchyGrant.setRollupPolicy(hierarchyGrant.rollupPolicy());
        oLvl = findLevel(prepareLevel(hierarchyGrant.topLevel()));
        oLvl.ifPresent(l -> accessHierarchyGrant.setTopLevel(l));
        accessHierarchyGrant.getMemberGrants().addAll(transformAccessMemberGrants(hierarchyGrant.memberGrants()));
        return accessHierarchyGrant;

    }

    private String prepareLevel(String level) {
        if (level != null) {
            return level.replace("[", "").replace("]", "");
        }
        return null;
    }

    private List<AccessHierarchyGrant> transformAccessHierarchyGrants(List<HierarchyGrant> dimensionGrants) {
        return dimensionGrants.stream().map(this::transformAccessHierarchyGrant).toList();
    }

    private AccessMemberGrant transformAccessMemberGrant(MemberGrant memberGrant) {
        AccessMemberGrant accessMemberGrant = EmfRolapMappingFactory.eINSTANCE.createAccessMemberGrant();
        accessMemberGrant.setAccess(memberGrant.access() != null ? memberGrant.access().toString() : null);
        accessMemberGrant.setMember(memberGrant.member());
        return accessMemberGrant;

    }

    private List<AccessMemberGrant> transformAccessMemberGrants(List<MemberGrant> memberGrants) {
        return memberGrants.stream().map(this::transformAccessMemberGrant).toList();
    }

    private AccessSchemaGrant transformAccessSchemaGrant(SchemaGrant schemaGrant) {
        AccessSchemaGrant accessSchemaGrant = EmfRolapMappingFactory.eINSTANCE.createAccessSchemaGrant();
        accessSchemaGrant.setAccess(schemaGrant.access() != null ? schemaGrant.access().toString() : null);
        accessSchemaGrant.getCubeGrant().addAll(transformAccessCubeGrants(schemaGrant.cubeGrants()));
        return accessSchemaGrant;
    }

    private List<AccessSchemaGrant> transformAccessSchemaGrants(List<SchemaGrant> schemaGrants) {
        return schemaGrants.stream().map(this::transformAccessSchemaGrant).toList();
    }

    private Annotation transformAnnotation(org.eclipse.daanse.rolap.mapping.mondrian.model.Annotation annotation) {
        Annotation a = EmfRolapMappingFactory.eINSTANCE.createAnnotation();
        a.setName(annotation.name());
        a.setValue(annotation.content());
        return a;
    }

    private List<Annotation> transformAnnotations(
            List<org.eclipse.daanse.rolap.mapping.mondrian.model.Annotation> annotations) {
        return annotations.stream().map(this::transformAnnotation).toList();
    }

    private Dimension transformDimension(org.eclipse.daanse.rolap.mapping.mondrian.model.Dimension dimension) {

        Dimension dim = null;
        if (DimensionTypeEnum.TIME_DIMENSION.equals(dimension.type())) {
            dim = EmfRolapMappingFactory.eINSTANCE.createTimeDimension();
        } else {
            dim = EmfRolapMappingFactory.eINSTANCE.createStandardDimension();
        }
        dim.setId("d_" + counterDimension.incrementAndGet());
        dim.setName(dimension.name());
        dim.setDescription(dimension.description());
        dim.setUsagePrefix(dimension.usagePrefix());
        dim.setVisible(dimension.visible());
        dim.getAnnotations().addAll(transformAnnotations(dimension.annotations()));
        List<org.eclipse.daanse.rolap.mapping.emf.rolapmapping.Hierarchy> hierarchies = transformHierarchies(
                dimension.hierarchies());
        rolapContext.getHierarchies().addAll(hierarchies);
        dim.getHierarchies().addAll(hierarchies);
        return dim;
    }

    private DimensionConnector transformVirtualCubeDimensionConnector(VirtualCubeDimension virtualCubeDimension) {
        DimensionConnector dc = EmfRolapMappingFactory.eINSTANCE.createDimensionConnector();
        dc.setId("dc_" + counterDimensionConnector.incrementAndGet());
        if (virtualCubeDimension.cubeName() != null) {
            Optional<Dimension> oDim = findDimensionByCubeNameByDimensionName(virtualCubeDimension.cubeName(),
                    virtualCubeDimension.name());
            oDim.ifPresent(d -> dc.setDimension(d));
        } else {
            Optional<Dimension> oDim = findDimension(virtualCubeDimension.name());
            oDim.ifPresent(d -> dc.setDimension(d));
        }
        dc.setVisible(virtualCubeDimension.visible());
        dc.setForeignKey(virtualCubeDimension.foreignKey());
        return dc;
    }

    private DimensionConnector transformDimensionConnector(DimensionOrDimensionUsage dimensionUsageOrDimensions) {

        DimensionConnector dc = EmfRolapMappingFactory.eINSTANCE.createDimensionConnector();
        dc.setId("dc_" + counterDimensionConnector.incrementAndGet());
        if (dimensionUsageOrDimensions instanceof org.eclipse.daanse.rolap.mapping.mondrian.model.Dimension d) {
            Dimension dim = transformDimension(d);
            rolapContext.getDimensions().add(dim);
            dc.setDimension(dim);
            dc.setForeignKey(d.foreignKey());
        } else if (dimensionUsageOrDimensions instanceof DimensionUsage du) {
            Optional<Dimension> oDim = findDimension(du.source());
            oDim.ifPresent(d -> dc.setDimension(d));
            dc.setForeignKey(du.foreignKey());
            if (du.level() != null) {
                Optional<org.eclipse.daanse.rolap.mapping.emf.rolapmapping.Level> oLvl = findLevel(du.level());
                oLvl.ifPresent(l -> dc.setLevel(l));
            }
            dc.setOverrideDimensionName(du.name());
            dc.setVisible(du.visible());
            dc.setUsagePrefix(du.usagePrefix());
        }
        return dc;
    }

    private List<DimensionConnector> transformVirtualCubeDimensionConnectors(
            List<VirtualCubeDimension> dimensionUsageOrDimensions) {
        return dimensionUsageOrDimensions.stream().map(this::transformVirtualCubeDimensionConnector).toList();
    }

    private List<DimensionConnector> transformDimensionConnectors(
            List<DimensionOrDimensionUsage> dimensionUsageOrDimensions) {
        return dimensionUsageOrDimensions.stream().map(this::transformDimensionConnector).toList();
    }

    private List<org.eclipse.daanse.rolap.mapping.emf.rolapmapping.Hierarchy> transformHierarchies(
            List<Hierarchy> hierarchies) {
        return hierarchies.stream().map(this::transformHierarchy).toList();
    }

    private org.eclipse.daanse.rolap.mapping.emf.rolapmapping.Hierarchy transformHierarchy(Hierarchy hierarchy) {
        org.eclipse.daanse.rolap.mapping.emf.rolapmapping.Hierarchy h = EmfRolapMappingFactory.eINSTANCE
                .createHierarchy();
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
        List<org.eclipse.daanse.rolap.mapping.emf.rolapmapping.Level> lvls = transformLevels(hierarchy.levels());
        rolapContext.getLevels().addAll(lvls);
        h.getLevels().addAll(lvls);
        List<org.eclipse.daanse.rolap.mapping.emf.rolapmapping.MemberReaderParameter> mrps = transformMemberReaderParameters(
                hierarchy.memberReaderParameters());
        h.getMemberReaderParameters().addAll(mrps);

        return h;
    }

    private List<MemberReaderParameter> transformMemberReaderParameters(
            List<org.eclipse.daanse.rolap.mapping.mondrian.model.MemberReaderParameter> memberReaderParameters) {
        return memberReaderParameters.stream().map(this::transformtransformMemberReaderParameter).toList();
    }

    private MemberReaderParameter transformtransformMemberReaderParameter(
            org.eclipse.daanse.rolap.mapping.mondrian.model.MemberReaderParameter memberReaderParameter) {
        if (memberReaderParameter != null) {
            MemberReaderParameter mrp = EmfRolapMappingFactory.eINSTANCE.createMemberReaderParameter();
            mrp.setName(memberReaderParameter.name());
            mrp.setValue(memberReaderParameter.value());
            return mrp;
        }
        return null;

    }

    private org.eclipse.daanse.rolap.mapping.emf.rolapmapping.Level transformLevel(Level level) {
        org.eclipse.daanse.rolap.mapping.emf.rolapmapping.Level l = EmfRolapMappingFactory.eINSTANCE.createLevel();

        l.setId("l_" + counterLevel.incrementAndGet());
        l.setName(level.name());
        l.setDescription(level.description());
        l.setApproxRowCount(level.approxRowCount());
        l.setCaptionColumn(level.captionColumn());
        l.setCaptionExpression(transformSQLExpression(level.captionExpression()));
        l.setColumn(level.column());
        l.setHideMemberIf(level.hideMemberIf().getValue());
        if (level.internalType() != null) {
            l.setInternalType(level.internalType().getValue());
        }
        l.setKeyExpression(transformSQLExpression(level.keyExpression()));
        l.setLevelType(level.levelType().getValue());
        l.setMemberFormatter(transformMemberFormatter(level.memberFormatter()));
        l.setNameColumn(level.nameColumn());
        l.setNameExpression(transformSQLExpression(level.nameExpression()));
        l.setNullParentValue(level.nullParentValue());
        l.setOrdinalColumn(level.ordinalColumn());
        l.setOrdinalExpression(transformSQLExpression(level.ordinalExpression()));
        TableQuery tableQuery = EmfRolapMappingFactory.eINSTANCE.createTableQuery();
        tableQuery.setName(l.getTable());
        l.setParentChildLink(transformParentChildLink(level.closure()));
        l.setParentColumn(level.parentColumn());
        l.setParentExpression(transformSQLExpression(level.parentExpression()));
        l.setTable(level.table());
        l.setType(level.type().getValue());
        l.setUniqueMembers(level.uniqueMembers());
        l.setVisible(level.visible());
        l.getMemberProperties().addAll(transformMemberProperties(level.properties()));

        return l;
    }

    private List<MemberProperty> transformMemberProperties(List<Property> properties) {
        return properties.stream().map(this::transformMemberProperty).toList();
    }

    private MemberProperty transformMemberProperty(Property property) {
        if (property != null) {
            MemberProperty mp = EmfRolapMappingFactory.eINSTANCE.createMemberProperty();
            mp.getAnnotations().addAll(transformAnnotations(property.annotations()));
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

    private MemberPropertyFormatter transformMemberPropertyFormatter(String formatter) {
        if (formatter != null) {
            MemberPropertyFormatter mpf = EmfRolapMappingFactory.eINSTANCE.createMemberPropertyFormatter();
            mpf.setRef(formatter);
        }
        return null;
    }

    private ParentChildLink transformParentChildLink(Closure closure) {
        if (closure != null) {
            ParentChildLink pchl = EmfRolapMappingFactory.eINSTANCE.createParentChildLink();
            pchl.setTable(transformTableQuery(closure.table()));
            pchl.setChildColumn(closure.childColumn());
            pchl.setParentColumn(closure.parentColumn());
            return pchl;
        }
        return null;
    }

    private MemberFormatter transformMemberFormatter(ElementFormatter memberFormatter) {
        if (memberFormatter != null) {
            MemberFormatter mf = EmfRolapMappingFactory.eINSTANCE.createMemberFormatter();
            if (memberFormatter.className() != null) {
                mf.setRef(memberFormatter.className());
            }
            mf.setId("mf_" + counterMemberFormatter.incrementAndGet());
            rolapContext.getFormatters().add(mf);
            return mf;
        }
        return null;
    }

    private SQLExpression transformSQLExpression(ExpressionView expressionView) {
        if (expressionView != null) {
            SQLExpression sqlExpression = EmfRolapMappingFactory.eINSTANCE.createSQLExpression();
            sqlExpression.getSqls().addAll(transformSqls(expressionView.sqls()));
            return sqlExpression;
        }
        return null;
    }

    private List<org.eclipse.daanse.rolap.mapping.emf.rolapmapping.Level> transformLevels(List<Level> levels) {
        return levels.stream().map(this::transformLevel).toList();
    }

    private org.eclipse.daanse.rolap.mapping.emf.rolapmapping.Measure transformMeasure(Measure measure) {
        org.eclipse.daanse.rolap.mapping.emf.rolapmapping.Measure m = switch (measure.aggregator()) {
        case "sum" -> EmfRolapMappingFactory.eINSTANCE.createSumMeasure();
        case "avg" -> m = EmfRolapMappingFactory.eINSTANCE.createCountMeasure();
        case "count" -> m = EmfRolapMappingFactory.eINSTANCE.createAvgMeasure();
        case "distinct-count" -> m = EmfRolapMappingFactory.eINSTANCE.createAvgMeasure();
        case "min" -> m = EmfRolapMappingFactory.eINSTANCE.createMinMeasure();
        case "max" -> m = EmfRolapMappingFactory.eINSTANCE.createMaxMeasure();
        default -> throw new IllegalArgumentException("Unexpected value: " + measure.aggregator());
        };
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

    private SQLExpression transformSqlExpression(List<org.eclipse.daanse.rolap.mapping.mondrian.model.SQL> sqls) {
        SQLExpression sqlExpression = EmfRolapMappingFactory.eINSTANCE.createSQLExpression();
        sqlExpression.getSqls().addAll(transformSqls(sqls));
        return sqlExpression;
    }

    private MeasureGroup transformMeasureGroup(List<Measure> measures) {
        List<org.eclipse.daanse.rolap.mapping.emf.rolapmapping.Measure> ms = transformMeasures(measures);
        rolapContext.getMeasures().addAll(ms);
        MeasureGroup measureGroup = EmfRolapMappingFactory.eINSTANCE.createMeasureGroup();
        measureGroup.setName("");
        measureGroup.getMeasures().addAll(ms);
        return measureGroup;
    }

    private List<org.eclipse.daanse.rolap.mapping.emf.rolapmapping.Measure> transformMeasures(List<Measure> measures) {
        return measures.stream().map(this::transformMeasure).toList();
    }

    private PhysicalCube transformPhysicalCube(org.eclipse.daanse.rolap.mapping.mondrian.model.Cube cube) {
        PhysicalCube pc = EmfRolapMappingFactory.eINSTANCE.createPhysicalCube();
        pc.setId("pc_" + counterPhysicalCube.incrementAndGet());
        pc.setName(cube.name());
        pc.setCache(cube.cache());
        pc.setDescription(cube.description());
        pc.setEnabled(cube.enabled());

        Query query = transformQuery(cube.fact());
        pc.setQuery(query);
        pc.setVisible(cube.visible());
        pc.getAnnotations().addAll(transformAnnotations(cube.annotations()));
        pc.getMeasureGroups().add(transformMeasureGroup(cube.measures()));
        pc.getDimensionConnectors().addAll(transformDimensionConnectors(cube.dimensionUsageOrDimensions()));

        pc.getCalculatedMembers()
                .addAll(transformCalculatedMembers(pc.getDimensionConnectors(), cube.calculatedMembers()));
        pc.getNamedSets().addAll(transformNamedSets(cube.namedSets()));
        pc.getKpis().addAll(transformKpis(cube.kpis()));
        Optional<org.eclipse.daanse.rolap.mapping.emf.rolapmapping.Measure> oMeasure = findMeasure(
                cube.defaultMeasure());
        oMeasure.ifPresent(m -> pc.setDefaultMeasure(m));
        pc.setWritebackTable(transformWritebackTable(pc.getDimensionConnectors(), cube.writebackTable()));
        return pc;
    }

    private WritebackTable transformWritebackTable(List<DimensionConnector> dimensionConnectors,
            Optional<org.eclipse.daanse.rolap.mapping.mondrian.model.WritebackTable> oWritebackTable) {
        if (oWritebackTable.isPresent()) {
            org.eclipse.daanse.rolap.mapping.mondrian.model.WritebackTable writebackTable = oWritebackTable.get();
            WritebackTable wbt = EmfRolapMappingFactory.eINSTANCE.createWritebackTable();
            wbt.getWritebackAttribute()
                    .addAll(transformWritebackAttributes(dimensionConnectors, writebackTable.columns()));
            wbt.getWritebackMeasure().addAll(transformWritebackMeasures(writebackTable.columns()));
            wbt.setName(writebackTable.name());
            wbt.setSchema(writebackTable.schema());
            return wbt;
        }
        return null;
    }

    private List<WritebackMeasure> transformWritebackMeasures(
            List<org.eclipse.daanse.rolap.mapping.mondrian.model.WritebackColumn> column) {
        return column.stream()
                .filter(org.eclipse.daanse.rolap.mapping.mondrian.model.WritebackMeasure.class::isInstance)
                .map(wbm -> transformWritebackMeasure(
                        (org.eclipse.daanse.rolap.mapping.mondrian.model.WritebackMeasure) wbm))
                .toList();
    }

    private WritebackMeasure transformWritebackMeasure(
            org.eclipse.daanse.rolap.mapping.mondrian.model.WritebackMeasure writebackMeasure) {
        WritebackMeasure wbm = EmfRolapMappingFactory.eINSTANCE.createWritebackMeasure();
        wbm.setColumn(writebackMeasure.column());
        wbm.setName(writebackMeasure.name());
        return wbm;
    }

    private List<WritebackAttribute> transformWritebackAttributes(List<DimensionConnector> dimensionConnectors,
            List<org.eclipse.daanse.rolap.mapping.mondrian.model.WritebackColumn> column) {
        return column.stream()
                .filter(org.eclipse.daanse.rolap.mapping.mondrian.model.WritebackAttribute.class::isInstance)
                .map(wba -> transformWritebackAttribute(dimensionConnectors,
                        (org.eclipse.daanse.rolap.mapping.mondrian.model.WritebackAttribute) wba))
                .toList();
    }

    private WritebackAttribute transformWritebackAttribute(List<DimensionConnector> dimensionConnectors,
            org.eclipse.daanse.rolap.mapping.mondrian.model.WritebackAttribute writebackAttribute) {
        WritebackAttribute wba = EmfRolapMappingFactory.eINSTANCE.createWritebackAttribute();
        wba.setColumn(writebackAttribute.column());
        if (dimensionConnectors != null) {
            Optional<DimensionConnector> oDimC = dimensionConnectors.stream()
                    .filter(dc -> dc.getOverrideDimensionName().equals(writebackAttribute.dimension())).findAny();
            oDimC.ifPresent(d -> wba.setDimension(d.getDimension()));
        }
        return wba;
    }

    private List<Kpi> transformKpis(List<org.eclipse.daanse.rolap.mapping.mondrian.model.Kpi> kpis) {
        if (kpis != null) {
            return kpis.stream().map(this::transformKpi).toList();
        }
        return List.of();
    }

    private Kpi transformKpi(org.eclipse.daanse.rolap.mapping.mondrian.model.Kpi kpiM) {
        Kpi kpi = EmfRolapMappingFactory.eINSTANCE.createKpi();
        kpi.setId("kpi_" + counterKpi.incrementAndGet());
        kpi.setDescription(kpiM.description());
        kpi.setName(kpiM.name());
        kpi.getAnnotations().addAll(transformAnnotations(kpiM.annotations()));
        kpi.getTranslations().addAll(transformTranslations(kpiM.translations()));
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

    private List<Translation> transformTranslations(
            List<org.eclipse.daanse.rolap.mapping.mondrian.model.Translation> translations) {
        if (translations != null) {
            return translations.stream().map(this::transformTranslation).toList();
        }
        return List.of();
    }

    private Translation transformTranslation(org.eclipse.daanse.rolap.mapping.mondrian.model.Translation translation) {
        Translation t = EmfRolapMappingFactory.eINSTANCE.createTranslation();
        t.setDescription(translation.description());
        t.getAnnotations().addAll(transformAnnotations(translation.annotations()));
        t.setLanguage(translation.language());
        t.setCaption(translation.caption());
        t.setDisplayFolder(translation.displayFolder());
        return t;
    }

    private List<NamedSet> transformNamedSets(
            List<org.eclipse.daanse.rolap.mapping.mondrian.model.NamedSet> namedSets) {
        return namedSets.stream().map(this::transformNamedSet).toList();
    }

    private NamedSet transformNamedSet(org.eclipse.daanse.rolap.mapping.mondrian.model.NamedSet namedSet) {
        NamedSet ns = EmfRolapMappingFactory.eINSTANCE.createNamedSet();
        ns.setId("ns_" + counterNamedSet.incrementAndGet());
        ns.setDescription(namedSet.description());
        ns.setName(namedSet.name());
        ns.getAnnotations().addAll(transformAnnotations(namedSet.annotations()));
        ns.setDisplayFolder(namedSet.displayFolder());
        ns.setFormula(namedSet.formula());
        if (namedSet.formulaElement() != null) {
            ns.setFormula(namedSet.formulaElement().cdata());
        }
        return ns;
    }

    private List<CalculatedMember> transformCalculatedMembers(List<DimensionConnector> dimensionConnectors,
            List<org.eclipse.daanse.rolap.mapping.mondrian.model.CalculatedMember> calculatedMembers) {
        return calculatedMembers.stream().map(cm -> transformCalculatedMember(dimensionConnectors, cm)).toList();
    }

    private CalculatedMember transformCalculatedMember(List<DimensionConnector> dimensionConnectors,
            org.eclipse.daanse.rolap.mapping.mondrian.model.CalculatedMember calculatedMember) {
        CalculatedMember cm = EmfRolapMappingFactory.eINSTANCE.createCalculatedMember();
        cm.setId("cm_" + counterCalculatedMember.incrementAndGet());
        cm.setName(calculatedMember.name());
        cm.setDescription(calculatedMember.description());
        cm.getCalculatedMemberProperties()
                .addAll(transformCalculatedMemberProperties(calculatedMember.calculatedMemberProperties()));
        cm.setCellFormatter(transformCellFormatter(calculatedMember.cellFormatter()));
        cm.setFormula(calculatedMember.formula());
        if (calculatedMember.formulaElement() != null) {
            cm.setFormula(calculatedMember.formulaElement().cdata());
        }
        cm.setDisplayFolder(calculatedMember.displayFolder());
        cm.setFormatString(calculatedMember.formatString());

        cm.setParent(calculatedMember.parent());
        cm.setVisible(calculatedMember.visible());
        // calculatedMember.dimension() is a deprecated pattern we do not support.
        // if hierarchy is null server must take default hierarchy of measure dimension
        Optional<Entry<String, String>> oRes = resolveDimensionConnectorNameHierarchyName(calculatedMember.hierarchy());
        if (oRes.isPresent()) {
            Entry<String, String> res = oRes.get();
            Optional<org.eclipse.daanse.rolap.mapping.emf.rolapmapping.DimensionConnector> oDimCon = dimensionConnectors
                    .stream().filter(dc -> dc.getOverrideDimensionName().equals(res.getKey())).findAny();
            if (oDimCon.isPresent()) {
                cm.setDimensionConector(oDimCon.get());
                Optional<org.eclipse.daanse.rolap.mapping.emf.rolapmapping.Hierarchy> oHier = findHierarchyInDimensionConnectorByName(
                        oDimCon.get(), res.getValue());
                oHier.ifPresent(d -> cm.setHierarchy(d));
            }
        }
        return cm;
    }

    private List<CalculatedMemberProperty> transformCalculatedMemberProperties(
            List<org.eclipse.daanse.rolap.mapping.mondrian.model.CalculatedMemberProperty> calculatedMemberProperties) {
        return calculatedMemberProperties.stream().map(this::transformCalculatedMemberProperty).toList();
    }

    private CalculatedMemberProperty transformCalculatedMemberProperty(
            org.eclipse.daanse.rolap.mapping.mondrian.model.CalculatedMemberProperty calculatedMemberProperty) {
        CalculatedMemberProperty cmp = EmfRolapMappingFactory.eINSTANCE.createCalculatedMemberProperty();
        cmp.setId("cmp_" + counterCalculatedMemberProperty.incrementAndGet());
        cmp.setDescription(calculatedMemberProperty.description());
        cmp.setName(calculatedMemberProperty.name());
        cmp.getAnnotations().addAll(transformAnnotations(calculatedMemberProperty.annotations()));
        cmp.setValue(calculatedMemberProperty.value());
        cmp.setExpression(calculatedMemberProperty.expression());
        return cmp;
    }

    private CellFormatter transformCellFormatter(
            org.eclipse.daanse.rolap.mapping.mondrian.model.CellFormatter cellFormatter) {
        if (cellFormatter != null) {
            CellFormatter cf = EmfRolapMappingFactory.eINSTANCE.createCellFormatter();
            cf.setId("cf_" + counterCellFormatter.incrementAndGet());
            cf.getAnnotations().addAll(List.of());
            if (cellFormatter.className() != null) {
                cf.setRef(cellFormatter.className());
            }
            rolapContext.getFormatters().add(cf);
            return cf;
        }
        return null;
    }

    private List<PhysicalCube> transformPhysicalCubes(
            List<org.eclipse.daanse.rolap.mapping.mondrian.model.Cube> cubes) {
        return cubes.stream().map(this::transformPhysicalCube).toList();
    }

    private Query transformQuery(org.eclipse.daanse.rolap.mapping.mondrian.model.RelationOrJoin relationOrJoin) {

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

    private SqlSelectQuery transformSqlSelectQuery(View v) {
        if (v != null) {
            SqlSelectQuery sqlSelectQuery = EmfRolapMappingFactory.eINSTANCE.createSqlSelectQuery();
            sqlSelectQuery.setAlias(v.alias());
            sqlSelectQuery.getSQL().addAll(transformSqls(v.sqls()));
            return sqlSelectQuery;
        }
        return null;
    }

    private InlineTableQuery transformInlineTable(InlineTable it) {
        if (it != null) {
            InlineTableQuery inlineTableQuery = EmfRolapMappingFactory.eINSTANCE.createInlineTableQuery();
            inlineTableQuery.setAlias(it.alias());
            inlineTableQuery.getColumnDefinitions().addAll(transformInlineTableColumnDefinitions(it.columnDefs()));
            inlineTableQuery.getRows().addAll(transformInlineTableRows(it.rows()));
            return inlineTableQuery;
        }
        return null;
    }

    private JoinQuery transformJoinQuery(Join j) {
        if (j != null) {
            JoinQuery joinQuery = EmfRolapMappingFactory.eINSTANCE.createJoinQuery();
            RelationOrJoin rojl = j.relations() != null && j.relations().size() > 0 ? j.relations().get(0) : null;
            RelationOrJoin rojr = j.relations() != null && j.relations().size() > 1 ? j.relations().get(1) : null;
            joinQuery.setLeft(transformJoinedQueryElement(j.leftAlias(), j.leftKey(), rojl));
            joinQuery.setRight(transformJoinedQueryElement(j.rightAlias(), j.rightKey(), rojr));
            return joinQuery;
        }
        return null;
    }

    private TableQuery transformTableQuery(Table t) {
        if (t != null) {
            TableQuery tableQuery = EmfRolapMappingFactory.eINSTANCE.createTableQuery();
            tableQuery.setAlias(t.alias());
            tableQuery.setName(t.name());
            tableQuery.setSchema(t.schema());
            org.eclipse.daanse.rolap.mapping.mondrian.model.SQL sql = t.sql();
            if (sql != null) {
                tableQuery.setSqlWhereExpression(transformSql(sql));
            }
            tableQuery.getAggregationExcludes().addAll(transformAggregationExcludes(t.aggExcludes()));
            tableQuery.getAggregationTables().addAll(transformAggregationTables(t.aggTables()));
            tableQuery.getOptimizationHints().addAll(transformTableQueryOptimizationHints(t.hints()));
            return tableQuery;
        }
        return null;
    }

    private List<SQL> transformSqls(List<org.eclipse.daanse.rolap.mapping.mondrian.model.SQL> sqls) {
        return sqls.stream().map(this::transformSql).toList();
    }

    private List<InlineTableColumnDefinition> transformInlineTableColumnDefinitions(List<ColumnDef> columnDefs) {
        return columnDefs.stream().map(this::transformInlineTableColumnDefinition).toList();
    }

    private List<InlineTableRow> transformInlineTableRows(List<Row> rows) {
        return rows.stream().map(this::transformInlineTableRow).toList();
    }

    private InlineTableColumnDefinition transformInlineTableColumnDefinition(ColumnDef columnDef) {
        InlineTableColumnDefinition itcd = EmfRolapMappingFactory.eINSTANCE.createInlineTableColumnDefinition();
        itcd.setName(columnDef.name());
        if (columnDef.type() != null) {
            itcd.setType(columnDef.type().getValue());
        }
        return itcd;
    }

    private InlineTableRow transformInlineTableRow(Row row) {
        InlineTableRow itr = EmfRolapMappingFactory.eINSTANCE.createInlineTableRow();
        itr.getCells().addAll(transformInlineTableRowCells(row.values()));
        return itr;
    }

    private List<InlineTableRowCell> transformInlineTableRowCells(List<Value> values) {
        return values.stream().map(this::transformInlineTableRowCell).toList();
    }

    private InlineTableRowCell transformInlineTableRowCell(Value value) {
        InlineTableRowCell itrc = EmfRolapMappingFactory.eINSTANCE.createInlineTableRowCell();
        itrc.setValue(value.content());
        itrc.setColumnName(value.column());
        return itrc;
    }

    private JoinedQueryElement transformJoinedQueryElement(String alias, String key, RelationOrJoin roj) {
        JoinedQueryElement jqe = EmfRolapMappingFactory.eINSTANCE.createJoinedQueryElement();
        jqe.setAlias(alias);
        jqe.setKey(key);
        jqe.setQuery(transformQuery(roj));
        return jqe;
    }

    private List<TableQueryOptimizationHint> transformTableQueryOptimizationHints(List<Hint> hints) {
        return hints.stream().map(this::transformTableQueryOptimizationHint).toList();
    }

    private TableQueryOptimizationHint transformTableQueryOptimizationHint(Hint aggTable) {
        TableQueryOptimizationHint h = EmfRolapMappingFactory.eINSTANCE.createTableQueryOptimizationHint();
        h.setValue(aggTable.content());
        h.setType(aggTable.type());
        return h;
    }

    private List<AggregationTable> transformAggregationTables(List<AggTable> aggExcludes) {
        return aggExcludes.stream().map(this::transformAggregationTable).toList();
    }

    private AggregationTable transformAggregationTable(AggTable aggTable) {
        if (aggTable instanceof AggName aggName) {
            AggregationName an = EmfRolapMappingFactory.eINSTANCE.createAggregationName();
            an.setId("an_" + counterAggregationName.incrementAndGet());
            an.setAggregationFactCount(transformAggregationColumnName(aggName.aggFactCount()));
            an.getAggregationIgnoreColumns().addAll(transformAggregationColumnNames(aggName.aggIgnoreColumns()));
            an.getAggregationForeignKeys().addAll(transformAggregationForeignKeys(aggName.aggForeignKeys()));
            an.getAggregationMeasures().addAll(transformAggregationMeasures(aggName.aggMeasures()));
            an.getAggregationLevels().addAll(transformAggregationLevels(aggName.aggLevels()));
            an.getAggregationMeasureFactCounts()
                    .addAll(transformAggregationMeasureFactCounts(aggName.measuresFactCounts()));
            an.setIgnorecase(aggName.ignorecase());
            an.setApproxRowCount(aggName.approxRowCount());
            an.setName(aggName.name());
            rolapContext.getAggregationTables().add(an);
            return an;
        }
        if (aggTable instanceof AggPattern aggPattern) {
            AggregationPattern ap = EmfRolapMappingFactory.eINSTANCE.createAggregationPattern();
            ap.setId("ap_" + counterAggregationPattern.incrementAndGet());
            ap.setAggregationFactCount(transformAggregationColumnName(aggPattern.aggFactCount()));
            ap.getAggregationIgnoreColumns().addAll(transformAggregationColumnNames(aggPattern.aggIgnoreColumns()));
            ap.getAggregationForeignKeys().addAll(transformAggregationForeignKeys(aggPattern.aggForeignKeys()));
            ap.getAggregationMeasures().addAll(transformAggregationMeasures(aggPattern.aggMeasures()));
            ap.getAggregationLevels().addAll(transformAggregationLevels(aggPattern.aggLevels()));
            ap.getAggregationMeasureFactCounts()
                    .addAll(transformAggregationMeasureFactCounts(aggPattern.measuresFactCounts()));
            ap.setIgnorecase(aggPattern.ignorecase());
            ap.setPattern(aggPattern.pattern());
            ap.getExcludes().addAll(transformAggregationExcludes(aggPattern.aggExcludes()));
            rolapContext.getAggregationTables().add(ap);
            return ap;
        }
        return null;
    }

    private List<AggregationMeasureFactCount> transformAggregationMeasureFactCounts(
            List<AggMeasureFactCount> aggMeasureFactCount) {
        if (aggMeasureFactCount != null) {
            return aggMeasureFactCount.stream().map(this::transformAggregationMeasureFactCount).toList();
        }
        return List.of();
    }

    private AggregationMeasureFactCount transformAggregationMeasureFactCount(AggMeasureFactCount aggMeasureFactCount) {
        AggregationMeasureFactCount amfc = EmfRolapMappingFactory.eINSTANCE.createAggregationMeasureFactCount();
        amfc.setColumn(aggMeasureFactCount.column());
        amfc.setFactColumn(aggMeasureFactCount.factColumn());
        return amfc;
    }

    private List<AggregationLevel> transformAggregationLevels(List<AggLevel> aggLevels) {
        return aggLevels.stream().map(this::transformAggregationLevel).toList();
    }

    private AggregationLevel transformAggregationLevel(AggLevel aggLevel) {
        AggregationLevel al = EmfRolapMappingFactory.eINSTANCE.createAggregationLevel();
        al.getAggregationLevelProperties().addAll(transformAggregationLevelProperties(aggLevel.properties()));
        al.setCaptionColumn(aggLevel.captionColumn());
        al.setCollapsed(aggLevel.collapsed());
        al.setColumn(aggLevel.column());
        al.setName(aggLevel.name());
        al.setNameColumn(aggLevel.nameColumn());
        al.setOrdinalColumn(aggLevel.ordinalColumn());
        return al;
    }

    private List<AggregationLevelProperty> transformAggregationLevelProperties(
            List<AggLevelProperty> aggLevelProperties) {
        return aggLevelProperties.stream().map(this::transformAggregationLevelProperty).toList();
    }

    private AggregationLevelProperty transformAggregationLevelProperty(AggLevelProperty aggLevelProperty) {
        AggregationLevelProperty ap = EmfRolapMappingFactory.eINSTANCE.createAggregationLevelProperty();
        ap.setColumn(aggLevelProperty.column());
        ap.setName(aggLevelProperty.name());
        return ap;
    }

    private List<AggregationMeasure> transformAggregationMeasures(List<AggMeasure> aggregationMeasures) {
        return aggregationMeasures.stream().map(this::transformAggregationMeasure).toList();
    }

    private AggregationMeasure transformAggregationMeasure(AggMeasure aggMeasure) {
        AggregationMeasure am = EmfRolapMappingFactory.eINSTANCE.createAggregationMeasure();
        am.setColumn(aggMeasure.column());
        am.setName(aggMeasure.name());
        am.setRollupType(aggMeasure.rollupType());
        return am;
    }

    private List<AggregationForeignKey> transformAggregationForeignKeys(List<AggForeignKey> aggForeignKeys) {
        return aggForeignKeys.stream().map(this::transformAggregationForeignKey).toList();
    }

    private AggregationForeignKey transformAggregationForeignKey(AggForeignKey aggForeignKey) {
        AggregationForeignKey afk = EmfRolapMappingFactory.eINSTANCE.createAggregationForeignKey();
        afk.setAggregationColumn(aggForeignKey.aggColumn());
        afk.setFactColumn(aggForeignKey.factColumn());
        return afk;
    }

    private List<AggregationColumnName> transformAggregationColumnNames(List<AggColumnName> aggColumnNames) {
        return aggColumnNames.stream().map(this::transformAggregationColumnName).toList();
    }

    private AggregationColumnName transformAggregationColumnName(AggColumnName aggColumnName) {
        AggregationColumnName acn = EmfRolapMappingFactory.eINSTANCE.createAggregationColumnName();
        acn.setColumn(aggColumnName.column());
        return acn;
    }

    private List<AggregationExclude> transformAggregationExcludes(List<AggExclude> aggExcludes) {
        return aggExcludes.stream().map(this::transformAggregationExclude).toList();
    }

    private AggregationExclude transformAggregationExclude(AggExclude aggExclude) {
        AggregationExclude ae = EmfRolapMappingFactory.eINSTANCE.createAggregationExclude();
        ae.setId("ae_" + counterAggregationExclude.incrementAndGet());
        ae.setIgnorecase(aggExclude.ignorecase());
        ae.setName(aggExclude.name());
        ae.setPattern(aggExclude.pattern());
        rolapContext.getAggregationExcludes().add(ae);
        return ae;
    }

    private AccessRole transformRole(Role role) {
        AccessRole accessRole = EmfRolapMappingFactory.eINSTANCE.createAccessRole();
        accessRole.setName(role.name());
        accessRole.setId("ar_" + counterAccessRole.incrementAndGet());
        accessRole.setDescription(role.description());
        accessRole.getAccessSchemaGrants().addAll(transformAccessSchemaGrants(role.schemaGrants()));
        accessRole.getAnnotations().addAll(transformAnnotations(role.annotations()));
        accessRole.getReferencedAccessRoles();
        return accessRole;
    }

    private List<AccessRole> transformRoles(List<Role> roles) {
        return roles.stream().map(this::transformRole).toList();
    }

    private List<Dimension> transformSharedDimensions(
            List<org.eclipse.daanse.rolap.mapping.mondrian.model.Dimension> sharedDimensions) {
        return sharedDimensions.stream().map(this::transformDimension).toList();
    }

    private SQL transformSql(org.eclipse.daanse.rolap.mapping.mondrian.model.SQL sql) {
        SQL s = EmfRolapMappingFactory.eINSTANCE.createSQL();
        s.setStatement(sql.content());
        s.getDialects().add(sql.dialect());
        return s;
    }

}
