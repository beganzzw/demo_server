package org.gwhere.permission.mapper;

import org.gwhere.permission.model.SysUser;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface SysUserMapper extends Mapper<SysUser> {

    /**
     * 获取可授权用户
     * @return
     */
    List<SysUser> getGrantAbleUsers();

    /**
     * 获取被授权用户
     * @param mallId
     * @return
     */
    List<SysUser> getGrantedUsers(Long mallId);
}
