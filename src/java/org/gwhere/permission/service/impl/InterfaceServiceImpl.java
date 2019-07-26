package org.gwhere.permission.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.gwhere.exception.ErrorCode;
import org.gwhere.exception.SystemException;
import org.gwhere.permission.cache.PermissionCache;
import org.gwhere.permission.mapper.SysInterfaceMapper;
import org.gwhere.permission.model.SysInterface;
import org.gwhere.permission.model.SysUser;
import org.gwhere.permission.service.InterfaceService;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class InterfaceServiceImpl implements InterfaceService {

    @Resource
    private SysInterfaceMapper sysInterfaceMapper;

    @Override
    public PageInfo getInterfaces(Map map) {
        //使用pageHelp工具类
        Integer page = Integer.valueOf(String.valueOf(map.get("page")));
        Integer pageSize = Integer.valueOf(String.valueOf(map.get("pageSize")));
        PageHelper.startPage(page, pageSize);

        Example example = new Example(SysInterface.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("status", 1);
        if (!StringUtils.isEmpty(map.get("name"))) {
            criteria.andEqualTo("name", map.get("name"));
        }
        if (!StringUtils.isEmpty(map.get("path"))) {
            criteria.andLike("path", "%" + map.get("path") + "%");
        }
        if (!StringUtils.isEmpty(map.get("existIds"))) {
            String[] idArr = ((String)map.get("existIds")).split(",");
            List<Long> ids = new ArrayList<>();
            for (String id : idArr) {
                ids.add(Long.valueOf(id));
            }
            criteria.andNotIn("id", ids);
        }
        example.orderBy("name");
        List<SysInterface> interfaces = sysInterfaceMapper.selectByExample(example);
        return new PageInfo(interfaces, pageSize);
    }

    @Override
    public void saveInterfaces(List<SysInterface> interfaces, SysUser operator) {
        String operatorName = operator.getUsername();
        Date operateDate = new Date();
        for (SysInterface sysInterface : interfaces) {
            //脏数据
            if (sysInterface.getId() == null && sysInterface.getStatus() != 1) {
                continue;
            }

            validateInterface(sysInterface);

            if (sysInterface.getId() == null) {
                //新增数据
                sysInterface.setCreateTime(operateDate);
                sysInterface.setCreateUser(operatorName);
                sysInterface.setLastUpdateTime(operateDate);
                sysInterface.setLastUpdateUser(operatorName);
                sysInterfaceMapper.insertSelective(sysInterface);
            } else {
                //删除或修改
                sysInterface.setLastUpdateTime(operateDate);
                sysInterface.setLastUpdateUser(operatorName);
                sysInterfaceMapper.updateByPrimaryKeySelective(sysInterface);
            }
        }
        PermissionCache.refresh();
    }

    private void validateInterface(SysInterface sysInterface) {

        //删除不做验证
        if (sysInterface.getStatus() == 0) {
            return;
        }
        if (sysInterface.getName() == null || "".equals(sysInterface.getName())) {
            throw new SystemException(ErrorCode.MODIFY_DATA_FAILED, "接口名称不能为空！");
        }
        if (sysInterface.getPath() == null || "".equals(sysInterface.getPath())) {
            throw new SystemException(ErrorCode.MODIFY_DATA_FAILED, "接口路径不能为空！");
        }
    }
}
