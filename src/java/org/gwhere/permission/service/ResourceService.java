package org.gwhere.permission.service;

import org.gwhere.permission.model.SysResource;
import org.gwhere.permission.model.SysUser;

import java.util.List;

public interface ResourceService {

    /**
     * 获取菜单资源
     *
     * @param
     * @return
     */
    List<SysResource> getMenuResources(SysUser user);

    /**
     * 获取系统资源
     *
     * @return
     */
    List<SysResource> getAllBackResources();

    /**
     * 获取资源树
     *
     * @return
     */
    List<SysResource> getResourcesForTree();

    /**
     * 保存资源
     *
     * @param resource
     */
    SysResource saveResource(SysResource resource, SysUser operator);
}
