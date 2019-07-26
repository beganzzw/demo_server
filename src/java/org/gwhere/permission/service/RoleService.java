package org.gwhere.permission.service;

import com.github.pagehelper.PageInfo;
import org.gwhere.permission.model.SysRole;
import org.gwhere.permission.model.SysUser;

import java.util.List;
import java.util.Map;

public interface RoleService {

    /**
     * 获取角色
     *
     * @param roleCode
     * @param roleName
     * @return
     */
    PageInfo getRoles(Map map);

    /**
     * 保存角色
     *
     * @param roles
     */
    void saveRoles(List<SysRole> roles, SysUser operator);

    List<SysRole> getBrandRoles();
}
