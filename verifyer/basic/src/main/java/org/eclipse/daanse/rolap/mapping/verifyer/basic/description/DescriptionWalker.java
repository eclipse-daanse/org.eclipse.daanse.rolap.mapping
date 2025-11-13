/*
 * Copyright (c) 2022 Contributors to the Eclipse Foundation.
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
package org.eclipse.daanse.rolap.mapping.verifyer.basic.description;

import static org.eclipse.daanse.rolap.mapping.verifyer.basic.SchemaWalkerMessages.ACTION;
import static org.eclipse.daanse.rolap.mapping.verifyer.basic.SchemaWalkerMessages.ACTION_MUST_CONTAIN_DESCRIPTION;
import static org.eclipse.daanse.rolap.mapping.verifyer.basic.SchemaWalkerMessages.CALCULATED_MEMBER;
import static org.eclipse.daanse.rolap.mapping.verifyer.basic.SchemaWalkerMessages.CALCULATED_MEMBER_MUST_CONTAIN_DESCRIPTION;
import static org.eclipse.daanse.rolap.mapping.verifyer.basic.SchemaWalkerMessages.CALCULATED_MEMBER_PROPERTY;
import static org.eclipse.daanse.rolap.mapping.verifyer.basic.SchemaWalkerMessages.CALCULATED_MEMBER_PROPERTY_MUST_CONTAIN_DESCRIPTION;
import static org.eclipse.daanse.rolap.mapping.verifyer.basic.SchemaWalkerMessages.CUBE;
import static org.eclipse.daanse.rolap.mapping.verifyer.basic.SchemaWalkerMessages.CUBE_MUST_CONTAIN_DESCRIPTION;
import static org.eclipse.daanse.rolap.mapping.verifyer.basic.SchemaWalkerMessages.DIMENSIONS;
import static org.eclipse.daanse.rolap.mapping.verifyer.basic.SchemaWalkerMessages.DIMENSION_MUST_CONTAIN_DESCRIPTION;
import static org.eclipse.daanse.rolap.mapping.verifyer.basic.SchemaWalkerMessages.DRILL_THROUGH_ACTION;
import static org.eclipse.daanse.rolap.mapping.verifyer.basic.SchemaWalkerMessages.DRILL_THROUGH_ACTION_MUST_CONTAIN_DESCRIPTION;
import static org.eclipse.daanse.rolap.mapping.verifyer.basic.SchemaWalkerMessages.HIERARCHY;
import static org.eclipse.daanse.rolap.mapping.verifyer.basic.SchemaWalkerMessages.HIERARCHY_MUST_CONTAIN_DESCRIPTION;
import static org.eclipse.daanse.rolap.mapping.verifyer.basic.SchemaWalkerMessages.KPI;
import static org.eclipse.daanse.rolap.mapping.verifyer.basic.SchemaWalkerMessages.KPI_MUST_CONTAIN_DESCRIPTION;
import static org.eclipse.daanse.rolap.mapping.verifyer.basic.SchemaWalkerMessages.LEVEL;
import static org.eclipse.daanse.rolap.mapping.verifyer.basic.SchemaWalkerMessages.LEVEL_MUST_CONTAIN_DESCRIPTION;
import static org.eclipse.daanse.rolap.mapping.verifyer.basic.SchemaWalkerMessages.MEASURE;
import static org.eclipse.daanse.rolap.mapping.verifyer.basic.SchemaWalkerMessages.MEASURE_MUST_CONTAIN_DESCRIPTION;
import static org.eclipse.daanse.rolap.mapping.verifyer.basic.SchemaWalkerMessages.NAMED_SET;
import static org.eclipse.daanse.rolap.mapping.verifyer.basic.SchemaWalkerMessages.NAMED_SET_MUST_CONTAIN_DESCRIPTION;
import static org.eclipse.daanse.rolap.mapping.verifyer.basic.SchemaWalkerMessages.PARAMETER;
import static org.eclipse.daanse.rolap.mapping.verifyer.basic.SchemaWalkerMessages.PARAMETER_MUST_CONTAIN_DESCRIPTION;
import static org.eclipse.daanse.rolap.mapping.verifyer.basic.SchemaWalkerMessages.PROPERTY;
import static org.eclipse.daanse.rolap.mapping.verifyer.basic.SchemaWalkerMessages.PROPERTY_MUST_CONTAIN_DESCRIPTION;
import static org.eclipse.daanse.rolap.mapping.verifyer.basic.SchemaWalkerMessages.SCHEMA;
import static org.eclipse.daanse.rolap.mapping.verifyer.basic.SchemaWalkerMessages.SCHEMA_MUST_CONTAIN_DESCRIPTION;
import static org.eclipse.daanse.rolap.mapping.verifyer.basic.SchemaWalkerMessages.VIRTUAL_CUBE;
import static org.eclipse.daanse.rolap.mapping.verifyer.basic.SchemaWalkerMessages.VIRTUAL_CUBE_MUST_CONTAIN_DESCRIPTION;

import java.util.List;

import org.eclipse.daanse.rolap.mapping.model.Action;
import org.eclipse.daanse.rolap.mapping.model.BaseMeasure;
import org.eclipse.daanse.rolap.mapping.model.CalculatedMember;
import org.eclipse.daanse.rolap.mapping.model.CalculatedMemberProperty;
import org.eclipse.daanse.rolap.mapping.model.Catalog;
import org.eclipse.daanse.rolap.mapping.model.Cube;
import org.eclipse.daanse.rolap.mapping.model.Dimension;
import org.eclipse.daanse.rolap.mapping.model.DrillThroughAction;
import org.eclipse.daanse.rolap.mapping.model.Hierarchy;
import org.eclipse.daanse.rolap.mapping.model.Kpi;
import org.eclipse.daanse.rolap.mapping.model.MemberProperty;
import org.eclipse.daanse.rolap.mapping.model.NamedSet;
import org.eclipse.daanse.rolap.mapping.model.Parameter;
import org.eclipse.daanse.rolap.mapping.model.PhysicalCube;
import org.eclipse.daanse.rolap.mapping.model.VirtualCube;
import org.eclipse.daanse.rolap.mapping.verifyer.api.Cause;
import org.eclipse.daanse.rolap.mapping.verifyer.api.Level;
import org.eclipse.daanse.rolap.mapping.verifyer.api.VerificationResult;
import org.eclipse.daanse.rolap.mapping.verifyer.basic.AbstractSchemaWalker;
import org.eclipse.daanse.rolap.mapping.verifyer.basic.VerificationResultR;

public class DescriptionWalker extends AbstractSchemaWalker {

    private DescriptionVerifierConfig config;

    public DescriptionWalker(DescriptionVerifierConfig config) {
        this.config = config;
    }

    @Override
    public List<VerificationResult> checkSchema(Catalog schema) {
        super.checkSchema(schema);
        Level lavel = config.schema();
        if (lavel != null && (schema.getDescription() == null || schema.getDescription()
                .isEmpty())) {
            results.add(new VerificationResultR(SCHEMA, SCHEMA_MUST_CONTAIN_DESCRIPTION, lavel, Cause.SCHEMA));
        }

        return results;
    }

    @Override
    protected void checkDimension(Dimension dimension, Cube cube, Catalog schema) {
        super.checkDimension(dimension, cube, schema);
        Level lavel = config.dimension();
        if (lavel != null && (dimension.getDescription() == null || dimension.getDescription()
                .isEmpty())) {
            results.add(
                    new VerificationResultR(DIMENSIONS, DIMENSION_MUST_CONTAIN_DESCRIPTION, lavel, Cause.SCHEMA));
        }
    }

    @Override
    protected void checkVirtualCube(VirtualCube virtualCube, Catalog schema) {
        super.checkVirtualCube(virtualCube, schema);
        Level lavel = config.virtualCube();
        if (lavel != null && (virtualCube.getDescription() == null || virtualCube.getDescription()
                .isEmpty())) {
            results.add(new VerificationResultR(VIRTUAL_CUBE, VIRTUAL_CUBE_MUST_CONTAIN_DESCRIPTION, lavel,
                    Cause.SCHEMA));
        }
    }

    @Override
    protected void checkPhysicalCube(PhysicalCube cube, Catalog schema) {
        super.checkPhysicalCube(cube, schema);
        Level lavel = config.cube();
        if (lavel != null && (cube.getDescription() == null || cube.getDescription()
                .isEmpty())) {
            results.add(new VerificationResultR(CUBE, CUBE_MUST_CONTAIN_DESCRIPTION, lavel, Cause.SCHEMA));
        }
    }

    @Override
    protected void checkMeasure(BaseMeasure measure, Cube cube) {
        super.checkMeasure(measure, cube);
        Level lavel = config.measure();
        if (lavel != null && (measure.getDescription() == null || measure.getDescription()
                .isEmpty())) {
            results.add(new VerificationResultR(MEASURE, MEASURE_MUST_CONTAIN_DESCRIPTION, lavel, Cause.SCHEMA));
        }
    }

    @Override
    protected void checkKpi(Kpi kpi, Cube cube) {
        super.checkKpi(kpi, cube);
        Level lavel = config.kpi();
        if (lavel != null && (kpi.getDescription() == null || kpi.getDescription()
            .isEmpty())) {
            results.add(new VerificationResultR(KPI, KPI_MUST_CONTAIN_DESCRIPTION, lavel, Cause.SCHEMA));
        }
    }

    @Override
    protected void checkCalculatedMemberProperty(CalculatedMemberProperty calculatedMemberProperty) {
        super.checkCalculatedMemberProperty(calculatedMemberProperty);
        Level lavel = config.calculatedMemberProperty();
        if (lavel != null && (calculatedMemberProperty.getDescription() == null || calculatedMemberProperty.getDescription()
                .isEmpty())) {
            results.add(new VerificationResultR(CALCULATED_MEMBER_PROPERTY,
                CALCULATED_MEMBER_PROPERTY_MUST_CONTAIN_DESCRIPTION, lavel, Cause.SCHEMA));
        }
    }

    @Override
    protected void checkCalculatedMember(CalculatedMember calculatedMember) {
        super.checkCalculatedMember(calculatedMember);
        Level lavel = config.calculatedMember();
        if (lavel != null && (calculatedMember.getDescription() == null || calculatedMember.getDescription()
                .isEmpty())) {
            results.add(new VerificationResultR(CALCULATED_MEMBER, CALCULATED_MEMBER_MUST_CONTAIN_DESCRIPTION, lavel,
                    Cause.SCHEMA));
        }
    }

    @Override
    protected void checkHierarchy(Hierarchy hierarchy, Dimension cubeDimension, Cube cube) {
        super.checkHierarchy(hierarchy, cubeDimension, cube);
        Level lavel = config.hierarchy();
        if (lavel != null && (hierarchy.getDescription() == null || hierarchy.getDescription()
                .isEmpty())) {
            results.add(
                    new VerificationResultR(HIERARCHY, HIERARCHY_MUST_CONTAIN_DESCRIPTION, lavel, Cause.SCHEMA));
        }
    }

    @Override
    protected void checkLevel(final org.eclipse.daanse.rolap.mapping.model.Level l, Hierarchy hierarchy, Dimension parentDimension, Cube cube) {
        super.checkLevel(l, hierarchy, parentDimension, cube);
        Level lavel = config.level();
        if (lavel != null && (l.getDescription() == null || l.getDescription()
                .isEmpty())) {
            results.add(new VerificationResultR(LEVEL, LEVEL_MUST_CONTAIN_DESCRIPTION, lavel, Cause.SCHEMA));
        }
    }

    @Override
    protected void checkAction(final Action action) {
        super.checkAction(action);
        Level lavel = config.action();
        if (lavel != null && (action.getDescription() == null || action.getDescription()
                .isEmpty())) {
            if (action instanceof DrillThroughAction) {
                results.add(new VerificationResultR(DRILL_THROUGH_ACTION, DRILL_THROUGH_ACTION_MUST_CONTAIN_DESCRIPTION,
                        lavel, Cause.SCHEMA));
            } else {
                results.add(new VerificationResultR(ACTION, ACTION_MUST_CONTAIN_DESCRIPTION, lavel, Cause.SCHEMA));
            }
        }
    }


    @Override
    protected void checkMemberProperty(final MemberProperty property, org.eclipse.daanse.rolap.mapping.model.Level level, Hierarchy hierarchy, Cube cube) {
        super.checkMemberProperty(property, level, hierarchy, cube);
        Level lavel = config.property();
        if (lavel != null && (property.getDescription() == null || property.getDescription()
                .isEmpty())) {
            results.add(new VerificationResultR(PROPERTY, PROPERTY_MUST_CONTAIN_DESCRIPTION, lavel, Cause.SCHEMA));
        }
    }

    @Override
    protected void checkNamedSet(final NamedSet namedSet) {
        super.checkNamedSet(namedSet);
        Level lavel = config.namedSet();
        if (lavel != null && (namedSet.getDescription() == null || namedSet.getDescription()
                .isEmpty())) {
            results.add(new VerificationResultR(NAMED_SET, NAMED_SET_MUST_CONTAIN_DESCRIPTION, lavel, Cause.SCHEMA));
        }
    }

    @Override
    protected void checkParameter(final Parameter parameter) {
        super.checkParameter(parameter);
        Level lavel = config.parameter();
        if (lavel != null && (parameter.getDescription() == null || parameter.getDescription()
                .isEmpty())) {
            results.add(
                    new VerificationResultR(PARAMETER, PARAMETER_MUST_CONTAIN_DESCRIPTION, lavel, Cause.SCHEMA));
        }
    }
}
