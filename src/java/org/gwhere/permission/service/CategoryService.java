package org.gwhere.permission.service;

import org.gwhere.permission.model.SysCategory;

import java.util.ArrayList;

public interface CategoryService {
    ArrayList<SysCategory> selectSelsective ();

    long updateByPrimary(SysCategory record);

    SysCategory selectByPrimaryKey (long id);

    int deleteByPrimaryKey(Long id);

    int insert(SysCategory record);

}
