package org.gwhere.permission.model;

import org.gwhere.permission.vo.PermissionVO;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Table(name = "sys_resource")
public class SysResource {

    //菜单资源
    public static final String RESOURCE_TYPE_MENU = "1";
    //非菜单资源
    public static final String RESOURCE_TYPE_NON_MENU = "2";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String resourceType;

    private Long parentId;

    private String name;

    private String routerPath;

    private String path;

    private String icon;

    private Integer theOrder;

    private String description;

    private Boolean needLogin;

    private Boolean needPermission;

    private Integer status;

    private Date createTime;

    private String createUser;

    private Date lastUpdateTime;

    private String lastUpdateUser;

    @Transient
    private List<SysResource> children = new ArrayList<>();

    @Transient
    private List<SysInterface> interfaces = new ArrayList<>();


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getResourceType() {
        return resourceType;
    }

    public void setResourceType(String resourceType) {
        this.resourceType = resourceType;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRouterPath() {
        return routerPath;
    }

    public void setRouterPath(String routerPath) {
        this.routerPath = routerPath;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public Integer getTheOrder() {
        return theOrder;
    }

    public void setTheOrder(Integer theOrder) {
        this.theOrder = theOrder;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    public Date getLastUpdateTime() {
        return lastUpdateTime;
    }

    public void setLastUpdateTime(Date lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
    }

    public String getLastUpdateUser() {
        return lastUpdateUser;
    }

    public void setLastUpdateUser(String lastUpdateUser) {
        this.lastUpdateUser = lastUpdateUser;
    }

    public List<SysResource> getChildren() {
        return children;
    }

    public void setChildren(List<SysResource> children) {
        this.children = children;
    }

    public List<SysInterface> getInterfaces() {
        return interfaces;
    }

    public void setInterfaces(List<SysInterface> interfaces) {
        this.interfaces = interfaces;
    }

    /**
     * 获取权限值
     *
     * @return
     */
    public int getPermissionValue() {
        return (needLogin ? 1 : 0) + (needPermission ? 2 : 0);
    }

    /**
     * 生成权限实例
     *
     * @return
     */
    public PermissionVO generatePermissionVO() {
        PermissionVO permissionVO = new PermissionVO();
        permissionVO.setResourceType(this.resourceType);
        permissionVO.setPath(this.path);
        permissionVO.setNeedLogin(this.needLogin);
        permissionVO.setNeedPermission(this.needPermission);
        return permissionVO;
    }
}