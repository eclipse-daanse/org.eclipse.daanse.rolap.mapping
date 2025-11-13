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
package org.eclipse.daanse.rolap.mapping.verifyer.api;

import java.util.List;

import javax.sql.DataSource;

import org.eclipse.daanse.rolap.mapping.model.Catalog;



public interface CheckService {

    List<VerificationResult> verify(Catalog catalog, DataSource dataSource);
}
