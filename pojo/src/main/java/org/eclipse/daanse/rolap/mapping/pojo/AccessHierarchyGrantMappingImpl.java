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
import org.eclipse.daanse.rolap.mapping.api.model.AccessMemberGrantMapping;
import org.eclipse.daanse.rolap.mapping.api.model.HierarchyMapping;
import org.eclipse.daanse.rolap.mapping.api.model.LevelMapping;

public class AccessHierarchyGrantMappingImpl implements AccessHierarchyGrantMapping {

    private List<? extends AccessMemberGrantMapping> memberGrants;

    private String access;

    private LevelMapping bottomLevel;

    private String rollupPolicy;

    private LevelMapping topLevel;

    private HierarchyMapping hierarchy;

    public List<? extends AccessMemberGrantMapping> getMemberGrants() {
        return memberGrants;
    }

    public void setMemberGrants(List<? extends AccessMemberGrantMapping> memberGrants) {
        this.memberGrants = memberGrants;
    }

    public String getAccess() {
        return access;
    }

    public void setAccess(String access) {
        this.access = access;
    }

    public LevelMapping getBottomLevel() {
        return bottomLevel;
    }

    public void setBottomLevel(LevelMapping bottomLevel) {
        this.bottomLevel = bottomLevel;
    }

    public String getRollupPolicy() {
        return rollupPolicy;
    }

    public void setRollupPolicy(String rollupPolicy) {
        this.rollupPolicy = rollupPolicy;
    }

    public LevelMapping getTopLevel() {
        return topLevel;
    }

    public void setTopLevel(LevelMapping topLevel) {
        this.topLevel = topLevel;
    }

    public HierarchyMapping getHierarchy() {
        return hierarchy;
    }

    public void setHierarchy(HierarchyMapping hierarchy) {
        this.hierarchy = hierarchy;
    }
}
