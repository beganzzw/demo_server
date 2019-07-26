package org.gwhere.permission.service.impl;

import org.gwhere.permission.mapper.SysCategoryMapper;
import org.gwhere.permission.model.SysCategory;
import org.gwhere.permission.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class CategoryServiceImpl  implements CategoryService {

    @Autowired
    private SysCategoryMapper mapper;

    @Override
    public ArrayList<SysCategory> selectSelsective() {
        return mapper.selectSelsective();
    }

    @Override
    public long updateByPrimary(SysCategory record) {
        return mapper.updateByPrimaryKey(record);
    }

    @Override
    public SysCategory selectByPrimaryKey(long id) {
        return mapper.selectByPrimaryKey(id);
    }

    @Override
    public int deleteByPrimaryKey(Long id) {
        return mapper.deleteByPrimaryKey(id);
    }

    @Override
    public int insert(SysCategory record) {
        return mapper.insert(record);
    }


}
