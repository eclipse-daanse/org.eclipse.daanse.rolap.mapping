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
package org.eclipse.daanse.rolap.mapping.instance.emf.tutorial.cube.measure.aggregator.bit;

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
        container.setName("Daanse Tutorial - Measure Aggregator Bit");

        TEntityContainer biContainer = biFactory.createTEntityContainer();
        biContainer.setCaption("MeasuresAggregatorsCube");
        biContainer.setCulture("de-DE");
        container.setBiEntityContainer(biContainer);

        EntitySetType entitySet1 = edmFactory.createEntitySetType();
        entitySet1.setName("BitAggAND");
        entitySet1.setEntityType("Model.BitAggAND");

        TEntitySet biEntitySet1 = biFactory.createTEntitySet();
        biEntitySet1.setCaption("BitAgg AND");
        entitySet1.setBiEntitySet(biEntitySet1);

        EntitySetType entitySet2 = edmFactory.createEntitySetType();
        entitySet2.setName("BitAggOR");
        entitySet2.setEntityType("Model.BitAggOR");

        TEntitySet biEntitySet2 = biFactory.createTEntitySet();
        biEntitySet2.setCaption("BitAgg OR");
        entitySet2.setBiEntitySet(biEntitySet2);

        EntitySetType entitySet3 = edmFactory.createEntitySetType();
        entitySet3.setName("BitAggXOR");
        entitySet3.setEntityType("Model.BitAggXOR");

        TEntitySet biEntitySet3 = biFactory.createTEntitySet();
        biEntitySet3.setCaption("BitAgg XOR");
        entitySet3.setBiEntitySet(biEntitySet3);

        EntitySetType entitySet4 = edmFactory.createEntitySetType();
        entitySet4.setName("BitAggNAND");
        entitySet4.setEntityType("Model.BitAggNAND");

        TEntitySet biEntitySet4 = biFactory.createTEntitySet();
        biEntitySet4.setCaption("BitAgg NAND");
        entitySet4.setBiEntitySet(biEntitySet4);

        EntitySetType entitySet5 = edmFactory.createEntitySetType();
        entitySet5.setName("BitAggNOR");
        entitySet5.setEntityType("Model.BitAggNOR");

        TEntitySet biEntitySet5 = biFactory.createTEntitySet();
        biEntitySet5.setCaption("BitAgg NOR");
        entitySet5.setBiEntitySet(biEntitySet5);

        EntitySetType entitySet6 = edmFactory.createEntitySetType();
        entitySet6.setName("BitAggNXOR");
        entitySet6.setEntityType("Model.BitAggNXOR");

        TEntitySet biEntitySet6 = biFactory.createTEntitySet();
        biEntitySet6.setCaption("BitAgg NXOR");
        entitySet6.setBiEntitySet(biEntitySet6);

        container.getEntitySet().add(entitySet1);
        container.getEntitySet().add(entitySet2);
        container.getEntitySet().add(entitySet3);
        container.getEntitySet().add(entitySet4);
        container.getEntitySet().add(entitySet5);
        container.getEntitySet().add(entitySet6);

        schema.getEntityContainer().add(container);

        org.eclipse.daanse.xmla.csdl.model.v2.edm.TEntityType bitAggANDType = edmFactory.createTEntityType();
        bitAggANDType.setName("BitAggAND");

        TEntityType biBitAggANDType = biFactory.createTEntityType();
        biBitAggANDType.setContents("BitAggAND");
        bitAggANDType.setBiEntityType(biBitAggANDType);

        TEntityProperty bitAggANDProperty = edmFactory.createTEntityProperty();
        bitAggANDProperty.setName("Fact.VALUE");
        bitAggANDProperty.setType("Int32");
        bitAggANDProperty.setNullable(false);

        TMeasure biBitAggANDMeasure = biFactory.createTMeasure();
        biBitAggANDMeasure.setCaption("BitAgg AND");
        biBitAggANDMeasure.setHidden(false);
        biBitAggANDMeasure.setReferenceName("[measures].[BitAgg AND]");
        bitAggANDProperty.setBiMeasure(biBitAggANDMeasure);

        bitAggANDType.getProperty().add(bitAggANDProperty);

        org.eclipse.daanse.xmla.csdl.model.v2.edm.TEntityType bitAggORType = edmFactory.createTEntityType();
        bitAggORType.setName("BitAggOR");

        TEntityType biBitAggORType = biFactory.createTEntityType();
        biBitAggORType.setContents("BitAgg OR");
        bitAggORType.setBiEntityType(biBitAggORType);

        TEntityProperty bitAggORProperty = edmFactory.createTEntityProperty();
        bitAggORProperty.setName("Fact.VALUE");
        bitAggORProperty.setType("Int32");
        bitAggORProperty.setNullable(false);

        TMeasure biBitAggORMeasure = biFactory.createTMeasure();
        biBitAggORMeasure.setCaption("BitAgg OR");
        biBitAggORMeasure.setHidden(false);
        biBitAggORMeasure.setReferenceName("[measures].[BitAgg OR]");
        bitAggORProperty.setBiMeasure(biBitAggORMeasure);

        bitAggORType.getProperty().add(bitAggORProperty);

        org.eclipse.daanse.xmla.csdl.model.v2.edm.TEntityType bitAggXORType = edmFactory.createTEntityType();
        bitAggXORType.setName("BitAggXOR");

        TEntityType biBitAggXORType = biFactory.createTEntityType();
        biBitAggXORType.setContents("BitAggXOR");
        bitAggXORType.setBiEntityType(biBitAggXORType);

        TEntityProperty bitAggXORProperty = edmFactory.createTEntityProperty();
        bitAggXORProperty.setName("Fact.VALUE");
        bitAggXORProperty.setType("Int32");
        bitAggXORProperty.setNullable(false);

        TMeasure biBitAggXORMeasure = biFactory.createTMeasure();
        biBitAggXORMeasure.setCaption("BitAgg XOR");
        biBitAggXORMeasure.setHidden(false);
        biBitAggXORMeasure.setReferenceName("[measures].[BitAgg XOR]");
        bitAggXORProperty.setBiMeasure(biBitAggXORMeasure);

        bitAggXORType.getProperty().add(bitAggXORProperty);

        org.eclipse.daanse.xmla.csdl.model.v2.edm.TEntityType bitAggNANDType = edmFactory.createTEntityType();
        bitAggNANDType.setName("BitAggNAND");

        TEntityType biBitAggNANDType = biFactory.createTEntityType();
        biBitAggNANDType.setContents("BitAggNAND");
        bitAggNANDType.setBiEntityType(biBitAggXORType);

        TEntityProperty bitAggNANDProperty = edmFactory.createTEntityProperty();
        bitAggNANDProperty.setName("Fact.VALUE");
        bitAggNANDProperty.setType("Int32");
        bitAggNANDProperty.setNullable(false);

        TMeasure biBitAggNANDMeasure = biFactory.createTMeasure();
        biBitAggNANDMeasure.setCaption("BitAgg NAND");
        biBitAggNANDMeasure.setHidden(false);
        biBitAggNANDMeasure.setReferenceName("[measures].[BitAgg NAND]");
        bitAggNANDProperty.setBiMeasure(biBitAggNANDMeasure);

        bitAggNANDType.getProperty().add(bitAggNANDProperty);

        org.eclipse.daanse.xmla.csdl.model.v2.edm.TEntityType bitAggNORType = edmFactory.createTEntityType();
        bitAggNORType.setName("BitAggNOR");

        TEntityType biBitAggNORType = biFactory.createTEntityType();
        biBitAggNORType.setContents("BitAggNOR");
        bitAggNORType.setBiEntityType(biBitAggNORType);

        TEntityProperty bitAggNORProperty = edmFactory.createTEntityProperty();
        bitAggNORProperty.setName("Fact.VALUE");
        bitAggNORProperty.setType("Int32");
        bitAggNORProperty.setNullable(false);

        TMeasure biBitAggNORMeasure = biFactory.createTMeasure();
        biBitAggNORMeasure.setCaption("BitAgg NOR");
        biBitAggNORMeasure.setHidden(false);
        biBitAggNORMeasure.setReferenceName("[measures].[BitAgg NOR]");
        bitAggNORProperty.setBiMeasure(biBitAggNORMeasure);

        bitAggNORType.getProperty().add(bitAggNORProperty);

        org.eclipse.daanse.xmla.csdl.model.v2.edm.TEntityType bitAggNXORType = edmFactory.createTEntityType();
        bitAggNXORType.setName("BitAggNXOR");

        TEntityType biBitAggNXORType = biFactory.createTEntityType();
        biBitAggNXORType.setContents("BitAggNXOR");
        bitAggNXORType.setBiEntityType(biBitAggNORType);

        TEntityProperty bitAggNXORProperty = edmFactory.createTEntityProperty();
        bitAggNXORProperty.setName("Fact.VALUE");
        bitAggNXORProperty.setType("Int32");
        bitAggNXORProperty.setNullable(false);

        TMeasure biBitAggNXORMeasure = biFactory.createTMeasure();
        biBitAggNXORMeasure.setCaption("BitAgg NXOR");
        biBitAggNXORMeasure.setHidden(false);
        biBitAggNXORMeasure.setReferenceName("[measures].[BitAgg NXOR]");
        bitAggNXORProperty.setBiMeasure(biBitAggNXORMeasure);

        bitAggNXORType.getProperty().add(bitAggNXORProperty);

        schema.getEntityType().add(bitAggANDType);
        schema.getEntityType().add(bitAggORType);
        schema.getEntityType().add(bitAggXORType);
        schema.getEntityType().add(bitAggNANDType);
        schema.getEntityType().add(bitAggNORType);
        schema.getEntityType().add(bitAggNXORType);
        schema.getEntityType().add(bitAggNXORType);

        return schema;
	}

}
