package org.gwhere.permission.cache;

import org.gwhere.permission.mapper.SysInterfaceMapper;
import org.gwhere.permission.mapper.SysResourceMapper;
import org.gwhere.permission.model.SysInterface;
import org.gwhere.permission.model.SysResource;
import org.gwhere.permission.vo.PermissionVO;
import org.gwhere.utils.SpringContextUtils;
import tk.mybatis.mapper.entity.Example;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PermissionCache {

    private static Map<String, PermissionVO> permissions = new HashMap<String, PermissionVO>();

    public static void put(String key, PermissionVO value) {
        permissions.put(key, value);
    }

    public static PermissionVO get(String key) {
        return permissions.get(key);
    }

    public static void clear() {
        permissions.clear();
    }

    public static boolean isNotInitialized() {
        return permissions.isEmpty();
    }

    public static void refresh(){
        Map<String, PermissionVO> temp = new HashMap<String, PermissionVO>();
        Example example = new Example(SysResource.class);
        example.createCriteria().andEqualTo("status", 1);
        SysResourceMapper sysResourceMapper = SpringContextUtils.getBean(SysResourceMapper.class);
        SysInterfaceMapper sysInterfaceMapper = SpringContextUtils.getBean(SysInterfaceMapper.class);
        List<SysResource> resources = sysResourceMapper.selectByExample(example);
        List<SysInterface> interfaces = sysInterfaceMapper.getEnableInterfaces();
        for (SysResource resource : resources) {
            temp.put(resource.getPath(), resource.generatePermissionVO());
        }
        for (SysInterface sysInterface : interfaces) {
            temp.put(sysInterface.getPath(), sysInterface.generatePermissionVO());
        }
        permissions = temp;
    }
}
