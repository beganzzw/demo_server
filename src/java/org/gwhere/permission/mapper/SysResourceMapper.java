package org.gwhere.permission.mapper;

import org.apache.ibatis.annotations.Param;
import org.gwhere.permission.model.SysResource;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface SysResourceMapper extends Mapper<SysResource> {

    /**
     * 获取角色资源路径
     *
     * @param userId
     * @return
     */
    List<String> getResourcePathsByUserId(Long userId);

    /**
     * 获取用户菜单资源
     *
     * @param userId 用户Id
     * @return
     */
    List<SysResource> getMenuResources(Long userId);

    List<SysResource> getMenuResourcesByUserBrand(@Param("userId") Long userId, @Param("brandId") Long brandId);

    /**
     * 获取所有资源
     *
     * @return
     */
    List<SysResource> getAllBackResources();

    /**
     * 获取角色资源
     *
     * @param roleId
     * @return
     */
    List<SysResource> getResourcesByRoleId(Long roleId);

    List<String> getResourcePathsByUserBrand(@Param("userId") Long userId, @Param("brandId") Long brandId);
}
