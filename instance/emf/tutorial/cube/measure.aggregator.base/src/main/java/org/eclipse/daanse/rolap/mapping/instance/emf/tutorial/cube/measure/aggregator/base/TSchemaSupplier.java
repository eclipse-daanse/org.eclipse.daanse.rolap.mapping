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
package org.eclipse.daanse.rolap.mapping.instance.emf.tutorial.cube.measure.aggregator.base;

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
import org.eclipse.daanse.xmla.csdl.model.v2.edm.TEntityKeyElement;
import org.eclipse.daanse.xmla.csdl.model.v2.edm.TEntityProperty;
import org.eclipse.daanse.xmla.csdl.model.v2.edm.TPropertyRef;
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
        container.setName("Daanse Tutorial - Measure Aggregator Base");

        TEntityContainer biContainer = biFactory.createTEntityContainer();
        biContainer.setCaption("MeasuresAggregatorsCube");
        biContainer.setCulture("de-DE");
        container.setBiEntityContainer(biContainer);

        EntitySetType entitySet1 = edmFactory.createEntitySetType();
        entitySet1.setName("Sum_of_Value");
        entitySet1.setEntityType("Model.Sum_of_Value");

        TEntitySet biEntitySet1 = biFactory.createTEntitySet();
        biEntitySet1.setCaption("Sum of Value");
        entitySet1.setBiEntitySet(biEntitySet1);

        EntitySetType entitySet2 = edmFactory.createEntitySetType();
        entitySet2.setName("Max_of_Value");
        entitySet2.setEntityType("Model.Max_of_Value");

        TEntitySet biEntitySet2 = biFactory.createTEntitySet();
        biEntitySet2.setCaption("Max of Value");
        entitySet2.setBiEntitySet(biEntitySet2);

        EntitySetType entitySet3 = edmFactory.createEntitySetType();
        entitySet3.setName("Min_of_Value");
        entitySet3.setEntityType("Model.Min_of_Value");

        TEntitySet biEntitySet3 = biFactory.createTEntitySet();
        biEntitySet3.setCaption("Min of Value");
        entitySet3.setBiEntitySet(biEntitySet3);

        EntitySetType entitySet4 = edmFactory.createEntitySetType();
        entitySet4.setName("Avg_of_Value");
        entitySet4.setEntityType("Model.Avg_of_Value");

        TEntitySet biEntitySet4 = biFactory.createTEntitySet();
        biEntitySet4.setCaption("Avg of Value");
        entitySet4.setBiEntitySet(biEntitySet4);

        container.getEntitySet().add(entitySet1);
        container.getEntitySet().add(entitySet2);
        container.getEntitySet().add(entitySet3);
        container.getEntitySet().add(entitySet4);

        schema.getEntityContainer().add(container);

        org.eclipse.daanse.xmla.csdl.model.v2.edm.TEntityType sumOfValueType = edmFactory.createTEntityType();
        sumOfValueType.setName("Sum_of_Value");

        TEntityType biSumOfValueType = biFactory.createTEntityType();
        biSumOfValueType.setContents("Sum_of_Value");
        sumOfValueType.setBiEntityType(biSumOfValueType);

        TEntityProperty sumOfValueProperty = edmFactory.createTEntityProperty();
        sumOfValueProperty.setName("Fact.VALUE");
        sumOfValueProperty.setType("Int32");
        sumOfValueProperty.setNullable(false);

        TMeasure biSumOfValueMeasure = biFactory.createTMeasure();
        biSumOfValueMeasure.setCaption("Sum of Value");
        biSumOfValueMeasure.setHidden(false);
        biSumOfValueMeasure.setReferenceName("[measures].[Sum of Value]");
        sumOfValueProperty.setBiMeasure(biSumOfValueMeasure);

        sumOfValueType.getProperty().add(sumOfValueProperty);

        org.eclipse.daanse.xmla.csdl.model.v2.edm.TEntityType maxOfValueType = edmFactory.createTEntityType();
        maxOfValueType.setName("Max_of_Value");

        TEntityType biMaxOfValueType = biFactory.createTEntityType();
        biMaxOfValueType.setContents("Max_of_Value");
        maxOfValueType.setBiEntityType(biMaxOfValueType);

        TEntityProperty maxOfValueProperty = edmFactory.createTEntityProperty();
        maxOfValueProperty.setName("Fact.VALUE");
        maxOfValueProperty.setType("Int32");
        maxOfValueProperty.setNullable(false);

        TMeasure biMaxOfValueMeasure = biFactory.createTMeasure();
        biMaxOfValueMeasure.setCaption("Max of Value");
        biMaxOfValueMeasure.setHidden(false);
        biMaxOfValueMeasure.setReferenceName("[measures].[Max of Value]");
        maxOfValueProperty.setBiMeasure(biMaxOfValueMeasure);

        maxOfValueType.getProperty().add(maxOfValueProperty);

        org.eclipse.daanse.xmla.csdl.model.v2.edm.TEntityType minOfValueType = edmFactory.createTEntityType();
        minOfValueType.setName("Min_of_Value");

        TEntityType biMinOfValueType = biFactory.createTEntityType();
        biMinOfValueType.setContents("Min_of_Value");
        minOfValueType.setBiEntityType(biMinOfValueType);

        TEntityProperty minOfValueProperty = edmFactory.createTEntityProperty();
        minOfValueProperty.setName("Fact.VALUE");
        minOfValueProperty.setType("Int32");
        minOfValueProperty.setNullable(false);

        TMeasure biMinOfValueMeasure = biFactory.createTMeasure();
        biMinOfValueMeasure.setCaption("Min of Value");
        biMinOfValueMeasure.setHidden(false);
        biMinOfValueMeasure.setReferenceName("[measures].[Min of Value]");
        minOfValueProperty.setBiMeasure(biMinOfValueMeasure);

        minOfValueType.getProperty().add(minOfValueProperty);

        org.eclipse.daanse.xmla.csdl.model.v2.edm.TEntityType avgOfValueType = edmFactory.createTEntityType();
        avgOfValueType.setName("Avg_of_Value");

        TEntityType biAvgOfValueType = biFactory.createTEntityType();
        biAvgOfValueType.setContents("Avg_of_Value");
        avgOfValueType.setBiEntityType(biMinOfValueType);

        TEntityProperty avgOfValueProperty = edmFactory.createTEntityProperty();
        avgOfValueProperty.setName("Fact.VALUE");
        avgOfValueProperty.setType("Int32");
        avgOfValueProperty.setNullable(false);

        TMeasure biAvgOfValueMeasure = biFactory.createTMeasure();
        biAvgOfValueMeasure.setCaption("Avg of Value");
        biAvgOfValueMeasure.setHidden(false);
        biAvgOfValueMeasure.setReferenceName("[measures].[Avg of Value]");
        avgOfValueProperty.setBiMeasure(biAvgOfValueMeasure);

        avgOfValueType.getProperty().add(avgOfValueProperty);

        schema.getEntityType().add(sumOfValueType);
        schema.getEntityType().add(maxOfValueType);
        schema.getEntityType().add(minOfValueType);
        schema.getEntityType().add(avgOfValueType);

        return schema;
	}

}
