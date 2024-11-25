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
package org.eclipse.daanse.rolap.mapping.verifyer.basic.jdbc;

import org.eclipse.daanse.rolap.mapping.api.model.SchemaMapping;
import org.eclipse.daanse.rolap.mapping.verifyer.api.Cause;
import org.eclipse.daanse.rolap.mapping.verifyer.api.Level;
import org.eclipse.daanse.rolap.mapping.verifyer.api.VerificationResult;
import org.eclipse.daanse.rolap.mapping.verifyer.api.Verifyer;
import org.eclipse.daanse.rolap.mapping.verifyer.basic.VerificationResultR;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.metatype.annotations.Designate;
import org.osgi.util.converter.Converter;
import org.osgi.util.converter.Converters;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Designate(ocd = DatabaseVerifierConfig.class)
@Component(service = Verifyer.class)
public class DatabaseVerifyer implements Verifyer {

    public static final Converter CONVERTER = Converters.standardConverter();

    @Reference()
    private DatabaseService databaseService;

    @Reference
    private DialectResolver dialectResolver;

    private DatabaseVerifierConfig config;

    @Activate
    public void activate(Map<String, Object> configMap) {
        this.config = CONVERTER.convert(configMap)
                .to(DatabaseVerifierConfig.class);
    }

    @Deactivate
    public void deactivate() {
        config = null;
    }

    @Override
    public List<VerificationResult> verify(SchemaMapping schema, DataSource dataSource) {
        List<VerificationResult> results = new ArrayList<>();

        try (Connection connection = dataSource.getConnection()) {
            DatabaseMetaData databaseMetaData = connection.getMetaData();
            Optional<Dialect> optionalDialect = dialectResolver.resolve(dataSource);
            JDBCSchemaWalker walker = new JDBCSchemaWalker(config, databaseService, databaseMetaData, optionalDialect);
            return walker.checkSchema(schema);

        } catch (SQLException e) {
            results.add(new VerificationResultR("Database access error", e.getMessage(), Level.ERROR, Cause.DATABASE));
        }

        return results;
    }

}
