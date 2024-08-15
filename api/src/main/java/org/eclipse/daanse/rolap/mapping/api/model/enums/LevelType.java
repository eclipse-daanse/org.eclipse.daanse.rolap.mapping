/*
 * Copyright (c) 2024 Contributors to the Eclipse Foundation.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   SmartCity Jena, Stefan Bischof - initial
 *
 */
package org.eclipse.daanse.rolap.mapping.api.model.enums;

/**
 * Enumerates the types of levels.
 */
public enum LevelType {

    /** Indicates that the level is not related to time. */
    REGULAR("Regular"),

    /**
     * Indicates that a level refers to years.
     */
    TIME_YEARS("TimeYears"),

    /**
     * Indicates that a level refers to half years.
     */
    TIME_HALF_YEARS("TimeHalfYears"),

    /**
     * Indicates that a level refers to quarters.
     */
    TIME_QUARTERS("TimeQuarters"),

    /**
     * Indicates that a level refers to months.
     */
    TIME_MONTHS("TimeMonths"),

    /**
     * Indicates that a level refers to weeks.
     */
    TIME_WEEKS("TimeWeeks"),

    /**
     * Indicates that a level refers to days.
     */
    TIME_DAYS("TimeDays"),

    /**
     * Indicates that a level refers to hours.

     */
    TIME_HOURS("TimeHours"),

    /**
     * Indicates that a level refers to minutes.
     */
    TIME_MINUTES("TimeMinutes"),

    /**
     * Indicates that a level refers to seconds.
     */
    TIME_SECONDS("TimeSeconds"),

    /**
     * Indicates that a level is an unspecified time period.
     */
    TIME_UNDEFINED("TimeUndefined"),

    /**
     * Indicates that a level holds the null member.
     */
    NULL("Null"),

    /**
     * Indicates that a level holds the geographical object Continent.
     */
    GEO_CONTINENT("GeographicContinent"),

    /**
     * Indicates that a level holds the geographical object Region.
     */
    GEO_REGION("GeographicRegion"),

    /**
     * Indicates that a level holds the geographical object Country.
     */
    GEO_COUNTRY("GeographicCountry"),

    /**
     * Indicates that a level holds the geographical objects State or Province.
     */
    GEO_STATE_OR_PROVINCE("GeographicProvince"),

    /**
     * Indicates that a level holds the geographical object County.
     */
    GEO_COUNTY("GeographicCounty"),

    /**
     * Indicates that a level holds the geographical object City.
     */
    GEO_CITY("GeographicCity"),

    /**
     * Indicates that a level holds the geographical object PostalCode.
     */
    GEO_POSTALCODE("GeographicPostalCode"),

    /**
     * Indicates that a level holds the geographical object Point.
     */
    GEO_POINT("GeographicPoint"),

    /**
     * Indicates that a level holds a OrganisationUnit.
     */
    ORG_UNIT("OrganisationUnit"),

    /**
     * Indicates that a level holds a Bom Resource.
     */
    BOM_RESOURCE("BomResource"),

    /**
     * Indicates that a level is a QUANTITATIVE.
     */
    QUANTITATIVE("Quantitative"),

    /**
     * Indicates that a level is a Account.
     */
    ACCOUNT("Account"),

    /**
     * Indicates that a level is a Customer.
     */
    CUSTOMER("Customer"),

    /**
     * Indicates that a level is a CustomerGroup.
     */
    CUSTOMER_GROUP("CustomerGroup"),

    /**
     * Indicates that a level is a CustomerGroup.
     */
    CUSTOMER_HOUSEHOLD("CustomerHouseHold"),

    /**
     * Indicates that a level is a Product.
     */
    PRODUCT("Product"),

    /**
     * Indicates that a level is a ProductGroup.
     */
    PRODUCT_GROUP("ProductGroup"),

    /**
     * Indicates that a level is a Scenario.
     */
    SCENARIO("Scenario"),

    /**
     * Indicates that a level is a Utility.
     */
    UTILITY("Utility"),

    /**
     * Indicates that a level is a Person.
     */
    PERSON("Person"),

    /**
     * Indicates that a level is a Company.
     */
    COMPANY("Company"),

    /**
     * Indicates that a level is a CurrencySource.
     */
    CURRENCY_SOURCE("CurrencySource"),
    /**
     * Indicates that a level is a CurrencyDestination.
     */
    CURRENCY_DESTINATION("CurrencyDestination"),

    /**
     * Indicates that a level is a Channel.
     */
    CHANNEL("Channel"),

    /**
     * Indicates that a level is a Representative.
     */
    REPRESENTATIVE("Representative"),

    /**
     * Indicates that a level is a Promotion.
     */
    PROMOTION("Promotion");

    private final String value;

    LevelType(String v) {
        value = v;
    }

    public String getValue() {
        return value;
    }

    public static LevelType fromValue(String v) {
        for (LevelType c : LevelType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        return REGULAR;
//			throw new IllegalArgumentException(v);
    }

    /**
     * Returns whether this is a time level.
     *
     * @return Whether this is a time level.
     */
    public boolean isTime() {
        return switch (this) {
        case TIME_YEARS, //
                TIME_HALF_YEARS, //
                TIME_QUARTERS, //
                TIME_MONTHS, //
                TIME_WEEKS, //
                TIME_DAYS, //
                TIME_HOURS, //
                TIME_MINUTES, //
                TIME_SECONDS, //
                TIME_UNDEFINED ->
            true;
        default -> false;
        };
    }
}
