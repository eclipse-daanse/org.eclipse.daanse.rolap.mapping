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
package org.eclipse.daanse.rolap.mapping.pojo;

import java.util.List;

import org.eclipse.daanse.rolap.mapping.api.model.AccessHierarchyGrantMapping;

public class AccessHierarchyGrantMappingImpl implements AccessHierarchyGrantMapping {

    private List<AccessMemberGrantMappingImpl> memberGrants;

    private String access;

    private LevelMappingImpl bottomLevel;

    private String rollupPolicy;

    private LevelMappingImpl topLevel;

    private HierarchyMappingImpl hierarchy;

    public List<AccessMemberGrantMappingImpl> getMemberGrants() {
        return memberGrants;
    }

    public void setMemberGrants(List<AccessMemberGrantMappingImpl> memberGrants) {
        this.memberGrants = memberGrants;
    }

    public String getAccess() {
        return access;
    }

    public void setAccess(String access) {
        this.access = access;
    }

    public LevelMappingImpl getBottomLevel() {
        return bottomLevel;
    }

    public void setBottomLevel(LevelMappingImpl bottomLevel) {
        this.bottomLevel = bottomLevel;
    }

    public String getRollupPolicy() {
        return rollupPolicy;
    }

    public void setRollupPolicy(String rollupPolicy) {
        this.rollupPolicy = rollupPolicy;
    }

    public LevelMappingImpl getTopLevel() {
        return topLevel;
    }

    public void setTopLevel(LevelMappingImpl topLevel) {
        this.topLevel = topLevel;
    }

    public HierarchyMappingImpl getHierarchy() {
        return hierarchy;
    }

    public void setHierarchy(HierarchyMappingImpl hierarchy) {
        this.hierarchy = hierarchy;
    }
}
