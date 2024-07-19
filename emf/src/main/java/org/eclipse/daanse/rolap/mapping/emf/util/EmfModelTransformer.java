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
package org.eclipse.daanse.rolap.mapping.emf.util;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.eclipse.daanse.rolap.mapping.api.model.AccessCubeGrantMapping;
import org.eclipse.daanse.rolap.mapping.api.model.AccessDimensionGrantMapping;
import org.eclipse.daanse.rolap.mapping.api.model.AccessHierarchyGrantMapping;
import org.eclipse.daanse.rolap.mapping.api.model.AccessRoleMapping;
import org.eclipse.daanse.rolap.mapping.api.model.AccessSchemaGrantMapping;
import org.eclipse.daanse.rolap.mapping.api.model.ActionMappingMapping;
import org.eclipse.daanse.rolap.mapping.api.model.AggregationColumnNameMapping;
import org.eclipse.daanse.rolap.mapping.api.model.AggregationExcludeMapping;
import org.eclipse.daanse.rolap.mapping.api.model.AggregationForeignKeyMapping;
import org.eclipse.daanse.rolap.mapping.api.model.AggregationLevelMapping;
import org.eclipse.daanse.rolap.mapping.api.model.AggregationLevelPropertyMapping;
import org.eclipse.daanse.rolap.mapping.api.model.AggregationMeasureFactCountMapping;
import org.eclipse.daanse.rolap.mapping.api.model.AggregationMeasureMapping;
import org.eclipse.daanse.rolap.mapping.api.model.AggregationNameMapping;
import org.eclipse.daanse.rolap.mapping.api.model.AggregationPatternMapping;
import org.eclipse.daanse.rolap.mapping.api.model.AggregationTableMapping;
import org.eclipse.daanse.rolap.mapping.api.model.AnnotationMapping;
import org.eclipse.daanse.rolap.mapping.api.model.AvgMeasureMapping;
import org.eclipse.daanse.rolap.mapping.api.model.CalculatedMemberMapping;
import org.eclipse.daanse.rolap.mapping.api.model.CalculatedMemberPropertyMapping;
import org.eclipse.daanse.rolap.mapping.api.model.CatalogMapping;
import org.eclipse.daanse.rolap.mapping.api.model.CellFormatterMapping;
import org.eclipse.daanse.rolap.mapping.api.model.CountMeasureMapping;
import org.eclipse.daanse.rolap.mapping.api.model.CubeConnectorMapping;
import org.eclipse.daanse.rolap.mapping.api.model.CubeMapping;
import org.eclipse.daanse.rolap.mapping.api.model.DatabaseColumnMapping;
import org.eclipse.daanse.rolap.mapping.api.model.DatabaseSchemaMapping;
import org.eclipse.daanse.rolap.mapping.api.model.DatabaseTableMapping;
import org.eclipse.daanse.rolap.mapping.api.model.DimensionConnectorMapping;
import org.eclipse.daanse.rolap.mapping.api.model.DimensionMapping;
import org.eclipse.daanse.rolap.mapping.api.model.DocumentationMapping;
import org.eclipse.daanse.rolap.mapping.api.model.FormatterMapping;
import org.eclipse.daanse.rolap.mapping.api.model.HierarchyMapping;
import org.eclipse.daanse.rolap.mapping.api.model.InlineTableColumnDefinitionMapping;
import org.eclipse.daanse.rolap.mapping.api.model.InlineTableQueryMapping;
import org.eclipse.daanse.rolap.mapping.api.model.InlineTableRowCellMapping;
import org.eclipse.daanse.rolap.mapping.api.model.InlineTableRowMappingMapping;
import org.eclipse.daanse.rolap.mapping.api.model.JoinQueryMapping;
import org.eclipse.daanse.rolap.mapping.api.model.JoinedQueryElementMapping;
import org.eclipse.daanse.rolap.mapping.api.model.KpiMapping;
import org.eclipse.daanse.rolap.mapping.api.model.LevelMapping;
import org.eclipse.daanse.rolap.mapping.api.model.MaxMeasureMapping;
import org.eclipse.daanse.rolap.mapping.api.model.MeasureGroupMapping;
import org.eclipse.daanse.rolap.mapping.api.model.MeasureMapping;
import org.eclipse.daanse.rolap.mapping.api.model.MemberFormatterMapping;
import org.eclipse.daanse.rolap.mapping.api.model.MemberPropertyFormatterMapping;
import org.eclipse.daanse.rolap.mapping.api.model.MemberPropertyMapping;
import org.eclipse.daanse.rolap.mapping.api.model.MemberReaderParameterMapping;
import org.eclipse.daanse.rolap.mapping.api.model.MinMeasureMapping;
import org.eclipse.daanse.rolap.mapping.api.model.NamedSetMapping;
import org.eclipse.daanse.rolap.mapping.api.model.ParameterMapping;
import org.eclipse.daanse.rolap.mapping.api.model.ParentChildLinkMapping;
import org.eclipse.daanse.rolap.mapping.api.model.PhysicalCubeMapping;
import org.eclipse.daanse.rolap.mapping.api.model.QueryMapping;
import org.eclipse.daanse.rolap.mapping.api.model.RolapContextMapping;
import org.eclipse.daanse.rolap.mapping.api.model.SQLExpressionMapping;
import org.eclipse.daanse.rolap.mapping.api.model.SQLMapping;
import org.eclipse.daanse.rolap.mapping.api.model.SchemaMapping;
import org.eclipse.daanse.rolap.mapping.api.model.SqlSelectQueryMapping;
import org.eclipse.daanse.rolap.mapping.api.model.StandardDimensionMapping;
import org.eclipse.daanse.rolap.mapping.api.model.SumMeasureMapping;
import org.eclipse.daanse.rolap.mapping.api.model.TableQueryMapping;
import org.eclipse.daanse.rolap.mapping.api.model.TableQueryOptimizationHintMapping;
import org.eclipse.daanse.rolap.mapping.api.model.TimeDimensionMapping;
import org.eclipse.daanse.rolap.mapping.api.model.VirtualCubeMapping;
import org.eclipse.daanse.rolap.mapping.api.model.WritebackAttributeMapping;
import org.eclipse.daanse.rolap.mapping.api.model.WritebackMeasureMapping;
import org.eclipse.daanse.rolap.mapping.api.model.WritebackTableMapping;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.AccessCubeGrant;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.AccessDimensionGrant;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.AccessHierarchyGrant;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.AccessRole;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.AccessSchemaGrant;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.Action;
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
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.AvgMeasure;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.CalculatedMember;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.CalculatedMemberProperty;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.Catalog;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.CellFormatter;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.CountMeasure;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.Cube;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.CubeConnector;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.DatabaseColumn;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.DatabaseSchema;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.DatabaseTable;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.Dimension;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.DimensionConnector;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.Documentation;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.EmfRolapMappingFactory;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.Formatter;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.Hierarchy;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.InlineTableColumnDefinition;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.InlineTableQuery;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.InlineTableRow;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.InlineTableRowCell;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.JoinQuery;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.JoinedQueryElement;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.Kpi;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.Level;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.MaxMeasure;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.Measure;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.MeasureGroup;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.MemberFormatter;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.MemberProperty;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.MemberPropertyFormatter;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.MemberReaderParameter;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.MinMeasure;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.NamedSet;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.Parameter;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.ParentChildLink;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.PhysicalCube;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.Query;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.RolapContext;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.SQL;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.SQLExpression;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.Schema;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.SqlSelectQuery;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.StandardDimension;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.SumMeasure;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.TableQuery;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.TableQueryOptimizationHint;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.TimeDimension;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.VirtualCube;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.WritebackAttribute;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.WritebackMeasure;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.WritebackTable;

public class EmfModelTransformer {

    private RolapContext rolapContext;

    RolapContext transform(RolapContextMapping rc) {
        rolapContext = EmfRolapMappingFactory.eINSTANCE.createRolapContext();
        transformDbschemas(rc.getDbschemas());
        transformFormatters(rc.getFormatters());
        transformAggregationExcludes(rc.getAggregationExcludes());
        transformAggregationTables(rc.getAggregationTables());
        transformMeasures(rc.getMeasures());
        transformLevels(rc.getLevels());
        transformHierarchies(rc.getHierarchies());
        transformDimensions(rc.getDimensions());
        transformCubes(rc.getCubes());
        transformAccessRoles(rc.getAccessRoles());
        transformSchemas(rc.getSchemas());
        transformCatalogs(rc.getCatalogs());
        return rolapContext;
    }

    private Optional<DatabaseSchema> findDatabaseSchemaById(String id) {
        return rolapContext.getDbschemas().stream().filter(ds -> ds.getId().equals(id)).findAny();
    }

    private Optional<Formatter> findFormatterById(String id) {
        return rolapContext.getFormatters().stream().filter(f -> f.getId().equals(id)).findAny();
    }

    private Optional<AggregationExclude> findAggregationExcludeById(String id) {
        return rolapContext.getAggregationExcludes().stream().filter(ae -> ae.getId().equals(id)).findAny();
    }

    private Optional<AggregationTable> findAggregationTableById(String id) {
        return rolapContext.getAggregationTables().stream().filter(at -> at.getId().equals(id)).findAny();
    }

    private Optional<Measure> findMeasureById(String id) {
        return rolapContext.getMeasures().stream().filter(m -> m.getId().equals(id)).findAny();
    }

    private Optional<Level> findLevelById(String id) {
        return rolapContext.getLevels().stream().filter(l -> l.getId().equals(id)).findAny();
    }

    private Optional<Hierarchy> findHierarchyById(String id) {
        return rolapContext.getHierarchies().stream().filter(h -> h.getId().equals(id)).findAny();
    }

    private Optional<Dimension> findDimensionById(String id) {
        return rolapContext.getDimensions().stream().filter(d -> d.getId().equals(id)).findAny();
    }

    private Optional<Cube> findCubeById(String id) {
        return rolapContext.getCubes().stream().filter(c -> c.getId().equals(id)).findAny();
    }

    private Optional<AccessRole> findAccessRoleById(String id) {
        return rolapContext.getAccessRoles().stream().filter(c -> c.getId().equals(id)).findAny();
    }

    private Optional<Schema> findSchemaById(String id) {
        return rolapContext.getSchemas().stream().filter(s -> s.getId().equals(id)).findAny();
    }

    private Optional<Catalog> findCatalogById(String id) {
        return rolapContext.getCatalogs().stream().filter(c -> c.getId().equals(id)).findAny();
    }

    private List<? extends AggregationExclude> transformAggregationExcludes(
        List<? extends AggregationExcludeMapping> aggregationExcludes
    ) {
        return aggregationExcludes.stream().map(a -> transformAggregationExclude(a)).toList();
    }

    private AggregationExclude transformAggregationExclude(AggregationExcludeMapping a) {
        Optional<AggregationExclude> oAE = findAggregationExcludeById(a.getId());
        if (oAE.isPresent()) {
            return oAE.get();
        } else {
            AggregationExclude emfAE = EmfRolapMappingFactory.eINSTANCE.createAggregationExclude();
            emfAE.setIgnorecase(a.isIgnorecase());
            emfAE.setName(a.getName());
            emfAE.setPattern(a.getPattern());
            emfAE.setId(a.getId());
            rolapContext.getAggregationExcludes().add(emfAE);
            return emfAE;
        }
    }

    private List<? extends AggregationTable> transformAggregationTables(
        List<? extends AggregationTableMapping> aggregationTables
    ) {
        return aggregationTables.stream().map(a -> transformAggregationTable(a)).toList();
    }

    private AggregationTable transformAggregationTable(AggregationTableMapping at) {
        Optional<AggregationTable> oAt = findAggregationTableById(at.getId());
        if (oAt.isPresent()) {
            return oAt.get();
        } else {
            if (at instanceof AggregationNameMapping aN) {
                AggregationName emfAN = EmfRolapMappingFactory.eINSTANCE.createAggregationName();
                emfAN.setAggregationFactCount(transformAggregationColumnName(aN.getAggregationFactCount()));
                emfAN.getAggregationIgnoreColumns()
                    .addAll(transformAggregationColumnNames(aN.getAggregationIgnoreColumns()));
                emfAN.getAggregationForeignKeys()
                    .addAll(transformAggregationForeignKeys(aN.getAggregationForeignKeys()));
                emfAN.getAggregationMeasures().addAll(transformAggregationMeasures(aN.getAggregationMeasures()));
                emfAN.getAggregationLevels().addAll(transformAggregationLevels(aN.getAggregationLevels()));
                emfAN.getAggregationMeasureFactCounts()
                    .addAll(transformAggregationMeasureFactCounts(aN.getAggregationMeasureFactCounts()));
                emfAN.setIgnorecase(aN.isIgnorecase());
                emfAN.setId(aN.getId());

                emfAN.setApproxRowCount(aN.getApproxRowCount());
                emfAN.setName(aN.getName());
                rolapContext.getAggregationTables().add(emfAN);
                return emfAN;
            }
            if (at instanceof AggregationPatternMapping aP) {
                AggregationPattern emfAP = EmfRolapMappingFactory.eINSTANCE.createAggregationPattern();
                emfAP.setAggregationFactCount(transformAggregationColumnName(aP.getAggregationFactCount()));
                emfAP.getAggregationIgnoreColumns()
                    .addAll(transformAggregationColumnNames(aP.getAggregationIgnoreColumns()));
                emfAP.getAggregationForeignKeys()
                    .addAll(transformAggregationForeignKeys(aP.getAggregationForeignKeys()));
                emfAP.getAggregationMeasures().addAll(transformAggregationMeasures(aP.getAggregationMeasures()));
                emfAP.getAggregationLevels().addAll(transformAggregationLevels(aP.getAggregationLevels()));
                emfAP.getAggregationMeasureFactCounts()
                    .addAll(transformAggregationMeasureFactCounts(aP.getAggregationMeasureFactCounts()));
                emfAP.setIgnorecase(aP.isIgnorecase());
                emfAP.setId(aP.getId());

                emfAP.setPattern(aP.getPattern());
                emfAP.getExcludes().addAll(transformAggregationExcludes(aP.getExcludes()));
                rolapContext.getAggregationTables().add(emfAP);
                return emfAP;
            }
            return null;
        }
    }

    private Collection<? extends AggregationColumnName> transformAggregationColumnNames(
        List<? extends AggregationColumnNameMapping> aggregationIgnoreColumns
    ) {
        return aggregationIgnoreColumns.stream().map(acn -> transformAggregationColumnName(acn)).toList();
    }

    private List<? extends AggregationMeasureFactCount> transformAggregationMeasureFactCounts(
        List<? extends AggregationMeasureFactCountMapping> aggregationMeasureFactCounts
    ) {
        return aggregationMeasureFactCounts.stream().map(amfc -> transformAggregationMeasureFactCount(amfc)).toList();
    }

    private AggregationMeasureFactCount transformAggregationMeasureFactCount(AggregationMeasureFactCountMapping amfc) {
        AggregationMeasureFactCount emfAmfc = EmfRolapMappingFactory.eINSTANCE.createAggregationMeasureFactCount();
        emfAmfc.setColumn(amfc.getColumn());
        emfAmfc.setFactColumn(amfc.getFactColumn());
        return emfAmfc;
    }

    private List<? extends AggregationLevel> transformAggregationLevels(
        List<? extends AggregationLevelMapping> aggregationLevels
    ) {
        return aggregationLevels.stream().map(al -> transformAggregationLevel(al)).toList();
    }

    private AggregationLevel transformAggregationLevel(AggregationLevelMapping al) {
        AggregationLevel emfAL = EmfRolapMappingFactory.eINSTANCE.createAggregationLevel();
        emfAL.getAggregationLevelProperties()
            .addAll(transformAggregationLevelProperties(al.getAggregationLevelProperties()));
        emfAL.setCaptionColumn(al.getCaptionColumn());
        emfAL.setCollapsed(al.isCollapsed());
        emfAL.setColumn(al.getColumn());
        emfAL.setName(al.getName());
        emfAL.setNameColumn(al.getNameColumn());
        emfAL.setOrdinalColumn(al.getOrdinalColumn());
        return emfAL;
    }

    private List<? extends AggregationLevelProperty> transformAggregationLevelProperties(
        List<? extends AggregationLevelPropertyMapping> aggregationLevelProperties
    ) {
        return aggregationLevelProperties.stream().map(alp -> transformAggregationLevelProperty(alp)).toList();
    }

    private AggregationLevelProperty transformAggregationLevelProperty(AggregationLevelPropertyMapping alp) {
        AggregationLevelProperty emfAlp = EmfRolapMappingFactory.eINSTANCE.createAggregationLevelProperty();
        emfAlp.setColumn(alp.getColumn());
        emfAlp.setName(alp.getName());
        return emfAlp;
    }

    private List<? extends AggregationMeasure> transformAggregationMeasures(
        List<? extends AggregationMeasureMapping> aggregationMeasures
    ) {
        return aggregationMeasures.stream().map(am -> transformAggregationMeasure(am)).toList();
    }

    private AggregationMeasure transformAggregationMeasure(AggregationMeasureMapping am) {
        AggregationMeasure emfAm = EmfRolapMappingFactory.eINSTANCE.createAggregationMeasure();
        emfAm.setColumn(am.getColumn());
        emfAm.setName(am.getName());
        emfAm.setRollupType(am.getRollupType());
        return emfAm;
    }

    private List<? extends AggregationForeignKey> transformAggregationForeignKeys(
        List<? extends AggregationForeignKeyMapping> aggregationForeignKeys
    ) {
        return aggregationForeignKeys.stream().map(afk -> transformAggregationForeignKey(afk)).toList();
    }

    private AggregationForeignKey transformAggregationForeignKey(AggregationForeignKeyMapping afk) {
        AggregationForeignKey emfAfk = EmfRolapMappingFactory.eINSTANCE.createAggregationForeignKey();
        emfAfk.setAggregationColumn(afk.getAggregationColumn());
        emfAfk.setFactColumn(afk.getFactColumn());
        return emfAfk;
    }

    private AggregationColumnName transformAggregationColumnName(AggregationColumnNameMapping acn) {
        AggregationColumnName emfAcn = EmfRolapMappingFactory.eINSTANCE.createAggregationColumnName();
        emfAcn.setColumn(acn.getColumn());
        return emfAcn;

    }

    private List<? extends AccessRole> transformAccessRoles(List<? extends AccessRoleMapping> accessRoles) {
        return accessRoles.stream().map(a -> transformAccessRole(a)).toList();
    }

    private AccessRole transformAccessRole(AccessRoleMapping a) {
        Optional<AccessRole> oR = findAccessRoleById(a.getId());
        if (oR.isPresent()) {
            return oR.get();
        } else {
            AccessRole emfAR = EmfRolapMappingFactory.eINSTANCE.createAccessRole();
            emfAR.getAnnotations().addAll(transformAnnotations(a.getAnnotations()));
            emfAR.setId(a.getId());
            emfAR.setDescription(a.getDescription());
            emfAR.setName(a.getName());
            emfAR.setDocumentation(transformDocumentation(a.getDocumentation()));

            emfAR.getAccessSchemaGrants().addAll(transformAccessSchemaGrants(a.getAccessSchemaGrants()));
            emfAR.getReferencedAccessRoles().addAll(transformAccessRoles(a.getReferencedAccessRoles()));
            rolapContext.getAccessRoles().add(emfAR);
            return emfAR;
        }
    }

    private List<? extends AccessSchemaGrant> transformAccessSchemaGrants(
        List<? extends AccessSchemaGrantMapping> accessSchemaGrants
    ) {
        return accessSchemaGrants.stream().map(asg -> transformAccessSchemaGrant(asg)).toList();
    }

    private AccessSchemaGrant transformAccessSchemaGrant(AccessSchemaGrantMapping asg) {
        AccessSchemaGrant emfAsg = EmfRolapMappingFactory.eINSTANCE.createAccessSchemaGrant();
        emfAsg.getCubeGrant().addAll(transformAccessCubeGrants(asg.getCubeGrant()));
        emfAsg.setAccess(asg.getAccess());
        return emfAsg;
    }

    private List<? extends AccessCubeGrant> transformAccessCubeGrants(
        List<? extends AccessCubeGrantMapping> cubeGrants
    ) {
        return cubeGrants.stream().map(cg -> transformAccessCubeGrant(cg)).toList();
    }

    private AccessCubeGrant transformAccessCubeGrant(AccessCubeGrantMapping cg) {
        AccessCubeGrant emfAcg = EmfRolapMappingFactory.eINSTANCE.createAccessCubeGrant();
        emfAcg.getDimensionGrants().addAll(transformAccessDimensionGrants(cg.getDimensionGrants()));
        emfAcg.getHierarchyGrants().addAll(transformAccessHierarchyGrants(cg.getHierarchyGrants()));
        emfAcg.setAccess(cg.getAccess());
        emfAcg.setCube(transformCube(cg.getCube()));
        return emfAcg;
    }

    private List<? extends AccessHierarchyGrant> transformAccessHierarchyGrants(
        List<? extends AccessHierarchyGrantMapping> hierarchyGrants
    ) {
        return hierarchyGrants.stream().map(hg -> transformAccessHierarchyGrant(hg)).toList();
    }

    private AccessHierarchyGrant transformAccessHierarchyGrant(AccessHierarchyGrantMapping hg) {
        AccessHierarchyGrant emfHg = EmfRolapMappingFactory.eINSTANCE.createAccessHierarchyGrant();
        emfHg.getMemberGrants().addAll(null);
        emfHg.setAccess(hg.getAccess());
        emfHg.setBottomLevel(transformLevel(hg.getBottomLevel()));
        emfHg.setRollupPolicy(hg.getRollupPolicy());
        emfHg.setTopLevel(transformLevel(hg.getTopLevel()));
        emfHg.setHierarchy(transformHierarchy(hg.getHierarchy()));
        return emfHg;

    }

    private List<? extends AccessDimensionGrant> transformAccessDimensionGrants(
        List<? extends AccessDimensionGrantMapping> dimensionGrants
    ) {
        return dimensionGrants.stream().map(dg -> transformAccessDimensionGrant(dg)).toList();
    }

    private AccessDimensionGrant transformAccessDimensionGrant(AccessDimensionGrantMapping dg) {
        AccessDimensionGrant emfDg = EmfRolapMappingFactory.eINSTANCE.createAccessDimensionGrant();
        emfDg.setAccess(dg.getAccess());
        emfDg.setAccess(dg.getAccess());
        emfDg.setDimension(transformDimension(dg.getDimension()));
        return emfDg;
    }

    private List<? extends Measure> transformMeasures(List<? extends MeasureMapping> measures) {
        return measures.stream().map(m -> transformMeasure(m)).toList();
    }

    private Measure transformMeasure(MeasureMapping m) {
        Optional<Measure> oM = findMeasureById(m.getId());
        if (oM.isPresent()) {
            return oM.get();
        } else {
            Measure mes = null;
            if (m instanceof AvgMeasureMapping avgM) {
                mes = transformAvgMeasure(avgM);
                rolapContext.getMeasures().add(mes);
                return mes;
            }
            if (m instanceof CountMeasureMapping countM) {
                mes = transformCountMeasure(countM);
                rolapContext.getMeasures().add(mes);
                return mes;
            }
            if (m instanceof MaxMeasureMapping maxM) {
                mes = transformMaxMeasure(maxM);
                rolapContext.getMeasures().add(mes);
                return mes;
            }
            if (m instanceof MinMeasureMapping minM) {
                mes = transformMinMeasure(minM);
                rolapContext.getMeasures().add(mes);
                return mes;
            }
            if (m instanceof SumMeasureMapping sumM) {
                mes = transformSumMeasure(sumM);
                rolapContext.getMeasures().add(mes);
                return mes;
            }
            return null;
        }
    }

    private SumMeasure transformSumMeasure(SumMeasureMapping sumM) {
        SumMeasure emfM = EmfRolapMappingFactory.eINSTANCE.createSumMeasure();
        emfM.setId(sumM.getId());
        emfM.setMeasureExpression(transformSQLExpression(sumM.getMeasureExpression()));
        emfM.getCalculatedMemberProperty()
            .addAll(transformCalculatedMemberProperties(sumM.getCalculatedMemberProperty()));
        emfM.setCellFormatter(transformCellFormatter(sumM.getCellFormatter()));
        emfM.setBackColor(sumM.getBackColor());
        emfM.setColumn(sumM.getColumn());
        emfM.setDatatype(sumM.getDatatype());
        emfM.setDisplayFolder(sumM.getDisplayFolder());
        emfM.setFormatString(sumM.getFormatString());
        emfM.setFormatter(sumM.getFormatter());
        emfM.setVisible(sumM.isVisible());
        emfM.setName(sumM.getName());
        return emfM;
    }

    private List<? extends CalculatedMemberProperty> transformCalculatedMemberProperties(
        List<? extends CalculatedMemberPropertyMapping> calculatedMemberProperties
    ) {
        return calculatedMemberProperties.stream().map(cmp -> transformCalculatedMemberProperty(cmp)).toList();
    }

    private CalculatedMemberProperty transformCalculatedMemberProperty(CalculatedMemberPropertyMapping cmp) {
        CalculatedMemberProperty emfCmp = EmfRolapMappingFactory.eINSTANCE.createCalculatedMemberProperty();
        emfCmp.getAnnotations().addAll(transformAnnotations(cmp.getAnnotations()));
        emfCmp.setId(cmp.getId());
        emfCmp.setDescription(cmp.getDescription());
        emfCmp.setName(cmp.getName());
        emfCmp.setDocumentation(transformDocumentation(cmp.getDocumentation()));

        emfCmp.setExpression(cmp.getExpression());
        emfCmp.setValue(cmp.getValue());
        return emfCmp;
    }

    private MinMeasure transformMinMeasure(MinMeasureMapping minM) {
        MinMeasure emfM = EmfRolapMappingFactory.eINSTANCE.createMinMeasure();
        emfM.setId(minM.getId());
        emfM.setMeasureExpression(transformSQLExpression(minM.getMeasureExpression()));
        emfM.getCalculatedMemberProperty()
            .addAll(transformCalculatedMemberProperties(minM.getCalculatedMemberProperty()));
        emfM.setCellFormatter(transformCellFormatter(minM.getCellFormatter()));
        emfM.setBackColor(minM.getBackColor());
        emfM.setColumn(minM.getColumn());
        emfM.setDatatype(minM.getDatatype());
        emfM.setDisplayFolder(minM.getDisplayFolder());
        emfM.setFormatString(minM.getFormatString());
        emfM.setFormatter(minM.getFormatter());
        emfM.setVisible(minM.isVisible());
        emfM.setName(minM.getName());
        return emfM;
    }

    private MaxMeasure transformMaxMeasure(MaxMeasureMapping maxM) {
        MaxMeasure emfM = EmfRolapMappingFactory.eINSTANCE.createMaxMeasure();
        emfM.setId(maxM.getId());
        emfM.setMeasureExpression(transformSQLExpression(maxM.getMeasureExpression()));
        emfM.getCalculatedMemberProperty()
            .addAll(transformCalculatedMemberProperties(maxM.getCalculatedMemberProperty()));
        emfM.setCellFormatter(transformCellFormatter(maxM.getCellFormatter()));
        emfM.setBackColor(maxM.getBackColor());
        emfM.setColumn(maxM.getColumn());
        emfM.setDatatype(maxM.getDatatype());
        emfM.setDisplayFolder(maxM.getDisplayFolder());
        emfM.setFormatString(maxM.getFormatString());
        emfM.setFormatter(maxM.getFormatter());
        emfM.setVisible(maxM.isVisible());
        emfM.setName(maxM.getName());

        return emfM;
    }

    private CountMeasure transformCountMeasure(CountMeasureMapping countM) {
        CountMeasure emfM = EmfRolapMappingFactory.eINSTANCE.createCountMeasure();
        emfM.setId(countM.getId());
        emfM.setMeasureExpression(transformSQLExpression(countM.getMeasureExpression()));
        emfM.getCalculatedMemberProperty()
            .addAll(transformCalculatedMemberProperties(countM.getCalculatedMemberProperty()));
        emfM.setCellFormatter(transformCellFormatter(countM.getCellFormatter()));
        emfM.setBackColor(countM.getBackColor());
        emfM.setColumn(countM.getColumn());
        emfM.setDatatype(countM.getDatatype());
        emfM.setDisplayFolder(countM.getDisplayFolder());
        emfM.setFormatString(countM.getFormatString());
        emfM.setFormatter(countM.getFormatter());
        emfM.setVisible(countM.isVisible());
        emfM.setName(countM.getName());
        return emfM;
    }

    private AvgMeasure transformAvgMeasure(AvgMeasureMapping avgM) {
        AvgMeasure emfM = EmfRolapMappingFactory.eINSTANCE.createAvgMeasure();
        emfM.setId(avgM.getId());
        emfM.setMeasureExpression(transformSQLExpression(avgM.getMeasureExpression()));
        emfM.getCalculatedMemberProperty()
            .addAll(transformCalculatedMemberProperties(avgM.getCalculatedMemberProperty()));
        emfM.setCellFormatter(transformCellFormatter(avgM.getCellFormatter()));
        emfM.setBackColor(avgM.getBackColor());
        emfM.setColumn(avgM.getColumn());
        emfM.setDatatype(avgM.getDatatype());
        emfM.setDisplayFolder(avgM.getDisplayFolder());
        emfM.setFormatString(avgM.getFormatString());
        emfM.setFormatter(avgM.getFormatter());
        emfM.setVisible(avgM.isVisible());
        emfM.setName(avgM.getName());
        return emfM;
    }

    private List<? extends DatabaseSchema> transformDbschemas(List<? extends DatabaseSchemaMapping> dbschemas) {
        return dbschemas.stream().map(d -> transformDbschemas(d)).toList();
    }

    private DatabaseSchema transformDbschemas(DatabaseSchemaMapping d) {
        Optional<DatabaseSchema> oDs = findDatabaseSchemaById(d.getId());
        if (oDs.isPresent()) {
            return oDs.get();
        } else {
            DatabaseSchema emfDs = EmfRolapMappingFactory.eINSTANCE.createDatabaseSchema();
            emfDs.setId(d.getId());
            emfDs.setName(d.getName());
            emfDs.getTables().addAll(transformDatabaseTables(d.getTables()));
            rolapContext.getDbschemas().add(emfDs);
            return emfDs;
        }
    }

    private List<? extends DatabaseTable> transformDatabaseTables(List<? extends DatabaseTableMapping> tables) {
        return tables.stream().map(t -> transformDatabaseTable(t)).toList();
    }

    private DatabaseTable transformDatabaseTable(DatabaseTableMapping t) {
        DatabaseTable emfDt = EmfRolapMappingFactory.eINSTANCE.createDatabaseTable();
        emfDt.setId(t.getId());
        emfDt.setName(t.getName());
        emfDt.setDescription(t.getDescription());
        emfDt.getColumns().addAll(transformDatabaseColumns(t.getColumns()));
        return emfDt;
    }

    private List<? extends DatabaseColumn> transformDatabaseColumns(List<? extends DatabaseColumnMapping> columns) {
        return columns.stream().map(dc -> transformDatabaseColumn(dc)).toList();
    }

    private DatabaseColumn transformDatabaseColumn(DatabaseColumnMapping dc) {
        DatabaseColumn emfDc = EmfRolapMappingFactory.eINSTANCE.createDatabaseColumn();
        emfDc.setId(dc.getId());
        emfDc.setName(dc.getName());
        emfDc.setType(dc.getType());
        emfDc.getTypeQualifiers().addAll(dc.getTypeQualifiers());
        emfDc.setDescription(dc.getDescription());
        return emfDc;
    }

    private List<? extends Level> transformLevels(List<? extends LevelMapping> levels) {
        return levels.stream().map(l -> transformLevel(l)).toList();
    }

    private Level transformLevel(LevelMapping l) {
        Optional<Level> oL = findLevelById(l.getId());
        if (oL.isPresent()) {
            return oL.get();
        } else {
            Level emfL = EmfRolapMappingFactory.eINSTANCE.createLevel();
            emfL.setId(l.getId());
            emfL.setKeyExpression(transformSQLExpression(l.getKeyExpression()));
            emfL.setNameExpression(transformSQLExpression(l.getKeyExpression()));
            emfL.setCaptionExpression(transformSQLExpression(l.getCaptionExpression()));
            emfL.setOrdinalExpression(transformSQLExpression(l.getOrdinalExpression()));
            emfL.setParentExpression(transformSQLExpression(l.getParentExpression()));
            emfL.setParentChildLink(transformParentChildLink(l.getParentChildLink()));
            emfL.getMemberProperties().addAll(transformMemberProperties(l.getMemberProperties()));
            emfL.setMemberFormatter(transformMemberFormatter(l.getMemberFormatter()));
            emfL.setApproxRowCount(l.getApproxRowCount());
            emfL.setCaptionColumn(l.getCaptionColumn());
            emfL.setColumn(l.getColumn());
            emfL.setHideMemberIf(l.getHideMemberIf());
            emfL.setInternalType(l.getInternalType());
            emfL.setLevelType(l.getLevelType());
            emfL.setNameColumn(l.getNameColumn());
            emfL.setNullParentValue(l.getNullParentValue());
            emfL.setOrdinalColumn(l.getOrdinalColumn());
            emfL.setParentColumn(l.getParentColumn());
            emfL.setType(l.getType());
            emfL.setUniqueMembers(l.isUniqueMembers());
            emfL.setVisible(l.isVisible());
            emfL.setName(l.getName());
            rolapContext.getLevels().add(emfL);
            return emfL;
        }
    }

    private MemberFormatter transformMemberFormatter(MemberFormatterMapping memberFormatter) {
        MemberFormatter emfMf = EmfRolapMappingFactory.eINSTANCE.createMemberFormatter();
        emfMf.getAnnotations().addAll(transformAnnotations(memberFormatter.getAnnotations()));
        emfMf.setId(memberFormatter.getId());
        emfMf.setDescription(memberFormatter.getDescription());
        emfMf.setName(memberFormatter.getName());
        emfMf.setDocumentation(transformDocumentation(memberFormatter.getDocumentation()));

        emfMf.setRef(memberFormatter.getRef());

        return emfMf;
    }

    private List<? extends MemberProperty> transformMemberProperties(
        List<? extends MemberPropertyMapping> memberProperties
    ) {
        return memberProperties.stream().map(mp -> transformMemberProperty(mp)).toList();
    }

    private MemberProperty transformMemberProperty(MemberPropertyMapping mp) {
        MemberProperty emfMp = EmfRolapMappingFactory.eINSTANCE.createMemberProperty();
        emfMp.setFormatter(transformMemberPropertyFormatter(mp.getFormatter()));
        emfMp.setColumn(mp.getColumn());
        emfMp.setDependsOnLevelValue(mp.isDependsOnLevelValue());
        emfMp.setType(mp.getType());
        return null;
    }

    private MemberPropertyFormatter transformMemberPropertyFormatter(MemberPropertyFormatterMapping mpfm) {
        MemberPropertyFormatter emfMpf = EmfRolapMappingFactory.eINSTANCE.createMemberPropertyFormatter();
        emfMpf.getAnnotations().addAll(transformAnnotations(mpfm.getAnnotations()));
        emfMpf.setId(mpfm.getId());
        emfMpf.setDescription(mpfm.getDescription());
        emfMpf.setName(mpfm.getName());
        emfMpf.setDocumentation(transformDocumentation(mpfm.getDocumentation()));
        emfMpf.setRef(mpfm.getRef());
        return emfMpf;
    }

    private ParentChildLink transformParentChildLink(ParentChildLinkMapping parentChildLink) {
        ParentChildLink emfPchl = EmfRolapMappingFactory.eINSTANCE.createParentChildLink();
        emfPchl.setTable(transformTableQuery(parentChildLink.getTable()));
        emfPchl.setChildColumn(parentChildLink.getChildColumn());
        emfPchl.setParentColumn(parentChildLink.getParentColumn());
        return emfPchl;
    }

    private SQLExpression transformSQLExpression(SQLExpressionMapping sqlExpression) {
        SQLExpression emfSE = EmfRolapMappingFactory.eINSTANCE.createSQLExpression();
        emfSE.getSqls().addAll(transformSQLs(sqlExpression.getSqls()));
        return emfSE;
    }

    private List<? extends SQL> transformSQLs(List<? extends SQLMapping> sqls) {
        return sqls.stream().map(s -> transformSQL(s)).toList();
    }

    private SQL transformSQL(SQLMapping s) {
        SQL emfS = EmfRolapMappingFactory.eINSTANCE.createSQL();
        emfS.getDialects().addAll(s.getDialects());
        emfS.setStatement(s.getStatement());
        return emfS;
    }

    private List<? extends Hierarchy> transformHierarchies(List<? extends HierarchyMapping> hierarchies) {
        return hierarchies.stream().map(h -> transformHierarchy(h)).toList();
    }

    private Hierarchy transformHierarchy(HierarchyMapping h) {
        Optional<Hierarchy> oH = findHierarchyById(h.getId());
        if (oH.isPresent()) {
            return oH.get();
        } else {
            Hierarchy emfH = EmfRolapMappingFactory.eINSTANCE.createHierarchy();
            emfH.getAnnotations().addAll(transformAnnotations(h.getAnnotations()));
            emfH.setId(h.getId());
            emfH.setDescription(h.getDescription());
            emfH.setName(h.getName());
            emfH.setDocumentation(transformDocumentation(h.getDocumentation()));

            emfH.getLevels().addAll(transformLevels(h.getLevels()));
            emfH.getMemberReaderParameters().addAll(transformMemberReaderParameter(h.getMemberReaderParameters()));
            emfH.setAllLevelName(h.getAllLevelName());
            emfH.setAllMemberCaption(h.getAllMemberCaption());
            emfH.setAllMemberName(h.getAllMemberName());
            emfH.setDefaultMember(h.getDefaultMember());
            emfH.setDisplayFolder(h.getDisplayFolder());
            emfH.setHasAll(h.isHasAll());
            emfH.setMemberReaderClass(h.getMemberReaderClass());
            emfH.setOrigin(h.getOrigin());
            emfH.setPrimaryKey(h.getPrimaryKey());
            emfH.setPrimaryKeyTable(h.getPrimaryKeyTable());
            emfH.setUniqueKeyLevelName(h.getAllLevelName());
            emfH.setVisible(h.isVisible());
            emfH.setQuery(transformQuery(h.getQuery()));
            rolapContext.getHierarchies().add(emfH);
            return emfH;
        }
    }

    private Collection<? extends MemberReaderParameter> transformMemberReaderParameter(
        List<? extends MemberReaderParameterMapping> memberReaderParameters
    ) {
        return memberReaderParameters.stream().map(p -> transformMemberReaderParameters(p)).toList();
    }

    private MemberReaderParameter transformMemberReaderParameters(MemberReaderParameterMapping p) {
        MemberReaderParameter emfp = EmfRolapMappingFactory.eINSTANCE.createMemberReaderParameter();
        emfp.setName(p.getName());
        emfp.setValue(p.getValue());
        return emfp;
    }

    private List<? extends Dimension> transformDimensions(List<? extends DimensionMapping> dimensions) {
        return dimensions.stream().map(d -> transformDimension(d)).toList();
    }

    private Dimension transformDimension(DimensionMapping d) {
        Optional<Dimension> oD = findDimensionById(d.getId());
        if (oD.isPresent()) {
            return oD.get();
        } else {
            if (d instanceof StandardDimensionMapping sd) {
                StandardDimension emfD = EmfRolapMappingFactory.eINSTANCE.createStandardDimension();
                emfD.getAnnotations().addAll(transformAnnotations(sd.getAnnotations()));
                emfD.setId(sd.getId());
                emfD.setDescription(sd.getDescription());
                emfD.setName(sd.getName());
                emfD.setDocumentation(transformDocumentation(sd.getDocumentation()));
                emfD.getHierarchies().addAll(transformHierarchies(sd.getHierarchies()));
                emfD.setUsagePrefix(sd.getUsagePrefix());
                emfD.setVisible(sd.isVisible());
                rolapContext.getDimensions().add(emfD);
                return emfD;
            }
            if (d instanceof TimeDimensionMapping td) {
                TimeDimension emfD = EmfRolapMappingFactory.eINSTANCE.createTimeDimension();
                emfD.getAnnotations().addAll(transformAnnotations(td.getAnnotations()));
                emfD.setId(td.getId());
                emfD.setDescription(td.getDescription());
                emfD.setName(td.getName());
                emfD.setDocumentation(transformDocumentation(td.getDocumentation()));
                emfD.getHierarchies().addAll(transformHierarchies(td.getHierarchies()));
                emfD.setUsagePrefix(td.getUsagePrefix());
                emfD.setVisible(td.isVisible());
                rolapContext.getDimensions().add(emfD);
                return emfD;
            }
            return null;
        }
    }

    private List<? extends Cube> transformCubes(List<? extends CubeMapping> cubes) {
        return cubes.stream().map(c -> transformCube(c)).toList();
    }

    private Cube transformCube(CubeMapping c) {
        Optional<Cube> oC = findCubeById(c.getId());
        if (oC.isPresent()) {
            return oC.get();
        } else {
            if (c instanceof PhysicalCubeMapping pcm) {
                PhysicalCube emfFC = EmfRolapMappingFactory.eINSTANCE.createPhysicalCube();
                emfFC.getAnnotations().addAll(transformAnnotations(pcm.getAnnotations()));
                emfFC.setId(pcm.getId());
                emfFC.setDescription(pcm.getDescription());
                emfFC.setName(pcm.getName());
                emfFC.setDocumentation(transformDocumentation(pcm.getDocumentation()));

                emfFC.setQuery(transformQuery(pcm.getQuery()));
                emfFC.setWritebackTable(transformWritebackTable(pcm.getWritebackTable()));
                emfFC.getAction().addAll(transformActions(pcm.getAction()));
                emfFC.setCache(pcm.isCache());

                emfFC.getDimensionConnectors().addAll(transformDimensionConnectors(pcm.getDimensionConnectors()));
                emfFC.getCalculatedMembers().addAll(transformCalculatedMembers(pcm.getCalculatedMembers()));
                emfFC.getNamedSets().addAll(transformNamedSets(pcm.getNamedSets()));
                emfFC.getKpis().addAll(transformKpis(pcm.getKpis()));
                emfFC.setDefaultMeasure(transformMeasure(pcm.getDefaultMeasure()));
                emfFC.setEnabled(pcm.isEnabled());
                emfFC.setVisible(pcm.isVisible());
                emfFC.getMeasureGroups().addAll(transformnullMeasureGroups(pcm.getMeasureGroups()));
                rolapContext.getCubes().add(emfFC);
                return emfFC;
            }
            if (c instanceof VirtualCubeMapping vcm) {
                VirtualCube emfVC = EmfRolapMappingFactory.eINSTANCE.createVirtualCube();
                emfVC.getAnnotations().addAll(transformAnnotations(vcm.getAnnotations()));
                emfVC.setId(vcm.getId());
                emfVC.setDescription(vcm.getDescription());
                emfVC.setName(vcm.getName());
                emfVC.setDocumentation(transformDocumentation(vcm.getDocumentation()));

                emfVC.getCubeUsages().addAll(transformCubeConnectors(vcm.getCubeUsages()));

                emfVC.getDimensionConnectors().addAll(transformDimensionConnectors(vcm.getDimensionConnectors()));
                emfVC.getCalculatedMembers().addAll(transformCalculatedMembers(vcm.getCalculatedMembers()));
                emfVC.getNamedSets().addAll(transformNamedSets(vcm.getNamedSets()));
                emfVC.getKpis().addAll(transformKpis(vcm.getKpis()));
                emfVC.setDefaultMeasure(transformMeasure(vcm.getDefaultMeasure()));
                emfVC.setEnabled(vcm.isEnabled());
                emfVC.setVisible(vcm.isVisible());
                emfVC.getMeasureGroups().addAll(transformnullMeasureGroups(vcm.getMeasureGroups()));
                rolapContext.getCubes().add(emfVC);
                return emfVC;
            }
            return null;
        }
    }

    private List<? extends CubeConnector> transformCubeConnectors(List<? extends CubeConnectorMapping> cubeConnectors) {
        return cubeConnectors.stream().map(cc -> transformCubeConnector(cc)).toList();
    }

    private CubeConnector transformCubeConnector(CubeConnectorMapping cc) {
        CubeConnector emfCC = EmfRolapMappingFactory.eINSTANCE.createCubeConnector();
        emfCC.setCube(transformCube(cc.getCube()));
        emfCC.setIgnoreUnrelatedDimensions(cc.isIgnoreUnrelatedDimensions());
        return emfCC;
    }

    private List<? extends MeasureGroup> transformnullMeasureGroups(List<? extends MeasureGroupMapping> measureGroups) {
        return measureGroups.stream().map(mg -> transformMeasureGroup(mg)).toList();
    }

    private MeasureGroup transformMeasureGroup(MeasureGroupMapping mg) {
        MeasureGroup emfMG = EmfRolapMappingFactory.eINSTANCE.createMeasureGroup();
        emfMG.getMeasures().addAll(transformMeasures(mg.getMeasures()));
        emfMG.setName(mg.getName());
        return emfMG;
    }

    private Collection<? extends Kpi> transformKpis(List<? extends KpiMapping> kpis) {
        return kpis.stream().map(kpi -> transformKpi(kpi)).toList();
    }

    private Kpi transformKpi(KpiMapping kpi) {
        Kpi emfKpi = EmfRolapMappingFactory.eINSTANCE.createKpi();
        emfKpi.getAnnotations().addAll(transformAnnotations(kpi.getAnnotations()));
        emfKpi.setId(kpi.getId());
        emfKpi.setDescription(kpi.getDescription());
        emfKpi.setName(kpi.getName());
        emfKpi.setDocumentation(transformDocumentation(kpi.getDocumentation()));

        emfKpi.getTranslations().addAll(null);
        emfKpi.setDisplayFolder(kpi.getDisplayFolder());
        emfKpi.setAssociatedMeasureGroupID(kpi.getAssociatedMeasureGroupID());
        emfKpi.setValue(kpi.getValue());
        emfKpi.setGoal(kpi.getGoal());
        emfKpi.setStatus(kpi.getStatus());
        emfKpi.setTrend(kpi.getTrend());
        emfKpi.setWeight(kpi.getWeight());
        emfKpi.setTrendGraphic(kpi.getTrendGraphic());
        emfKpi.setStatusGraphic(kpi.getStatusGraphic());
        emfKpi.setCurrentTimeMember(kpi.getCurrentTimeMember());
        emfKpi.setParentKpiID(kpi.getParentKpiID());
        return emfKpi;
    }

    private List<? extends CalculatedMember> transformCalculatedMembers(
        List<? extends CalculatedMemberMapping> calculatedMembers
    ) {
        return calculatedMembers.stream().map(cm -> transformCalculatedMember(cm)).toList();
    }

    private CalculatedMember transformCalculatedMember(CalculatedMemberMapping cm) {
        CalculatedMember emfCM = EmfRolapMappingFactory.eINSTANCE.createCalculatedMember();
        emfCM.getAnnotations().addAll(transformAnnotations(cm.getAnnotations()));
        emfCM.setId(cm.getId());
        emfCM.setDescription(cm.getDescription());
        emfCM.setName(cm.getName());
        emfCM.setDocumentation(transformDocumentation(cm.getDocumentation()));

        emfCM.getCalculatedMemberProperties()
            .addAll(transformCalculatedMemberProperties(cm.getCalculatedMemberProperties()));
        emfCM.setCellFormatter(transformCellFormatter(cm.getCellFormatter()));
        emfCM.setFormula(cm.getFormula());
        emfCM.setDisplayFolder(cm.getDisplayFolder());
        emfCM.setFormatString(cm.getFormatString());
        emfCM.setHierarchy(transformHierarchy(cm.getHierarchy()));
        emfCM.setDimensionConector(transformDimensionConnector(cm.getDimensionConector()));
        emfCM.setParent(cm.getParent());
        return emfCM;
    }

    private List<? extends DimensionConnector> transformDimensionConnectors(
        List<? extends DimensionConnectorMapping> dimensionConnectors
    ) {
        return dimensionConnectors.stream().map(dc -> transformDimensionConnector(dc)).toList();
    }

    private DimensionConnector transformDimensionConnector(DimensionConnectorMapping dc) {
        DimensionConnector emfDC = EmfRolapMappingFactory.eINSTANCE.createDimensionConnector();
        emfDC.setForeignKey(dc.getForeignKey());
        emfDC.setLevel(transformLevel(dc.getLevel()));
        emfDC.setUsagePrefix(dc.getUsagePrefix());
        emfDC.setVisible(dc.isVisible());
        emfDC.getDimension();
        emfDC.setOverrideDimensionName(dc.getOverrideDimensionName());
        return emfDC;
    }

    private List<? extends Action> transformActions(List<? extends ActionMappingMapping> actions) {
        return actions.stream().map(a -> transformAction(a)).toList();
    }

    private Action transformAction(ActionMappingMapping a) {
        Action emfA = EmfRolapMappingFactory.eINSTANCE.createAction();
        emfA.getAnnotations().addAll(transformAnnotations(a.getAnnotations()));
        emfA.setId(a.getId());
        emfA.setDescription(a.getDescription());
        emfA.setName(a.getName());
        emfA.setDocumentation(transformDocumentation(a.getDocumentation()));
        return emfA;
    }

    private WritebackTable transformWritebackTable(WritebackTableMapping writebackTable) {
        WritebackTable emfWbt = EmfRolapMappingFactory.eINSTANCE.createWritebackTable();
        emfWbt.getWritebackAttribute().addAll(transformWritebackAttributes(writebackTable.getWritebackAttribute()));
        emfWbt.getWritebackMeasure().addAll(transformWritebackMeasures(writebackTable.getWritebackMeasure()));
        emfWbt.setName(writebackTable.getName());
        emfWbt.setSchema(writebackTable.getSchema());
        return emfWbt;
    }

    private List<? extends WritebackMeasure> transformWritebackMeasures(
        List<? extends WritebackMeasureMapping> writebackMeasures
    ) {
        return writebackMeasures.stream().map(m -> transformWritebackMeasure(m)).toList();
    }

    private WritebackMeasure transformWritebackMeasure(WritebackMeasureMapping m) {
        WritebackMeasure emfWbm = EmfRolapMappingFactory.eINSTANCE.createWritebackMeasure();
        emfWbm.setColumn(m.getColumn());
        emfWbm.setName(m.getName());
        return emfWbm;
    }

    private List<? extends WritebackAttribute> transformWritebackAttributes(
        List<? extends WritebackAttributeMapping> writebackAttributes
    ) {
        return writebackAttributes.stream().map(a -> transformWritebackAttribute(a)).toList();
    }

    private WritebackAttribute transformWritebackAttribute(WritebackAttributeMapping a) {
        WritebackAttribute emfWba = EmfRolapMappingFactory.eINSTANCE.createWritebackAttribute();
        emfWba.setColumn(a.getColumn());
        emfWba.setDimension(transformDimension(a.getDimension()));
        return emfWba;
    }

    private Query transformQuery(QueryMapping query) {
        if (query instanceof TableQueryMapping tq) {
            return transformTableQuery(tq);
        }
        if (query instanceof JoinQueryMapping jq) {
            return transformJoinQuery(jq);
        }
        if (query instanceof InlineTableQueryMapping itq) {
            return transformInlineTableQuery(itq);
        }
        if (query instanceof SqlSelectQueryMapping ssq) {
            return transformSqlSelectQuery(ssq);
        }
        return null;
    }

    private SqlSelectQuery transformSqlSelectQuery(SqlSelectQueryMapping ssq) {
        SqlSelectQuery emfSSQ = EmfRolapMappingFactory.eINSTANCE.createSqlSelectQuery();
        emfSSQ.getSQL().addAll(transformSQLs(ssq.getSQL()));
        emfSSQ.setAlias(ssq.getAlias());
        return emfSSQ;
    }

    private InlineTableQuery transformInlineTableQuery(InlineTableQueryMapping itq) {
        InlineTableQuery emfIT = EmfRolapMappingFactory.eINSTANCE.createInlineTableQuery();
        emfIT.getColumnDefinitions().addAll(transformInlineTableColumnDefinitions(itq.getColumnDefinitions()));
        emfIT.getRows().addAll(transformRows(itq.getRows()));
        emfIT.setAlias(itq.getAlias());
        return emfIT;
    }

    private List<? extends InlineTableColumnDefinition> transformInlineTableColumnDefinitions(
        List<? extends InlineTableColumnDefinitionMapping> columnDefinitions
    ) {
        return columnDefinitions.stream().map(cd -> transformInlineTableColumnDefinition(cd)).toList();
    }

    private InlineTableColumnDefinition transformInlineTableColumnDefinition(InlineTableColumnDefinitionMapping cd) {
        InlineTableColumnDefinition emfItcd = EmfRolapMappingFactory.eINSTANCE.createInlineTableColumnDefinition();
        emfItcd.setName(cd.getName());
        emfItcd.setType(cd.getType());
        return emfItcd;
    }

    private List<? extends InlineTableRow> transformRows(List<? extends InlineTableRowMappingMapping> rows) {
        return rows.stream().map(r -> transformInlineTableRow(r)).toList();
    }

    private InlineTableRow transformInlineTableRow(InlineTableRowMappingMapping r) {
        InlineTableRow emfItr = EmfRolapMappingFactory.eINSTANCE.createInlineTableRow();
        emfItr.getCells().addAll(transformInlineTableRowCells(r.getCells()));
        return emfItr;
    }

    private List<? extends InlineTableRowCell> transformInlineTableRowCells(
        List<? extends InlineTableRowCellMapping> cells
    ) {
        return cells.stream().map(c -> transformInlineTableRowCell(c)).toList();
    }

    private InlineTableRowCell transformInlineTableRowCell(InlineTableRowCellMapping c) {
        InlineTableRowCell emfItrc = EmfRolapMappingFactory.eINSTANCE.createInlineTableRowCell();
        emfItrc.setValue(c.getValue());
        emfItrc.setColumnName(c.getColumnName());
        return emfItrc;
    }

    private JoinQuery transformJoinQuery(JoinQueryMapping jq) {
        JoinQuery emfJQ = EmfRolapMappingFactory.eINSTANCE.createJoinQuery();
        emfJQ.setLeft(transformJoinedQueryElement(jq.getLeft()));
        emfJQ.setRight(transformJoinedQueryElement(jq.getRight()));
        return emfJQ;
    }

    private JoinedQueryElement transformJoinedQueryElement(JoinedQueryElementMapping jqe) {
        JoinedQueryElement emfjqe = EmfRolapMappingFactory.eINSTANCE.createJoinedQueryElement();
        emfjqe.setAlias(jqe.getAlias());
        emfjqe.setKey(jqe.getKey());
        emfjqe.setQuery(transformQuery(jqe.getQuery()));
        return null;
    }

    private TableQuery transformTableQuery(TableQueryMapping tq) {
        TableQuery emfTQ = EmfRolapMappingFactory.eINSTANCE.createTableQuery();
        emfTQ.setSqlWhereExpression(transformSQL(tq.getSqlWhereExpression()));
        emfTQ.getAggregationExcludes().addAll(transformAggregationExcludes(tq.getAggregationExcludes()));
        emfTQ.getOptimizationHints().addAll(transformOptimizationHints(tq.getOptimizationHints()));
        emfTQ.setName(tq.getName());
        emfTQ.setSchema(tq.getSchema());
        emfTQ.getAggregationTables().addAll(transformAggregationTables(tq.getAggregationTables()));
        return emfTQ;
    }

    private List<? extends TableQueryOptimizationHint> transformOptimizationHints(
        List<? extends TableQueryOptimizationHintMapping> optimizationHints
    ) {
        return optimizationHints.stream().map(oh -> transformTableQueryOptimizationHint(oh)).toList();
    }

    private TableQueryOptimizationHint transformTableQueryOptimizationHint(TableQueryOptimizationHintMapping oh) {
        TableQueryOptimizationHint tqoh = EmfRolapMappingFactory.eINSTANCE.createTableQueryOptimizationHint();
        tqoh.setValue(oh.getValue());
        tqoh.setType(oh.getType());
        return tqoh;
    }

    private List<? extends Schema> transformSchemas(List<? extends SchemaMapping> schemas) {
        return schemas.stream().map(s -> transformSchema(s)).toList();
    }

    private Schema transformSchema(SchemaMapping s) {
        Optional<Schema> oS = findSchemaById(s.getId());
        if (oS.isPresent()) {
            return oS.get();
        } else {
            Schema emfS = EmfRolapMappingFactory.eINSTANCE.createSchema();
            emfS.getAnnotations().addAll(transformAnnotations(s.getAnnotations()));
            emfS.setId(s.getId());
            emfS.setDescription(s.getDescription());
            emfS.setName(s.getName());
            emfS.setDocumentation(transformDocumentation(s.getDocumentation()));
            emfS.getParameters().addAll(transformParameters(s.getParameters()));
            emfS.getCubes().addAll(transformCubes(s.getCubes()));
            emfS.getNamedSets().addAll(transformNamedSets(s.getNamedSets()));
            emfS.getAccessRoles().addAll(transformAccessRoles(s.getAccessRoles()));
            emfS.setDefaultAccessRole(transformAccessRole(s.getDefaultAccessRole()));
            emfS.setMeasuresDimensionName(s.getMeasuresDimensionName());
            rolapContext.getSchemas().add(emfS);
            return emfS;
        }
    }

    private List<? extends NamedSet> transformNamedSets(List<? extends NamedSetMapping> namedSets) {
        return namedSets.stream().map(ns -> transformNamedSet(ns)).toList();
    }

    private NamedSet transformNamedSet(NamedSetMapping ns) {
        NamedSet emfNs = EmfRolapMappingFactory.eINSTANCE.createNamedSet();
        emfNs.getAnnotations().addAll(transformAnnotations(ns.getAnnotations()));
        emfNs.setId(ns.getId());
        emfNs.setDescription(ns.getDescription());
        emfNs.setName(ns.getName());
        emfNs.setDocumentation(transformDocumentation(ns.getDocumentation()));
        emfNs.setDisplayFolder(ns.getDisplayFolder());
        emfNs.setFormula(ns.getFormula());
        return emfNs;
    }

    private List<? extends Parameter> transformParameters(List<? extends ParameterMapping> parameters) {
        return parameters.stream().map(p -> transformParameter(p)).toList();
    }

    private Parameter transformParameter(ParameterMapping p) {
        Parameter emfP = EmfRolapMappingFactory.eINSTANCE.createParameter();
        emfP.setDefaultValue(p.getDefaultValue());
        emfP.setDescription(p.getDescription());
        emfP.setModifiable(p.isModifiable());
        emfP.setName(p.getName());
        emfP.setType(p.getType());
        return emfP;
    }

    private List<? extends Catalog> transformCatalogs(List<? extends CatalogMapping> catalogs) {
        return catalogs.stream().map(c -> transformCatalog(c)).toList();
    }

    private Catalog transformCatalog(CatalogMapping c) {
        Optional<Catalog> oC = findCatalogById(c.getId());
        if (oC.isPresent()) {
            return oC.get();
        } else {
            Catalog emfC = EmfRolapMappingFactory.eINSTANCE.createCatalog();
            emfC.getAnnotations().addAll(transformAnnotations(c.getAnnotations()));
            emfC.setId(c.getId());
            emfC.setDescription(c.getDescription());
            emfC.setName(c.getName());
            emfC.setDocumentation(transformDocumentation(c.getDocumentation()));
            emfC.getSchemas().addAll(transformSchemas(c.getSchemas()));
            rolapContext.getCatalogs().add(emfC);
            return emfC;
        }
    }

    private List<? extends Formatter> transformFormatters(List<? extends FormatterMapping> formatters) {
        return formatters.stream().map(f -> transformFormatter(f)).toList();
    }

    private Formatter transformFormatter(FormatterMapping f) {
        Optional<Formatter> oF = findFormatterById(f.getId());
        if (oF.isPresent()) {
            return oF.get();
        } else {
            Formatter formatter = null;
            if (f instanceof CellFormatterMapping cfm) {
                formatter = transformCellFormatter(cfm);
                rolapContext.getFormatters().add(formatter);
                return formatter;
            }
            if (f instanceof MemberFormatterMapping mfm) {
                formatter = transformMemberFormatter(mfm);
                rolapContext.getFormatters().add(formatter);
                return formatter;

            }
            if (f instanceof MemberPropertyFormatterMapping mpfm) {
                formatter = transformMemberPropertyFormatter(mpfm);
                rolapContext.getFormatters().add(formatter);
                return formatter;

            }
            return null;
        }
    }

    private CellFormatter transformCellFormatter(CellFormatterMapping cfm) {
        CellFormatter emfCf = EmfRolapMappingFactory.eINSTANCE.createCellFormatter();
        emfCf.getAnnotations().addAll(transformAnnotations(cfm.getAnnotations()));
        emfCf.setId(cfm.getId());
        emfCf.setDescription(cfm.getDescription());
        emfCf.setName(cfm.getName());
        emfCf.setDocumentation(transformDocumentation(cfm.getDocumentation()));
        emfCf.setRef(cfm.getRef());
        return emfCf;
    }

    private Documentation transformDocumentation(DocumentationMapping documentation) {
        Documentation emfd = EmfRolapMappingFactory.eINSTANCE.createDocumentation();
        emfd.setValue(documentation.getValue());
        return emfd;
    }

    private List<? extends Annotation> transformAnnotations(List<? extends AnnotationMapping> annotations) {
        return annotations.stream().map(a -> transformAnnotation(a)).toList();
    }

    private Annotation transformAnnotation(AnnotationMapping a) {
        Annotation emfa = EmfRolapMappingFactory.eINSTANCE.createAnnotation();
        emfa.setValue(a.getValue());
        emfa.setName(a.getName());
        return emfa;
    }

}
