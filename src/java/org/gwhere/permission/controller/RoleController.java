package org.gwhere.permission.controller;

import com.github.pagehelper.PageInfo;
import org.gwhere.permission.model.SysRole;
import org.gwhere.permission.model.SysUser;
import org.gwhere.permission.service.RoleService;
import org.gwhere.permission.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/role")
public class RoleController {

    @Resource
    private RoleService roleService;
    @Autowired
    private UserService userService;

    /**
     * 获取角色
     *
     * @param map
     * @return
     */
    @RequestMapping("/getRoles")
    public PageInfo getRoles(@RequestBody Map map) {
        return roleService.getRoles(map);
    }

    /**
     * 保存角色
     *
     * @param roles
     */
    @RequestMapping("/saveRoles")
    public void saveRoles(@RequestBody List<SysRole> roles, HttpSession session) {
        SysUser operator = userService.getUser(session);
        roleService.saveRoles(roles, operator);
    }

    @RequestMapping("/getBrandRoles")
    @ResponseBody
    public List<SysRole> getBrandRoles() {
        return roleService.getBrandRoles();
    }
}
