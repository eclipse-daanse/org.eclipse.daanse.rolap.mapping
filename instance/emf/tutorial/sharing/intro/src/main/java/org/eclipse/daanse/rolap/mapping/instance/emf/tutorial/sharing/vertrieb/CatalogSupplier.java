/*
 * Copyright (c) 2026 Contributors to the Eclipse Foundation.
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
package org.eclipse.daanse.rolap.mapping.instance.emf.tutorial.sharing.vertrieb;

import java.util.List;

import org.eclipse.daanse.cwm.model.cwm.resource.relational.Column;
import org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory;
import org.eclipse.daanse.cwm.model.cwm.resource.relational.Schema;
import org.eclipse.daanse.cwm.model.cwm.resource.relational.Table;
import org.eclipse.daanse.cwm.model.cwm.resource.relational.util.SQLSimpleTypes;
import org.eclipse.daanse.rolap.mapping.instance.api.Kind;
import org.eclipse.daanse.rolap.mapping.instance.api.MappingInstance;
import org.eclipse.daanse.rolap.mapping.instance.api.Source;
import org.eclipse.daanse.rolap.mapping.model.catalog.Catalog;
import org.eclipse.daanse.rolap.mapping.model.catalog.CatalogFactory;
import org.eclipse.daanse.rolap.mapping.model.database.source.SourceFactory;
import org.eclipse.daanse.rolap.mapping.model.database.source.TableSource;
import org.eclipse.daanse.rolap.mapping.model.olap.cube.CubeFactory;
import org.eclipse.daanse.rolap.mapping.model.olap.cube.MeasureGroup;
import org.eclipse.daanse.rolap.mapping.model.olap.cube.PhysicalCube;
import org.eclipse.daanse.rolap.mapping.model.olap.cube.measure.MeasureFactory;
import org.eclipse.daanse.rolap.mapping.model.olap.cube.measure.SumMeasure;
import org.eclipse.daanse.rolap.mapping.model.olap.dimension.DimensionConnector;
import org.eclipse.daanse.rolap.mapping.model.olap.dimension.DimensionFactory;
import org.eclipse.daanse.rolap.mapping.model.provider.CatalogMappingSupplier;
import org.eclipse.daanse.rolap.mapping.model.provider.util.Naming;
import org.osgi.service.component.annotations.Component;

/**
 * Catalog B of the sharing pair: it IMPORTS the time dimension owned by
 * the Stammdaten supplier (CWM: available = owned + imported) and
 * wires it to its own cube — usage without ownership, the multi-catalog
 * form of sharing. The dependency points only from here to the owner;
 * serialized as separate files, this reference becomes an href (see
 * CrossCatalogImportTest).
 */
@MappingInstance(kind = Kind.TUTORIAL, number = "2.19.02", source = Source.EMF, group = "Sharing")
@Component(service = CatalogMappingSupplier.class)
public class CatalogSupplier implements CatalogMappingSupplier {

    public static final String CATALOG_NAME = "Daanse Tutorial - Sharing Vertrieb";

    private static final Catalog CATALOG;

    static {
        Schema schema = RelationalFactory.eINSTANCE.createSchema();

        Column zeitKey = RelationalFactory.eINSTANCE.createColumn();
        zeitKey.setName("ZEIT_KEY");
        zeitKey.setType(SQLSimpleTypes.Sql99.integerType());

        Column betrag = RelationalFactory.eINSTANCE.createColumn();
        betrag.setName("BETRAG");
        betrag.setType(SQLSimpleTypes.Sql99.integerType());

        Table umsatzTable = RelationalFactory.eINSTANCE.createTable();
        umsatzTable.setName("UMSATZ");
        umsatzTable.getFeature().addAll(List.of(zeitKey, betrag));
        schema.getOwnedElement().add(umsatzTable);

        TableSource umsatzSource = SourceFactory.eINSTANCE.createTableSource();
        umsatzSource.setTable(umsatzTable);

        SumMeasure summe = MeasureFactory.eINSTANCE.createSumMeasure();
        summe.setName("Betrag");
        summe.setColumn(betrag);

        MeasureGroup group = CubeFactory.eINSTANCE.createMeasureGroup();
        group.getMeasures().add(summe);

        // The shared moment: the connector references the dimension OWNED by
        // catalog A — an ordinary usage reference across catalog borders.
        DimensionConnector zeit = DimensionFactory.eINSTANCE.createDimensionConnector();
        zeit.setDimension(org.eclipse.daanse.rolap.mapping.instance.emf.tutorial.sharing.stammdaten.CatalogSupplier.DIMENSION_ZEIT);
        zeit.setForeignKey(zeitKey);

        PhysicalCube umsatz = CubeFactory.eINSTANCE.createPhysicalCube();
        umsatz.setName("Umsatz");
        umsatz.setSource(umsatzSource);
        umsatz.getDimensionConnectors().add(zeit);
        umsatz.getMeasureGroups().add(group);

        CATALOG = CatalogFactory.eINSTANCE.createCatalog();
        CATALOG.setName(CATALOG_NAME);
        // own schema plus the FOREIGN dimension: both enter through imports —
        // exposure is owned + imported, ownership stays with catalog A.
        CATALOG.getImportedElement().add(schema);
        CATALOG.getImportedElement().add(org.eclipse.daanse.rolap.mapping.instance.emf.tutorial.sharing.stammdaten.CatalogSupplier.DATABASE_SCHEMA);
        CATALOG.getImportedElement().add(org.eclipse.daanse.rolap.mapping.instance.emf.tutorial.sharing.stammdaten.CatalogSupplier.DIMENSION_ZEIT);
        CATALOG.getOwnedElement().addAll(List.of(umsatzSource, umsatz));
        Naming.complete(CATALOG);
    }

    @Override
    public Catalog get() {
        return CATALOG;
    }
}
