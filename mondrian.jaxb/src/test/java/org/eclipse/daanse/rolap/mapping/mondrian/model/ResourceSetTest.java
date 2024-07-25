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
*   SmartCity Jena - initial
*   Stefan Bischof (bipolis.org) - initial
*/
package org.eclipse.daanse.rolap.mapping.mondrian.model;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.SQLException;

import org.eclipse.daanse.rolap.mapping.api.model.CatalogMapping;
import org.eclipse.daanse.rolap.mapping.mondrian.api.RolapMappingTransformer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.io.TempDir;
import org.osgi.test.common.annotation.InjectService;
import org.osgi.test.junit5.cm.ConfigurationExtension;
import org.osgi.test.junit5.context.BundleContextExtension;
import org.osgi.test.junit5.service.ServiceExtension;

@ExtendWith(BundleContextExtension.class)
@ExtendWith(ServiceExtension.class)
@ExtendWith(ConfigurationExtension.class)
public class ResourceSetTest {

    private static String BASE_DIR = System.getProperty("basePath");

    @TempDir
    Path tempDir;

    @Test
    public void write(@InjectService RolapMappingTransformer transformer)
            throws SQLException, InterruptedException, IOException {

        String data = Files.readString(Path.of(BASE_DIR, "src/test/resources/schema.xml"));
        CatalogMapping rc = transformer.transform(new ByteArrayInputStream(data.getBytes(StandardCharsets.UTF_8)));
        assertThat(rc).isNotNull();

    }

}
