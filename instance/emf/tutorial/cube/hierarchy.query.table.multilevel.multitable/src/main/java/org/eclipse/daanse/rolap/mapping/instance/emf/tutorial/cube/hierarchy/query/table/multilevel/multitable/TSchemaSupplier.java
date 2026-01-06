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
package org.eclipse.daanse.rolap.mapping.instance.emf.tutorial.cube.hierarchy.query.table.multilevel.multitable;

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
        container.setName("Daanse Tutorial - Hierarchy Query Table Multilevel Multitable");

        TEntityContainer biContainer = biFactory.createTEntityContainer();
        biContainer.setCaption("Cube Query linked Tables");
        biContainer.setCulture("de-DE");
        container.setBiEntityContainer(biContainer);

        EntitySetType entitySetTownHierarchy = edmFactory.createEntitySetType();
        entitySetTownHierarchy.setName("TownHierarchy");
        entitySetTownHierarchy.setEntityType("Model.TownHierarchy");

        TEntitySet biEntitySetTownHierarchy  = biFactory.createTEntitySet();
        biEntitySetTownHierarchy.setCaption("TownHierarchy");

        entitySetTownHierarchy.setBiEntitySet(biEntitySetTownHierarchy);

        container.getEntitySet().add(entitySetTownHierarchy);

        EntitySetType entitySet1 = edmFactory.createEntitySetType();
        entitySet1.setName("theMeasure");
        entitySet1.setEntityType("Model.theMeasure");

        TEntitySet biEntitySet1 = biFactory.createTEntitySet();
        biEntitySet1.setCaption("theMeasure");
        entitySet1.setBiEntitySet(biEntitySet1);

        container.getEntitySet().add(entitySet1);

        schema.getEntityContainer().add(container);

        org.eclipse.daanse.xmla.csdl.model.v2.edm.TEntityType townHierarchyType = edmFactory.createTEntityType();
        townHierarchyType.setName("TownHierarchy");

        TLevel tLevelTown = biFactory.createTLevel();
        tLevelTown.setName("Town");
        tLevelTown.setCaption("Town");
        tLevelTown.setReferenceName("[Town].[TownHierarchy].[Town]");

        TLevel tLevelCountry = biFactory.createTLevel();
        tLevelCountry.setName("Country");
        tLevelCountry.setCaption("Country");
        tLevelCountry.setReferenceName("[Town].[TownHierarchy].[Country]");

        THierarchy townHierarchyTHierarchy = biFactory.createTHierarchy();
        townHierarchyTHierarchy.setCaption("TownHierarchy");
        townHierarchyTHierarchy.setName("TownHierarchy");
        townHierarchyTHierarchy.setReferenceName("[Town].[TownHierarchy]");
        townHierarchyTHierarchy.getLevel().add(tLevelTown);

        TEntityType townHierarchyTEntityType = biFactory.createTEntityType();
        townHierarchyTEntityType.setContents("TownHierarchy");
        townHierarchyTEntityType.getHierarchy().add(townHierarchyTHierarchy);

        townHierarchyType.setBiEntityType(townHierarchyTEntityType);

        TEntityProperty factTownIdProperty = edmFactory.createTEntityProperty();
        factTownIdProperty.setName("Fact.TOWN_ID");
        factTownIdProperty.setType("Int32");
        factTownIdProperty.setNullable(false);

        TPropertyRef factTownIdPropertyRef = edmFactory.createTPropertyRef();
        factTownIdPropertyRef.setName("Fact.TOWN_ID");

        TEntityKeyElement key =  edmFactory.createTEntityKeyElement();
        key.getPropertyRef().add(factTownIdPropertyRef);

        TEntityProperty townIdProperty = edmFactory.createTEntityProperty();
        townIdProperty.setName("Town.ID");
        townIdProperty.setType("Int32");
        townIdProperty.setNullable(false);

        TEntityProperty townNameProperty = edmFactory.createTEntityProperty();
        townNameProperty.setName("Town.NAME");
        townNameProperty.setType("String");
        townNameProperty.setNullable(false);

        TEntityProperty townCountryProperty = edmFactory.createTEntityProperty();
        townCountryProperty.setName("Town.COUNTRY");
        townCountryProperty.setType("String");
        townCountryProperty.setNullable(false);

        townHierarchyType.getProperty().add(factTownIdProperty);
        townHierarchyType.getProperty().add(townIdProperty);
        townHierarchyType.getProperty().add(townNameProperty);
        townHierarchyType.getProperty().add(townCountryProperty);

        schema.getEntityType().add(townHierarchyType);

        org.eclipse.daanse.xmla.csdl.model.v2.edm.TEntityType theMeasureType = edmFactory.createTEntityType();
        theMeasureType.setName("theMeasure");

        TEntityType biTheMeasureType = biFactory.createTEntityType();
        biTheMeasureType.setContents("theMeasure");
        theMeasureType.setBiEntityType(biTheMeasureType);

        TEntityProperty theMeasureProperty = edmFactory.createTEntityProperty();
        theMeasureProperty.setName("Fact.VALUE");
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
