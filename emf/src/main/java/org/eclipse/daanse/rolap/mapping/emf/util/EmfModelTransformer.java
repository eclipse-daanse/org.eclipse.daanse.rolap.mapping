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

import java.util.List;

import org.eclipse.daanse.rolap.mapping.api.model.AccessRoleMapping;
import org.eclipse.daanse.rolap.mapping.api.model.AggregationExcludeMapping;
import org.eclipse.daanse.rolap.mapping.api.model.AggregationNameMapping;
import org.eclipse.daanse.rolap.mapping.api.model.AggregationPatternMapping;
import org.eclipse.daanse.rolap.mapping.api.model.AggregationTableMapping;
import org.eclipse.daanse.rolap.mapping.api.model.AnnotationMapping;
import org.eclipse.daanse.rolap.mapping.api.model.AvgMeasureMapping;
import org.eclipse.daanse.rolap.mapping.api.model.CatalogMapping;
import org.eclipse.daanse.rolap.mapping.api.model.CellFormatterMapping;
import org.eclipse.daanse.rolap.mapping.api.model.CountMeasureMapping;
import org.eclipse.daanse.rolap.mapping.api.model.CubeMapping;
import org.eclipse.daanse.rolap.mapping.api.model.DatabaseSchemaMapping;
import org.eclipse.daanse.rolap.mapping.api.model.DimensionMapping;
import org.eclipse.daanse.rolap.mapping.api.model.DocumentationMapping;
import org.eclipse.daanse.rolap.mapping.api.model.FormatterMapping;
import org.eclipse.daanse.rolap.mapping.api.model.HierarchyMapping;
import org.eclipse.daanse.rolap.mapping.api.model.LevelMapping;
import org.eclipse.daanse.rolap.mapping.api.model.MaxMeasureMapping;
import org.eclipse.daanse.rolap.mapping.api.model.MeasureMapping;
import org.eclipse.daanse.rolap.mapping.api.model.MemberFormatterMapping;
import org.eclipse.daanse.rolap.mapping.api.model.MemberPropertyFormatterMapping;
import org.eclipse.daanse.rolap.mapping.api.model.MinMeasureMapping;
import org.eclipse.daanse.rolap.mapping.api.model.NamedSetMapping;
import org.eclipse.daanse.rolap.mapping.api.model.ParameterMapping;
import org.eclipse.daanse.rolap.mapping.api.model.PhysicalCubeMapping;
import org.eclipse.daanse.rolap.mapping.api.model.RolapContextMapping;
import org.eclipse.daanse.rolap.mapping.api.model.SchemaMapping;
import org.eclipse.daanse.rolap.mapping.api.model.SumMeasureMapping;
import org.eclipse.daanse.rolap.mapping.api.model.VirtualCubeMapping;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.AccessRole;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.AggregationExclude;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.AggregationTable;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.Annotation;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.Catalog;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.CellFormatter;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.Cube;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.DatabaseSchema;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.Dimension;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.Documentation;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.EmfRolapMappingFactory;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.Formatter;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.Hierarchy;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.Level;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.Measure;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.MemberFormatter;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.MemberPropertyFormatter;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.NamedSet;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.Parameter;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.RolapContext;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.Schema;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.StandardDimension;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.TimeDimension;

public class EmfModelTransformer {

    RolapContext transform(RolapContextMapping rc) {
        RolapContext emfRc = EmfRolapMappingFactory.eINSTANCE.createRolapContext();
        emfRc.getFormatters().addAll(transformFormatter(rc.getFormatters()));
        emfRc.getCatalogs().addAll(transformCatalogs(rc.getCatalogs()));
        emfRc.getSchemas().addAll(transformSchemas(rc.getSchemas()));
        emfRc.getCubes().addAll(transformCubes(rc.getCubes()));
        emfRc.getDimensions().addAll(transformDimensions(rc.getDimensions()));
        emfRc.getHierarchies().addAll(transformHierarchies(rc.getHierarchies()));
        emfRc.getLevels().addAll(transformLevels(rc.getLevels()));
        emfRc.getDbschemas().addAll(transformDbschemas(rc.getDbschemas()));
        emfRc.getMeasures().addAll(transformMeasures(rc.getMeasures()));
        emfRc.getAccessRoles().addAll(transformAccessRoles(rc.getAccessRoles()));
        emfRc.getAggregationTables().addAll(transformAggregationTables(rc.getAggregationTables()));
        emfRc.getAggregationExcludes().addAll(transformAggregationExcludes(rc.getAggregationExcludes()));
        return emfRc;
    }

    private List<? extends AggregationExclude> transformAggregationExcludes(
        List<? extends AggregationExcludeMapping> aggregationExcludes
    ) {
        return aggregationExcludes.stream().map(a -> transformAggregationExclude(a)).toList();
    }

    private AggregationExclude transformAggregationExclude(AggregationExcludeMapping a) {
        AggregationExclude emfAE = EmfRolapMappingFactory.eINSTANCE.createAggregationExclude();
        // TODO Auto-generated method stub
        return emfAE;
    }

    private List<? extends AggregationTable> transformAggregationTables(
        List<? extends AggregationTableMapping> aggregationTables
    ) {
        return aggregationTables.stream().map(a -> transformAggregationTable(a)).toList();
    }

    private AggregationTable transformAggregationTable(AggregationTableMapping at) {
        if (at instanceof AggregationNameMapping aN) {
            AggregationTable emfAN = EmfRolapMappingFactory.eINSTANCE.createAggregationName();
            return emfAN;
        }
        if (at instanceof AggregationPatternMapping aN) {
            AggregationTable emfAP = EmfRolapMappingFactory.eINSTANCE.createAggregationPattern();
            return emfAP;
        }
        // TODO Auto-generated method stub
        return null;
    }

    private List<? extends AccessRole> transformAccessRoles(List<? extends AccessRoleMapping> accessRoles) {
        return accessRoles.stream().map(a -> transformAccessRole(a)).toList();
    }

    private AccessRole transformAccessRole(AccessRoleMapping a) {
        AccessRole emfAR = EmfRolapMappingFactory.eINSTANCE.createAccessRole();
        return emfAR;
    }

    private List<? extends Measure> transformMeasures(List<? extends MeasureMapping> measures) {
        return measures.stream().map(m -> transformMeasure(m)).toList();
    }

    private Measure transformMeasure(MeasureMapping m) {
        if (m instanceof AvgMeasureMapping avgM) {
            Measure emfM = EmfRolapMappingFactory.eINSTANCE.createAvgMeasure();
            return emfM;
        }
        if (m instanceof CountMeasureMapping countM) {
            Measure emfM = EmfRolapMappingFactory.eINSTANCE.createCountMeasure();
            return emfM;
        }
        if (m instanceof MaxMeasureMapping maxM) {
            Measure emfM = EmfRolapMappingFactory.eINSTANCE.createMaxMeasure();
            return emfM;
        }
        if (m instanceof MinMeasureMapping minM) {
            Measure emfM = EmfRolapMappingFactory.eINSTANCE.createMinMeasure();
            return emfM;
        }
        if (m instanceof SumMeasureMapping sumM) {
            Measure emfM = EmfRolapMappingFactory.eINSTANCE.createSumMeasure();
            return emfM;
        }
        // TODO Auto-generated method stub
        return null;
    }

    private List<? extends DatabaseSchema> transformDbschemas(List<? extends DatabaseSchemaMapping> dbschemas) {
        return dbschemas.stream().map(d -> transformDbschemas(d)).toList();
    }

    private DatabaseSchema transformDbschemas(DatabaseSchemaMapping d) {
        DatabaseSchema emfDs = EmfRolapMappingFactory.eINSTANCE.createDatabaseSchema();
        // TODO Auto-generated method stub
        return emfDs;
    }

    private List<? extends Level> transformLevels(List<? extends LevelMapping> levels) {
        return levels.stream().map(l -> transformLevel(l)).toList();
    }

    private Level transformLevel(LevelMapping l) {
        Level emfL = EmfRolapMappingFactory.eINSTANCE.createLevel();
        // TODO Auto-generated method stub
        return emfL;
    }

    private List<? extends Hierarchy> transformHierarchies(List<? extends HierarchyMapping> hierarchies) {
        return hierarchies.stream().map(h -> transformHierarchy(h)).toList();
    }

    private Hierarchy transformHierarchy(HierarchyMapping h) {
        Hierarchy emfH = EmfRolapMappingFactory.eINSTANCE.createHierarchy();
        // TODO Auto-generated method stub
        return emfH;
    }

    private List<? extends Dimension> transformDimensions(List<? extends DimensionMapping> dimensions) {
        return dimensions.stream().map(d -> transformDimension(d)).toList();
    }

    private Dimension transformDimension(DimensionMapping d) {
        if (d instanceof StandardDimension sd) {
            Dimension emfD = EmfRolapMappingFactory.eINSTANCE.createStandardDimension();
            return emfD;
        }
        if (d instanceof TimeDimension td) {
            Dimension emfD = EmfRolapMappingFactory.eINSTANCE.createTimeDimension();
            return emfD;
        }
        // TODO Auto-generated method stub
        return null;
    }

    private List<? extends Cube> transformCubes(List<? extends CubeMapping> cubes) {
        return cubes.stream().map(c -> transformCube(c)).toList();
    }

    private Cube transformCube(CubeMapping c) {
        Cube emfC = null;
        if (c instanceof PhysicalCubeMapping pcm) {
            emfC = EmfRolapMappingFactory.eINSTANCE.createPhysicalCube();
        }
        if (c instanceof VirtualCubeMapping vcm) {
            emfC = EmfRolapMappingFactory.eINSTANCE.createVirtualCube();
        }
        // TODO Auto-generated method stub
        return emfC;
    }

    private List<? extends Schema> transformSchemas(List<? extends SchemaMapping> schemas) {
        return schemas.stream().map(s -> transformSchema(s)).toList();
    }

    private Schema transformSchema(SchemaMapping s) {
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
        return emfS;
    }

    private List<? extends NamedSet> transformNamedSets(List<? extends NamedSetMapping> namedSets) {
        return namedSets.stream().map(ns -> transformNamedSet(ns)).toList();
    }

    private NamedSet transformNamedSet(NamedSetMapping ns) {
        NamedSet emfNs = EmfRolapMappingFactory.eINSTANCE.createNamedSet();
        // TODO Auto-generated method stub
        return emfNs;
    }

    private List<? extends Parameter> transformParameters(List<? extends ParameterMapping> parameters) {
        return parameters.stream().map(p -> transformParameter(p)).toList();
    }

    private Parameter transformParameter(ParameterMapping p) {
        Parameter emfP = EmfRolapMappingFactory.eINSTANCE.createParameter();
        // TODO Auto-generated method stub
        return emfP;
    }

    private List<? extends Catalog> transformCatalogs(List<? extends CatalogMapping> catalogs) {
        return catalogs.stream().map(c -> transformCatalog(c)).toList();
    }

    private Catalog transformCatalog(CatalogMapping c) {
        Catalog emfC = EmfRolapMappingFactory.eINSTANCE.createCatalog();
        emfC.getAnnotations().addAll(transformAnnotations(c.getAnnotations()));
        emfC.setId(c.getId());
        emfC.setDescription(c.getDescription());
        emfC.setName(c.getName());
        emfC.setDocumentation(transformDocumentation(c.getDocumentation()));
        emfC.getSchemas().addAll(transformSchemas(c.getSchemas()));
        return emfC;
    }

    private List<? extends Formatter> transformFormatter(List<? extends FormatterMapping> formatters) {
        return formatters.stream().map(f -> transformFormatter(f)).toList();
    }

    private Formatter transformFormatter(FormatterMapping f) {

        if (f instanceof CellFormatterMapping cfm) {
            CellFormatter emfCf = EmfRolapMappingFactory.eINSTANCE.createCellFormatter();
            emfCf.getAnnotations().addAll(transformAnnotations(cfm.getAnnotations()));
            emfCf.setId(cfm.getId());
            emfCf.setDescription(cfm.getDescription());
            emfCf.setName(cfm.getName());
            emfCf.setDocumentation(transformDocumentation(cfm.getDocumentation()));
            emfCf.setRef(cfm.getRef());
            return emfCf;
        }
        if (f instanceof MemberFormatterMapping mfm) {
            MemberFormatter emfMf = EmfRolapMappingFactory.eINSTANCE.createMemberFormatter();
            emfMf.getAnnotations().addAll(transformAnnotations(mfm.getAnnotations()));
            emfMf.setId(mfm.getId());
            emfMf.setDescription(mfm.getDescription());
            emfMf.setName(mfm.getName());
            emfMf.setDocumentation(transformDocumentation(mfm.getDocumentation()));
            emfMf.setRef(mfm.getRef());
            return emfMf;
        }
        if (f instanceof MemberPropertyFormatterMapping mpfm) {
            MemberPropertyFormatter emfMpf = EmfRolapMappingFactory.eINSTANCE.createMemberPropertyFormatter();
            emfMpf.getAnnotations().addAll(transformAnnotations(mpfm.getAnnotations()));
            emfMpf.setId(mpfm.getId());
            emfMpf.setDescription(mpfm.getDescription());
            emfMpf.setName(mpfm.getName());
            emfMpf.setDocumentation(transformDocumentation(mpfm.getDocumentation()));
            emfMpf.setRef(mpfm.getRef());
            return emfMpf;
        }
        return null;
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
