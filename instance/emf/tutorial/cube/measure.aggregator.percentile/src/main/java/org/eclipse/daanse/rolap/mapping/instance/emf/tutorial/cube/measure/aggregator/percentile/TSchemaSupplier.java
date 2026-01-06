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
package org.eclipse.daanse.rolap.mapping.instance.emf.tutorial.cube.measure.aggregator.percentile;

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
        container.setName("Daanse Tutorial - Measure Aggregator Percentile");

        TEntityContainer biContainer = biFactory.createTEntityContainer();
        biContainer.setCaption("MeasuresAggregatorsCube");
        biContainer.setCulture("de-DE");
        container.setBiEntityContainer(biContainer);

        EntitySetType entitySetHierarchy = edmFactory.createEntitySetType();
        entitySetHierarchy.setName("Hierarchy");
        entitySetHierarchy.setEntityType("Model.Hierarchy");

        TEntitySet biEntitySetHierarchy  = biFactory.createTEntitySet();
        biEntitySetHierarchy.setCaption("Hierarchy");

        entitySetHierarchy.setBiEntitySet(biEntitySetHierarchy);

        container.getEntitySet().add(entitySetHierarchy);

        EntitySetType entitySet1 = edmFactory.createEntitySetType();
        entitySet1.setName("PercentileDisc025");
        entitySet1.setEntityType("Model.PercentileDisc025");

        TEntitySet biEntitySet1 = biFactory.createTEntitySet();
        biEntitySet1.setCaption("Percentile disc 0.25");
        entitySet1.setBiEntitySet(biEntitySet1);

        EntitySetType entitySet2 = edmFactory.createEntitySetType();
        entitySet2.setName("PercentileCont025");
        entitySet2.setEntityType("Model.PercentileCont025");

        TEntitySet biEntitySet2 = biFactory.createTEntitySet();
        biEntitySet2.setCaption("Percentile cont 0.25");
        entitySet2.setBiEntitySet(biEntitySet2);

        EntitySetType entitySet3 = edmFactory.createEntitySetType();
        entitySet3.setName("PercentileDisc042");
        entitySet3.setEntityType("Model.PercentileDisc042");

        TEntitySet biEntitySet3 = biFactory.createTEntitySet();
        biEntitySet3.setCaption("Percentile disc 0.42");
        entitySet3.setBiEntitySet(biEntitySet3);

        EntitySetType entitySet4 = edmFactory.createEntitySetType();
        entitySet4.setName("PercentileCont042");
        entitySet4.setEntityType("Model.PercentileCont042");

        TEntitySet biEntitySet4 = biFactory.createTEntitySet();
        biEntitySet4.setCaption("Percentile cont 0.42");
        entitySet4.setBiEntitySet(biEntitySet4);

        EntitySetType entitySet5 = edmFactory.createEntitySetType();
        entitySet5.setName("PercentileDisc05");
        entitySet5.setEntityType("Model.PercentileDisc05");

        TEntitySet biEntitySet5 = biFactory.createTEntitySet();
        biEntitySet5.setCaption("Percentile disc 0.5");
        entitySet5.setBiEntitySet(biEntitySet5);

        EntitySetType entitySet6 = edmFactory.createEntitySetType();
        entitySet6.setName("PercentileCont05");
        entitySet6.setEntityType("Model.PercentileCont05");

        TEntitySet biEntitySet6 = biFactory.createTEntitySet();
        biEntitySet6.setCaption("Percentile cont 0.5");
        entitySet6.setBiEntitySet(biEntitySet6);

        EntitySetType entitySet7 = edmFactory.createEntitySetType();
        entitySet7.setName("PercentileDisc075");
        entitySet7.setEntityType("Model.PercentileDisc075");

        TEntitySet biEntitySet7 = biFactory.createTEntitySet();
        biEntitySet7.setCaption("Percentile disc 0.75");
        entitySet7.setBiEntitySet(biEntitySet7);

        EntitySetType entitySet8 = edmFactory.createEntitySetType();
        entitySet8.setName("PercentileCont075");
        entitySet8.setEntityType("Model.PercentileCont075");

        TEntitySet biEntitySet8 = biFactory.createTEntitySet();
        biEntitySet8.setCaption("Percentile cont 0.75");
        entitySet8.setBiEntitySet(biEntitySet8);

        EntitySetType entitySet9 = edmFactory.createEntitySetType();
        entitySet9.setName("PercentileDisc100");
        entitySet9.setEntityType("Model.PercentileDisc100");

        TEntitySet biEntitySet9 = biFactory.createTEntitySet();
        biEntitySet9.setCaption("Percentile disc 1.00");
        entitySet9.setBiEntitySet(biEntitySet9);

        EntitySetType entitySet10 = edmFactory.createEntitySetType();
        entitySet10.setName("PercentileCont100");
        entitySet10.setEntityType("Model.PercentileCont100");

        TEntitySet biEntitySet10 = biFactory.createTEntitySet();
        biEntitySet10.setCaption("Percentile cont 1.00");
        entitySet10.setBiEntitySet(biEntitySet10);

        container.getEntitySet().add(entitySet1);
        container.getEntitySet().add(entitySet2);
        container.getEntitySet().add(entitySet3);
        container.getEntitySet().add(entitySet4);
        container.getEntitySet().add(entitySet5);
        container.getEntitySet().add(entitySet6);
        container.getEntitySet().add(entitySet7);
        container.getEntitySet().add(entitySet8);
        container.getEntitySet().add(entitySet9);
        container.getEntitySet().add(entitySet10);

        schema.getEntityContainer().add(container);

        org.eclipse.daanse.xmla.csdl.model.v2.edm.TEntityType hierarchyType = edmFactory.createTEntityType();
        hierarchyType.setName("Hierarchy");

        TLevel tLevelLevel = biFactory.createTLevel();
        tLevelLevel.setName("Level");
        tLevelLevel.setCaption("Level");
        tLevelLevel.setReferenceName("[Dim].[Hierarchy].[Level]");

        THierarchy hierarchyTHierarchy = biFactory.createTHierarchy();
        hierarchyTHierarchy.setCaption("Hierarchy");
        hierarchyTHierarchy.setName("Hierarchy");
        hierarchyTHierarchy.setReferenceName("[Dim].[Hierarchy]");
        hierarchyTHierarchy.getLevel().add(tLevelLevel);

        TEntityType hierarchyTEntityType = biFactory.createTEntityType();
        hierarchyTEntityType.setContents("Hierarchy");
        hierarchyTEntityType.getHierarchy().add(hierarchyTHierarchy);

        hierarchyType.setBiEntityType(hierarchyTEntityType);

        TEntityProperty factIdProperty = edmFactory.createTEntityProperty();
        factIdProperty.setName("Fact.ID");
        factIdProperty.setType("Int32");
        factIdProperty.setNullable(false);

        TPropertyRef factIdPropertyRef = edmFactory.createTPropertyRef();
        factIdPropertyRef.setName("Fact.ID");

        TEntityKeyElement key =  edmFactory.createTEntityKeyElement();
        key.getPropertyRef().add(factIdPropertyRef);

        TEntityProperty facktLevelProperty = edmFactory.createTEntityProperty();
        facktLevelProperty.setName("Fact.LEVEL");
        facktLevelProperty.setType("String");
        facktLevelProperty.setNullable(false);

        hierarchyType.getProperty().add(factIdProperty);
        hierarchyType.getProperty().add(facktLevelProperty);

        schema.getEntityType().add(hierarchyType);

        createTEntityType(schema, "PercentileDisc025", "Percentile disc 0.25", "Fact.VALUE", "Int32", false);
        createTEntityType(schema, "PercentileCont025", "Percentile cont 0.25", "Fact.VALUE", "Int32", false);
        createTEntityType(schema, "PercentileDisc042", "Percentile disc 0.42", "Fact.VALUE", "Int32", false);
        createTEntityType(schema, "PercentileCont042", "Percentile cont 0.42", "Fact.VALUE", "Int32", false);
        createTEntityType(schema, "PercentileDisc05", "Percentile disc 0.5", "Fact.VALUE", "Int32", false);
        createTEntityType(schema, "PercentileCont05", "Percentile cont 0.5", "Fact.VALUE", "Int32", false);
        createTEntityType(schema, "PercentileDisc075", "Percentile disc 0.75", "Fact.VALUE", "Int32", false);
        createTEntityType(schema, "PercentileCont075", "Percentile cont 0.75", "Fact.VALUE", "Int32", false);
        createTEntityType(schema, "PercentileDisc100", "Percentile disc 1.00", "Fact.VALUE", "Int32", false);
        createTEntityType(schema, "PercentileCont100", "Percentile cont 1.00", "Fact.VALUE", "Int32", false);

        return schema;
	}

	private void createTEntityType(TSchema schema, String name, String caption, String edmEntityPropertyName, String edmEntityPropertyType, boolean nullable) {
        org.eclipse.daanse.xmla.csdl.model.v2.edm.TEntityType edmEntityType = edmFactory.createTEntityType();
        edmEntityType.setName(name);

        TEntityType biEntityType = biFactory.createTEntityType();
        biEntityType.setContents(name);
        edmEntityType.setBiEntityType(biEntityType);

        TEntityProperty edmEntityProperty = edmFactory.createTEntityProperty();
        edmEntityProperty.setName(edmEntityPropertyName);
        edmEntityProperty.setType(edmEntityPropertyType);
        edmEntityProperty.setNullable(nullable);

        TMeasure biMeasure = biFactory.createTMeasure();
        biMeasure.setCaption(caption);
        biMeasure.setHidden(false);
        biMeasure.setReferenceName("[measures].[" + caption + "]");
        edmEntityProperty.setBiMeasure(biMeasure);

        edmEntityType.getProperty().add(edmEntityProperty);
        
        schema.getEntityType().add(edmEntityType);
    }

}
