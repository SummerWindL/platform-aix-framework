package com.platform.aix.common.datacommon.db.domain;

import com.platform.aix.common.datacommon.db.KeyMethodInterface;
import com.platform.aix.service.pikai.PikaiMenuInterface;
import com.platform.core.base.BaseEntity;

import java.io.Serializable;

public class PikaiMenu extends BaseEntity implements Serializable, PikaiMenuInterface, Cloneable, KeyMethodInterface<Integer> {
    private Integer id;

    private String menuId;

    private String parentMenuId;

    private String menuName;

    private String menuLayer;

    private Short menuOrder;

    private String menuUrl;

    private Object menuIcon;

    private String menuStatus;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMenuId() {
        return menuId;
    }

    public void setMenuId(String menuId) {
        this.menuId = menuId == null ? null : menuId.trim();
    }

    public String getParentMenuId() {
        return parentMenuId;
    }

    public void setParentMenuId(String parentMenuId) {
        this.parentMenuId = parentMenuId == null ? null : parentMenuId.trim();
    }

    public String getMenuName() {
        return menuName;
    }

    public void setMenuName(String menuName) {
        this.menuName = menuName == null ? null : menuName.trim();
    }

    public String getMenuLayer() {
        return menuLayer;
    }

    public void setMenuLayer(String menuLayer) {
        this.menuLayer = menuLayer == null ? null : menuLayer.trim();
    }

    public Short getMenuOrder() {
        return menuOrder;
    }

    public void setMenuOrder(Short menuOrder) {
        this.menuOrder = menuOrder;
    }

    public String getMenuUrl() {
        return menuUrl;
    }

    public void setMenuUrl(String menuUrl) {
        this.menuUrl = menuUrl == null ? null : menuUrl.trim();
    }

    public Object getMenuIcon() {
        return menuIcon;
    }

    public void setMenuIcon(Object menuIcon) {
        this.menuIcon = menuIcon;
    }

    public String getMenuStatus() {
        return menuStatus;
    }

    public void setMenuStatus(String menuStatus) {
        this.menuStatus = menuStatus == null ? null : menuStatus.trim();
    }
}