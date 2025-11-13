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
package org.eclipse.daanse.rolap.mapping.model.provider.util;

import org.eclipse.daanse.rolap.mapping.model.Documentation;
import org.eclipse.daanse.rolap.mapping.model.DocumentedElement;
import org.eclipse.daanse.rolap.mapping.model.RolapMappingFactory;

public class DocumentationUtil {

    public static void document(DocumentedElement element, String title, String body, int orderMajor, int orderMinor,
            int orderMicro, boolean showContainer, int setShowContainments) {
        Documentation doc = RolapMappingFactory.eINSTANCE.createDocumentation();

        if (title != null) {
            doc.setTitle(title);
        }
        if (body != null) {
            doc.setValue(body);
        }

        doc.setOrderMajor(orderMajor);
        doc.setOrderMinor(orderMinor);
        doc.setOrderMicro(orderMicro);
        doc.setShowContainer(showContainer);
        doc.setShowContainments(setShowContainments);

        element.getDocumentations().add(doc);
    }

}
