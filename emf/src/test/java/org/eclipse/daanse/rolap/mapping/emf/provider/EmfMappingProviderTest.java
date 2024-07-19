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
package org.eclipse.daanse.rolap.mapping.emf.provider;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.sql.SQLException;

import org.eclipse.daanse.rolap.mapping.api.RolapContextMappingSupplier;
import org.eclipse.daanse.rolap.mapping.api.model.RolapContextMapping;
import org.eclipse.daanse.rolap.mapping.emf.provider.AnnotationHelper.SetupMappingProviderWithTestInstance;
import org.gecko.emf.osgi.annotation.require.RequireEMF;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.osgi.service.cm.annotations.RequireConfigurationAdmin;
import org.osgi.service.component.annotations.RequireServiceComponentRuntime;
import org.osgi.test.common.annotation.InjectService;
import org.osgi.test.common.service.ServiceAware;
import org.osgi.test.junit5.cm.ConfigurationExtension;
import org.osgi.test.junit5.context.BundleContextExtension;
import org.osgi.test.junit5.service.ServiceExtension;

@ExtendWith(BundleContextExtension.class)
@ExtendWith(ServiceExtension.class)
@ExtendWith(ConfigurationExtension.class)
@RequireEMF
@RequireConfigurationAdmin
@RequireServiceComponentRuntime
public class EmfMappingProviderTest {

    private static String BASE_DIR = System.getProperty("basePath");

    @SetupMappingProviderWithTestInstance
    @Test
    public void loadSimpleFile(
            @InjectService(cardinality = 1, timeout = 2000) ServiceAware<RolapContextMappingSupplier> saRolapContextMappingSupplier)
            throws SQLException, InterruptedException, IOException {
        assertThat(saRolapContextMappingSupplier.getServices()).hasSize(1);

        RolapContextMappingSupplier rcms = saRolapContextMappingSupplier.getService();

        RolapContextMapping rCtx = rcms.get();

        assertThat(rCtx).isNotNull();

    }

}
