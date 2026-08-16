/*
* Copyright (c) 2026 Contributors to the Eclipse Foundation.
*
* This program and the accompanying materials are made
* available under the terms of the Eclipse Public License 2.0
* which is available at https://www.eclipse.org/legal/epl-2.0/
*
* SPDX-License-Identifier: EPL-2.0
*/
package org.eclipse.daanse.rolap.mapping.model.provider.util;

/**
 * The Daanse conventions on top of the open CWM string vocabularies. CWM
 * leaves {@code Description.type}, {@code Document.type},
 * {@code ResponsibleParty.responsibility} and {@code TaggedValue.tag} open;
 * the generic mechanics live in the cwm util helpers ({@code Descriptions},
 * {@code Documents}, {@code ResponsibleParties}, {@code TaggedValues}), this
 * class holds the vocabulary the mapping model agrees on.
 */
public final class CwmHelper {

    /** Description type: general documentation text. */
    public static final String TYPE_DOCUMENTATION = "documentation";
    /** Description type: localized display caption; fallback is the element name. */
    public static final String TYPE_CAPTION = "caption";

    /** Document type: link to the data dictionary entry. */
    public static final String DOC_TYPE_DATA_DICTIONARY = "data-dictionary";
    /** Document type: link to lineage / provenance documentation. */
    public static final String DOC_TYPE_LINEAGE = "lineage";
    /** Document type: link to the operational runbook. */
    public static final String DOC_TYPE_RUNBOOK = "runbook";

    /** Role: accountable owner of the element. */
    public static final String ROLE_OWNER = "owner";
    /** Role: data steward curating content and quality. */
    public static final String ROLE_STEWARD = "steward";
    /** Role: operational on-call contact. */
    public static final String ROLE_ON_CALL = "on-call";
    /** Role: sign-off authority for changes. */
    public static final String ROLE_APPROVER = "approver";

    /**
     * The IETF BCP-47 tag for "undetermined", used for language-neutral
     * texts (CWM prescribes no language vocabulary).
     */
    public static final String LANGUAGE_NEUTRAL = "und";

    /** Prefix of the Daanse tag namespace. */
    public static final String TAG_PREFIX = "daanse:";

    private CwmHelper() {
    }

    /** Builds a conventional tag: {@code daanse:} + {@code path}. */
    public static String tag(String path) {
        if (path == null || path.isBlank()) {
            throw new IllegalArgumentException("tag path must not be blank");
        }
        return TAG_PREFIX + path;
    }

    /**
     * Whether {@code tag} follows the naming scheme: the {@code daanse:}
     * namespace or one of the grandfathered localization key families
     * ({@code caption.<locale>}, {@code description.<locale>} — the engine's
     * getLocalized contract). Unprefixed tags are reserved for future CWM/OMG
     * conventions; the verifyer warns about tags outside the scheme.
     */
    public static boolean followsNamingScheme(String tag) {
        return tag != null && (tag.startsWith(TAG_PREFIX)
                || tag.startsWith("caption.") || tag.startsWith("description."));
    }
}
