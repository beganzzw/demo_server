package org.gwhere.permission.model;

import org.gwhere.permission.vo.PermissionVO;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Table(name = "sys_interface")
public class SysInterface {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String path;

    private Integer status;

    private Date createTime;

    private String createUser;

    private Date lastUpdateTime;

    private String lastUpdateUser;

    @Transient
    private List<SysResource> resources;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
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

    public List<SysResource> getResources() {
        return resources;
    }

    public void setResources(List<SysResource> resources) {
        this.resources = resources;
    }

    /**
     * 获取最低权限要求资源
     *
     * @return
     */
    public SysResource getLowestResource() {
        if (resources == null || resources.isEmpty()) {
            return null;
        }
        SysResource lowestResource = null;
        for(SysResource resource : resources) {
            if(lowestResource==null) {
                lowestResource = resource;
            } else if(resource.getPermissionValue() < lowestResource.getPermissionValue()) {
                lowestResource = resource;
            }
        }
        return lowestResource;
    }

    /**
     * 生成权限实例
     *
     * @return
     */
    public PermissionVO generatePermissionVO() {
        SysResource resource = getLowestResource();
        PermissionVO permissionVO = new PermissionVO();
        permissionVO.setPath(this.getPath());
        permissionVO.setNeedLogin(resource.getNeedLogin());
        permissionVO.setNeedPermission(resource.getNeedPermission());
        return permissionVO;
    }
}
