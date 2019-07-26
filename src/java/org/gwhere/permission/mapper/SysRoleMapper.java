package org.gwhere.permission.mapper;

import org.gwhere.permission.model.SysRole;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface SysRoleMapper extends Mapper<SysRole> {

    /**
     * 获取用户角色
     *
     * @param userId
     * @return
     */
    List<SysRole> getRolesByUserId(Long userId);
}
