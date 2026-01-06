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
package org.eclipse.daanse.rolap.mapping.instance.emf.tutorial.cube.hierarchy.hasall;

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
        container.setName("Daanse Tutorial - Hierarchy Has All");

        TEntityContainer biContainer = biFactory.createTEntityContainer();
        biContainer.setCaption("HasAll Cube");
        biContainer.setCulture("de-DE");
        container.setBiEntityContainer(biContainer);

        EntitySetType entitySetHierarchyWithHasAll = edmFactory.createEntitySetType();
        entitySetHierarchyWithHasAll.setName("HierarchyWithHasAll");
        entitySetHierarchyWithHasAll.setEntityType("Model.HierarchyWithHasAll");

        TEntitySet biEntitySetHierarchyWithHasAll  = biFactory.createTEntitySet();
        biEntitySetHierarchyWithHasAll.setCaption("HierarchyWithHasAll");

        entitySetHierarchyWithHasAll.setBiEntitySet(biEntitySetHierarchyWithHasAll);

        container.getEntitySet().add(entitySetHierarchyWithHasAll);

        EntitySetType entitySetHierarchyWithHasAllAndNames = edmFactory.createEntitySetType();
        entitySetHierarchyWithHasAllAndNames.setName("HierarchyWithHasAllAndNames");
        entitySetHierarchyWithHasAllAndNames.setEntityType("Model.HierarchyWithHasAllAndNames");

        TEntitySet biEntitySetHierarchyWithHasAllAndNames  = biFactory.createTEntitySet();
        biEntitySetHierarchyWithHasAllAndNames.setCaption("HierarchyWithHasAllAndNames");

        entitySetHierarchyWithHasAllAndNames.setBiEntitySet(biEntitySetHierarchyWithHasAllAndNames);

        container.getEntitySet().add(entitySetHierarchyWithHasAllAndNames);

        EntitySetType entitySetHierarchyWithoutHasAll = edmFactory.createEntitySetType();
        entitySetHierarchyWithoutHasAll.setName("HierarchyWithoutHasAll");
        entitySetHierarchyWithoutHasAll.setEntityType("Model.HierarchyWithoutHasAll");

        TEntitySet biEntitySetHierarchyWithoutHasAll  = biFactory.createTEntitySet();
        biEntitySetHierarchyWithoutHasAll.setCaption("HierarchyWithoutHasAll");

        entitySetHierarchyWithoutHasAll.setBiEntitySet(biEntitySetHierarchyWithoutHasAll);

        container.getEntitySet().add(entitySetHierarchyWithoutHasAll);

        EntitySetType entitySet1 = edmFactory.createEntitySetType();
        entitySet1.setName("theMeasure");
        entitySet1.setEntityType("Model.theMeasure");

        TEntitySet biEntitySet1 = biFactory.createTEntitySet();
        biEntitySet1.setCaption("theMeasure");
        entitySet1.setBiEntitySet(biEntitySet1);

        container.getEntitySet().add(entitySet1);

        schema.getEntityContainer().add(container);

        org.eclipse.daanse.xmla.csdl.model.v2.edm.TEntityType hierarchyWithHasAllType = edmFactory.createTEntityType();
        hierarchyWithHasAllType.setName("HierarchyWithHasAll");

        TLevel tLevel = biFactory.createTLevel();
        tLevel.setName("theLevel");
        tLevel.setCaption("theLevel");
        tLevel.setReferenceName("[Dimension1].[Hierarchy - with HasAll].[theLevel]");

        THierarchy hierarchyWithHasAllTHierarchy = biFactory.createTHierarchy();
        hierarchyWithHasAllTHierarchy.setCaption("Hierarchy - with HasAll");
        hierarchyWithHasAllTHierarchy.setName("HierarchyWithHasAll");
        hierarchyWithHasAllTHierarchy.setReferenceName("[Dimension1].[Hierarchy - with HasAll]");
        hierarchyWithHasAllTHierarchy.getLevel().add(tLevel);

        TEntityType hierarchyWithHasAllTEntityType = biFactory.createTEntityType();
        hierarchyWithHasAllTEntityType.setContents("HierarchyWithHasAll");
        hierarchyWithHasAllTEntityType.getHierarchy().add(hierarchyWithHasAllTHierarchy);

        hierarchyWithHasAllType.setBiEntityType(hierarchyWithHasAllTEntityType);

        TEntityProperty hierarchyWithHasAllProperty = edmFactory.createTEntityProperty();
        hierarchyWithHasAllProperty.setName("HierarchyWithHasAll");
        hierarchyWithHasAllProperty.setType("String");
        hierarchyWithHasAllProperty.setNullable(false);

        hierarchyWithHasAllType.getProperty().add(hierarchyWithHasAllProperty);

        schema.getEntityType().add(hierarchyWithHasAllType);

        org.eclipse.daanse.xmla.csdl.model.v2.edm.TEntityType hierarchyWithHasAllAndNamesType = edmFactory.createTEntityType();
        hierarchyWithHasAllAndNamesType.setName("HierarchyWithHasAll");

        TLevel tLevel1 = biFactory.createTLevel();
        tLevel1.setName("theLevelAndName");
        tLevel1.setCaption("theLevelAndName");
        tLevel1.setReferenceName("[Dimension1].[Hierarchy - with HasAll and Names].[theLevel]");

        THierarchy hierarchyWithHasAllAndNamesTHierarchy = biFactory.createTHierarchy();
        hierarchyWithHasAllAndNamesTHierarchy.setCaption("Hierarchy - with HasAll and Names");
        hierarchyWithHasAllAndNamesTHierarchy.setName("HierarchyWithHasAllAndNames");
        hierarchyWithHasAllAndNamesTHierarchy.setReferenceName("[Dimension1].[Hierarchy - with HasAll and Names]");
        hierarchyWithHasAllAndNamesTHierarchy.getLevel().add(tLevel1);

        TEntityType hierarchyWithHasAllAndNamesTEntityType = biFactory.createTEntityType();
        hierarchyWithHasAllAndNamesTEntityType.setContents("HierarchyWithHasAllAndNames");
        hierarchyWithHasAllAndNamesTEntityType.getHierarchy().add(hierarchyWithHasAllAndNamesTHierarchy);

        hierarchyWithHasAllAndNamesType.setBiEntityType(hierarchyWithHasAllAndNamesTEntityType);

        TEntityProperty hierarchyWithHasAllAndNamesProperty = edmFactory.createTEntityProperty();
        hierarchyWithHasAllAndNamesProperty.setName("HierarchyWithHasAllAndNames");
        hierarchyWithHasAllAndNamesProperty.setType("String");
        hierarchyWithHasAllAndNamesProperty.setNullable(false);

        hierarchyWithHasAllAndNamesType.getProperty().add(hierarchyWithHasAllAndNamesProperty);

        schema.getEntityType().add(hierarchyWithHasAllAndNamesType);

        org.eclipse.daanse.xmla.csdl.model.v2.edm.TEntityType hierarchyWithoutHasAllType = edmFactory.createTEntityType();
        hierarchyWithHasAllType.setName("HierarchyWithoutHasAll");

        TLevel tLevel2 = biFactory.createTLevel();
        tLevel2.setName("theLevelWithoutHasAll");
        tLevel2.setCaption("theLevel");
        tLevel2.setReferenceName("[Dimension1].[Hierarchy - Without HasAll].[theLevel]");

        THierarchy hierarchyWithoutHasAllTHierarchy = biFactory.createTHierarchy();
        hierarchyWithoutHasAllTHierarchy.setCaption("Hierarchy - Without HasAll");
        hierarchyWithoutHasAllTHierarchy.setName("HierarchyWithoutHasAll");
        hierarchyWithoutHasAllTHierarchy.setReferenceName("[Dimension1].[Hierarchy - Without HasAll]");
        hierarchyWithoutHasAllTHierarchy.getLevel().add(tLevel);

        TEntityType hierarchyWithoutHasAllTEntityType = biFactory.createTEntityType();
        hierarchyWithoutHasAllTEntityType.setContents("HierarchyWithHasAll");
        hierarchyWithoutHasAllTEntityType.getHierarchy().add(hierarchyWithoutHasAllTHierarchy);

        hierarchyWithoutHasAllType.setBiEntityType(hierarchyWithoutHasAllTEntityType);

        TEntityProperty hierarchyWithoutHasAllProperty = edmFactory.createTEntityProperty();
        hierarchyWithoutHasAllProperty.setName("HierarchyWithoutHasAll");
        hierarchyWithoutHasAllProperty.setType("String");
        hierarchyWithoutHasAllProperty.setNullable(false);

        hierarchyWithoutHasAllType.getProperty().add(hierarchyWithHasAllProperty);

        schema.getEntityType().add(hierarchyWithoutHasAllType);

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
