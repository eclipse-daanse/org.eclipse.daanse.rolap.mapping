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
package org.eclipse.daanse.rolap.mapping.instance.emf.tutorial.cube.minimal;

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

        EntitySetType entitySet = edmFactory.createEntitySetType();
        entitySet.setName("Measure-Sum");
        entitySet.setEntityType("Model.Measure-Sum");

        TEntitySet biEntitySet = biFactory.createTEntitySet();
        biEntitySet.setCaption("Measure-Sum");
        entitySet.setBiEntitySet(biEntitySet);

        container.getEntitySet().add(entitySet);

        schema.getEntityContainer().add(container);

        org.eclipse.daanse.xmla.csdl.model.v2.edm.TEntityType measureSumType = edmFactory.createTEntityType();
        measureSumType.setName("Measure-Sum");

        TEntityType biMeasureSumType = biFactory.createTEntityType();
        biMeasureSumType.setContents("Measure-Sum");
        measureSumType.setBiEntityType(biMeasureSumType);

        TEntityProperty valueProperty = edmFactory.createTEntityProperty();
        valueProperty.setName("VALUE");
        valueProperty.setType("Int32");
        valueProperty.setNullable(false);

        TMeasure biValueMeasure = biFactory.createTMeasure();
        biValueMeasure.setCaption("VALUE");
        biValueMeasure.setHidden(false);
        valueProperty.setBiMeasure(biValueMeasure);

        measureSumType.getProperty().add(valueProperty);

        schema.getEntityType().add(measureSumType);

        return schema;
	}

}
