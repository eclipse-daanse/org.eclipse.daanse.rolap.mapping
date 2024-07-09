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
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.Dimension;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.DimensionConnector;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.EmfRolapMappingFactory;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.Kpi;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.MeasureGroup;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.NamedSet;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.PhysicalCube;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.Query;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.RolapContext;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.SQL;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.TableQuery;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.TableQueryOptimizationHint;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.Translation;
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
import org.eclipse.daanse.rolap.mapping.mondrian.model.CubeGrant;
import org.eclipse.daanse.rolap.mapping.mondrian.model.DimensionGrant;
import org.eclipse.daanse.rolap.mapping.mondrian.model.DimensionOrDimensionUsage;
import org.eclipse.daanse.rolap.mapping.mondrian.model.DimensionTypeEnum;
import org.eclipse.daanse.rolap.mapping.mondrian.model.DimensionUsage;
import org.eclipse.daanse.rolap.mapping.mondrian.model.Hierarchy;
import org.eclipse.daanse.rolap.mapping.mondrian.model.HierarchyGrant;
import org.eclipse.daanse.rolap.mapping.mondrian.model.Hint;
import org.eclipse.daanse.rolap.mapping.mondrian.model.Level;
import org.eclipse.daanse.rolap.mapping.mondrian.model.Measure;
import org.eclipse.daanse.rolap.mapping.mondrian.model.MemberGrant;
import org.eclipse.daanse.rolap.mapping.mondrian.model.Role;
import org.eclipse.daanse.rolap.mapping.mondrian.model.Schema;
import org.eclipse.daanse.rolap.mapping.mondrian.model.SchemaGrant;
import org.eclipse.daanse.rolap.mapping.mondrian.model.Table;

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
    private AtomicInteger counterNamedSet = new AtomicInteger();
    private AtomicInteger counterKpi = new AtomicInteger();

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

    private Optional<org.eclipse.daanse.rolap.mapping.emf.rolapmapping.Level> findfLevel(String name) {
        return rolapContext.getLevels().stream().filter(l -> l.getName().equals(name)).findAny();
    }

    private Optional<org.eclipse.daanse.rolap.mapping.emf.rolapmapping.Hierarchy> findHierarchy(String name) {
        return rolapContext.getHierarchies().stream().filter(h -> h.getName().equals(name)).findAny();
    }

    private Optional<org.eclipse.daanse.rolap.mapping.emf.rolapmapping.Measure> findMeasure(String name) {
        return rolapContext.getMeasures().stream().filter(m -> m.getName().equals(name)).findAny();
    }

    org.eclipse.daanse.rolap.mapping.emf.rolapmapping.RolapContext transform() {

        rolapContext = EmfRolapMappingFactory.eINSTANCE.createRolapContext();
        org.eclipse.daanse.rolap.mapping.emf.rolapmapping.Schema s = EmfRolapMappingFactory.eINSTANCE.createSchema();

        s.setId("s_" + 1);
        List<AccessRole> accessRoles = transformRoles(mondrianSchema.roles());
        rolapContext.getAccessRoles().addAll(accessRoles);
        List<Dimension> dimensionsShared = transformSharedDimensions(mondrianSchema.dimensions());
        rolapContext.getDimensions().addAll(dimensionsShared);
        List<PhysicalCube> physicalCubes = transformPhysicalCubes(mondrianSchema.cubes());
        rolapContext.getCubes().addAll(physicalCubes);
//
//
//        List<VirtualCube> virtualCubes= transformVirtualCubes(schema.virtualCubes());
//

        String accessRoleName = mondrianSchema.defaultRole();
        Optional<AccessRole> oDefaultAccessRole = findAccessRole(accessRoles, accessRoleName);
        oDefaultAccessRole.ifPresent(ar -> s.setDefaultAccessRole(ar));

        s.setName(mondrianSchema.name());
        s.setDescription(mondrianSchema.description());
        s.getAccessRoles().addAll(accessRoles);
        s.getAnnotations().addAll(transformAnnotations(mondrianSchema.annotations()));
        s.setMeasuresDimensionName(mondrianSchema.measuresCaption());
        s.getCubes().addAll(physicalCubes);
        rolapContext.getSchemas().add(s);

        return rolapContext;
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
        accessHierarchyGrant.setAccess(hierarchyGrant.access().toString());
//        accessHierarchyGrant.setBottomLevel(null);// hierarchyGrant.bottomLevel();
        Optional<org.eclipse.daanse.rolap.mapping.emf.rolapmapping.Hierarchy> oHier = findHierarchy(
                hierarchyGrant.hierarchy());
        oHier.ifPresent(h -> accessHierarchyGrant.setHierarchy(h));
        accessHierarchyGrant.setRollupPolicy(hierarchyGrant.rollupPolicy());
//        accessHierarchyGrant.setTopLevel(null);// hierarchyGrant.topLevel();
        accessHierarchyGrant.getMemberGrants().addAll(transformAccessMemberGrants(hierarchyGrant.memberGrants()));
        return accessHierarchyGrant;

    }

    private List<AccessHierarchyGrant> transformAccessHierarchyGrants(List<HierarchyGrant> dimensionGrants) {
        return dimensionGrants.stream().map(this::transformAccessHierarchyGrant).toList();
    }

    private AccessMemberGrant transformAccessMemberGrant(MemberGrant memberGrant) {
        AccessMemberGrant accessMemberGrant = EmfRolapMappingFactory.eINSTANCE.createAccessMemberGrant();
        accessMemberGrant.setAccess(memberGrant.access().toString());
        accessMemberGrant.setMember(memberGrant.member());
        return accessMemberGrant;

    }

    private List<AccessMemberGrant> transformAccessMemberGrants(List<MemberGrant> memberGrants) {
        return memberGrants.stream().map(this::transformAccessMemberGrant).toList();
    }

    private AccessSchemaGrant transformAccessSchemaGrant(SchemaGrant schemaGrant) {
        AccessSchemaGrant accessSchemaGrant = EmfRolapMappingFactory.eINSTANCE.createAccessSchemaGrant();
        accessSchemaGrant.setAccess(schemaGrant.access().toString());
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
//        standardDimension.setUsagePrefix(sharedDimension);
//        standardDimension.setVisible(sharedDimension.);
        dim.getAnnotations().addAll(transformAnnotations(dimension.annotations()));
        List<org.eclipse.daanse.rolap.mapping.emf.rolapmapping.Hierarchy> hierarchies = transformHierarchies(
                dimension.hierarchies());
        rolapContext.getHierarchies().addAll(hierarchies);
        dim.getHierarchies().addAll(hierarchies);
        return dim;
    }

    private DimensionConnector transformDimensionConnector(DimensionOrDimensionUsage dimensionUsageOrDimensions) {

        DimensionConnector dc = EmfRolapMappingFactory.eINSTANCE.createDimensionConnector();

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
                Optional<org.eclipse.daanse.rolap.mapping.emf.rolapmapping.Level> oLvl = findfLevel(du.level());
                oLvl.ifPresent(l -> dc.setLevel(l));
            }
            dc.setOverrideDimensionName(du.name());
            dc.setVisible(du.visible());
            dc.setUsagePrefix(du.usagePrefix());
        }
        return dc;
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
        return h;
    }

    private org.eclipse.daanse.rolap.mapping.emf.rolapmapping.Level transformLevel(Level level) {
        org.eclipse.daanse.rolap.mapping.emf.rolapmapping.Level l = EmfRolapMappingFactory.eINSTANCE.createLevel();

        l.setId("l_" + counterLevel.incrementAndGet());
        l.setName(level.name());
        l.setDescription(level.description());
        l.setApproxRowCount(level.approxRowCount());
        l.setCaptionColumn(level.captionColumn());
//      l.setCaptionExpression(level.captionExpression());
        l.setColumn(level.column());
        l.setFormatter(level.formatter());
        l.setHideMemberIf(level.hideMemberIf().getValue());
//        l.setInternalType(level.internalType().getValue());
//      l.setKeyExpression(level.keyExpression());
        l.setLevelType(level.levelType().getValue());
//      l.setMemberFormatter(level.memberFormatter());
        l.setNameColumn(level.nameColumn());
//      l.setNameExpression(level.nameExpression());
        l.setNullParentValue(level.nullParentValue());
        l.setOrdinalColumn(level.ordinalColumn());
//      l.setOrdinalExpression(level.ordinalExpression());
//        l.setParentChildLink(level.pa);
        l.setParentColumn(level.parentColumn());
//        l.setParentExpression(level.parentExpression());
        l.setTable(level.table());
        l.setType(level.type().getValue());
        l.setUniqueMembers(level.uniqueMembers());
        l.setVisible(level.visible());
        return l;
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
//        m.setCellFormatter(null);
        m.setColumn(measure.column());
        if (measure.datatype() != null) {
            m.setDatatype(measure.datatype().toString());
        }
        m.setDisplayFolder(measure.displayFolder());
        m.setFormatString(measure.formatString());
        m.setName(measure.name());
        m.setVisible(measure.visible());
//        m.setMeasureExpression(transformSql(measure.measureExpression().sqls());
        return m;
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
//pc.setWritebackTable(c);
        pc.getAnnotations().addAll(transformAnnotations(cube.annotations()));
        pc.getMeasureGroups().add(transformMeasureGroup(cube.measures()));
        pc.getDimensionConnectors().addAll(transformDimensionConnectors(cube.dimensionUsageOrDimensions()));

        pc.getCalculatedMembers().addAll(transformCalculatedMembers(cube.calculatedMembers()));
        pc.getNamedSets().addAll(transformNamedSets(cube.namedSets()));
        pc.getKpis().addAll(transformKpis(cube.kpis()));
        Optional<org.eclipse.daanse.rolap.mapping.emf.rolapmapping.Measure> oMeasure = findMeasure(cube.defaultMeasure());
        oMeasure.ifPresent(m -> pc.setDefaultMeasure(m));
        return pc;
    }

    private List<Kpi> transformKpis(
        List<org.eclipse.daanse.rolap.mapping.mondrian.model.Kpi> kpis) {
        return kpis.stream().map(this::transformKpi).toList();
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
        return translations.stream().map(this::transformTranslation).toList();
    }

    private Translation transformTranslation(org.eclipse.daanse.rolap.mapping.mondrian.model.Translation translation) {
        Translation t = EmfRolapMappingFactory.eINSTANCE.createTranslation();
        t.setDescription(translation.description());
        //t.setName(translation.name());
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
        //TODO
        return ns;
    }

    private List<CalculatedMember> transformCalculatedMembers(
        List<org.eclipse.daanse.rolap.mapping.mondrian.model.CalculatedMember> calculatedMembers) {
        return calculatedMembers.stream().map(this::transformCalculatedMember).toList();
    }

    private CalculatedMember transformCalculatedMember(org.eclipse.daanse.rolap.mapping.mondrian.model.CalculatedMember calculatedMember) {
        CalculatedMember cm = EmfRolapMappingFactory.eINSTANCE.createCalculatedMember();
        cm.setId("cm_" + counterCalculatedMember.incrementAndGet());
        cm.getCalculatedMemberProperties().addAll(transformCalculatedMemberProperties(calculatedMember.calculatedMemberProperties()));
        cm.setCellFormatter(transformCellFormatter(calculatedMember.cellFormatter()));
        cm.setFormula(calculatedMember.formula());
        cm.setDisplayFolder(calculatedMember.displayFolder());
        cm.setFormatString(calculatedMember.formatString());
        cm.setParent(calculatedMember.parent());
        cm.setVisible(calculatedMember.visible());
        Optional<Dimension> oDim = findDimension(calculatedMember.dimension());
        oDim.ifPresent(d -> cm.setDimension(d));
        Optional<org.eclipse.daanse.rolap.mapping.emf.rolapmapping.Hierarchy> oHier = findHierarchy(calculatedMember.hierarchy());
        oHier.ifPresent(d -> cm.setHierarchy(d));
        return cm;
    }

    private List<CalculatedMemberProperty> transformCalculatedMemberProperties(
            List<org.eclipse.daanse.rolap.mapping.mondrian.model.CalculatedMemberProperty> calculatedMemberProperties) {
            return calculatedMemberProperties.stream().map(this::transformCalculatedMemberProperty).toList();
    }

    private CalculatedMemberProperty transformCalculatedMemberProperty(org.eclipse.daanse.rolap.mapping.mondrian.model.CalculatedMemberProperty calculatedMemberProperty) {
        CalculatedMemberProperty cmp = EmfRolapMappingFactory.eINSTANCE.createCalculatedMemberProperty();
        cmp.setId("cmp_" + counterCalculatedMemberProperty.incrementAndGet());
        cmp.setDescription(calculatedMemberProperty.description());
        cmp.setName(calculatedMemberProperty.name());
        cmp.getAnnotations().addAll(transformAnnotations(calculatedMemberProperty.annotations()));
        cmp.setValue(calculatedMemberProperty.value());
        cmp.setExpression(calculatedMemberProperty.expression());
        //TODO
        return cmp;
    }

    private CellFormatter transformCellFormatter(org.eclipse.daanse.rolap.mapping.mondrian.model.CellFormatter cellFormatter) {
        CellFormatter cf = EmfRolapMappingFactory.eINSTANCE.createCellFormatter();
        cf.setId("cm_" + counterCellFormatter.incrementAndGet());
        cf.setDescription(null);
        cf.setName(null);
        cf.getAnnotations().addAll(List.of());
        //TODO
        return cf;
    }


    private List<PhysicalCube> transformPhysicalCubes(
            List<org.eclipse.daanse.rolap.mapping.mondrian.model.Cube> cubes) {
        return cubes.stream().map(this::transformPhysicalCube).toList();
    }

    private Query transformQuery(org.eclipse.daanse.rolap.mapping.mondrian.model.RelationOrJoin relationOrJoin) {

        if (relationOrJoin instanceof Table t) {
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
            an.setAggregationFactCount(transformAggregationColumnName(aggName.aggFactCount()));
            an.getAggregationIgnoreColumns().addAll(transformAggregationColumnNames(aggName.aggIgnoreColumns()));
            an.getAggregationForeignKeys().addAll(transformAggregationForeignKeys(aggName.aggForeignKeys()));
            an.getAggregationMeasures().addAll(transformAggregationMeasures(aggName.aggMeasures()));
            an.getAggregationLevels().addAll(transformAggregationLevels(aggName.aggLevels()));
            an.getAggregationMeasureFactCounts().addAll(transformAggregationMeasureFactCounts(aggName.measuresFactCounts()));
            an.setIgnorecase(aggName.ignorecase());
            an.setApproxRowCount(aggName.approxRowCount());
            an.setName(aggName.name());
            return an;
        } if (aggTable instanceof AggPattern aggPattern) {
            AggregationPattern ap = EmfRolapMappingFactory.eINSTANCE.createAggregationPattern();
            ap.setAggregationFactCount(transformAggregationColumnName(aggPattern.aggFactCount()));
            ap.getAggregationIgnoreColumns().addAll(transformAggregationColumnNames(aggPattern.aggIgnoreColumns()));
            ap.getAggregationForeignKeys().addAll(transformAggregationForeignKeys(aggPattern.aggForeignKeys()));
            ap.getAggregationMeasures().addAll(transformAggregationMeasures(aggPattern.aggMeasures()));
            ap.getAggregationLevels().addAll(transformAggregationLevels(aggPattern.aggLevels()));
            ap.getAggregationMeasureFactCounts().addAll(transformAggregationMeasureFactCounts(aggPattern.measuresFactCounts()));
            ap.setIgnorecase(aggPattern.ignorecase());
            ap.setPattern(aggPattern.pattern());
            ap.getExcludes().addAll(null);
            return ap;
        }
        return null;
    }

    private List<AggregationMeasureFactCount> transformAggregationMeasureFactCounts(List<AggMeasureFactCount> aggMeasureFactCount) {
        return aggMeasureFactCount.stream().map(this::transformAggregationMeasureFactCount).toList();
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

    private List<AggregationLevelProperty> transformAggregationLevelProperties(List<AggLevelProperty> aggLevelProperties) {
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
        ae.setIgnorecase(aggExclude.ignorecase());
        ae.setName(aggExclude.name());
        ae.setPattern(aggExclude.pattern());
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
