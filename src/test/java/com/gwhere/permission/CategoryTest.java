package com.gwhere.permission;

import org.gwhere.permission.mapper.SysCategoryMapper;
import org.gwhere.permission.model.SysCategory;
import org.junit.Test;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Logger;

public class CategoryTest extends BaseTest {
//    @SuppressWarnings("unused")
//    private Logger logger = (Logger) LoggerFactory.getLogger(this.getClass());

    /**
     *
     */
    @Autowired
    private SysCategoryMapper sysCategoryMapper;


    //添加数据
    @Test
    public void addCategory() {
        for (int i = 0; i < 2; i++) {
            SysCategory category = new SysCategory();
            category.setCategoryName("测试1");
            category.setCategoryValue("1");
            category.setCreateTime(new Date());
            category.setCreateUser("admin");
            category.setStatus(1);
            sysCategoryMapper.insert(category);
        }
        System.out.println("数据生成完毕");
    }

    //查询所有数据
    @Test
    public void selectAll() {
        ArrayList<SysCategory> sysCategories = sysCategoryMapper.selectSelsective();
        for (SysCategory sysCategory : sysCategories) {
            long id = sysCategory.getId();
            String categoryName = sysCategory.getCategoryName();
            String categoryValue = sysCategory.getCategoryValue();

            System.out.println("categoryName :" + categoryName + "categoryValue :" + categoryValue + "id :" + id);
        }
        System.out.println("查询完成");
    }

    //    java.lang.Exception: No tests found matching Method selectByPrimary(com.gwhere.permission.CategoryTest) from org.junit.internal.requests.ClassRequest@63a65a25
//
//    at org.junit.internal.requests.FilterRequest.getRunner(FilterRequest.java:35)
//    at com.intellij.junit4.JUnit4IdeaTestRunner.startRunnerWithArgs(JUnit4IdeaTestRunner.java:49)
//    at com.intellij.rt.execution.junit.IdeaTestRunner$Repeater.startRunnerWithArgs(IdeaTestRunner.java:47)
//    at com.intellij.rt.execution.junit.JUnitStarter.prepareStreamsAndStart(JUnitStarter.java:242)
//    at com.intellij.rt.execution.junit.JUnitStarter.main(JUnitStarter.java:70)
    //查询单个ID数据
    @Test
    public void selectByPrimaryKey() {
        SysCategory sysCategory = sysCategoryMapper.selectByPrimaryKey((long) 23);
        System.out.println("ID : " + sysCategory.getId() + " Name :" + sysCategory.getCategoryName() + " Value :" + sysCategory.getCategoryValue());
    }

    //删除单个ID数据
    @Test
    public void deleteByPrimary() {
        long id = sysCategoryMapper.deleteByPrimaryKey((long) 27);
        System.out.println("sysCategory.getId(); " + id);
    }

    //修改数据
    @Test
    public void updateByPrimary() {
        SysCategory sysCategory = new SysCategory();
        sysCategory.setCategoryName("demo2");
        sysCategory.setCategoryValue("MDLL");
        sysCategory.setId((long) 28);
        sysCategoryMapper.updateByPrimaryKey(sysCategory);
        sysCategory = sysCategoryMapper.selectByPrimaryKey((long) 28);
        System.out.println("  id : " + sysCategory.getId() + "  name : " + sysCategory.getCategoryName() + "  value :" + sysCategory.getCategoryValue());
    }


}
