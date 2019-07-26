package org.gwhere.permission.service;

import org.gwhere.permission.model.SysCategoryItem;

import java.util.ArrayList;

public interface CategoryItemService {
    ArrayList<SysCategoryItem> selectSelective (Long id);

    long updateByPrimary(SysCategoryItem record);

    SysCategoryItem selectByPrimaryKey (long id);

    int deleteByPrimaryKey(Long id);

    int insert(SysCategoryItem record);

    ArrayList<SysCategoryItem> select (int categoryId);

}
