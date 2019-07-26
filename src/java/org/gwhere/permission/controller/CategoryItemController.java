package org.gwhere.permission.controller;

import org.gwhere.permission.model.SysCategory;
import org.gwhere.permission.model.SysCategoryItem;
import org.gwhere.permission.model.SysUser;
import org.gwhere.permission.service.CategoryItemService;
import org.springframework.web.bind.annotation.*;

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
@RequestMapping("/user/categoryitem")
public class CategoryItemController {

    @Resource
    private CategoryItemService service;

    @RequestMapping(value = "/search", method = RequestMethod.POST)
    public ArrayList<SysCategoryItem> selectByPrimaryKey(@RequestBody Map map, HttpSession session, HttpServletRequest request) {
        Long id = Long.parseLong(map.get("id").toString());
        ArrayList<SysCategoryItem> SysCategoryItem = service.selectSelective(id);
        return SysCategoryItem;
    }

    @RequestMapping(value = "/select", method = RequestMethod.POST)
    public ArrayList<SysCategoryItem> select(@RequestBody Map map, HttpSession session, HttpServletRequest request) {
        Integer categoryId = Integer.parseInt(map.get("categoryId").toString());
        ArrayList<SysCategoryItem> sysCategoryItems = service.select(categoryId);
        return sysCategoryItems;
    }


    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public ArrayList<SysCategoryItem> deleteByPrimaryKey(@RequestBody Map map, HttpSession session, HttpServletRequest request) {
        Long id = Long.parseLong(map.get("id").toString());
        Integer categoryId = Integer.parseInt(map.get("categoryId").toString());
        service.deleteByPrimaryKey(id);
        ArrayList<SysCategoryItem> sysCategoryItem = service.selectSelective(Long.valueOf(categoryId));
        System.out.println("删除小类数据成功!");
        return sysCategoryItem;
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public ArrayList<SysCategoryItem> updateByPrimaryKey(@RequestBody Map map, HttpSession session, HttpServletRequest request) throws ParseException {
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date createTime = new Date();
        Date lastUpdateTime = new Date();
        SysUser sysUser = (SysUser) request.getSession().getAttribute("user");
        Long id = Long.parseLong(map.get("id").toString());
        int categoryId = Integer.parseInt(map.get("categoryId").toString());
        String itemValue = (String) map.get("itemValue");
        String itemName = (String) map.get("itemName");
        int itemOrder = Integer.parseInt(map.get("itemOrder").toString());
        String date = format.format(createTime);
        createTime = format.parse(date);
        date = format.format(lastUpdateTime);
        lastUpdateTime = format.parse(date);
        String createUser = sysUser.getCreateUser();
        String lastUpdateUser = sysUser.getLastUpdateUser();
        int status = Integer.parseInt(map.get("status").toString());
        SysCategoryItem sysCategoryItem = new SysCategoryItem();
        sysCategoryItem.setId(id);
        sysCategoryItem.setCategoryId(categoryId);
        sysCategoryItem.setItemValue(itemValue);
        sysCategoryItem.setItemName(itemName);
        sysCategoryItem.setItemOrder(itemOrder);
        sysCategoryItem.setStatus(status);
        sysCategoryItem.setCreateUser(createUser);
        sysCategoryItem.setCreateTime(createTime);
        sysCategoryItem.setLastUpdateTime(lastUpdateTime);
        sysCategoryItem.setLastUpdateUser(lastUpdateUser);
        service.updateByPrimary(sysCategoryItem);
        System.out.println("修改成功!");
        ArrayList<SysCategoryItem> sysCategoryItems = service.select(categoryId);
        return sysCategoryItems;
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public void insert(@RequestBody Map map, HttpSession session, HttpServletRequest request) throws ParseException {
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date createTime = new Date();
        Date lastUpdateTime = new Date();
        Integer status = 1;
        //默认ItemOrder值为0
        int itemOrder = 0;
        SysUser sysUser = (SysUser) request.getSession().getAttribute("user");
        Long id = null;
        int categoryId = Integer.parseInt(map.get("categoryId").toString());
        String itemValue = (String) map.get("itemValue");
        String itemName = (String) map.get("itemName");
        //日期处理
        String date = format.format(createTime);
        createTime = format.parse(date);
        date = format.format(lastUpdateTime);
        lastUpdateTime = format.parse(date);
        String createUser = sysUser.getCreateUser();
        String lastUpdateUser = sysUser.getLastUpdateUser();
        SysCategoryItem sysCategoryItem = new SysCategoryItem();
        sysCategoryItem.setId(id);
        sysCategoryItem.setCategoryId(categoryId);
        sysCategoryItem.setItemValue(itemValue);
        sysCategoryItem.setItemName(itemName);
        sysCategoryItem.setItemOrder(itemOrder);
        sysCategoryItem.setStatus(status);
        sysCategoryItem.setCreateUser(createUser);
        sysCategoryItem.setCreateTime(createTime);
        sysCategoryItem.setLastUpdateTime(lastUpdateTime);
        sysCategoryItem.setLastUpdateUser(lastUpdateUser);
        service.insert(sysCategoryItem);
        System.out.println("新增数据成功!");
    }
}
