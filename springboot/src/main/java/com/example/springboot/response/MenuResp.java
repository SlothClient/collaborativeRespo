package com.example.springboot.response;

import java.util.ArrayList;
import java.util.List;

/**
 * @author ys
 * @date： 2024/8/20 23:09
 */

public class MenuResp {

    private String menuId;
    private String parentId;
    private String menuName;
    private String menuUrl;
    private String menuIcon;
    private Integer menuOrder;
    private String permission;
    private List<MenuResp> children;

    public MenuResp(String menuId, String parentId, String menuName, String menuUrl, String menuIcon, Integer menuOrder, String permission, List<MenuResp> children) {
        this.menuId = menuId;
        this.parentId = parentId;
        this.menuName = menuName;
        this.menuUrl = menuUrl;
        this.menuIcon = menuIcon;
        this.menuOrder = menuOrder;
        this.permission = permission;
        this.children = children;
    }

    public MenuResp() {
    }

    public static MenuRespBuilder builder() {
        return new MenuRespBuilder();
    }

    public void addChild(MenuResp child) {
        if (children == null) {
            children = new ArrayList<>();
        }
        children.add(child);
    }

    public String getMenuId() {
        return this.menuId;
    }

    public String getParentId() {
        return this.parentId;
    }

    public String getMenuName() {
        return this.menuName;
    }

    public String getMenuUrl() {
        return this.menuUrl;
    }

    public String getMenuIcon() {
        return this.menuIcon;
    }

    public Integer getMenuOrder() {
        return this.menuOrder;
    }

    public String getPermission() {
        return this.permission;
    }

    public List<MenuResp> getChildren() {
        return this.children;
    }

    public void setMenuId(String menuId) {
        this.menuId = menuId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public void setMenuName(String menuName) {
        this.menuName = menuName;
    }

    public void setMenuUrl(String menuUrl) {
        this.menuUrl = menuUrl;
    }

    public void setMenuIcon(String menuIcon) {
        this.menuIcon = menuIcon;
    }

    public void setMenuOrder(Integer menuOrder) {
        this.menuOrder = menuOrder;
    }

    public void setPermission(String permission) {
        this.permission = permission;
    }

    public void setChildren(List<MenuResp> children) {
        this.children = children;
    }

    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof MenuResp)) return false;
        final MenuResp other = (MenuResp) o;
        if (!other.canEqual((Object) this)) return false;
        final Object this$menuId = this.getMenuId();
        final Object other$menuId = other.getMenuId();
        if (this$menuId == null ? other$menuId != null : !this$menuId.equals(other$menuId)) return false;
        final Object this$parentId = this.getParentId();
        final Object other$parentId = other.getParentId();
        if (this$parentId == null ? other$parentId != null : !this$parentId.equals(other$parentId)) return false;
        final Object this$menuName = this.getMenuName();
        final Object other$menuName = other.getMenuName();
        if (this$menuName == null ? other$menuName != null : !this$menuName.equals(other$menuName)) return false;
        final Object this$menuUrl = this.getMenuUrl();
        final Object other$menuUrl = other.getMenuUrl();
        if (this$menuUrl == null ? other$menuUrl != null : !this$menuUrl.equals(other$menuUrl)) return false;
        final Object this$menuIcon = this.getMenuIcon();
        final Object other$menuIcon = other.getMenuIcon();
        if (this$menuIcon == null ? other$menuIcon != null : !this$menuIcon.equals(other$menuIcon)) return false;
        final Object this$menuOrder = this.getMenuOrder();
        final Object other$menuOrder = other.getMenuOrder();
        if (this$menuOrder == null ? other$menuOrder != null : !this$menuOrder.equals(other$menuOrder)) return false;
        final Object this$permission = this.getPermission();
        final Object other$permission = other.getPermission();
        if (this$permission == null ? other$permission != null : !this$permission.equals(other$permission))
            return false;
        final Object this$children = this.getChildren();
        final Object other$children = other.getChildren();
        if (this$children == null ? other$children != null : !this$children.equals(other$children)) return false;
        return true;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof MenuResp;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $menuId = this.getMenuId();
        result = result * PRIME + ($menuId == null ? 43 : $menuId.hashCode());
        final Object $parentId = this.getParentId();
        result = result * PRIME + ($parentId == null ? 43 : $parentId.hashCode());
        final Object $menuName = this.getMenuName();
        result = result * PRIME + ($menuName == null ? 43 : $menuName.hashCode());
        final Object $menuUrl = this.getMenuUrl();
        result = result * PRIME + ($menuUrl == null ? 43 : $menuUrl.hashCode());
        final Object $menuIcon = this.getMenuIcon();
        result = result * PRIME + ($menuIcon == null ? 43 : $menuIcon.hashCode());
        final Object $menuOrder = this.getMenuOrder();
        result = result * PRIME + ($menuOrder == null ? 43 : $menuOrder.hashCode());
        final Object $permission = this.getPermission();
        result = result * PRIME + ($permission == null ? 43 : $permission.hashCode());
        final Object $children = this.getChildren();
        result = result * PRIME + ($children == null ? 43 : $children.hashCode());
        return result;
    }

    public String toString() {
        return "MenuResp(menuId=" + this.getMenuId() + ", parentId=" + this.getParentId() + ", menuName=" + this.getMenuName() + ", menuUrl=" + this.getMenuUrl() + ", menuIcon=" + this.getMenuIcon() + ", menuOrder=" + this.getMenuOrder() + ", permission=" + this.getPermission() + ", children=" + this.getChildren() + ")";
    }

    public static class MenuRespBuilder {
        private String menuId;
        private String parentId;
        private String menuName;
        private String menuUrl;
        private String menuIcon;
        private Integer menuOrder;
        private String permission;
        private List<MenuResp> children;

        MenuRespBuilder() {
        }

        public MenuRespBuilder menuId(String menuId) {
            this.menuId = menuId;
            return this;
        }

        public MenuRespBuilder parentId(String parentId) {
            this.parentId = parentId;
            return this;
        }

        public MenuRespBuilder menuName(String menuName) {
            this.menuName = menuName;
            return this;
        }

        public MenuRespBuilder menuUrl(String menuUrl) {
            this.menuUrl = menuUrl;
            return this;
        }

        public MenuRespBuilder menuIcon(String menuIcon) {
            this.menuIcon = menuIcon;
            return this;
        }

        public MenuRespBuilder menuOrder(Integer menuOrder) {
            this.menuOrder = menuOrder;
            return this;
        }

        public MenuRespBuilder permission(String permission) {
            this.permission = permission;
            return this;
        }

        public MenuRespBuilder children(List<MenuResp> children) {
            this.children = children;
            return this;
        }

        public MenuResp build() {
            return new MenuResp(this.menuId, this.parentId, this.menuName, this.menuUrl, this.menuIcon, this.menuOrder, this.permission, this.children);
        }

        public String toString() {
            return "MenuResp.MenuRespBuilder(menuId=" + this.menuId + ", parentId=" + this.parentId + ", menuName=" + this.menuName + ", menuUrl=" + this.menuUrl + ", menuIcon=" + this.menuIcon + ", menuOrder=" + this.menuOrder + ", permission=" + this.permission + ", children=" + this.children + ")";
        }
    }
}
