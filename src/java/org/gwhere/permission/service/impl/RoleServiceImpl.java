package org.gwhere.permission.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.gwhere.exception.ErrorCode;
import org.gwhere.exception.SystemException;
import org.gwhere.permission.mapper.SysResourceMapper;
import org.gwhere.permission.mapper.SysRoleMapper;
import org.gwhere.permission.mapper.SysRoleResourceMapper;
import org.gwhere.permission.model.SysResource;
import org.gwhere.permission.model.SysRole;
import org.gwhere.permission.model.SysRoleResource;
import org.gwhere.permission.model.SysUser;
import org.gwhere.permission.service.RoleService;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.*;

@Service
public class RoleServiceImpl implements RoleService {

    @Resource
    private SysRoleMapper sysRoleMapper;

    @Resource
    private SysRoleResourceMapper sysRoleResourceMapper;

    @Resource
    private SysResourceMapper sysResourceMapper;

    @Override
    public PageInfo getRoles(Map map) {
        PageInfo pageInfo = null;
        Integer page = null;
        Integer pageSize = null;
        Map resultMap = new HashMap();
        if(!StringUtils.isEmpty(map.get("page"))){
            page = Integer.valueOf(String.valueOf(map.get("page")));
            pageSize = Integer.valueOf(String.valueOf(map.get("pageSize")));
            PageHelper.startPage(page, pageSize);
        }
        Example example = new Example(SysRole.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("status", 1);
        if (!StringUtils.isEmpty(map.get("roleCode"))) {
            criteria.andEqualTo("roleCode", map.get("roleCode"));
        }
        if (!StringUtils.isEmpty(map.get("roleName"))) {
            criteria.andEqualTo("roleName", map.get("roleName"));
        }
        if(StringUtils.isEmpty(map.get("page"))){
            criteria.andLike("roleName","%PLATFORM%");
        }
        List<SysRole> roles = sysRoleMapper.selectByExample(example);
        for (SysRole role : roles) {
            role.setResources(sysResourceMapper.getResourcesByRoleId(role.getId()));
        }
        if(pageSize != null){
            pageInfo = new PageInfo<>(roles,pageSize);
        }else{
            pageInfo = new PageInfo<>();
            pageInfo.setList(roles);
        }
        return pageInfo;
    }

    @Override
    public void saveRoles(List<SysRole> roles, SysUser operator) {
        String operatorName = operator.getUsername();
        Date operateDate = new Date();
        for (SysRole role : roles) {
            //脏数据
            if (role.getId() == null && role.getStatus() != 1) {
                continue;
            }

            validateRole(role);

            if (role.getId() == null) {
                //新增
                role.setCreateTime(operateDate);
                role.setCreateUser(operatorName);
                role.setLastUpdateTime(operateDate);
                role.setLastUpdateUser(operatorName);
                sysRoleMapper.insertSelective(role);
                List<SysResource> resources = role.getResources();
                if (resources != null) {
                    for (SysResource resource : resources) {
                        SysRoleResource sysRoleResource = new SysRoleResource();
                        sysRoleResource.setRoleId(role.getId());
                        sysRoleResource.setResourceId(resource.getId());
                        sysRoleResource.setStatus(1);
                        sysRoleResource.setCreateTime(operateDate);
                        sysRoleResource.setCreateUser(operatorName);
                        sysRoleResource.setLastUpdateTime(operateDate);
                        sysRoleResource.setLastUpdateUser(operatorName);
                        sysRoleResourceMapper.insertSelective(sysRoleResource);
                    }
                }
            } else if (role.getStatus() == 0) {
                role.setLastUpdateTime(operateDate);
                role.setLastUpdateUser(operatorName);
                sysRoleMapper.updateByPrimaryKeySelective(role);
            } else {
                role.setLastUpdateTime(operateDate);
                role.setLastUpdateUser(operatorName);
                sysRoleMapper.updateByPrimaryKeySelective(role);

                List<SysResource> resources = role.getResources();
                Example example = new Example(SysRoleResource.class);
                example.createCriteria().andEqualTo("roleId", role.getId()).andEqualTo("status", 1);
                List<SysRoleResource> sysRoleResources = sysRoleResourceMapper.selectByExample(example);

                HashSet<Long> resourceIds = new HashSet<>();
                if (resources != null) {
                    for (SysResource resource : resources) {
                        resourceIds.add(resource.getId());
                    }
                }

                HashMap<Long, SysRoleResource> sysRoleResourceMap = new HashMap<>();
                for (SysRoleResource roleResource : sysRoleResources) {
                    sysRoleResourceMap.put(roleResource.getResourceId(), roleResource);
                }

                HashSet<Long> duplicateIds = new HashSet<>();
                //去除前台接口ID集合与已经存在接口ID集合重复部分，前台剩余为新增，后台剩余为删除
                for (Long resourceId : resourceIds) {
                    if (sysRoleResourceMap.keySet().contains(resourceId)) {
                        duplicateIds.add(resourceId);
                    }
                }

                for (Long resourceId : duplicateIds) {
                    resourceIds.remove(resourceId);
                    sysRoleResourceMap.remove(resourceId);
                }

                for (Long resourceId : resourceIds) {
                    SysRoleResource sysRoleResource = new SysRoleResource();
                    sysRoleResource.setRoleId(role.getId());
                    sysRoleResource.setResourceId(resourceId);
                    sysRoleResource.setStatus(1);
                    sysRoleResource.setCreateTime(operateDate);
                    sysRoleResource.setCreateUser(operatorName);
                    sysRoleResource.setLastUpdateTime(operateDate);
                    sysRoleResource.setLastUpdateUser(operatorName);
                    sysRoleResourceMapper.insertSelective(sysRoleResource);
                }

                for (SysRoleResource sysRoleResource : sysRoleResourceMap.values()) {
                    sysRoleResource.setStatus(0);
                    sysRoleResource.setLastUpdateTime(operateDate);
                    sysRoleResource.setLastUpdateUser(operatorName);
                    sysRoleResourceMapper.updateByPrimaryKeySelective(sysRoleResource);
                }
            }
        }
    }

    private void validateRole(SysRole role) {

        //删除不做验证
        if (role.getStatus() == 0) {
            return;
        }

        if (role.getRoleCode() == null || "".equals(role.getRoleCode())) {
            throw new SystemException(ErrorCode.MODIFY_DATA_FAILED, "角色代码不能为空！");
        }
        if (role.getRoleName() == null || "".equals(role.getRoleName())) {
            throw new SystemException(ErrorCode.MODIFY_DATA_FAILED, "角色名称不能为空！");
        }

        Example codeExample = new Example(SysRole.class);
        Example.Criteria codeCriteria = codeExample.createCriteria();
        codeCriteria.andEqualTo("status", 1);
        codeCriteria.andEqualTo("roleCode", role.getRoleCode());
        if (role.getId() != null) {
            codeCriteria.andNotEqualTo("id", role.getId());
        }
        List<SysRole> roles = sysRoleMapper.selectByExample(codeExample);
        if (roles.size() > 0) {
            throw new SystemException(ErrorCode.MODIFY_DATA_FAILED, "角色代码已经被使用！");
        }

        Example nameExample = new Example(SysRole.class);
        Example.Criteria nameCriteria = nameExample.createCriteria();
        nameCriteria.andEqualTo("status", 1);
        nameCriteria.andEqualTo("roleName", role.getRoleName());
        if (role.getId() != null) {
            nameCriteria.andNotEqualTo("id", role.getId());
        }
        List<SysRole> roles2 = sysRoleMapper.selectByExample(nameExample);
        if (roles2.size() > 0) {
            throw new SystemException(ErrorCode.MODIFY_DATA_FAILED, "角色名称已经被使用！");
        }
    }

    public List<SysRole> getBrandRoles(){
        Example example = new Example(SysRole.class);
        example.createCriteria()
                .andLike("roleCode","%BRAND%")
                .andEqualTo("status", 1);
        return sysRoleMapper.selectByExample(example);
    }
}
