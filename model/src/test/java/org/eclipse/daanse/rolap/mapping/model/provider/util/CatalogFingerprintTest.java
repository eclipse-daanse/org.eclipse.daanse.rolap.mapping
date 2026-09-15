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
*   Stefan Bischof (bipolis.org) - initial
*/
package org.eclipse.daanse.rolap.mapping.model.provider.util;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;

import org.eclipse.daanse.cwm.model.cwm.resource.relational.Column;
import org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory;
import org.eclipse.daanse.cwm.model.cwm.resource.relational.Schema;
import org.eclipse.daanse.cwm.model.cwm.resource.relational.Table;
import org.eclipse.daanse.cwm.model.cwm.resource.relational.util.SQLSimpleTypes;
import org.eclipse.daanse.rolap.mapping.model.RolapMappingPackage;
import org.eclipse.daanse.rolap.mapping.model.catalog.Catalog;
import org.eclipse.daanse.rolap.mapping.model.catalog.CatalogFactory;
import org.eclipse.daanse.rolap.mapping.model.database.source.SourceFactory;
import org.eclipse.daanse.rolap.mapping.model.database.source.TableSource;
import org.eclipse.daanse.rolap.mapping.model.olap.cube.CubeFactory;
import org.eclipse.daanse.rolap.mapping.model.olap.cube.MeasureGroup;
import org.eclipse.daanse.rolap.mapping.model.olap.cube.PhysicalCube;
import org.eclipse.daanse.rolap.mapping.model.olap.cube.measure.MeasureFactory;
import org.eclipse.daanse.rolap.mapping.model.olap.cube.measure.SumMeasure;
import org.eclipse.daanse.rolap.mapping.model.olap.dimension.DimensionFactory;
import org.eclipse.daanse.rolap.mapping.model.olap.dimension.TimeDimension;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;
import org.junit.jupiter.api.Test;

class CatalogFingerprintTest {

    private static Catalog buildCatalog(String cubeName) {
        Schema schema = RelationalFactory.eINSTANCE.createSchema();
        schema.setName("Fact-Schema");

        Column keyColumn = RelationalFactory.eINSTANCE.createColumn();
        keyColumn.setName("KEY");
        keyColumn.setType(SQLSimpleTypes.Sql99.varcharType());

        Column valueColumn = RelationalFactory.eINSTANCE.createColumn();
        valueColumn.setName("VALUE");
        valueColumn.setType(SQLSimpleTypes.Sql99.integerType());

        // second VARCHAR column exercises the SQLSimpleType collapse
        Column nameColumn = RelationalFactory.eINSTANCE.createColumn();
        nameColumn.setName("NAME");
        nameColumn.setType(SQLSimpleTypes.Sql99.varcharType());

        Table table = RelationalFactory.eINSTANCE.createTable();
        table.setName("Fact");
        table.getFeature().addAll(List.of(keyColumn, valueColumn, nameColumn));
        schema.getOwnedElement().add(table);

        TableSource query = SourceFactory.eINSTANCE.createTableSource();
        query.setTable(table);

        SumMeasure measure = MeasureFactory.eINSTANCE.createSumMeasure();
        measure.setName("Measure-Sum");
        measure.setColumn(valueColumn);

        MeasureGroup measureGroup = CubeFactory.eINSTANCE.createMeasureGroup();
        measureGroup.getMeasures().add(measure);

        PhysicalCube cube = CubeFactory.eINSTANCE.createPhysicalCube();
        cube.setName(cubeName);
        cube.setSource(query);
        cube.getMeasureGroups().add(measureGroup);

        Catalog catalog = CatalogFactory.eINSTANCE.createCatalog();
        catalog.setName("Fingerprint-Katalog");
        catalog.getImportedElement().add(schema);
        catalog.getOwnedElement().addAll(List.of(query, cube));
        return catalog;
    }

    @Test
    void t1_sameInstanceHashesIdentically() {
        Catalog catalog = buildCatalog("Umsatz");
        assertThat(CatalogFingerprint.sha256Hex(catalog)).isEqualTo(CatalogFingerprint.sha256Hex(catalog));
    }

    @Test
    void t2_saveAndReloadHashesIdentically() throws Exception {
        Catalog catalog = buildCatalog("Umsatz");
        byte[] xmi = CatalogFingerprint.canonicalXmiBytes(catalog);

        ResourceSet in = resourceSet();
        Resource resource = in.createResource(URI.createFileURI("catalog.xmi"));
        resource.load(new ByteArrayInputStream(xmi), null);
        Catalog reloaded = resource.getContents().stream().filter(Catalog.class::isInstance).map(Catalog.class::cast)
                .findFirst().orElseThrow();

        assertThat(CatalogFingerprint.sha256Hex(reloaded)).isEqualTo(CatalogFingerprint.sha256Hex(catalog));
    }

    @Test
    void t3_rebuildFromSameCodeHashesIdentically() {
        assertThat(CatalogFingerprint.sha256Hex(buildCatalog("Umsatz")))
                .isEqualTo(CatalogFingerprint.sha256Hex(buildCatalog("Umsatz")));
    }

    @Test
    void t4_renamedElementHashesDifferently() {
        assertThat(CatalogFingerprint.sha256Hex(buildCatalog("Umsatz")))
                .isNotEqualTo(CatalogFingerprint.sha256Hex(buildCatalog("Absatz")));
    }

    @Test
    void t6_importerOppositeStaysOutOfTheIdentity() {
        Catalog a = CatalogFactory.eINSTANCE.createCatalog();
        a.setName("Stammdaten");
        TimeDimension zeit = DimensionFactory.eINSTANCE.createTimeDimension();
        zeit.setName("Zeit");
        a.getOwnedElement().add(zeit);

        String aloneHex = CatalogFingerprint.sha256Hex(a);

        Catalog b = CatalogFactory.eINSTANCE.createCatalog();
        b.setName("Vertrieb");
        b.getImportedElement().add(zeit);

        String aXmi = new String(CatalogFingerprint.canonicalXmiBytes(a), StandardCharsets.UTF_8);
        assertThat(aXmi).contains("Zeit").doesNotContain("Vertrieb");
        assertThat(CatalogFingerprint.sha256Hex(a)).isEqualTo(aloneHex);

        String bXmi = new String(CatalogFingerprint.canonicalXmiBytes(b), StandardCharsets.UTF_8);
        assertThat(bXmi).contains("Stammdaten");
    }

    @Test
    void t7_goldenHashOfTheReferenceCatalog() {
        // a change here means the canonicalization changed and needs a new version salt
        assertThat(CatalogFingerprint.sha256Hex(buildCatalog("Umsatz")))
                .isEqualTo("08aa8c6c82d374952e302962fce2966925ee7f9f67b2c566289fdf85a5983bd0");
    }

    @Test
    void servedModelStaysUntouched() {
        Catalog catalog = buildCatalog("Umsatz");
        int ownedBefore = catalog.getOwnedElement().size();
        int importedBefore = catalog.getImportedElement().size();

        CatalogFingerprint.sha256(catalog);

        assertThat(catalog.eResource()).isNull();
        assertThat(catalog.getOwnedElement()).hasSize(ownedBefore);
        assertThat(catalog.getImportedElement()).hasSize(importedBefore);
        // the type collapse happens on the copy only
        Schema schema = (Schema) catalog.getImportedElement().get(0);
        Table table = (Table) schema.getOwnedElement().get(0);
        Column key = (Column) table.getFeature().get(0);
        Column name = (Column) table.getFeature().get(2);
        assertThat(key.getType()).isNotSameAs(name.getType());
    }

    @Test
    void unresolvedProxyVoidsTheIdentity() {
        Catalog catalog = buildCatalog("Umsatz");
        TimeDimension proxy = DimensionFactory.eINSTANCE.createTimeDimension();
        ((org.eclipse.emf.ecore.impl.BasicEObjectImpl) proxy)
                .eSetProxyURI(URI.createURI("missing.xmi#_timedimension_zeit"));
        catalog.getImportedElement().add(proxy);

        assertThatThrownBy(() -> CatalogFingerprint.sha256(catalog)).isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("proxies");
    }

    private static ResourceSet resourceSet() {
        ResourceSet rs = new ResourceSetImpl();
        rs.getResourceFactoryRegistry().getExtensionToFactoryMap().put("xmi", new XMIResourceFactoryImpl());
        register(rs, RolapMappingPackage.eINSTANCE);
        register(rs, org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalPackage.eINSTANCE);
        return rs;
    }

    private static void register(ResourceSet rs, EPackage p) {
        rs.getPackageRegistry().put(p.getNsURI(), p);
        p.getESubpackages().forEach(s -> register(rs, s));
    }
}
