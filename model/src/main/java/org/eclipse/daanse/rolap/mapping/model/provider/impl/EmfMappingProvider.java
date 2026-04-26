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
package org.eclipse.daanse.rolap.mapping.model.provider.impl;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.PathMatcher;
import java.nio.file.Paths;
import java.util.Map;
import java.util.stream.Stream;

import org.eclipse.daanse.rolap.mapping.model.provider.CatalogMappingSupplier;
import org.eclipse.daanse.rolap.mapping.model.catalog.Catalog;
import org.eclipse.daanse.rolap.mapping.model.RolapMappingPackage;
import org.eclipse.daanse.rolap.mapping.model.provider.Constants;
import org.eclipse.daanse.rolap.mapping.model.provider.EmfMappingProviderConfig;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.fennec.emf.osgi.constants.EMFNamespaces;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ServiceScope;
import org.osgi.service.metatype.annotations.Designate;

@Component(service = CatalogMappingSupplier.class, scope = ServiceScope.SINGLETON, configurationPid = Constants.PID_EMF_MAPPING_PROVIDER)
@Designate(factory = true, ocd = EmfMappingProviderConfig.class)
public class EmfMappingProvider implements CatalogMappingSupplier {

    @Reference(target = "(" + EMFNamespaces.EMF_MODEL_NSURI + "=" + RolapMappingPackage.eNS_URI+ ")")
    private ResourceSet resourceSet;

    private Catalog catalogMapping;

    @Activate
    public void activate(EmfMappingProviderConfig config) throws IOException {

        String[] globs = config.additional_resource_globs();
        if (globs != null) {
            for (String glob : globs) {
                loadResourcesByGlob(glob);
            }
        }

        String url = config.resource_url();

        URI uri = URI.createFileURI(url);
        Resource resource = resourceSet.getResource(uri, true);
        resource.load(Map.of());
        EcoreUtil.resolveAll(resource);
        EList<EObject> contents = resource.getContents();

        for (EObject eObject : contents) {
            if (eObject instanceof Catalog rcm) {
                catalogMapping = rcm;
            }
        }

    }

    private void loadResourcesByGlob(String glob) throws IOException {
        Path startDir = getGlobBaseDir(glob);
        if (!Files.isDirectory(startDir)) {
            return;
        }
        PathMatcher matcher = FileSystems.getDefault().getPathMatcher("glob:" + glob);
        try (Stream<Path> paths = Files.walk(startDir)) {
            paths.filter(Files::isRegularFile)
                .filter(matcher::matches)
                .forEach(path -> {
                    URI fileUri = URI.createFileURI(path.toAbsolutePath().toString());
                    Resource res = resourceSet.getResource(fileUri, true);
                    try {
                        res.load(Map.of());
                    } catch (IOException e) {
                        throw new UncheckedIOException(e);
                    }
                });
        }
    }

    private static Path getGlobBaseDir(String glob) {
        String path = glob;
        int firstWildcard = Integer.MAX_VALUE;
        for (char c : new char[]{'*', '?', '[', '{'}) {
            int idx = path.indexOf(c);
            if (idx >= 0 && idx < firstWildcard) {
                firstWildcard = idx;
            }
        }
        if (firstWildcard < Integer.MAX_VALUE) {
            path = path.substring(0, firstWildcard);
        }
        Path p = Paths.get(path);
        if (!Files.isDirectory(p)) {
            p = p.getParent();
        }
        return p != null ? p : Paths.get(".");
    }

    @Deactivate
    public void deactivate() {
        cleanAllResources();
    }

    @Override
    public Catalog get() {
        return catalogMapping;
    }

    private void cleanAllResources() {
        resourceSet.getResources().forEach(Resource::unload);
    }
}
