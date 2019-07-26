package org.gwhere.permission.mapper;

import org.apache.ibatis.annotations.Param;
import org.gwhere.permission.model.SysInterface;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface SysInterfaceMapper extends Mapper<SysInterface> {

    /**
     * 获取所有可用接口
     *
     * @return
     */
    List<SysInterface> getEnableInterfaces();

    /**
     * 获取角色接口路径
     *
     * @param userId
     * @return
     */
    List<String> getInterfacePathsByUserId(Long userId);

    List<String> getInterfacePathsByUserBrand(@Param("userId") Long userId, @Param("brandId") Long brandId);
}
