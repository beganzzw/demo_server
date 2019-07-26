package org.gwhere.permission.controller;


import org.gwhere.permission.model.SysCategory;
import org.gwhere.permission.model.SysUser;
import org.gwhere.permission.service.CategoryService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;

@RestController
@RequestMapping("/user")
public class CategoryController {

    @Resource
    private CategoryService categoryService;

    //查询所有的数据
    @RequestMapping(value = "/category", method = RequestMethod.POST)
    public ArrayList<SysCategory> getAll(@RequestBody Map map, HttpSession session, HttpServletRequest request) {
        System.out.println(request.getRequestURI());
        ArrayList<SysCategory> sysCategory = categoryService.selectSelsective();
        System.out.println("返回大类数据成功！");
        return sysCategory;
    }

    //修改其中某条数据
    @RequestMapping(value = "/category/update", method = RequestMethod.POST)
    public ArrayList<SysCategory> updateByPrimary(@RequestBody Map map, HttpSession session, HttpServletRequest request) throws ParseException {
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date createTime = new Date();
        Date lastUpdateTime = new Date();
        SysUser sysUser = (SysUser) request.getSession().getAttribute("user");
        String date = format.format(createTime);
        createTime = format.parse(date);
        date = format.format(lastUpdateTime);
        lastUpdateTime = format.parse(date);
        String categoryName = (String) map.get("categoryName");
        String categoryValue = (String) map.get("categoryValue");
        String createUser = sysUser.getCreateUser();
        String lastUpdateUser = sysUser.getLastUpdateUser();
        int status = Integer.parseInt(map.get("status").toString());
        Long id = Long.parseLong(map.get("id").toString());
        SysCategory sysCategory = new SysCategory();
        sysCategory.setId(id);
        sysCategory.setCategoryName(categoryName);
        sysCategory.setCategoryValue(categoryValue);
        sysCategory.setStatus(status);
        sysCategory.setCreateUser(createUser);
        sysCategory.setCreateTime(createTime);
        sysCategory.setLastUpdateTime(lastUpdateTime);
        sysCategory.setLastUpdateUser(lastUpdateUser);
        categoryService.updateByPrimary(sysCategory);
        ArrayList<SysCategory> sysCategorys = categoryService.selectSelsective();
        System.out.println("修改成功!");
        return sysCategorys;
    }

    //删除选中数据
    @RequestMapping(value = "/category/delete", method = RequestMethod.POST)
    public ArrayList<SysCategory> deleteByPrimaryKey(@RequestBody Map map, HttpSession session) {
        Long id = Long.parseLong(map.get("id").toString());
        categoryService.deleteByPrimaryKey(id);
        ArrayList<SysCategory> sysCategorys = categoryService.selectSelsective();
        return sysCategorys;
    }

    @RequestMapping(value = "/category/add", method = RequestMethod.POST)
    public ArrayList<SysCategory> insert(@RequestBody Map map, HttpSession session, HttpServletRequest request) throws ParseException {
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date createTime = new Date();
        Date lastUpdateTime = new Date();
        //状态处理
        Integer status = 1;
        SysUser sysUser = (SysUser) request.getSession().getAttribute("user");
        String categoryName = (String) map.get("categoryName");
        String categoryValue = (String) map.get("categoryValue");
        //日期处理

        String date = format.format(createTime);
        createTime = format.parse(date);
        date = format.format(lastUpdateTime);
        lastUpdateTime = format.parse(date);
        String createUser = sysUser.getCreateUser();
        String lastUpdateUser = sysUser.getLastUpdateUser();
        Long id = null;
        SysCategory sysCategory = new SysCategory();
        sysCategory.setId(id);
        sysCategory.setCategoryName(categoryName);
        sysCategory.setCategoryValue(categoryValue);
        sysCategory.setStatus(status);
        sysCategory.setCreateUser(createUser);
        sysCategory.setCreateTime(createTime);
        sysCategory.setLastUpdateTime(lastUpdateTime);
        sysCategory.setLastUpdateUser(lastUpdateUser);
        categoryService.insert(sysCategory);
        ArrayList<SysCategory> sysCategorys = categoryService.selectSelsective();
        return sysCategorys;
    }

}
