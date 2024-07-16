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
 *
 */
package org.eclipse.daanse.rolap.mapping.emf.util;

import java.util.List;

import org.eclipse.daanse.rolap.mapping.api.model.CellFormatterMapping;
import org.eclipse.daanse.rolap.mapping.api.model.FormatterMapping;
import org.eclipse.daanse.rolap.mapping.api.model.RolapContextMapping;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.CellFormatter;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.EmfRolapMappingFactory;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.Formatter;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.RolapContext;

public class EmfModelTransformer {

    RolapContext transform(RolapContextMapping rc) {
        RolapContext emfRc = EmfRolapMappingFactory.eINSTANCE.createRolapContext();
        emfRc.getFormatters().addAll(transform(rc.getFormatters()));
        return emfRc;
    }

    private List<? extends Formatter> transform(List<? extends FormatterMapping> formatters) {
        return formatters.stream().map(f -> transform(f)).toList();
    }

    private Formatter transform(FormatterMapping f) {

        if (f instanceof CellFormatterMapping cfm) {
            CellFormatter emfCf = EmfRolapMappingFactory.eINSTANCE.createCellFormatter();
            emfCf.setName(cfm.getName());
            return emfCf;
        }

        return null;
    }

}
