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
package org.eclipse.daanse.rolap.mapping.emf.provider.impl;

import java.io.IOException;
import java.util.Map;

import org.eclipse.daanse.rolap.mapping.api.RolapContextMappingSupplier;
import org.eclipse.daanse.rolap.mapping.api.model.RolapContextMapping;
import org.eclipse.daanse.rolap.mapping.emf.provider.Constants;
import org.eclipse.daanse.rolap.mapping.emf.provider.EmfMappingProviderConfig;
import org.eclipse.daanse.rolap.mapping.emf.rolapmapping.EmfRolapMappingPackage;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.gecko.emf.osgi.constants.EMFNamespaces;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ServiceScope;
import org.osgi.service.metatype.annotations.Designate;

@Component(service = RolapContextMappingSupplier.class, scope = ServiceScope.SINGLETON, configurationPid = Constants.PID_EMF_MAPPING_PROVIDER)
@Designate(factory = true, ocd = EmfMappingProviderConfig.class)
public class EmfMappingProvider implements RolapContextMappingSupplier {

    @Reference(target = "(" + EMFNamespaces.EMF_MODEL_NAME + "=" + EmfRolapMappingPackage.eNAME + ")")
    private ResourceSet resourceSet;

    private RolapContextMapping rolapContextMapping;

    @Activate
    public void activate(EmfMappingProviderConfig config) throws IOException {

        URI uri = URI.createURI(config.resource_url());

        Resource resource = resourceSet.getResource(uri, true);
        resource.load(Map.of());

        EObject root = resource.getContents().get(0);

        if (root instanceof RolapContextMapping rcm) {
            rolapContextMapping = rcm;
        }
    }

    @Deactivate
    public void deactivate() {
        cleanAllResources();
    }

    @Override
    public RolapContextMapping get() {
        return rolapContextMapping;
    }

    private void cleanAllResources() {
        resourceSet.getResources().forEach(Resource::unload);
    }
}
