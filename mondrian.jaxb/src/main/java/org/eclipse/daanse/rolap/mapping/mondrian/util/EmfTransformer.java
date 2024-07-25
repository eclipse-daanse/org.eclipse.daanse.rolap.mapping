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
package org.eclipse.daanse.rolap.mapping.mondrian.util;

import java.io.InputStream;

import org.eclipse.daanse.rolap.mapping.mondrian.api.RolapMappingTransformer;
import org.eclipse.daanse.rolap.mapping.mondrian.model.Schema;
import org.eclipse.daanse.rolap.mapping.pojo.CatalogMappingImpl;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.ServiceScope;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Unmarshaller;

@Component(service = RolapMappingTransformer.class, scope = ServiceScope.SINGLETON)
public class EmfTransformer implements RolapMappingTransformer {

    private static Schema read(InputStream inputStream) throws JAXBException {
        JAXBContext jaxbContext = JAXBContext.newInstance(Schema.class);
        Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
        return (Schema) jaxbUnmarshaller.unmarshal(inputStream);
    }

    @Override
    public CatalogMappingImpl transform(InputStream inputStream) {
        try {
            Schema mondrianSchema = read(inputStream);
            TransformTask task = new TransformTask(mondrianSchema);
            return task.transform();
        } catch (JAXBException e) {
            throw new RuntimeException("Could not read Mondrian-Schema", e);
        }
    }

}
