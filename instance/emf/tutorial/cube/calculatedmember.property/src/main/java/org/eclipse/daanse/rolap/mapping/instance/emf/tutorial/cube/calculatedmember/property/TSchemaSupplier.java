/*
 * Copyright (c) 2025 Contributors to the Eclipse Foundation.
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
package org.eclipse.daanse.rolap.mapping.instance.emf.tutorial.cube.calculatedmember.property;

import org.eclipse.daanse.xmla.csdl.model.provider.OlapTSchemaSupplier;
import org.eclipse.daanse.xmla.csdl.model.v2.bi.BiFactory;
import org.eclipse.daanse.xmla.csdl.model.v2.bi.TEntityContainer;
import org.eclipse.daanse.xmla.csdl.model.v2.bi.TEntitySet;
import org.eclipse.daanse.xmla.csdl.model.v2.bi.TEntityType;
import org.eclipse.daanse.xmla.csdl.model.v2.bi.THierarchy;
import org.eclipse.daanse.xmla.csdl.model.v2.bi.TLevel;
import org.eclipse.daanse.xmla.csdl.model.v2.bi.TMeasure;
import org.eclipse.daanse.xmla.csdl.model.v2.edm.EdmFactory;
import org.eclipse.daanse.xmla.csdl.model.v2.edm.EntityContainerType;
import org.eclipse.daanse.xmla.csdl.model.v2.edm.EntitySetType;
import org.eclipse.daanse.xmla.csdl.model.v2.edm.TEntityProperty;
import org.eclipse.daanse.xmla.csdl.model.v2.edm.TSchema;
import org.osgi.service.component.annotations.Component;

@Component(service = OlapTSchemaSupplier.class)
public class TSchemaSupplier implements OlapTSchemaSupplier {
	private static EdmFactory edmFactory = EdmFactory.eINSTANCE;
    private static BiFactory biFactory = BiFactory.eINSTANCE;

    @Override
    public TSchema get() {
        TSchema schema = edmFactory.createTSchema();
        schema.setNamespace("Model");
        schema.setAlias("Model");

        EntityContainerType container = edmFactory.createEntityContainerType();
        container.setName("Daanse Tutorial - Cube Calculated Member Property");

        TEntityContainer biContainer = biFactory.createTEntityContainer();
        biContainer.setCaption("Cube CalculatedMember with properties");
        biContainer.setCulture("de-DE");
        container.setBiEntityContainer(biContainer);

        EntitySetType entitySetTheHierarchy = edmFactory.createEntitySetType();
        entitySetTheHierarchy.setName("theHierarchy");
        entitySetTheHierarchy.setEntityType("Model.theHierarchy");

        TEntitySet biEntitySetTheHierarchy  = biFactory.createTEntitySet();
        biEntitySetTheHierarchy.setCaption("theHierarchy");

        entitySetTheHierarchy.setBiEntitySet(biEntitySetTheHierarchy);

        container.getEntitySet().add(entitySetTheHierarchy);

        EntitySetType entitySet1 = edmFactory.createEntitySetType();
        entitySet1.setName("Measure1-Sum");
        entitySet1.setEntityType("Model.Measure1-Sum");

        TEntitySet biEntitySet1 = biFactory.createTEntitySet();
        biEntitySet1.setCaption("Measure1-Sum");
        entitySet1.setBiEntitySet(biEntitySet1);

        container.getEntitySet().add(entitySet1);

        EntitySetType entitySet2 = edmFactory.createEntitySetType();
        entitySet2.setName("Measure2-Count");
        entitySet2.setEntityType("Model.Measure2-Count");

        TEntitySet biEntitySet2 = biFactory.createTEntitySet();
        biEntitySet2.setCaption("Measure2-Count");
        entitySet2.setBiEntitySet(biEntitySet2);

        container.getEntitySet().add(entitySet2);

        EntitySetType entitySet3 = edmFactory.createEntitySetType();
        entitySet3.setName("Calculated_Member1");
        entitySet3.setEntityType("Model.Calculated_Member1");

        TEntitySet biEntitySet3 = biFactory.createTEntitySet();
        biEntitySet3.setCaption("Calculated Member 1");
        entitySet3.setBiEntitySet(biEntitySet3);

        container.getEntitySet().add(entitySet3);

        EntitySetType entitySet4 = edmFactory.createEntitySetType();
        entitySet4.setName("Calculated_Member2");
        entitySet4.setEntityType("Model.Calculated_Member2");

        TEntitySet biEntitySet4 = biFactory.createTEntitySet();
        biEntitySet4.setCaption("Calculated Member 2");
        entitySet4.setBiEntitySet(biEntitySet4);

        container.getEntitySet().add(entitySet4);

        schema.getEntityContainer().add(container);

        org.eclipse.daanse.xmla.csdl.model.v2.edm.TEntityType theHierarchyType = edmFactory.createTEntityType();
        theHierarchyType.setName("theHierarchy");

        TLevel tLevel = biFactory.createTLevel();
        tLevel.setName("theLevel");
        tLevel.setCaption("theLevel");
        tLevel.setReferenceName("[theDimension].[theHierarchy].[theLevel]");

        THierarchy theHierarchyTHierarchy = biFactory.createTHierarchy();
        theHierarchyTHierarchy.setCaption("theHierarchy");
        theHierarchyTHierarchy.setName("theHierarchy");
        theHierarchyTHierarchy.setReferenceName("[theDimension].[theHierarchy]");
        theHierarchyTHierarchy.getLevel().add(tLevel);

        TEntityType theHierarchyTEntityType = biFactory.createTEntityType();
        theHierarchyTEntityType.setContents("theHierarchy");
        theHierarchyTEntityType.getHierarchy().add(theHierarchyTHierarchy);

        theHierarchyType.setBiEntityType(theHierarchyTEntityType);

        TEntityProperty theHierarchyProperty = edmFactory.createTEntityProperty();
        theHierarchyProperty.setName("theHierarchy");
        theHierarchyProperty.setType("String");
        theHierarchyProperty.setNullable(false);

        theHierarchyType.getProperty().add(theHierarchyProperty);

        schema.getEntityType().add(theHierarchyType);

        org.eclipse.daanse.xmla.csdl.model.v2.edm.TEntityType measureSumType = edmFactory.createTEntityType();
        measureSumType.setName("Measure1-Sum");

        TEntityType biMeasureSumType = biFactory.createTEntityType();
        biMeasureSumType.setContents("Measure1-Sum");
        measureSumType.setBiEntityType(biMeasureSumType);

        TEntityProperty measureSumProperty = edmFactory.createTEntityProperty();
        measureSumProperty.setName("Measure1-Sum");
        measureSumProperty.setType("Int32");
        measureSumProperty.setNullable(false);

        TMeasure biMeasureSumMeasure = biFactory.createTMeasure();
        biMeasureSumMeasure.setCaption("Measure1-Sum");
        biMeasureSumMeasure.setHidden(false);
        biMeasureSumMeasure.setReferenceName("[measures].[Measure1-Sum]");
        measureSumProperty.setBiMeasure(biMeasureSumMeasure);

        measureSumType.getProperty().add(measureSumProperty);

        schema.getEntityType().add(measureSumType);

        org.eclipse.daanse.xmla.csdl.model.v2.edm.TEntityType measureCountType = edmFactory.createTEntityType();
        measureCountType.setName("Measure2-Count");

        TEntityType biMeasureCountType = biFactory.createTEntityType();
        biMeasureCountType.setContents("Measure2-Count");
        measureCountType.setBiEntityType(biMeasureSumType);

        TEntityProperty measureCountProperty = edmFactory.createTEntityProperty();
        measureCountProperty.setName("Measure2-Count");
        measureCountProperty.setType("Int32");
        measureCountProperty.setNullable(false);

        TMeasure biMeasureCountMeasure = biFactory.createTMeasure();
        biMeasureCountMeasure.setCaption("Measure2-Count");
        biMeasureCountMeasure.setHidden(false);
        biMeasureCountMeasure.setReferenceName("[measures].[Measure2-Count]");

        measureCountProperty.setBiMeasure(biMeasureCountMeasure);

        measureCountType.getProperty().add(measureCountProperty);

        schema.getEntityType().add(measureCountType);

        org.eclipse.daanse.xmla.csdl.model.v2.edm.TEntityType calculatedMember1Type = edmFactory.createTEntityType();
        calculatedMember1Type.setName("Calculated_Member1");

        TEntityType biCalculatedMember1Type = biFactory.createTEntityType();
        biCalculatedMember1Type.setContents("Calculated_Member1");
        calculatedMember1Type.setBiEntityType(biCalculatedMember1Type);

        TEntityProperty calculatedMember1Property = edmFactory.createTEntityProperty();
        calculatedMember1Property.setName("Calculated_Member1");
        calculatedMember1Property.setType("Int32");
        calculatedMember1Property.setNullable(false);

        TMeasure biMeasureCalculatedMember1 = biFactory.createTMeasure();
        biMeasureCalculatedMember1.setCaption("Calculated Member 1");
        biMeasureCalculatedMember1.setHidden(false);
        biMeasureCalculatedMember1.setReferenceName("[measures].[Calculated Member 1]");

        calculatedMember1Property.setBiMeasure(biMeasureCalculatedMember1);

        calculatedMember1Type.getProperty().add(calculatedMember1Property);

        schema.getEntityType().add(calculatedMember1Type);

        org.eclipse.daanse.xmla.csdl.model.v2.edm.TEntityType calculatedMember2Type = edmFactory.createTEntityType();
        calculatedMember2Type.setName("Calculated_Member2");

        TEntityType biCalculatedMember2Type = biFactory.createTEntityType();
        biCalculatedMember2Type.setContents("Calculated_Member2");
        calculatedMember1Type.setBiEntityType(biCalculatedMember1Type);

        TEntityProperty calculatedMember2Property = edmFactory.createTEntityProperty();
        calculatedMember2Property.setName("Calculated_Member2");
        calculatedMember2Property.setType("Int32");
        calculatedMember2Property.setNullable(false);

        TMeasure biMeasureCalculatedMember2 = biFactory.createTMeasure();
        biMeasureCalculatedMember2.setCaption("Calculated Member 2");
        biMeasureCalculatedMember2.setHidden(false);
        biMeasureCalculatedMember2.setReferenceName("[measures].[Calculated Member 2]");

        calculatedMember2Property.setBiMeasure(biMeasureCalculatedMember2);

        calculatedMember2Type.getProperty().add(calculatedMember2Property);

        schema.getEntityType().add(calculatedMember2Type);

        return schema;
	}

}
