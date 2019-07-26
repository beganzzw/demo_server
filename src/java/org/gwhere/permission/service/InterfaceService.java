package org.gwhere.permission.service;

import com.github.pagehelper.PageInfo;
import org.gwhere.permission.model.SysInterface;
import org.gwhere.permission.model.SysUser;

import java.util.List;
import java.util.Map;

public interface InterfaceService {

    /**
     * 获取接口
     *
     * @param map 参数
     * @return
     */
    PageInfo getInterfaces(Map map);

    /**
     * 保存接口
     *
     * @param interfaces
     */
    void saveInterfaces(List<SysInterface> interfaces, SysUser operator);
}
