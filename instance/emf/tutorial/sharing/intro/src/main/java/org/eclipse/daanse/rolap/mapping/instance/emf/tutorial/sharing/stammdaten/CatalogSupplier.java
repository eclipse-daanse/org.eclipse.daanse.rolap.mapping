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
package org.eclipse.daanse.rolap.mapping.instance.emf.tutorial.sharing.stammdaten;

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
import org.eclipse.daanse.rolap.mapping.model.olap.dimension.DimensionFactory;
import org.eclipse.daanse.rolap.mapping.model.olap.dimension.TimeDimension;
import org.eclipse.daanse.rolap.mapping.model.olap.dimension.hierarchy.ExplicitHierarchy;
import org.eclipse.daanse.rolap.mapping.model.olap.dimension.hierarchy.HierarchyFactory;
import org.eclipse.daanse.rolap.mapping.model.olap.dimension.hierarchy.level.Level;
import org.eclipse.daanse.rolap.mapping.model.olap.dimension.hierarchy.level.LevelFactory;
import org.eclipse.daanse.rolap.mapping.model.provider.CatalogMappingSupplier;
import org.eclipse.daanse.rolap.mapping.model.provider.util.Naming;
import org.osgi.service.component.annotations.Component;

/**
 * Catalog A of the sharing pair: it OWNS the time dimension (hierarchy,
 * level, source) that the Vertrieb supplier imports. The owner
 * knows nothing about its consumers — sharing is a property of the
 * importing side.
 */
@MappingInstance(kind = Kind.TUTORIAL, number = "2.19.01", source = Source.EMF, group = "Sharing")
@Component(service = CatalogMappingSupplier.class)
public class CatalogSupplier implements CatalogMappingSupplier {

    public static final String CATALOG_NAME = "Daanse Tutorial - Sharing Stammdaten";

    // Static singletons: the consuming catalog imports THESE instances, the
    // same way the complex suppliers share their static columns and tables.
    public static final Schema DATABASE_SCHEMA;
    public static final Table TABLE_ZEIT;
    public static final Column COLUMN_ZEIT_KEY;
    public static final Column COLUMN_JAHR;
    public static final TimeDimension DIMENSION_ZEIT;

    private static final Catalog CATALOG;

    static {
        DATABASE_SCHEMA = RelationalFactory.eINSTANCE.createSchema();

        COLUMN_ZEIT_KEY = RelationalFactory.eINSTANCE.createColumn();
        COLUMN_ZEIT_KEY.setName("ZEIT_KEY");
        COLUMN_ZEIT_KEY.setType(SQLSimpleTypes.Sql99.integerType());

        COLUMN_JAHR = RelationalFactory.eINSTANCE.createColumn();
        COLUMN_JAHR.setName("JAHR");
        COLUMN_JAHR.setType(SQLSimpleTypes.Sql99.integerType());

        TABLE_ZEIT = RelationalFactory.eINSTANCE.createTable();
        TABLE_ZEIT.setName("ZEIT");
        TABLE_ZEIT.getFeature().addAll(List.of(COLUMN_ZEIT_KEY, COLUMN_JAHR));
        DATABASE_SCHEMA.getOwnedElement().add(TABLE_ZEIT);

        TableSource zeitSource = SourceFactory.eINSTANCE.createTableSource();
        zeitSource.setTable(TABLE_ZEIT);

        Level jahr = LevelFactory.eINSTANCE.createLevel();
        jahr.setName("Jahr");
        jahr.setColumn(COLUMN_JAHR);

        ExplicitHierarchy hierarchy = HierarchyFactory.eINSTANCE.createExplicitHierarchy();
        hierarchy.setHasAll(true);
        hierarchy.setAllMemberName("Alle Jahre");
        hierarchy.setPrimaryKey(COLUMN_ZEIT_KEY);
        hierarchy.setSource(zeitSource);
        hierarchy.getLevels().add(jahr);

        DIMENSION_ZEIT = DimensionFactory.eINSTANCE.createTimeDimension();
        DIMENSION_ZEIT.setName("Zeit");
        DIMENSION_ZEIT.getHierarchies().add(hierarchy);

        CATALOG = CatalogFactory.eINSTANCE.createCatalog();
        CATALOG.setName(CATALOG_NAME);
        CATALOG.getImportedElement().add(DATABASE_SCHEMA);
        CATALOG.getOwnedElement().addAll(List.of(zeitSource, jahr, hierarchy, DIMENSION_ZEIT));
        Naming.complete(CATALOG);
    }

    @Override
    public Catalog get() {
        return CATALOG;
    }
}
