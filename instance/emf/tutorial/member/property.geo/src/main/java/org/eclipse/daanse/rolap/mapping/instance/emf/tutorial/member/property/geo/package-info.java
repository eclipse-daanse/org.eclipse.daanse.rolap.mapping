/*
 * Copyright (c) 2025 Contributors to the Eclipse Foundation.
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

/**
 * This tutorial demonstrates member properties with geographic data stored across multiple tables.
 *
 * Shows how to:
 * - Define member properties for location data (GeoJSON, latitude, longitude, description)
 * - Join fact table with member table to access properties
 * - Create a single-level hierarchy with rich member attributes
 * - Structure geographic data for OLAP analysis
 *
 * The example uses two tables:
 * - Fact table: Contains measures and member references
 * - Member table: Contains member names and geographic properties
 */
package org.eclipse.daanse.rolap.mapping.instance.emf.tutorial.member.property.geo;
