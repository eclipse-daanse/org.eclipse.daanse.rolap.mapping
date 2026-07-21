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
package org.eclipse.daanse.rolap.mapping.instance.emf.complex.accountingonecube;

import java.util.List;

import org.eclipse.daanse.cwm.model.cwm.resource.relational.Column;
import org.eclipse.daanse.cwm.model.cwm.resource.relational.Schema;
import org.eclipse.daanse.cwm.model.cwm.resource.relational.Table;
import org.eclipse.daanse.cwm.model.cwm.resource.relational.util.SqlSimpleTypes;

import org.eclipse.daanse.rolap.mapping.instance.api.CatalogRef;
import org.eclipse.daanse.rolap.mapping.instance.api.DocSection;
import org.eclipse.daanse.rolap.mapping.instance.api.Kind;
import org.eclipse.daanse.rolap.mapping.instance.api.MappingInstance;
import org.eclipse.daanse.rolap.mapping.instance.api.Source;
import org.eclipse.daanse.rolap.mapping.instance.api.TutorialDescription;
import org.eclipse.daanse.rolap.mapping.instance.api.TutorialDescriptionSupplier;

import org.eclipse.daanse.rolap.mapping.model.access.common.AccessCatalogGrant;
import org.eclipse.daanse.rolap.mapping.model.access.common.AccessRole;
import org.eclipse.daanse.rolap.mapping.model.access.common.CatalogAccess;
import org.eclipse.daanse.rolap.mapping.model.access.common.CommonFactory;
import org.eclipse.daanse.rolap.mapping.model.access.database.AccessDatabaseSchemaGrant;
import org.eclipse.daanse.rolap.mapping.model.access.database.AccessTableGrant;
import org.eclipse.daanse.rolap.mapping.model.access.database.DatabaseFactory;
import org.eclipse.daanse.rolap.mapping.model.access.database.DatabaseSchemaAccess;
import org.eclipse.daanse.rolap.mapping.model.access.database.TableAccess;
import org.eclipse.daanse.rolap.mapping.model.access.olap.AccessCubeGrant;
import org.eclipse.daanse.rolap.mapping.model.access.olap.AccessHierarchyGrant;
import org.eclipse.daanse.rolap.mapping.model.access.olap.AccessMemberGrant;
import org.eclipse.daanse.rolap.mapping.model.access.olap.CubeAccess;
import org.eclipse.daanse.rolap.mapping.model.access.olap.HierarchyAccess;
import org.eclipse.daanse.rolap.mapping.model.access.olap.MemberAccess;
import org.eclipse.daanse.rolap.mapping.model.access.olap.OlapFactory;

import org.eclipse.daanse.rolap.mapping.model.catalog.Catalog;
import org.eclipse.daanse.rolap.mapping.model.catalog.CatalogFactory;

import org.eclipse.daanse.rolap.mapping.model.database.relational.OrderedColumn;
import org.eclipse.daanse.rolap.mapping.model.database.relational.RelationalFactory;
import org.eclipse.daanse.rolap.mapping.model.database.source.SourceFactory;
import org.eclipse.daanse.rolap.mapping.model.database.source.TableSource;
import org.eclipse.daanse.rolap.mapping.model.database.writeback.WritebackAttribute;
import org.eclipse.daanse.rolap.mapping.model.database.writeback.WritebackFactory;
import org.eclipse.daanse.rolap.mapping.model.olap.cube.measure.WritebackMeasure;
import org.eclipse.daanse.rolap.mapping.model.database.writeback.WritebackTable;

import org.eclipse.daanse.rolap.mapping.model.olap.cube.CubeFactory;
import org.eclipse.daanse.rolap.mapping.model.olap.cube.Kpi;
import org.eclipse.daanse.rolap.mapping.model.olap.cube.MeasureGroup;
import org.eclipse.daanse.rolap.mapping.model.olap.cube.PhysicalCube;
import org.eclipse.daanse.rolap.mapping.model.olap.cube.measure.MeasureFactory;
import org.eclipse.daanse.rolap.mapping.model.olap.cube.measure.SumMeasure;
import org.eclipse.daanse.rolap.mapping.model.olap.cube.measure.TextAggMeasure;

import org.eclipse.daanse.rolap.mapping.model.olap.dimension.DimensionConnector;
import org.eclipse.daanse.rolap.mapping.model.olap.dimension.DimensionFactory;
import org.eclipse.daanse.rolap.mapping.model.olap.dimension.NamedSet;
import org.eclipse.daanse.rolap.mapping.model.olap.dimension.StandardDimension;
import org.eclipse.daanse.rolap.mapping.model.olap.dimension.TimeDimension;
import org.eclipse.daanse.rolap.mapping.model.olap.dimension.hierarchy.ExplicitHierarchy;
import org.eclipse.daanse.rolap.mapping.model.olap.dimension.hierarchy.HierarchyFactory;
import org.eclipse.daanse.rolap.mapping.model.olap.dimension.hierarchy.RollupPolicy;
import org.eclipse.daanse.rolap.mapping.model.olap.dimension.hierarchy.level.CalculatedMember;
import org.eclipse.daanse.rolap.mapping.model.olap.dimension.hierarchy.level.CalculatedMemberProperty;
import org.eclipse.daanse.rolap.mapping.model.olap.dimension.hierarchy.level.Level;
import org.eclipse.daanse.rolap.mapping.model.olap.dimension.hierarchy.level.LevelDefinition;
import org.eclipse.daanse.rolap.mapping.model.olap.dimension.hierarchy.level.LevelFactory;

import org.eclipse.daanse.rolap.mapping.model.provider.CatalogMappingSupplier;

import org.osgi.service.component.annotations.Component;

@MappingInstance(kind = Kind.COMPLEX, source = Source.EMF, number = "99.1.8", group = "Full Examples")
@Component(service = { CatalogMappingSupplier.class, TutorialDescriptionSupplier.class })
public class CatalogSupplier implements CatalogMappingSupplier, TutorialDescriptionSupplier {

    private static final String CATALOG_NAME = "Accounting";
    private static final String CUBE_NAME = "Accounting";

    private static final String TABLE_BOOKING = "BOOKING";
    private static final String TABLE_BOOKINGWB = "BOOKINGWB";
    private static final String TABLE_ACCOUNT = "ACCOUNT";
    private static final String TABLE_YEAR = "YEAR";
    private static final String TABLE_ORGUNIT = "ORGUNIT";

    private static final String DIM_YEAR = "Year";
    private static final String DIM_ACCOUNT = "Account";
    private static final String DIM_ORGUNIT = "OrgUnit";

    private static final String HIGHEST_YEAR = "2027";

    /**
     * Fully-qualified unique member name for {@code setDefaultMember}. daanse
     * resolves these against the level's {@code nameColumn}, not its key column,
     * so the trailing segment must be the displayed name, not the database KEY.
     */
    private static final String DEFAULT_YEAR_MEMBER = "[Year].[Year].[" + HIGHEST_YEAR + "]";

    private static final String CURRENCY_FORMAT = "#,##0 €";
    private static final String PERCENT_FORMAT = "#,##0.00%";

    /**
     * MDX-standard "section" syntax: positive;negative. Negative values render with
     * the literal `[Red]` token, which daanse and most other engines map to red
     * foreground.
     */
    private static final String VARIANCE_FORMAT = "#,##0 €;[Red]-#,##0 €";
    private static final String VARIANCE_PCT_FORMAT = "#,##0.00%;[Red]-#,##0.00%";

    private Catalog catalog;
    private Schema databaseSchema;

    private PhysicalCube cube;
    private WritebackTable writebackTable;
    private Table writebackPhysicalTable;

    private TableSource bookingSource;
    private TableSource accountSource;
    private TableSource yearSource;
    private TableSource orgUnitSource;

    private TimeDimension yearDimension;
    private StandardDimension accountDimension;
    private StandardDimension orgUnitDimension;

    private ExplicitHierarchy yearHierarchy;
    private ExplicitHierarchy accountHierarchy;
    private ExplicitHierarchy orgUnitHierarchy;
    private Level orgUnitLevelL1;
    private Level orgUnitLevelL3;

    private SumMeasure amountIstMeasure;
    private SumMeasure amountPlanMeasure;
    private TextAggMeasure commentsMeasure;
    private CalculatedMember varianceMember;
    private CalculatedMember variancePctMember;
    // KPI and named sets temporarily disabled — the KPI status/value formulas
    // reference measures that are not yet resolvable in the current daanse
    // engine, and the named-set Descendants() formulas need adjusting now that
    // the Account hierarchy is a three-level explicit hierarchy
    // (Category → Group → Account) instead of the original parent-child shape.
    // Re-enable once the formulas parse cleanly.
    // private Kpi budgetUtilizationKpi;
    // private NamedSet topExpenseAccountsSet;
    // private NamedSet planOverrunSet;
    // private NamedSet accountsWithoutCommentSet;

    private AccessRole roleDeptA1;
    private AccessRole roleDeptA2;
    private AccessRole roleDeptB1;
    private AccessRole roleDivisionA;
    private AccessRole roleAccounting;
    private AccessRole roleReadonly;

    private static final String catalogBody = """
            The `Accounting` catalog is a realistic financial-controlling example. It is
            intentionally small enough to read end-to-end yet covers most modeling
            features a real bookkeeping cube needs.

            Both actual postings (`IST`) and planned postings (`PLAN`) are recorded into
            the *same* fact table `BOOKING`, exposed as two separate `SumMeasure`s with
            a currency format string. A third measure aggregates the per-booking
            free-text `COMMENT` via a `TextAggMeasure`.

            The cube further demonstrates:

            - **Three-level `Account` (Sachkonto) dimension** — a snowflake-free
              `ExplicitHierarchy` on a single denormalised table (`Category` →
              `Group` → `Account`). For a worked example of writeback against a
              true parent-child hierarchy see `tutorial.writeback.parentchild`.
            - **`Year` time dimension** with `TIME_YEARS` semantics and a
              `defaultMember` pinned to the highest year (`2027`).
            - **Three-level `OrgUnit` dimension** on a single denormalised table; access
              rights are anchored at the lowest level.
            - **Currency-formatted measures** (`#,##0 €`) — `AmountIst` and
              `AmountPlan` render as `1.234.567 €` in tools that respect format strings.
            - **`Variance` and `VariancePct` calculated members** — IST vs. PLAN gap
              in currency and percent, with `iif` guarding division by zero. Both
              members carry a MDX-section format string that paints negative values
              red (`[Red]` token), demonstrating conditional cell coloring.

            *Note:* a `BudgetUtilization` KPI and three named sets
            (`Top5ExpenseAccounts`, `PlanOverrun`, `AccountsWithoutComment`) are
            kept in the source code as commented-out blocks. They are temporarily
            disabled because their MDX formulas need adjusting to the new
            three-level `Account` hierarchy (Category → Group → Account).
            Re-enable them once the formulas resolve cleanly.
            - **`BOOKINGWB` writeback table** — actual, plan amounts and comments can be
              entered per org-unit, account and year.
            - **Six access roles** — one per leaf org unit, one whole-division role, a
              full-access accounting role, and a read-only role that explicitly hides
              the writeback table at the schema level.
            """;

    private static final String databaseSchemaBody = """
            The database schema contains the following tables:

            - **`BOOKING`** (fact) — `BOOKING_ID`, `YEAR_KEY`, `ACCOUNT_KEY`,
              `ORG_UNIT_KEY`, `AMOUNT_IST`, `AMOUNT_PLAN`, `COMMENT`. Both IST and
              PLAN amounts live in the same row and are exposed as two separate
              measures. The `COMMENT` column feeds the text-aggregator measure.
            - **`BOOKINGWB`** (writeback target) — same dimensional key columns as
              `BOOKING`, plus `ID` and `USER` for audit, plus `AMOUNT_IST`,
              `AMOUNT_PLAN` and `COMMENT`.
            - **`ACCOUNT`** — single denormalised table with three level keys
              and names: `L1_KEY`/`L1_NAME` (Category, e.g. `EXPENSES`),
              `L2_KEY`/`L2_NAME` (Group, e.g. `PERSONNEL`), `L3_KEY`/`L3_NAME`
              (leaf account, e.g. `SALARIES`). The fact table joins on
              `BOOKING.ACCOUNT_KEY = ACCOUNT.L3_KEY`.
            - **`YEAR`** — `YEAR_KEY`, `YEAR_NAME` (one row per business year).
            - **`ORGUNIT`** — single denormalised table with three level keys and
              names: `L1_KEY`/`L1_NAME`, `L2_KEY`/`L2_NAME`, `L3_KEY`/`L3_NAME`. The
              fact table joins on the lowest level (`L3_KEY`) via `ORG_UNIT_KEY`.
            """;

    private static final String factQueryBody = """
            The fact `TableSource` reads all columns of the `BOOKING` table. Both the
            sum measures and the text-aggregation measure source their columns from
            this query.
            """;

    private static final String accountDimensionBody = """
            The `Account` (Sachkonto) dimension is a 3-level `ExplicitHierarchy`
            on a single denormalised `ACCOUNT` table (same snowflake-free
            pattern as `OrgUnit`). The levels are:

            - **`Category`** (L1) — e.g. `Expenses`, `Revenue`.
            - **`Group`** (L2) — e.g. `Personnel`, `Rent`, `Travel`, `Sales`.
            - **`Account`** (L3, leaf) — e.g. `Salaries`, `Office Rent`,
              `Flights`, `Product Sales`.

            The fact table joins on the leaf level via
            `BOOKING.ACCOUNT_KEY = ACCOUNT.L3_KEY`. Aggregations across an L2
            or L1 member roll up the underlying leaves through the usual SQL
            `GROUP BY` path.

            *Note:* if the accounting domain calls for a variable-depth tree,
            switch this dimension to a `ParentChildHierarchy` — the
            `tutorial.writeback.parentchild` example demonstrates that
            variant together with writeback.
            """;

    private static final String yearDimensionBody = """
            `Year` is modelled as a `TimeDimension` so that MDX time-functions like
            `Lag`, `ParallelPeriod` and `YTD` can be used against it. The single
            `Year` level is declared with `LevelDefinition.TIME_YEARS`.

            The hierarchy's `defaultMember` is set to the fully-qualified unique
            member name `[Year].[Year].[2027]` — the highest year in the data — so
            that, unless the user picks another year, all queries implicitly run
            against the most recent year. The unique-name form is required;
            passing the bare key (`"2027"`) would fail at cube initialisation with
            `Can not find Default Member`.
            """;

    private static final String orgUnitDimensionBody = """
            `OrgUnit` is a three-level explicit hierarchy that lives on a single
            denormalised table. The three levels are `L1` (e.g. the company), `L2`
            (e.g. division) and `L3` (e.g. department). The fact table joins on
            `ORG_UNIT_KEY = ORGUNIT.L3_KEY`, i.e. on the lowest level — that is the
            level at which postings happen.

            Access rights are also pinned to the lowest level: there is one role per
            `L3` member that grants `MemberAccess.ALL` on that member only (its
            ancestors stay visible via `RollupPolicy.FULL`).
            """;

    private static final String measuresBody = """
            Three stored measures are exposed by the cube:

            - **`AmountIst`** — `SumMeasure` over the `AMOUNT_IST` column. Read-only.
              `formatString = "#,##0 €"` so values render as currency in client tools
              that honour MDX format strings.
            - **`AmountPlan`** — `SumMeasure` over the `AMOUNT_PLAN` column. Same
              currency format. Writeable via the writeback table.
            - **`Comments`** — `TextAggMeasure` over the `COMMENT` column, with
              separator `" | "` and ordering by the comment text. Aggregating
              free-text comments across slicer selections is how the cube exposes the
              per-cell notes to the user — every slice produces a single, readable
              string concatenating all matching comments.

            Two **calculated members** complement the stored measures (see the
            "Calculated Members" section below). One **KPI** wraps the planning ratio
            (see the "KPI" section), and one **named set** picks the top expense
            accounts (see the "Named Set" section).
            """;

    private static final String currencyFormatBody = """
            `AmountIst` and `AmountPlan` are declared with `formatString = "#,##0 €"`.
            The format string follows the standard MDX/OLE-DB syntax:

            - `#` — optional digit
            - `,` — thousands separator
            - `0` — required digit
            - trailing literal `€`

            Client tools (Saiku, Excel, Power BI, the daanse front-end, …) that respect
            format strings will display `1234567` as `1.234.567 €` (locale-dependent
            grouping). The calculated member `Variance` reuses the same format string
            via a `CalculatedMemberProperty` named `FORMAT_STRING`; `VariancePct` uses
            `"#,##0.00%"` to render percentages.
            """;

    private static final String calculatedMembersBody = """
            Two calculated members compare IST against PLAN:

            - **`Variance`** — `[Measures].[AmountIst] - [Measures].[AmountPlan]`.
              Negative values indicate plan overrun, positive values indicate plan
              underrun.
            - **`VariancePct`** — the relative version,
              `(IST - PLAN) / PLAN`, wrapped in
              `iif([Measures].[AmountPlan] = 0, NULL, …)` so that cells without a plan
              return `NULL` instead of `#DIV/0!`.

            **Color formatting (negative-in-red).** Both members carry a
            `FORMAT_STRING` property whose value uses the MDX "section" syntax
            `positive;negative`. Each section can prefix the format with the literal
            `[Red]` (or any colour name the engine recognises) to colour matching
            values:

            ```
            Variance     →  "#,##0 €;[Red]-#,##0 €"
            VariancePct  →  "#,##0.00%;[Red]-#,##0.00%"
            ```

            **Alternative: daanse-style `BACK_COLOR` / `FORE_COLOR` properties.**
            Instead of (or in addition to) the `[Red]` token, daanse recognises
            `BACK_COLOR=<rgb-int>` and `FORE_COLOR=<rgb-int>` properties appended to
            the format string. Examples (see
            `tutorial/cube/calculatedmember.color`):

            ```
            "$#,##0.00;BACK_COLOR=32768;FORE_COLOR=0"     // green background, black text
            "$#,##0.00;BACK_COLOR=16711680;FORE_COLOR=0"  // red background, black text
            ```

            The `[Red]` syntax is the portable MDX standard and is honoured by most
            front-ends; the `BACK_COLOR=` properties are a daanse-specific
            extension that pivot tools render as cell shading.

            Calculated members are attached to the cube via
            `cube.getCalculatedMembers().add(...)`. They behave exactly like stored
            measures in MDX but are evaluated on the fly — no fact-table column is
            required.
            """;

    private static final String kpiBody = """
            The `BudgetUtilization` KPI wraps "what fraction of the plan has actually
            been spent" into one model element that KPI-aware clients can render with
            a status indicator and a trend arrow.

            - **`value`** — `IST / PLAN`, guarded by `iif` against a zero plan.
            - **`goal`** — the literal `1.0` (100 % consumed = exactly on plan).
            - **`status`** — returns `1` (good, traffic light green) when utilisation
              is ≤ 90 %, `0` (warning, yellow) when between 90 % and 100 %, and `-1`
              (bad, red) when the plan is over-consumed. Pure MDX, no separate measure
              required.
            - **`trend`** — points at `[Measures].[Variance]` so KPI-aware clients can
              show the absolute over/under-spending alongside the ratio.
            - **`displayFolder`** — `"KPIs"` keeps it grouped in the client's folder
              view.
            - **`statusGraphic`** — `"Traffic Light"`.
            - **`trendGraphic`** — `"Standard Arrow"`.

            KPIs attach to the cube via `cube.getKpis().add(...)`.
            """;

    private static final String namedSetBody = """
            Three named sets ship with the cube, grouped under the `"Analysis"`
            display folder:

            **`Top5ExpenseAccounts`** — five most expensive leaf accounts under the
            `EXPENSES` root, ranked by `AmountIst`:

            ```mdx
            TopCount(
                Descendants([Account].[Account].[EXPENSES], [Account].[Account]),
                5,
                [Measures].[AmountIst])
            ```

            **`PlanOverrun`** — every account whose actual spending exceeds its plan
            in the current slicer:

            ```mdx
            Filter(
                Descendants([Account].[Account].[All Accounts], [Account].[Account]),
                [Measures].[AmountIst] > [Measures].[AmountPlan])
            ```

            Because named-set formulas are re-evaluated against the current slicer,
            picking a different `Year` or `OrgUnit` automatically refreshes
            the list of overrun accounts.

            **`AccountsWithoutComment`** — accounts where no booking carried a
            comment. Useful for "did the controller forget to annotate?" reports:

            ```mdx
            Filter(
                Descendants([Account].[Account].[All Accounts], [Account].[Account]),
                IsEmpty([Measures].[Comments]))
            ```

            `IsEmpty` returns true when the `TextAggMeasure` produced no aggregated
            text for the cell.

            All three sets attach to the cube via `cube.getNamedSets().addAll(...)`.
            Clients can reference them in MDX as `[Top5ExpenseAccounts]`,
            `[PlanOverrun]` and `[AccountsWithoutComment]`.
            """;

    private static final String writebackBody = """
            The `BOOKINGWB` writeback table lets users enter planned amounts and free
            comments while preserving IST data as read-only.

            For every dimensional foreign key on the fact, the writeback table has a
            matching column wired through a `WritebackAttribute`:

            | Cube dimension | Writeback column |
            |---|---|
            | `Year`    | `YEAR_KEY` |
            | `Account` | `ACCOUNT_KEY` |
            | `OrgUnit` | `ORG_UNIT_KEY` |

            Three `WritebackMeasure` entries describe the writeable measures:

            - `AmountIst`  → `AMOUNT_IST`
            - `AmountPlan` → `AMOUNT_PLAN`
            - `Comments`   → `COMMENT`

            The extra `ID` and `USER` columns are technical bookkeeping columns that
            the writeback engine populates with the row id and the current user.

            **Note on the model package.** `WritebackMeasure` lives in the
            `olap/cube/measure/` ecore package alongside `SumMeasure`,
            `TextAggMeasure` and the other base measures — it is conceptually a
            measure (named by its logical cube-measure name, paired with a
            database column for persistence). Only the writeback *infrastructure*
            (`WritebackTable`, `WritebackAttribute`) remains in
            `database/writeback/`. In Java that means
            `MeasureFactory.eINSTANCE.createWritebackMeasure()` (not
            `WritebackFactory`), and the XMI tag carries the `rolapmeas:`
            namespace prefix.

            **`Comments` writeback specifically.** Because `Comments` is a
            `TextAggMeasure`, the daanse runtime detects its character bind type
            automatically (no extra declaration on the model). The
            `[Measures].[Comments]` cell is written as a single row at the
            cell's exact coordinates — allocation is skipped — and the
            read-side `ListAggAggregator` aggregates the new comment alongside
            any fact-table comments via the standard SQL `LISTAGG` path.
            """;

    private static final String rolesBody = """
            Six roles cover the typical org-rights matrix:

            - **`role_dept_A1`**, **`role_dept_A2`**, **`role_dept_B1`** — one role
              per L3 org unit ("I am a department head, I only see my own
              department"). Each role grants:
                - `CatalogAccess.ALL_DIMENSIONS` on the catalog,
                - `DatabaseSchemaAccess.ALL` on the database schema,
                - `CubeAccess.ALL` on the `Accounting` cube,
                - `HierarchyAccess.CUSTOM` on the `OrgUnit` hierarchy with
                  `RollupPolicy.FULL`, `topLevel = bottomLevel = L3`, and one
                  `AccessMemberGrant` granting `MemberAccess.ALL` on the matching
                  L3 member only.
              `RollupPolicy.FULL` keeps the parent (`L1`/`L2`) totals visible while
              hiding sibling departments.
            - **`role_division_A`** — whole-subtree access ("I am the head of
              Division A, I see Division A and all its departments"). The
              `AccessMemberGrant` targets the L2 member
              `[OrgUnit].[OrgUnit].[Company].[Division A]`, which automatically includes
              its descendants. `topLevel = L1` and `bottomLevel = L3` keep all three
              levels navigable.
            - **`role_accounting`** — central accounting role with full access to the
              catalog, schema and cube. No hierarchy restriction.
            - **`role_readonly`** — full read access on the cube, but the writeback
              table `BOOKINGWB` is hidden via an `AccessTableGrant(TableAccess.NONE)`
              wrapped inside an `AccessDatabaseSchemaGrant(DatabaseSchemaAccess.CUSTOM)`.
              The access model does not have a dedicated "writeback" flag; restricting
              the underlying physical table is the recommended workaround and is the
              pattern the application layer can use to disable the writeback UI.
            """;

    private static final String divisionRoleBody = """
            `role_division_A` demonstrates **whole-subtree access**: a division head
            should see their division plus every department in it, but not other
            divisions.

            The trick is that `AccessMemberGrant` with `MemberAccess.ALL` on a
            non-leaf member implicitly grants access to *all descendants* of that
            member. So a single grant on
            `[OrgUnit].[OrgUnit].[Company].[Division A]` covers both
            `[Department A1]` and `[Department A2]`. Setting `topLevel = L1` and
            `bottomLevel = L3` on the `AccessHierarchyGrant` keeps all three levels
            navigable so the user can still drill from `[Company]` down to
            `[Division A]` down to its departments.

            **Important:** member names are taken from the level's `nameColumn` (the
            display value, e.g. `"Department A1"`), not from the database `KEY`
            column (e.g. `"DEPT_A1"`). Passing the bare key in the unique-name path
            would fail with `MemberNotFoundException`.
            """;

    private static final String readonlyRoleBody = """
            The access model has no dedicated "writeback allowed/denied" attribute.
            To express *"can read everything, cannot write back"* the cleanest
            pattern is:

            1. Grant the cube with `CubeAccess.ALL` (full read).
            2. Grant the database schema with `DatabaseSchemaAccess.CUSTOM` and add a
               single `AccessTableGrant(TableAccess.NONE)` for the `BOOKINGWB`
               physical table.

            Hiding the writeback target at the schema level is what the application
            layer can detect to disable any "save" / "submit plan" UI. Because the
            grant only touches the writeback table, all other tables (the fact, the
            dimensions) remain accessible.
            """;

    private static final String cubeBody = """
            The `Accounting` physical cube binds everything together. It uses the
            `BOOKING` fact `TableSource`, declares one `DimensionConnector` per
            dimension (each with its own `ForeignKey` column on the fact), groups the
            three measures `AmountIst`, `AmountPlan` and `Comments` in a single
            `MeasureGroup`, and references the `BOOKINGWB` `WritebackTable` so plan
            data and comments can be entered.
            """;

    @Override
    public Catalog get() {
        if (catalog != null) {
            return catalog;
        }

        databaseSchema = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE
                .createSchema();

        Column bookingIdColumn = createColumn("BOOKING_ID", SqlSimpleTypes.Sql99.integerType());
        Column bookingYearKeyColumn = createColumn("YEAR_KEY", SqlSimpleTypes.Sql99.integerType());
        Column bookingAccountKeyColumn = createColumn("ACCOUNT_KEY", SqlSimpleTypes.Sql99.varcharType());
        Column bookingOrgUnitKeyColumn = createColumn("ORG_UNIT_KEY", SqlSimpleTypes.Sql99.varcharType());
        Column bookingAmountIstColumn = createColumn("AMOUNT_IST", SqlSimpleTypes.Sql99.integerType());
        Column bookingAmountPlanColumn = createColumn("AMOUNT_PLAN", SqlSimpleTypes.Sql99.integerType());
        Column bookingCommentColumn = createColumn("COMMENT", SqlSimpleTypes.Sql99.varcharType());

        Table bookingTable = createTable(TABLE_BOOKING,
                List.of(bookingIdColumn, bookingYearKeyColumn, bookingAccountKeyColumn,
                        bookingOrgUnitKeyColumn, bookingAmountIstColumn,
                        bookingAmountPlanColumn, bookingCommentColumn));
        databaseSchema.getOwnedElement().add(bookingTable);

        Column wbIdColumn = createColumn("ID", SqlSimpleTypes.Sql99.varcharType());
        Column wbUserColumn = createColumn("USER", SqlSimpleTypes.Sql99.varcharType());
        Column wbYearKeyColumn = createColumn("YEAR_KEY", SqlSimpleTypes.Sql99.integerType());
        Column wbAccountKeyColumn = createColumn("ACCOUNT_KEY", SqlSimpleTypes.Sql99.varcharType());
        Column wbOrgUnitKeyColumn = createColumn("ORG_UNIT_KEY", SqlSimpleTypes.Sql99.varcharType());
        Column wbBookingAmountIstColumn = createColumn("AMOUNT_IST", SqlSimpleTypes.Sql99.integerType());
        Column wbAmountIstColumn = createColumn("AMOUNT_IST", SqlSimpleTypes.Sql99.integerType());
        Column wbAmountPlanColumn = createColumn("AMOUNT_PLAN", SqlSimpleTypes.Sql99.integerType());
        Column wbCommentColumn = createColumn("COMMENT", SqlSimpleTypes.Sql99.varcharType());

        writebackPhysicalTable = createTable(TABLE_BOOKINGWB,
                List.of(wbIdColumn, wbUserColumn, wbYearKeyColumn, wbAccountKeyColumn,
                        wbOrgUnitKeyColumn, wbBookingAmountIstColumn, wbAmountPlanColumn, wbCommentColumn));
        databaseSchema.getOwnedElement().add(writebackPhysicalTable);

        // Account dimension is a 3-level explicit hierarchy on a single
        // denormalised table (same shape as ORGUNIT). L1 is the top
        // category (EXPENSES / REVENUE), L2 the account group
        // (PERSONNEL / RENT / ...) and L3 the leaf ledger account.
        Column accountL1KeyColumn = createColumn("L1_KEY", SqlSimpleTypes.Sql99.varcharType());
        Column accountL1NameColumn = createColumn("L1_NAME", SqlSimpleTypes.Sql99.varcharType());
        Column accountL2KeyColumn = createColumn("L2_KEY", SqlSimpleTypes.Sql99.varcharType());
        Column accountL2NameColumn = createColumn("L2_NAME", SqlSimpleTypes.Sql99.varcharType());
        Column accountL3KeyColumn = createColumn("L3_KEY", SqlSimpleTypes.Sql99.varcharType());
        Column accountL3NameColumn = createColumn("L3_NAME", SqlSimpleTypes.Sql99.varcharType());

        Table accountTable = createTable(TABLE_ACCOUNT,
                List.of(accountL1KeyColumn, accountL1NameColumn,
                        accountL2KeyColumn, accountL2NameColumn,
                        accountL3KeyColumn, accountL3NameColumn));
        databaseSchema.getOwnedElement().add(accountTable);

        Column yearKeyColumn = createColumn("YEAR_KEY", SqlSimpleTypes.Sql99.integerType());
        Column yearNameColumn = createColumn("YEAR_NAME", SqlSimpleTypes.Sql99.varcharType());

        Table yearTable = createTable(TABLE_YEAR, List.of(yearKeyColumn, yearNameColumn));
        databaseSchema.getOwnedElement().add(yearTable);

        Column ouL1KeyColumn = createColumn("L1_KEY", SqlSimpleTypes.Sql99.varcharType());
        Column ouL1NameColumn = createColumn("L1_NAME", SqlSimpleTypes.Sql99.varcharType());
        Column ouL2KeyColumn = createColumn("L2_KEY", SqlSimpleTypes.Sql99.varcharType());
        Column ouL2NameColumn = createColumn("L2_NAME", SqlSimpleTypes.Sql99.varcharType());
        Column ouL3KeyColumn = createColumn("L3_KEY", SqlSimpleTypes.Sql99.varcharType());
        Column ouL3NameColumn = createColumn("L3_NAME", SqlSimpleTypes.Sql99.varcharType());

        Table orgUnitTable = createTable(TABLE_ORGUNIT,
                List.of(ouL1KeyColumn, ouL1NameColumn, ouL2KeyColumn, ouL2NameColumn, ouL3KeyColumn, ouL3NameColumn));
        databaseSchema.getOwnedElement().add(orgUnitTable);

        bookingSource = SourceFactory.eINSTANCE.createTableSource();
        bookingSource.setTable(bookingTable);

        accountSource = SourceFactory.eINSTANCE.createTableSource();
        accountSource.setTable(accountTable);

        yearSource = SourceFactory.eINSTANCE.createTableSource();
        yearSource.setTable(yearTable);

        orgUnitSource = SourceFactory.eINSTANCE.createTableSource();
        orgUnitSource.setTable(orgUnitTable);

        Level yearLevel = LevelFactory.eINSTANCE.createLevel();
        yearLevel.setName("Year");
        yearLevel.setType(LevelDefinition.TIME_YEARS);
        yearLevel.setColumn(yearKeyColumn);
        yearLevel.setNameColumn(yearNameColumn);
        yearLevel.setUniqueMembers(true);

        yearHierarchy = HierarchyFactory.eINSTANCE.createExplicitHierarchy();
        yearHierarchy.setName(DIM_YEAR);
        yearHierarchy.setHasAll(true);
        yearHierarchy.setAllMemberName("All Years");
        yearHierarchy.setPrimaryKey(yearKeyColumn);
        yearHierarchy.setSource(yearSource);
        yearHierarchy.setDefaultMember(DEFAULT_YEAR_MEMBER);
        yearHierarchy.getLevels().add(yearLevel);

        yearDimension = DimensionFactory.eINSTANCE.createTimeDimension();
        yearDimension.setName(DIM_YEAR);
        yearDimension.getHierarchies().add(yearHierarchy);

        Level accountLevelL1 = LevelFactory.eINSTANCE.createLevel();
        accountLevelL1.setName("Category");
        accountLevelL1.setColumn(accountL1KeyColumn);
        accountLevelL1.setNameColumn(accountL1NameColumn);
        accountLevelL1.setUniqueMembers(true);

        Level accountLevelL2 = LevelFactory.eINSTANCE.createLevel();
        accountLevelL2.setName("Group");
        accountLevelL2.setColumn(accountL2KeyColumn);
        accountLevelL2.setNameColumn(accountL2NameColumn);
        accountLevelL2.setUniqueMembers(false);

        Level accountLevelL3 = LevelFactory.eINSTANCE.createLevel();
        accountLevelL3.setName("Account");
        accountLevelL3.setColumn(accountL3KeyColumn);
        accountLevelL3.setNameColumn(accountL3NameColumn);
        accountLevelL3.setUniqueMembers(false);

        accountHierarchy = HierarchyFactory.eINSTANCE.createExplicitHierarchy();
        accountHierarchy.setName(DIM_ACCOUNT);
        accountHierarchy.setHasAll(true);
        accountHierarchy.setAllMemberName("All Accounts");
        accountHierarchy.setPrimaryKey(accountL3KeyColumn);
        accountHierarchy.setSource(accountSource);
        accountHierarchy.getLevels().addAll(List.of(accountLevelL1, accountLevelL2, accountLevelL3));

        accountDimension = DimensionFactory.eINSTANCE.createStandardDimension();
        accountDimension.setName(DIM_ACCOUNT);
        accountDimension.getHierarchies().add(accountHierarchy);

        orgUnitLevelL1 = LevelFactory.eINSTANCE.createLevel();
        orgUnitLevelL1.setName("L1");
        orgUnitLevelL1.setColumn(ouL1KeyColumn);
        orgUnitLevelL1.setNameColumn(ouL1NameColumn);
        orgUnitLevelL1.setUniqueMembers(true);

        Level orgUnitLevelL2 = LevelFactory.eINSTANCE.createLevel();
        orgUnitLevelL2.setName("L2");
        orgUnitLevelL2.setColumn(ouL2KeyColumn);
        orgUnitLevelL2.setNameColumn(ouL2NameColumn);
        orgUnitLevelL2.setUniqueMembers(false);

        orgUnitLevelL3 = LevelFactory.eINSTANCE.createLevel();
        orgUnitLevelL3.setName("L3");
        orgUnitLevelL3.setColumn(ouL3KeyColumn);
        orgUnitLevelL3.setNameColumn(ouL3NameColumn);
        orgUnitLevelL3.setUniqueMembers(false);

        orgUnitHierarchy = HierarchyFactory.eINSTANCE.createExplicitHierarchy();
        orgUnitHierarchy.setName(DIM_ORGUNIT);
        orgUnitHierarchy.setHasAll(true);
        orgUnitHierarchy.setAllMemberName("All Org Units");
        orgUnitHierarchy.setPrimaryKey(ouL3KeyColumn);
        orgUnitHierarchy.setSource(orgUnitSource);
        orgUnitHierarchy.getLevels().addAll(List.of(orgUnitLevelL1, orgUnitLevelL2, orgUnitLevelL3));

        orgUnitDimension = DimensionFactory.eINSTANCE.createStandardDimension();
        orgUnitDimension.setName(DIM_ORGUNIT);
        orgUnitDimension.getHierarchies().add(orgUnitHierarchy);

        amountIstMeasure = MeasureFactory.eINSTANCE.createSumMeasure();
        amountIstMeasure.setName("AmountIst");
        amountIstMeasure.setColumn(bookingAmountIstColumn);
        amountIstMeasure.setFormatString(CURRENCY_FORMAT);

        amountPlanMeasure = MeasureFactory.eINSTANCE.createSumMeasure();
        amountPlanMeasure.setName("AmountPlan");
        amountPlanMeasure.setColumn(bookingAmountPlanColumn);
        amountPlanMeasure.setFormatString(CURRENCY_FORMAT);

        OrderedColumn commentOrderedColumn = RelationalFactory.eINSTANCE.createOrderedColumn();
        commentOrderedColumn.setColumn(bookingCommentColumn);

        commentsMeasure = MeasureFactory.eINSTANCE.createTextAggMeasure();
        commentsMeasure.setName("Comments");
        commentsMeasure.setColumn(bookingCommentColumn);
        commentsMeasure.setSeparator(" | ");
        commentsMeasure.getOrderByColumns().add(commentOrderedColumn);

        MeasureGroup measureGroup = CubeFactory.eINSTANCE.createMeasureGroup();
        measureGroup.getMeasures().addAll(List.of(amountIstMeasure, amountPlanMeasure, commentsMeasure));

        varianceMember = LevelFactory.eINSTANCE.createCalculatedMember();
        varianceMember.setName("Variance");
        varianceMember.setFormula("[Measures].[AmountIst] - [Measures].[AmountPlan]");
        CalculatedMemberProperty varianceFormatProp = LevelFactory.eINSTANCE.createCalculatedMemberProperty();
        varianceFormatProp.setName("FORMAT_STRING");
        varianceFormatProp.setValue(VARIANCE_FORMAT);
        varianceMember.getCalculatedMemberProperties().add(varianceFormatProp);

        variancePctMember = LevelFactory.eINSTANCE.createCalculatedMember();
        variancePctMember.setName("VariancePct");
        variancePctMember.setFormula("iif([Measures].[AmountPlan] = 0, NULL,"
                + " ([Measures].[AmountIst] - [Measures].[AmountPlan]) / [Measures].[AmountPlan])");
        CalculatedMemberProperty variancePctFormatProp = LevelFactory.eINSTANCE.createCalculatedMemberProperty();
        variancePctFormatProp.setName("FORMAT_STRING");
        variancePctFormatProp.setValue(VARIANCE_PCT_FORMAT);
        variancePctMember.getCalculatedMemberProperties().add(variancePctFormatProp);

        // KPI and named sets temporarily disabled — formulas need rework
        // (see field declarations above).
        //
        // budgetUtilizationKpi = CubeFactory.eINSTANCE.createKpi();
        // budgetUtilizationKpi.setName("BudgetUtilization");
        // budgetUtilizationKpi.setDescription("Share of the plan that has already been consumed by actual postings, "
        //         + "evaluated per OrgUnit and Account cell.");
        // budgetUtilizationKpi.setValue(
        //         "iif([Measures].[AmountPlan] = 0, NULL," + " [Measures].[AmountIst] / [Measures].[AmountPlan])");
        // budgetUtilizationKpi.setGoal("1.0");
        // budgetUtilizationKpi.setStatus("iif([Measures].[AmountPlan] = 0, 0,"
        //         + " iif([Measures].[AmountIst] / [Measures].[AmountPlan] <= 0.9, 1,"
        //         + " iif([Measures].[AmountIst] / [Measures].[AmountPlan] <= 1.0, 0, -1)))");
        // budgetUtilizationKpi.setTrend("[Measures].[Variance]");
        // budgetUtilizationKpi.setDisplayFolder("KPIs");
        // budgetUtilizationKpi.setStatusGraphic("Traffic Light");
        // budgetUtilizationKpi.setTrendGraphic("Standard Arrow");
        //
        // topExpenseAccountsSet = DimensionFactory.eINSTANCE.createNamedSet();
        // topExpenseAccountsSet.setName("Top5ExpenseAccounts");
        // topExpenseAccountsSet.setFormula("TopCount("
        //         + "Descendants([Account].[Account].[EXPENSES], [Account].[Account])," + " 5, [Measures].[AmountIst])");
        // topExpenseAccountsSet.setDisplayFolder("Analysis");
        //
        // planOverrunSet = DimensionFactory.eINSTANCE.createNamedSet();
        // planOverrunSet.setName("PlanOverrun");
        // planOverrunSet.setFormula("Filter(" + "Descendants([Account].[Account].[All Accounts], [Account].[Account]),"
        //         + " [Measures].[AmountIst] > [Measures].[AmountPlan])");
        // planOverrunSet.setDisplayFolder("Analysis");
        //
        // accountsWithoutCommentSet = DimensionFactory.eINSTANCE.createNamedSet();
        // accountsWithoutCommentSet.setName("AccountsWithoutComment");
        // accountsWithoutCommentSet
        //         .setFormula("Filter(" + "Descendants([Account].[Account].[All Accounts], [Account].[Account]),"
        //                 + " IsEmpty([Measures].[Comments]))");
        // accountsWithoutCommentSet.setDisplayFolder("Analysis");

        DimensionConnector yearConnector = DimensionFactory.eINSTANCE.createDimensionConnector();
        yearConnector.setOverrideDimensionName(DIM_YEAR);
        yearConnector.setDimension(yearDimension);
        yearConnector.setForeignKey(bookingYearKeyColumn);

        DimensionConnector accountConnector = DimensionFactory.eINSTANCE.createDimensionConnector();
        accountConnector.setOverrideDimensionName(DIM_ACCOUNT);
        accountConnector.setDimension(accountDimension);
        accountConnector.setForeignKey(bookingAccountKeyColumn);

        DimensionConnector orgUnitConnector = DimensionFactory.eINSTANCE.createDimensionConnector();
        orgUnitConnector.setOverrideDimensionName(DIM_ORGUNIT);
        orgUnitConnector.setDimension(orgUnitDimension);
        orgUnitConnector.setForeignKey(bookingOrgUnitKeyColumn);

        WritebackAttribute wbYearAttribute = createWritebackAttribute(yearConnector, wbYearKeyColumn);
        WritebackAttribute wbAccountAttribute = createWritebackAttribute(accountConnector, wbAccountKeyColumn);
        WritebackAttribute wbOrgUnitAttribute = createWritebackAttribute(orgUnitConnector, wbOrgUnitKeyColumn);

        WritebackMeasure wbAmountPlanMeasure = MeasureFactory.eINSTANCE.createWritebackMeasure();
        wbAmountPlanMeasure.setName("AmountPlan");
        wbAmountPlanMeasure.setColumn(wbAmountPlanColumn);

        WritebackMeasure wbAmountIstMeasure = MeasureFactory.eINSTANCE.createWritebackMeasure();
        wbAmountIstMeasure.setName("AmountIst");
        wbAmountIstMeasure.setColumn(wbAmountIstColumn);
        wbAmountIstMeasure.setEditable(false);

        WritebackMeasure wbCommentsMeasure = MeasureFactory.eINSTANCE.createWritebackMeasure();
        wbCommentsMeasure.setName("Comments");
        wbCommentsMeasure.setColumn(wbCommentColumn);

        writebackTable = WritebackFactory.eINSTANCE.createWritebackTable();
        writebackTable.setName(TABLE_BOOKINGWB);
        writebackTable.getWritebackAttribute().addAll(List.of(wbYearAttribute, wbAccountAttribute, wbOrgUnitAttribute));
        writebackTable.getWritebackMeasure().addAll(List.of(wbAmountIstMeasure, wbAmountPlanMeasure, wbCommentsMeasure));

        cube = CubeFactory.eINSTANCE.createPhysicalCube();
        cube.setName(CUBE_NAME);
        cube.setSource(bookingSource);
        cube.getDimensionConnectors().addAll(List.of(yearConnector, accountConnector, orgUnitConnector));
        cube.getMeasureGroups().add(measureGroup);
        cube.getCalculatedMembers().addAll(List.of(varianceMember, variancePctMember));
        // KPI and named sets disabled — re-enable after fixing formulas.
        // cube.getKpis().add(budgetUtilizationKpi);
        // cube.getNamedSets().addAll(List.of(topExpenseAccountsSet, planOverrunSet, accountsWithoutCommentSet));
        cube.setWritebackTable(writebackTable);

        // Member unique names use the level's nameColumn value (the display name),
        // not the database KEY — same rule as setDefaultMember above. The
        // ORGUNIT table maps DEPT_A1 → "Department A1", DIV_A → "Division A", etc.
        roleDeptA1 = createOrgUnitRole("role_dept_A1",
                "[OrgUnit].[OrgUnit].[Company].[Division A].[Department A1]");
        roleDeptA2 = createOrgUnitRole("role_dept_A2",
                "[OrgUnit].[OrgUnit].[Company].[Division A].[Department A2]");
        roleDeptB1 = createOrgUnitRole("role_dept_B1",
                "[OrgUnit].[OrgUnit].[Company].[Division B].[Department B1]");
        roleDivisionA = createDivisionRole("role_division_A",
                "[OrgUnit].[OrgUnit].[Company].[Division A]");
        roleAccounting = createAccountingRole();
        roleReadonly = createReadonlyRole();

        catalog = CatalogFactory.eINSTANCE.createCatalog();
        catalog.setName(CATALOG_NAME);
        catalog.setDescription("Accounting catalog with IST/PLAN postings, three-level accounts and "
                + "org units, writeback for actual, plan data and text-aggregated comments. "
                + "Includes Variance/VariancePct calculated members and a set of access roles "
                + "ranging from single-department to division-wide, an accounting all-access "
                + "role and a read-only role.");
        catalog.getDbschemas().add(databaseSchema);
        catalog.getCubes().add(cube);
        catalog.getAccessRoles()
                .addAll(List.of(roleDeptA1, roleDeptA2, roleDeptB1, roleDivisionA, roleAccounting, roleReadonly));

        return catalog;
    }

    @Override
    public TutorialDescription describe() {
        Catalog c = get();
        return new TutorialDescription(List.of(new DocSection("Accounting Catalog", catalogBody, 1, 0, 0, null, 0),
                new DocSection("Database Schema", databaseSchemaBody, 1, 1, 0, databaseSchema, 3),
                new DocSection("Fact Query", factQueryBody, 1, 2, 0, bookingSource, 2),
                new DocSection("Account (parent-child Sachkonto)", accountDimensionBody, 1, 3, 0, accountDimension, 0),
                new DocSection("Year (TimeDimension, default = " + HIGHEST_YEAR + ")", yearDimensionBody, 1, 4, 0,
                        yearDimension, 0),
                new DocSection("OrgUnit (three-level)", orgUnitDimensionBody, 1, 6, 0, orgUnitDimension, 0),
                new DocSection("Measures (IST / PLAN / Comments)", measuresBody, 1, 8, 0, null, 0),
                new DocSection("Currency Format", currencyFormatBody, 1, 9, 0, amountIstMeasure, 0),
                new DocSection("Calculated Members (Variance, VariancePct)", calculatedMembersBody, 1, 10, 0,
                        varianceMember, 0),
                // KPI and named-set DocSections disabled while their underlying
                // model elements are commented out (bad formulas):
                // new DocSection("KPI (BudgetUtilization)", kpiBody, 1, 11, 0, budgetUtilizationKpi, 0),
                // new DocSection("Named Sets (Top5ExpenseAccounts, PlanOverrun, AccountsWithoutComment)", namedSetBody,
                //         1, 12, 0, topExpenseAccountsSet, 0),
                new DocSection("Writeback (BOOKINGWB)", writebackBody, 1, 13, 0, writebackTable, 2),
                new DocSection("Access Roles", rolesBody, 1, 14, 0, roleAccounting, 0),
                new DocSection("Whole-Division Role (role_division_A)", divisionRoleBody, 1, 15, 0, roleDivisionA, 2),
                new DocSection("Read-Only Role (role_readonly)", readonlyRoleBody, 1, 16, 0, roleReadonly, 2),
                new DocSection("Cube", cubeBody, 1, 17, 0, cube, 2)), List.of(new CatalogRef("catalog", () -> c)));
    }

    private static Column createColumn(String name,
            org.eclipse.daanse.cwm.model.cwm.resource.relational.SQLSimpleType type) {
        Column c = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createColumn();
        c.setName(name);
        c.setType(type);
        return c;
    }

    private static Table createTable(String name, List<Column> columns) {
        Table t = org.eclipse.daanse.cwm.model.cwm.resource.relational.RelationalFactory.eINSTANCE.createTable();
        t.setName(name);
        t.getFeature().addAll(columns);
        return t;
    }

    private static WritebackAttribute createWritebackAttribute(DimensionConnector connector, Column column) {
        WritebackAttribute a = WritebackFactory.eINSTANCE.createWritebackAttribute();
        a.setDimensionConnector(connector);
        a.setColumn(column);
        return a;
    }

    private AccessRole createOrgUnitRole(String roleName, String memberName) {
        AccessDatabaseSchemaGrant dbGrant = DatabaseFactory.eINSTANCE.createAccessDatabaseSchemaGrant();
        dbGrant.setDatabaseSchemaAccess(DatabaseSchemaAccess.ALL);
        dbGrant.setDatabaseSchema(databaseSchema);

        AccessMemberGrant memberGrant = OlapFactory.eINSTANCE.createAccessMemberGrant();
        memberGrant.setMemberAccess(MemberAccess.ALL);
        memberGrant.setMember(memberName);

        AccessHierarchyGrant hierarchyGrant = OlapFactory.eINSTANCE.createAccessHierarchyGrant();
        hierarchyGrant.setHierarchy(orgUnitHierarchy);
        hierarchyGrant.setHierarchyAccess(HierarchyAccess.CUSTOM);
        hierarchyGrant.setTopLevel(orgUnitLevelL3);
        hierarchyGrant.setBottomLevel(orgUnitLevelL3);
        hierarchyGrant.setRollupPolicy(RollupPolicy.FULL);
        hierarchyGrant.getMemberGrants().add(memberGrant);

        AccessCubeGrant cubeGrant = OlapFactory.eINSTANCE.createAccessCubeGrant();
        cubeGrant.setCube(cube);
        cubeGrant.setCubeAccess(CubeAccess.ALL);
        cubeGrant.getHierarchyGrants().add(hierarchyGrant);

        AccessCatalogGrant catalogGrant = CommonFactory.eINSTANCE.createAccessCatalogGrant();
        catalogGrant.setCatalogAccess(CatalogAccess.ALL_DIMENSIONS);
        catalogGrant.getCubeGrants().add(cubeGrant);
        catalogGrant.getDatabaseSchemaGrants().add(dbGrant);

        AccessRole role = CommonFactory.eINSTANCE.createAccessRole();
        role.setName(roleName);
        role.getAccessCatalogGrants().add(catalogGrant);
        return role;
    }

    private AccessRole createAccountingRole() {
        AccessDatabaseSchemaGrant dbGrant = DatabaseFactory.eINSTANCE.createAccessDatabaseSchemaGrant();
        dbGrant.setDatabaseSchemaAccess(DatabaseSchemaAccess.ALL);
        dbGrant.setDatabaseSchema(databaseSchema);

        AccessCubeGrant cubeGrant = OlapFactory.eINSTANCE.createAccessCubeGrant();
        cubeGrant.setCube(cube);
        cubeGrant.setCubeAccess(CubeAccess.ALL);

        AccessCatalogGrant catalogGrant = CommonFactory.eINSTANCE.createAccessCatalogGrant();
        catalogGrant.setCatalogAccess(CatalogAccess.ALL_DIMENSIONS);
        catalogGrant.getCubeGrants().add(cubeGrant);
        catalogGrant.getDatabaseSchemaGrants().add(dbGrant);

        AccessRole role = CommonFactory.eINSTANCE.createAccessRole();
        role.setName("role_accounting");
        role.getAccessCatalogGrants().add(catalogGrant);
        return role;
    }

    private AccessRole createDivisionRole(String roleName, String memberName) {
        AccessDatabaseSchemaGrant dbGrant = DatabaseFactory.eINSTANCE.createAccessDatabaseSchemaGrant();
        dbGrant.setDatabaseSchemaAccess(DatabaseSchemaAccess.ALL);
        dbGrant.setDatabaseSchema(databaseSchema);

        AccessMemberGrant memberGrant = OlapFactory.eINSTANCE.createAccessMemberGrant();
        memberGrant.setMemberAccess(MemberAccess.ALL);
        memberGrant.setMember(memberName);

        AccessHierarchyGrant hierarchyGrant = OlapFactory.eINSTANCE.createAccessHierarchyGrant();
        hierarchyGrant.setHierarchy(orgUnitHierarchy);
        hierarchyGrant.setHierarchyAccess(HierarchyAccess.CUSTOM);
        hierarchyGrant.setTopLevel(orgUnitLevelL1);
        hierarchyGrant.setBottomLevel(orgUnitLevelL3);
        hierarchyGrant.setRollupPolicy(RollupPolicy.FULL);
        hierarchyGrant.getMemberGrants().add(memberGrant);

        AccessCubeGrant cubeGrant = OlapFactory.eINSTANCE.createAccessCubeGrant();
        cubeGrant.setCube(cube);
        cubeGrant.setCubeAccess(CubeAccess.ALL);
        cubeGrant.getHierarchyGrants().add(hierarchyGrant);

        AccessCatalogGrant catalogGrant = CommonFactory.eINSTANCE.createAccessCatalogGrant();
        catalogGrant.setCatalogAccess(CatalogAccess.ALL_DIMENSIONS);
        catalogGrant.getCubeGrants().add(cubeGrant);
        catalogGrant.getDatabaseSchemaGrants().add(dbGrant);

        AccessRole role = CommonFactory.eINSTANCE.createAccessRole();
        role.setName(roleName);
        role.getAccessCatalogGrants().add(catalogGrant);
        return role;
    }

    private AccessRole createReadonlyRole() {
        AccessTableGrant denyWriteback = DatabaseFactory.eINSTANCE.createAccessTableGrant();
        denyWriteback.setTableAccess(TableAccess.NONE);
        denyWriteback.setTable(writebackPhysicalTable);

        AccessDatabaseSchemaGrant dbGrant = DatabaseFactory.eINSTANCE.createAccessDatabaseSchemaGrant();
        dbGrant.setDatabaseSchemaAccess(DatabaseSchemaAccess.CUSTOM);
        dbGrant.setDatabaseSchema(databaseSchema);
        dbGrant.getTableGrants().add(denyWriteback);

        AccessCubeGrant cubeGrant = OlapFactory.eINSTANCE.createAccessCubeGrant();
        cubeGrant.setCube(cube);
        cubeGrant.setCubeAccess(CubeAccess.ALL);

        AccessCatalogGrant catalogGrant = CommonFactory.eINSTANCE.createAccessCatalogGrant();
        catalogGrant.setCatalogAccess(CatalogAccess.ALL_DIMENSIONS);
        catalogGrant.getCubeGrants().add(cubeGrant);
        catalogGrant.getDatabaseSchemaGrants().add(dbGrant);

        AccessRole role = CommonFactory.eINSTANCE.createAccessRole();
        role.setName("role_readonly");
        role.getAccessCatalogGrants().add(catalogGrant);
        return role;
    }
}
