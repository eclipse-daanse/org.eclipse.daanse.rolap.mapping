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
package org.eclipse.daanse.rolap.mapping.instance.emf.tutorial.cube.measure.aggregator.nth;

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
        container.setName("Daanse Tutorial - Measure Aggregator Nth");

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
        entitySet1.setName("NthAgg1");
        entitySet1.setEntityType("Model.NthAgg1");

        TEntitySet biEntitySet1 = biFactory.createTEntitySet();
        biEntitySet1.setCaption("NthAgg1");
        entitySet1.setBiEntitySet(biEntitySet1);

        EntitySetType entitySet2 = edmFactory.createEntitySetType();
        entitySet2.setName("NthAgg2");
        entitySet2.setEntityType("Model.NthAgg2");

        TEntitySet biEntitySet2 = biFactory.createTEntitySet();
        biEntitySet2.setCaption("NthAgg2");
        entitySet2.setBiEntitySet(biEntitySet2);

        EntitySetType entitySet3 = edmFactory.createEntitySetType();
        entitySet3.setName("NthAgg3");
        entitySet3.setEntityType("Model.NthAgg3");

        TEntitySet biEntitySet3 = biFactory.createTEntitySet();
        biEntitySet3.setCaption("NthAgg3");
        entitySet3.setBiEntitySet(biEntitySet3);

        EntitySetType entitySet4 = edmFactory.createEntitySetType();
        entitySet4.setName("NthAgg4");
        entitySet4.setEntityType("Model.NthAgg4");

        TEntitySet biEntitySet4 = biFactory.createTEntitySet();
        biEntitySet4.setCaption("NthAgg4");
        entitySet4.setBiEntitySet(biEntitySet4);

        EntitySetType entitySet5 = edmFactory.createEntitySetType();
        entitySet5.setName("NthAgg5");
        entitySet5.setEntityType("Model.NthAgg5");

        TEntitySet biEntitySet5 = biFactory.createTEntitySet();
        biEntitySet5.setCaption("NthAgg5");
        entitySet5.setBiEntitySet(biEntitySet5);

        EntitySetType entitySet6 = edmFactory.createEntitySetType();
        entitySet6.setName("NthAgg6");
        entitySet6.setEntityType("Model.NthAgg6");

        TEntitySet biEntitySet6 = biFactory.createTEntitySet();
        biEntitySet6.setCaption("NthAgg6");
        entitySet6.setBiEntitySet(biEntitySet6);

        EntitySetType entitySet7 = edmFactory.createEntitySetType();
        entitySet7.setName("NthAgg7");
        entitySet7.setEntityType("Model.NthAgg7");

        TEntitySet biEntitySet7 = biFactory.createTEntitySet();
        biEntitySet7.setCaption("NthAgg7");
        entitySet7.setBiEntitySet(biEntitySet7);

        container.getEntitySet().add(entitySet1);
        container.getEntitySet().add(entitySet2);
        container.getEntitySet().add(entitySet3);
        container.getEntitySet().add(entitySet4);
        container.getEntitySet().add(entitySet5);
        container.getEntitySet().add(entitySet6);
        container.getEntitySet().add(entitySet7);

        schema.getEntityContainer().add(container);

        org.eclipse.daanse.xmla.csdl.model.v2.edm.TEntityType townHierarchyType = edmFactory.createTEntityType();
        townHierarchyType.setName("Hierarchy");

        TLevel tLevelValue = biFactory.createTLevel();
        tLevelValue.setName("Value");
        tLevelValue.setCaption("Value");
        tLevelValue.setReferenceName("[Dim].[Hierarchy].[Value]");

        THierarchy hierarchyTHierarchy = biFactory.createTHierarchy();
        hierarchyTHierarchy.setCaption("Hierarchy");
        hierarchyTHierarchy.setName("Hierarchy");
        hierarchyTHierarchy.setReferenceName("[Dim].[Hierarchy]");
        hierarchyTHierarchy.getLevel().add(tLevelValue);

        TEntityType hierarchyTEntityType = biFactory.createTEntityType();
        hierarchyTEntityType.setContents("Hierarchy");
        hierarchyTEntityType.getHierarchy().add(hierarchyTHierarchy);

        townHierarchyType.setBiEntityType(hierarchyTEntityType);

        TEntityProperty factKeyProperty = edmFactory.createTEntityProperty();
        factKeyProperty.setName("Fact.KEY");
        factKeyProperty.setType("Int32");
        factKeyProperty.setNullable(false);

        TPropertyRef factTownIdPropertyRef = edmFactory.createTPropertyRef();
        factTownIdPropertyRef.setName("Fact.KEY");

        TEntityKeyElement key =  edmFactory.createTEntityKeyElement();
        key.getPropertyRef().add(factTownIdPropertyRef);

        TEntityProperty facktCountryProperty = edmFactory.createTEntityProperty();
        facktCountryProperty.setName("Fact.COUNTRY");
        facktCountryProperty.setType("String");
        facktCountryProperty.setNullable(false);

        townHierarchyType.getProperty().add(factKeyProperty);
        townHierarchyType.getProperty().add(facktCountryProperty);

        schema.getEntityType().add(townHierarchyType);

        org.eclipse.daanse.xmla.csdl.model.v2.edm.TEntityType nthAgg1Type = edmFactory.createTEntityType();
        nthAgg1Type.setName("NthAgg1");

        TEntityType biNthAgg1Type = biFactory.createTEntityType();
        biNthAgg1Type.setContents("NthAgg1");
        nthAgg1Type.setBiEntityType(biNthAgg1Type);

        TEntityProperty nthAgg1Property = edmFactory.createTEntityProperty();
        nthAgg1Property.setName("Fact.VALUE");
        nthAgg1Property.setType("Int32");
        nthAgg1Property.setNullable(false);

        TMeasure biNthAgg1Measure = biFactory.createTMeasure();
        biNthAgg1Measure.setCaption("NthAgg1");
        biNthAgg1Measure.setHidden(false);
        biNthAgg1Measure.setReferenceName("[measures].[NthAgg1]");
        nthAgg1Property.setBiMeasure(biNthAgg1Measure);

        nthAgg1Type.getProperty().add(nthAgg1Property);

        org.eclipse.daanse.xmla.csdl.model.v2.edm.TEntityType nthAgg2Type = edmFactory.createTEntityType();
        nthAgg2Type.setName("NthAgg2");

        TEntityType biNthAgg2Type = biFactory.createTEntityType();
        biNthAgg2Type.setContents("NthAgg2");
        nthAgg2Type.setBiEntityType(biNthAgg2Type);

        TEntityProperty nthAgg2Property = edmFactory.createTEntityProperty();
        nthAgg2Property.setName("Fact.VALUE");
        nthAgg2Property.setType("Int32");
        nthAgg2Property.setNullable(false);

        TMeasure biNthAgg2Measure = biFactory.createTMeasure();
        biNthAgg2Measure.setCaption("NthAgg2");
        biNthAgg2Measure.setHidden(false);
        biNthAgg2Measure.setReferenceName("[measures].[NthAgg2]");
        nthAgg2Property.setBiMeasure(biNthAgg2Measure);

        nthAgg2Type.getProperty().add(nthAgg2Property);

        org.eclipse.daanse.xmla.csdl.model.v2.edm.TEntityType nthAgg3Type = edmFactory.createTEntityType();
        nthAgg3Type.setName("NthAgg3");

        TEntityType biNthAgg3Type = biFactory.createTEntityType();
        biNthAgg3Type.setContents("NthAgg3");
        nthAgg3Type.setBiEntityType(biNthAgg3Type);

        TEntityProperty nthAgg3Property = edmFactory.createTEntityProperty();
        nthAgg3Property.setName("Fact.VALUE");
        nthAgg3Property.setType("Int32");
        nthAgg3Property.setNullable(false);

        TMeasure biNthAgg3Measure = biFactory.createTMeasure();
        biNthAgg3Measure.setCaption("NthAgg3");
        biNthAgg3Measure.setHidden(false);
        biNthAgg3Measure.setReferenceName("[measures].[NthAgg3]");
        nthAgg3Property.setBiMeasure(biNthAgg3Measure);

        nthAgg3Type.getProperty().add(nthAgg3Property);

        org.eclipse.daanse.xmla.csdl.model.v2.edm.TEntityType nthAgg4Type = edmFactory.createTEntityType();
        nthAgg4Type.setName("NthAgg4");

        TEntityType biNthAgg4Type = biFactory.createTEntityType();
        biNthAgg4Type.setContents("NthAgg4");
        nthAgg4Type.setBiEntityType(biNthAgg4Type);

        TEntityProperty nthAgg4Property = edmFactory.createTEntityProperty();
        nthAgg4Property.setName("Fact.VALUE");
        nthAgg4Property.setType("Int32");
        nthAgg4Property.setNullable(false);

        TMeasure biNthAgg4Measure = biFactory.createTMeasure();
        biNthAgg4Measure.setCaption("NthAgg4");
        biNthAgg4Measure.setHidden(false);
        biNthAgg4Measure.setReferenceName("[measures].[NthAgg4]");
        nthAgg4Property.setBiMeasure(biNthAgg4Measure);

        nthAgg4Type.getProperty().add(nthAgg4Property);

        org.eclipse.daanse.xmla.csdl.model.v2.edm.TEntityType nthAgg5Type = edmFactory.createTEntityType();
        nthAgg5Type.setName("NthAgg5");

        TEntityType biNthAgg5Type = biFactory.createTEntityType();
        biNthAgg5Type.setContents("NthAgg5");
        nthAgg5Type.setBiEntityType(biNthAgg5Type);

        TEntityProperty nthAgg5Property = edmFactory.createTEntityProperty();
        nthAgg5Property.setName("Fact.VALUE");
        nthAgg5Property.setType("Int32");
        nthAgg5Property.setNullable(false);

        TMeasure biNthAgg5Measure = biFactory.createTMeasure();
        biNthAgg5Measure.setCaption("NthAgg5");
        biNthAgg5Measure.setHidden(false);
        biNthAgg5Measure.setReferenceName("[measures].[NthAgg5]");
        nthAgg5Property.setBiMeasure(biNthAgg5Measure);

        nthAgg5Type.getProperty().add(nthAgg5Property);

        org.eclipse.daanse.xmla.csdl.model.v2.edm.TEntityType nthAgg6Type = edmFactory.createTEntityType();
        nthAgg6Type.setName("NthAgg5");

        TEntityType biNthAgg6Type = biFactory.createTEntityType();
        biNthAgg6Type.setContents("NthAgg6");
        nthAgg6Type.setBiEntityType(biNthAgg6Type);

        TEntityProperty nthAgg6Property = edmFactory.createTEntityProperty();
        nthAgg6Property.setName("Fact.VALUE");
        nthAgg6Property.setType("Int32");
        nthAgg6Property.setNullable(false);

        TMeasure biNthAgg6Measure = biFactory.createTMeasure();
        biNthAgg6Measure.setCaption("NthAgg6");
        biNthAgg6Measure.setHidden(false);
        biNthAgg6Measure.setReferenceName("[measures].[NthAgg6]");
        nthAgg6Property.setBiMeasure(biNthAgg6Measure);

        nthAgg6Type.getProperty().add(nthAgg6Property);

        org.eclipse.daanse.xmla.csdl.model.v2.edm.TEntityType nthAgg7Type = edmFactory.createTEntityType();
        nthAgg7Type.setName("NthAgg5");

        TEntityType biNthAgg7Type = biFactory.createTEntityType();
        biNthAgg7Type.setContents("NthAgg7");
        nthAgg7Type.setBiEntityType(biNthAgg7Type);

        TEntityProperty nthAgg7Property = edmFactory.createTEntityProperty();
        nthAgg7Property.setName("Fact.VALUE");
        nthAgg7Property.setType("Int32");
        nthAgg7Property.setNullable(false);

        TMeasure biNthAgg7Measure = biFactory.createTMeasure();
        biNthAgg7Measure.setCaption("NthAgg7");
        biNthAgg7Measure.setHidden(false);
        biNthAgg7Measure.setReferenceName("[measures].[NthAgg7]");
        nthAgg7Property.setBiMeasure(biNthAgg7Measure);

        nthAgg7Type.getProperty().add(nthAgg7Property);

        schema.getEntityType().add(nthAgg1Type);
        schema.getEntityType().add(nthAgg2Type);
        schema.getEntityType().add(nthAgg3Type);
        schema.getEntityType().add(nthAgg4Type);
        schema.getEntityType().add(nthAgg5Type);
        schema.getEntityType().add(nthAgg6Type);
        schema.getEntityType().add(nthAgg7Type);

        return schema;
	}

}
