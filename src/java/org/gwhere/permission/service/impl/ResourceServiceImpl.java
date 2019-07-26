package org.gwhere.permission.service.impl;

import org.gwhere.exception.ErrorCode;
import org.gwhere.exception.SystemException;
import org.gwhere.permission.cache.PermissionCache;
import org.gwhere.permission.mapper.SysResourceInterfaceMapper;
import org.gwhere.permission.mapper.SysResourceMapper;
import org.gwhere.permission.model.SysInterface;
import org.gwhere.permission.model.SysResource;
import org.gwhere.permission.model.SysResourceInterface;
import org.gwhere.permission.model.SysUser;
import org.gwhere.permission.service.ResourceService;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class ResourceServiceImpl implements ResourceService {

    @Resource
    private SysResourceMapper sysResourceMapper;

    @Resource
    private SysResourceInterfaceMapper sysResourceInterfaceMapper;

    @Override
    public List<SysResource> getMenuResources(SysUser user) {
        List<SysResource> resources = null;
        if(null == user.getBrandId()){
            resources = sysResourceMapper.getMenuResources(user.getId());
        }else{
            resources = sysResourceMapper.getMenuResourcesByUserBrand(user.getId(), user.getBrandId());
        }
        return assembleResourceTree(resources);
    }

    @Override
    public List<SysResource> getAllBackResources() {
        List<SysResource> resources = sysResourceMapper.getAllBackResources();
        return assembleResourceTree(resources);
    }

    /**
     * 组装资源树
     *
     * @param resources
     * @return
     */
    private List<SysResource> assembleResourceTree(List<SysResource> resources) {
        List<SysResource> menuResources = new ArrayList<>();
        Map<Long, SysResource> mapResources = new HashMap<>();
        Map<Long, List<Long>> mapRelations = new HashMap<>();
        for (SysResource resource : resources) {
            Long parentId = resource.getParentId();
            if (parentId == null) {
                menuResources.add(resource);
            } else {
                mapResources.put(resource.getId(), resource);
                List<Long> children = mapRelations.get(parentId);
                if (children != null) {
                    children.add(resource.getId());
                } else {
                    List<Long> list = new ArrayList<>();
                    list.add(resource.getId());
                    mapRelations.put(parentId, list);
                }
            }
        }

        for (SysResource resource : menuResources) {
            buildChildMenuResource(resource, mapResources, mapRelations);
        }
        return menuResources;
    }

    /**
     * 构建子菜单资源
     *
     * @param resource
     * @param mapResources
     * @param mapRelations
     */
    private void buildChildMenuResource(SysResource resource,
                                        Map<Long, SysResource> mapResources,
                                        Map<Long, List<Long>> mapRelations) {
        List<Long> list = mapRelations.get(resource.getId());
        if (list != null) {
            for (Long id : list) {
                SysResource child = mapResources.get(id);
                buildChildMenuResource(child, mapResources, mapRelations);
                resource.getChildren().add(child);
            }
        }
    }

    @Override
    public List<SysResource> getResourcesForTree() {
        Example example = new Example(SysResource.class);
        example.createCriteria().andEqualTo("status", 1)
                .andNotEqualTo("resourceType", "3")
                .andEqualTo("needPermission", 1);
        List<SysResource> resources = sysResourceMapper.selectByExample(example);
        return assembleResourceTree(resources);
    }

    @Override
    public SysResource saveResource(SysResource resource, SysUser operator) {
        String operatorName = operator.getUsername();
        Date operateDate = new Date();
        //脏数据
        if (resource.getId() == null && resource.getStatus() != 1) {
            return null;
        }

        validateResource(resource);

        if (resource.getId() == null) {
            //新增
            resource.setCreateTime(operateDate);
            resource.setCreateUser(operatorName);
            resource.setLastUpdateTime(operateDate);
            resource.setLastUpdateUser(operatorName);
            sysResourceMapper.insertSelective(resource);
            List<SysInterface> interfaces = resource.getInterfaces();
            if (interfaces != null) {
                for (SysInterface sysInterface : interfaces) {
                    SysResourceInterface sysResourceInterface = new SysResourceInterface();
                    sysResourceInterface.setResourceId(resource.getId());
                    sysResourceInterface.setInterfaceId(sysInterface.getId());
                    sysResourceInterface.setStatus(1);
                    sysResourceInterface.setCreateTime(operateDate);
                    sysResourceInterface.setCreateUser(operatorName);
                    sysResourceInterface.setLastUpdateTime(operateDate);
                    sysResourceInterface.setLastUpdateUser(operatorName);
                    sysResourceInterfaceMapper.insertSelective(sysResourceInterface);
                }
            }
            return resource;
        } else if (resource.getStatus() == 0) {
            //删除
            List<SysResource> deleteResources = new ArrayList<>();
            deleteResources.add(resource);
            deleteResources.addAll(getPosterity(resource.getId()));
            for (SysResource res : deleteResources) {
                res.setStatus(0);
                res.setLastUpdateTime(operateDate);
                res.setLastUpdateUser(operatorName);
                sysResourceMapper.updateByPrimaryKeySelective(res);
            }
            return null;
        } else {
            //修改
            resource.setLastUpdateTime(operateDate);
            resource.setLastUpdateUser(operatorName);
            sysResourceMapper.updateByPrimaryKeySelective(resource);

            List<SysInterface> interfaces = resource.getInterfaces();
            Example example = new Example(SysResourceInterface.class);
            example.createCriteria().andEqualTo("resourceId", resource.getId()).andEqualTo("status", 1);
            List<SysResourceInterface> sysResourceInterfaces = sysResourceInterfaceMapper.selectByExample(example);

            HashSet<Long> interfaceIds = new HashSet<>();
            if (interfaces != null) {
                for (SysInterface sysInterface : interfaces) {
                    interfaceIds.add(sysInterface.getId());
                }
            }
            HashMap<Long, SysResourceInterface> sysResourceInterfaceMap = new HashMap<>();
            for (SysResourceInterface sysResourceInterface : sysResourceInterfaces) {
                sysResourceInterfaceMap.put(sysResourceInterface.getInterfaceId(), sysResourceInterface);
            }

            HashSet<Long> duplicateIds = new HashSet<>();
            //去除前台接口ID集合与已经存在接口ID集合重复部分，前台剩余为新增，后台剩余为删除
            for (Long interfaceId : interfaceIds) {
                if (sysResourceInterfaceMap.keySet().contains(interfaceId)) {
                    duplicateIds.add(interfaceId);
                }
            }

            for (Long interfaceId : duplicateIds) {
                interfaceIds.remove(interfaceId);
                sysResourceInterfaceMap.remove(interfaceId);
            }

            for (Long interfaceId : interfaceIds) {
                SysResourceInterface sysResourceInterface = new SysResourceInterface();
                sysResourceInterface.setResourceId(resource.getId());
                sysResourceInterface.setInterfaceId(interfaceId);
                sysResourceInterface.setStatus(1);
                sysResourceInterface.setCreateTime(operateDate);
                sysResourceInterface.setCreateUser(operatorName);
                sysResourceInterface.setLastUpdateTime(operateDate);
                sysResourceInterface.setLastUpdateUser(operatorName);
                sysResourceInterfaceMapper.insertSelective(sysResourceInterface);
            }

            for (SysResourceInterface sysResourceInterface : sysResourceInterfaceMap.values()) {
                sysResourceInterface.setStatus(0);
                sysResourceInterface.setLastUpdateTime(operateDate);
                sysResourceInterface.setLastUpdateUser(operatorName);
                sysResourceInterfaceMapper.updateByPrimaryKeySelective(sysResourceInterface);
            }
            PermissionCache.refresh();
            return resource;
        }
    }


    /**
     * 获取所有后代节点
     *
     * @param resourceId
     * @return
     */
    private List<SysResource> getPosterity(Long resourceId) {
        List<SysResource> retval = new ArrayList<>();
        Example example = new Example(SysResource.class);
        example.createCriteria().andEqualTo("parentId", resourceId).andEqualTo("status", 1);
        List<SysResource> resources = sysResourceMapper.selectByExample(example);
        for (SysResource resource : resources) {
            retval.add(resource);
            retval.addAll(getPosterity(resource.getId()));
        }
        return retval;
    }

    private void validateResource(SysResource resource) {

        //删除不做验证
        if (resource.getStatus() == 0) {
            return;
        }

        if (resource.getName() == null || "".equals(resource.getName())) {
            throw new SystemException(ErrorCode.MODIFY_DATA_FAILED, "资源名称不能为空！");
        }
        if (SysResource.RESOURCE_TYPE_MENU.equals(resource.getResourceType()) && resource.getTheOrder() == null) {
            throw new SystemException(ErrorCode.MODIFY_DATA_FAILED, "菜单资源排序不能为空！");
        }

        if (SysResource.RESOURCE_TYPE_MENU.equals(resource.getResourceType())) {
            Pattern pattern = Pattern.compile("^(0|[1-9]\\d*)$");
            Matcher matcher = pattern.matcher(String.valueOf(resource.getTheOrder()));
            if (!matcher.find()) {
                throw new SystemException(ErrorCode.MODIFY_DATA_FAILED, "排序只能是非负整数！");
            }
        }
    }
}
