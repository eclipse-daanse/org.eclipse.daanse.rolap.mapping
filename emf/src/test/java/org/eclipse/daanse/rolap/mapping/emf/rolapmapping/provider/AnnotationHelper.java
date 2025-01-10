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
package org.eclipse.daanse.rolap.mapping.emf.rolapmapping.provider;

import static org.osgi.test.common.annotation.Property.ValueSource.SystemProperty;
import static org.osgi.test.common.annotation.Property.ValueSource.TestClass;
import static org.osgi.test.common.annotation.Property.ValueSource.TestMethod;
import static org.osgi.test.common.annotation.Property.ValueSource.TestUniqueId;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import org.osgi.test.common.annotation.Property;
import org.osgi.test.common.annotation.Property.TemplateArgument;
import org.osgi.test.common.annotation.Property.Type;
import org.osgi.test.common.annotation.config.WithFactoryConfiguration;

public class AnnotationHelper {

    public static final String PREFIX_MARKER_TESTING = "marker.testing.";
    public static final String MARKER_TEST_CLASS = PREFIX_MARKER_TESTING + "TestClass";
    public static final String MARKER_TEST_METHOD = PREFIX_MARKER_TESTING + "TestMethod";
    public static final String MARKER_TEST_UNIQUEID = PREFIX_MARKER_TESTING + "TestUniqueId";
    public static final String MARKER_TEST_UNIQUEID_HEX = PREFIX_MARKER_TESTING + "TestUniqueId.hex";

    @WithFactoryConfiguration(location = "?", factoryPid = Constants.PID_EMF_MAPPING_PROVIDER, properties = {
            @Property(key = Constants.RESOURCE_URL, type = Type.Scalar, value =
                    "%1$s/target/test-classes/%2$s/%3$s/catalog.xmi", //
                    templateArguments = { //
                            @TemplateArgument(source = SystemProperty, value = "basePath"), //
                            @TemplateArgument(source = TestClass), //
                            @TemplateArgument(source = TestMethod) }),
            @Property(key = MARKER_TEST_CLASS, source = TestClass), //
            @Property(key = MARKER_TEST_METHOD, source = TestMethod), //
            @Property(key = MARKER_TEST_UNIQUEID, source = TestUniqueId), //
            @Property(key = MARKER_TEST_UNIQUEID_HEX, value = "%h", templateArguments = @TemplateArgument(source = TestUniqueId)), //
    })
    @Retention(RetentionPolicy.RUNTIME)
    public @interface SetupMappingProviderWithTestInstance {
    }
}
