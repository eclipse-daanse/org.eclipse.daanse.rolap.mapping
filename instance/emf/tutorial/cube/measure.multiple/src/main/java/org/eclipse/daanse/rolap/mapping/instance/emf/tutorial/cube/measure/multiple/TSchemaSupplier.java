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
package org.eclipse.daanse.rolap.mapping.instance.emf.tutorial.cube.measure.multiple;

import org.eclipse.daanse.xmla.csdl.model.provider.OlapTSchemaSupplier;
import org.eclipse.daanse.xmla.csdl.model.v2.bi.BiFactory;
import org.eclipse.daanse.xmla.csdl.model.v2.bi.TEntityContainer;
import org.eclipse.daanse.xmla.csdl.model.v2.bi.TEntitySet;
import org.eclipse.daanse.xmla.csdl.model.v2.bi.TEntityType;
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
        container.setName("Daanse Tutorial - Cube Minimal");

        TEntityContainer biContainer = biFactory.createTEntityContainer();
        biContainer.setCaption("MinimalCube");
        biContainer.setCulture("de-DE");
        container.setBiEntityContainer(biContainer);

        EntitySetType entitySet1 = edmFactory.createEntitySetType();
        entitySet1.setName("Sum of Value1");
        entitySet1.setEntityType("Model.Sum of Value1");

        EntitySetType entitySet2 = edmFactory.createEntitySetType();
        entitySet2.setName("Sum of Value2");
        entitySet2.setEntityType("Model.Sum of Value2");

        EntitySetType entitySet3 = edmFactory.createEntitySetType();
        entitySet3.setName("Sum of Value3");
        entitySet3.setEntityType("Model.Sum of Value3");

        TEntitySet biEntitySet1 = biFactory.createTEntitySet();
        biEntitySet1.setCaption("Sum of Value1");
        entitySet1.setBiEntitySet(biEntitySet1);

        TEntitySet biEntitySet2 = biFactory.createTEntitySet();
        biEntitySet2.setCaption("Sum of Value2");
        entitySet2.setBiEntitySet(biEntitySet2);

        TEntitySet biEntitySet3 = biFactory.createTEntitySet();
        biEntitySet3.setCaption("Sum of Value3");
        entitySet3.setBiEntitySet(biEntitySet3);

        container.getEntitySet().add(entitySet1);
        container.getEntitySet().add(entitySet2);
        container.getEntitySet().add(entitySet3);

        schema.getEntityContainer().add(container);

        org.eclipse.daanse.xmla.csdl.model.v2.edm.TEntityType measureSumValue1Type = edmFactory.createTEntityType();
        measureSumValue1Type.setName("Sum of Value1");

        org.eclipse.daanse.xmla.csdl.model.v2.edm.TEntityType measureSumValue2Type = edmFactory.createTEntityType();
        measureSumValue2Type.setName("Sum of Value2");

        org.eclipse.daanse.xmla.csdl.model.v2.edm.TEntityType measureSumValue3Type = edmFactory.createTEntityType();
        measureSumValue3Type.setName("Sum of Value3");

        TEntityType biMeasureSumValue1Type = biFactory.createTEntityType();
        biMeasureSumValue1Type.setContents("Sum of Value1");
        measureSumValue1Type.setBiEntityType(biMeasureSumValue1Type);

        TEntityType biMeasureSumValue2Type = biFactory.createTEntityType();
        biMeasureSumValue2Type.setContents("Sum of Value2");
        measureSumValue2Type.setBiEntityType(biMeasureSumValue2Type);

        TEntityType biMeasureSumValue3Type = biFactory.createTEntityType();
        biMeasureSumValue3Type.setContents("Sum of Value3");
        measureSumValue3Type.setBiEntityType(biMeasureSumValue3Type);

        TEntityProperty value1Property = edmFactory.createTEntityProperty();
        value1Property.setName("VALUE1");
        value1Property.setType("Int32");
        value1Property.setNullable(false);

        TEntityProperty value2Property = edmFactory.createTEntityProperty();
        value2Property.setName("VALUE2");
        value2Property.setType("Int32");
        value2Property.setNullable(false);

        TEntityProperty value3Property = edmFactory.createTEntityProperty();
        value3Property.setName("VALUE3");
        value3Property.setType("Int32");
        value3Property.setNullable(false);

        TMeasure biValue1Measure = biFactory.createTMeasure();
        biValue1Measure.setCaption("VALUE1");
        biValue1Measure.setHidden(false);
        value1Property.setBiMeasure(biValue1Measure);

        TMeasure biValue2Measure = biFactory.createTMeasure();
        biValue2Measure.setCaption("VALUE2");
        biValue2Measure.setHidden(false);
        value2Property.setBiMeasure(biValue2Measure);

        TMeasure biValue3Measure = biFactory.createTMeasure();
        biValue3Measure.setCaption("VALUE3");
        biValue3Measure.setHidden(false);
        value3Property.setBiMeasure(biValue3Measure);


        measureSumValue1Type.getProperty().add(value1Property);
        measureSumValue2Type.getProperty().add(value2Property);
        measureSumValue3Type.getProperty().add(value3Property);

        schema.getEntityType().add(measureSumValue1Type);
        schema.getEntityType().add(measureSumValue2Type);
        schema.getEntityType().add(measureSumValue3Type);

        return schema;
	}

}
