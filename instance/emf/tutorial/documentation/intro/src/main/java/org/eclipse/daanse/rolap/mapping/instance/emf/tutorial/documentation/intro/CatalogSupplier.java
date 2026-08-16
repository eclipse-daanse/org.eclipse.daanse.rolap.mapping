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
package org.eclipse.daanse.rolap.mapping.instance.emf.tutorial.documentation.intro;

import org.eclipse.daanse.rolap.mapping.model.provider.util.Naming;
import java.util.List;

import org.eclipse.daanse.cwm.model.cwm.resource.relational.Column;
import org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory;
import org.eclipse.daanse.cwm.model.cwm.resource.relational.Schema;
import org.eclipse.daanse.cwm.model.cwm.resource.relational.Table;
import org.eclipse.daanse.cwm.model.cwm.resource.relational.util.SQLSimpleTypes;
import org.eclipse.daanse.rolap.mapping.instance.api.CatalogRef;
import org.eclipse.daanse.rolap.mapping.instance.api.DocSection;
import org.eclipse.daanse.rolap.mapping.instance.api.Kind;
import org.eclipse.daanse.rolap.mapping.instance.api.MappingInstance;
import org.eclipse.daanse.rolap.mapping.instance.api.Source;
import org.eclipse.daanse.rolap.mapping.instance.api.TutorialDescription;
import org.eclipse.daanse.rolap.mapping.instance.api.TutorialDescriptionSupplier;
import org.eclipse.daanse.rolap.mapping.model.catalog.Catalog;
import org.eclipse.daanse.rolap.mapping.model.catalog.CatalogFactory;
import org.eclipse.daanse.rolap.mapping.model.database.source.SourceFactory;
import org.eclipse.daanse.rolap.mapping.model.database.source.TableSource;
import org.eclipse.daanse.rolap.mapping.model.olap.cube.CubeFactory;
import org.eclipse.daanse.rolap.mapping.model.olap.cube.MeasureGroup;
import org.eclipse.daanse.rolap.mapping.model.olap.cube.PhysicalCube;
import org.eclipse.daanse.rolap.mapping.model.olap.cube.measure.MeasureFactory;
import org.eclipse.daanse.rolap.mapping.model.olap.cube.measure.SumMeasure;
import org.eclipse.daanse.rolap.mapping.model.provider.CatalogMappingSupplier;



import org.osgi.service.component.annotations.Component;

import org.eclipse.daanse.rolap.mapping.model.provider.util.CwmHelper;
import org.eclipse.daanse.cwm.model.cwm.foundation.businessinformation.BusinessinformationFactory;
import org.eclipse.daanse.cwm.model.cwm.foundation.businessinformation.Contact;
import org.eclipse.daanse.cwm.model.cwm.foundation.businessinformation.Document;
import org.eclipse.daanse.cwm.model.cwm.foundation.businessinformation.Email;
import org.eclipse.daanse.cwm.model.cwm.foundation.businessinformation.ResourceLocator;
import org.eclipse.daanse.cwm.model.cwm.foundation.businessinformation.ResponsibleParty;
import org.eclipse.daanse.cwm.model.cwm.foundation.businessinformation.util.Descriptions;
@MappingInstance(kind = Kind.TUTORIAL, number = "2.18.01", source = Source.EMF, group = "Documentation")
@Component(service = { CatalogMappingSupplier.class, TutorialDescriptionSupplier.class })
public class CatalogSupplier implements CatalogMappingSupplier, TutorialDescriptionSupplier {

    private Catalog catalog;
    private TableSource query;
    private Schema databaseSchema;

    public static final String CATALOG_NAME = "Daanse Tutorial - Documentation";
    public static final String CUBE_NAME = "DocumentedCube";
    public static final String MEASURE_NAME = "Measure-Sum";

    private static final String introBody = """
            Human-readable texts of catalog elements live in CWM businessinformation Descriptions:
            one Description per (element, type, language), owned by the nearest namespace of the
            element it describes and pointing back at it via its modelElement reference. The type
            'documentation' carries the general documentation text; the type
            'caption' carries localized display names with the element name as final fallback.
            Languages are canonical BCP-47 tags, the reserved tag 'und' marks language-neutral text.
            External documentation is linked with businessinformation Documents (a reference plus a
            type such as 'runbook'), not stored in the model.
            """;

    private static final String databaseSchemaBody = """
            The cube is based on a single table `Fact` with the columns `KEY` and `VALUE`. The
            table is a CWM namespace, so it owns the Descriptions of itself and of its columns:
            the caption of the `VALUE` column lives inside the table and references the column.
            """;

    private static final String cubeBody = """
            OLAP elements such as cubes are no namespaces; their ownership chain ends at the
            catalog, so the catalog owns their Descriptions. The business vocabulary uses
            businessnomenclature Glossaries by the same ownership rule: a glossary owned by
            the catalog defines terms (with definition and synonyms) and anchors them at
            elements — the glossary answers what an element means, captions answer how it
            is labelled per language. The cube here carries a
            language-neutral documentation text, German and English captions, and a runbook
            Document. Lookup shortens the requested language tag progressively (de-DE to de),
            falls back to 'und' and finally to the element name for captions. Governance
            metadata uses ResponsibleParty by the same ownership rule: the cube has an owner
            with an email contact and a steward — optional metadata for people and tools,
            never engine input.
            """;

    @Override
    public Catalog get() {
        databaseSchema = RelationalFactory.eINSTANCE.createSchema();

        Column keyColumn = RelationalFactory.eINSTANCE.createColumn();
        keyColumn.setName("KEY");
        keyColumn.setType(SQLSimpleTypes.Sql99.varcharType());

        Column valueColumn = RelationalFactory.eINSTANCE.createColumn();
        valueColumn.setName("VALUE");
        valueColumn.setType(SQLSimpleTypes.Sql99.integerType());

        Table table = RelationalFactory.eINSTANCE.createTable();
        table.setName("Fact");
        table.getFeature().addAll(List.of(keyColumn, valueColumn));
        databaseSchema.getOwnedElement().add(table);

        query = SourceFactory.eINSTANCE.createTableSource();
        query.setTable(table);

        SumMeasure measure = MeasureFactory.eINSTANCE.createSumMeasure();
        measure.setName(MEASURE_NAME);
        measure.setColumn(valueColumn);

        MeasureGroup measureGroup = CubeFactory.eINSTANCE.createMeasureGroup();
        measureGroup.getMeasures().add(measure);

        PhysicalCube cube = CubeFactory.eINSTANCE.createPhysicalCube();
        cube.setName(CUBE_NAME);
        cube.setSource(query);
        cube.getMeasureGroups().add(measureGroup);

        catalog = CatalogFactory.eINSTANCE.createCatalog();
        catalog.setName(CATALOG_NAME);
        catalog.getImportedElement().add(databaseSchema);
        catalog.getOwnedElement().addAll(List.of(query, cube));

        // The elements hang in their containment trees; now they can be described.
        // The cube's texts land in the catalog, the column's caption in its table.
        Descriptions.describe(cube, CwmHelper.TYPE_DOCUMENTATION, null,
                "Fact cube over the single-table star used by all documentation examples.");
        Descriptions.describe(cube, CwmHelper.TYPE_CAPTION, "de", "Dokumentierter Würfel");
        Descriptions.describe(cube, CwmHelper.TYPE_CAPTION, "en", "Documented Cube");
        Document runbook = BusinessinformationFactory.eINSTANCE.createDocument();
        runbook.setName(CUBE_NAME + "_" + CwmHelper.DOC_TYPE_RUNBOOK);
        runbook.setType(CwmHelper.DOC_TYPE_RUNBOOK);
        runbook.setReference("https://wiki.example.org/olap/documented-cube-runbook");
        runbook.getModelElement().add(cube);
        catalog.getOwnedElement().add(runbook);

        Descriptions.describe(table, CwmHelper.TYPE_DOCUMENTATION, null,
                "Single fact table; KEY discriminates, VALUE is aggregated.");
        Descriptions.describe(valueColumn, CwmHelper.TYPE_CAPTION, "de", "Betrag");
        Descriptions.describe(valueColumn, CwmHelper.TYPE_CAPTION, "en", "Amount");

        // Governance: responsible parties live by the same nearest-namespace rule.
        ResponsibleParty owner = BusinessinformationFactory.eINSTANCE.createResponsibleParty();
        owner.setName("BI Team");
        owner.setResponsibility(CwmHelper.ROLE_OWNER);
        owner.getModelElement().add(cube);
        catalog.getOwnedElement().add(owner);

        Email email = BusinessinformationFactory.eINSTANCE.createEmail();
        email.setName("bi-team@example.org");
        email.setEmailAddress("bi-team@example.org");
        email.setEmailType("business");
        ResourceLocator wiki = BusinessinformationFactory.eINSTANCE.createResourceLocator();
        wiki.setName("https://wiki.example.org/bi-team");
        wiki.setUrl("https://wiki.example.org/bi-team");
        Contact contact = BusinessinformationFactory.eINSTANCE.createContact();
        contact.setName("BI Team_contact");
        contact.getEmail().add(email);
        contact.getUrl().add(wiki);
        // contact parts are owned next to the party, reusable across parties
        catalog.getOwnedElement().addAll(List.of(email, wiki, contact));
        owner.getContact().add(contact);

        ResponsibleParty steward = BusinessinformationFactory.eINSTANCE.createResponsibleParty();
        steward.setName("Data Office");
        steward.setResponsibility(CwmHelper.ROLE_STEWARD);
        steward.getModelElement().add(cube);
        catalog.getOwnedElement().add(steward);

        Naming.complete(catalog);

        return catalog;
    }

    @Override
    public TutorialDescription describe() {
        return new TutorialDescription(
                List.of(
                        new DocSection(CATALOG_NAME, introBody, 1, 0, 0, null, 0),
                        new DocSection("Database Schema", databaseSchemaBody, 1, 1, 0, databaseSchema, 3),
                        new DocSection("Cube", cubeBody, 1, 2, 0, query, 2)),
                List.of(new CatalogRef("catalog", this::get)));
    }
}
