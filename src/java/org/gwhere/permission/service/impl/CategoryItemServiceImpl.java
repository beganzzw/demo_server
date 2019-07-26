package org.gwhere.permission.service.impl;

import org.gwhere.permission.mapper.SysCategoryItemMapper;
import org.gwhere.permission.model.SysCategoryItem;
import org.gwhere.permission.service.CategoryItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class CategoryItemServiceImpl  implements CategoryItemService {

    @Autowired
    public SysCategoryItemMapper sysCategoryItemMapper;


    @Override
    public ArrayList<SysCategoryItem> selectSelective(Long id) {
        return sysCategoryItemMapper.selectSelective(id);
    }

    @Override
    public long updateByPrimary(SysCategoryItem record) {
        return sysCategoryItemMapper.updateByPrimaryKey(record);
    }

    @Override
    public SysCategoryItem selectByPrimaryKey(long id) {
        return sysCategoryItemMapper.selectByPrimaryKey(id);
    }

    @Override
    public int deleteByPrimaryKey(Long id) {
        return sysCategoryItemMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int insert(SysCategoryItem record) {
        return sysCategoryItemMapper.insert(record);
    }

    @Override
    public ArrayList<SysCategoryItem> select(int categoryId) {
        return sysCategoryItemMapper.select(categoryId);
    }
}
