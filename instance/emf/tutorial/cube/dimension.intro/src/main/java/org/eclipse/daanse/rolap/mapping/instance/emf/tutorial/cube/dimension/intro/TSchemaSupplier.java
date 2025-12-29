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
package org.eclipse.daanse.rolap.mapping.instance.emf.tutorial.cube.dimension.intro;

import org.eclipse.daanse.olap.csdl.api.OlapTSchemaSupplier;
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
        container.setName("Daanse Tutorial - Dimension Intro");

        TEntityContainer biContainer = biFactory.createTEntityContainer();
        biContainer.setCaption("CubeWithSimpleDimension");
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
        entitySet1.setName("theMeasure");
        entitySet1.setEntityType("Model.theMeasure");

        TEntitySet biEntitySet1 = biFactory.createTEntitySet();
        biEntitySet1.setCaption("theMeasure");
        entitySet1.setBiEntitySet(biEntitySet1);

        container.getEntitySet().add(entitySet1);

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

        org.eclipse.daanse.xmla.csdl.model.v2.edm.TEntityType theMeasureType = edmFactory.createTEntityType();
        theMeasureType.setName("theMeasure");

        TEntityType biTheMeasureType = biFactory.createTEntityType();
        biTheMeasureType.setContents("theMeasure");
        theMeasureType.setBiEntityType(biTheMeasureType);

        TEntityProperty theMeasureProperty = edmFactory.createTEntityProperty();
        theMeasureProperty.setName("theMeasure");
        theMeasureProperty.setType("Int32");
        theMeasureProperty.setNullable(false);

        TMeasure biTheMeasureMeasure = biFactory.createTMeasure();
        biTheMeasureMeasure.setCaption("theMeasure");
        biTheMeasureMeasure.setHidden(false);
        biTheMeasureMeasure.setReferenceName("[measures].[theMeasure]");
        theMeasureProperty.setBiMeasure(biTheMeasureMeasure);

        theMeasureType.getProperty().add(theMeasureProperty);

        schema.getEntityType().add(theMeasureType);

        return schema;
	}

}
