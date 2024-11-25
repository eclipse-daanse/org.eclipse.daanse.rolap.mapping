/*
 * Copyright (c) 2022 Contributors to the Eclipse Foundation.
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
package org.eclipse.daanse.rolap.mapping.verifyer.basic.mandantory;

import org.eclipse.daanse.rolap.mapping.api.model.SchemaMapping;
import org.eclipse.daanse.rolap.mapping.verifyer.api.VerificationResult;
import org.eclipse.daanse.rolap.mapping.verifyer.api.Verifyer;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.metatype.annotations.Designate;
import org.osgi.util.converter.Converter;
import org.osgi.util.converter.Converters;

import javax.sql.DataSource;
import java.util.List;
import java.util.Map;

@Designate(ocd = MandantoriesVerifierConfig.class)
@Component(service = Verifyer.class)
public class MandantoriesVerifyer implements Verifyer {

    public static final Converter CONVERTER = Converters.standardConverter();

    private MandantoriesVerifierConfig config;

    @Activate
    public void activate(Map<String, Object> configMap) {
        this.config = CONVERTER.convert(configMap)
                .to(MandantoriesVerifierConfig.class);
    }

    @Deactivate
    public void deactivate() {
        config = null;
    }

    @Override
    public List<VerificationResult> verify(SchemaMapping schema, DataSource dataSource) {

        return new MandantoriesSchemaWalker(config).checkSchema(schema);
    }

}
