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
*   SmartCity Jena - initial
*   Stefan Bischof (bipolis.org) - initial
*/
package org.eclipse.daanse.rolap.mapping.emf.rolapmapping.docgen;

import java.util.Map;

import io.pebbletemplates.pebble.extension.AbstractExtension;
import io.pebbletemplates.pebble.extension.Function;

public class EcoreExtension extends AbstractExtension {
    @Override
    public Map<String, Function> getFunctions() {
        return Map.of(
                "doc", new DocFunction(), // ← Here you set the name
                "isEClass", new IsEClassFunction(), // ← Here you set the name
                "isEClassInterface", new IsEClassInterfaceFunction(), // ← Here you set the name
                "isEEnum", new IsEEnumFunction() // ← Here you set the name
        );
    }
}
