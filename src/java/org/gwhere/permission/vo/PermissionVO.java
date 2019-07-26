package org.gwhere.permission.vo;

public class PermissionVO {

    private String resourceType;

    private String path;

    private Boolean needLogin;

    private Boolean needPermission;

    public String getResourceType() {
        return resourceType;
    }

    public void setResourceType(String resourceType) {
        this.resourceType = resourceType;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Boolean getNeedLogin() {
        return needLogin;
    }

    public void setNeedLogin(Boolean needLogin) {
        this.needLogin = needLogin;
    }

    public Boolean getNeedPermission() {
        return needPermission;
    }

    public void setNeedPermission(Boolean needPermission) {
        this.needPermission = needPermission;
    }
}
