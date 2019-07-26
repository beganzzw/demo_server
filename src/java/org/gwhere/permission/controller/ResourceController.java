package org.gwhere.permission.controller;

import org.gwhere.exception.ErrorCode;
import org.gwhere.exception.SystemException;
import org.gwhere.permission.model.SysResource;
import org.gwhere.permission.model.SysUser;
import org.gwhere.permission.service.ResourceService;
import org.gwhere.permission.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.List;

@RestController
@RequestMapping("/resource")
public class ResourceController {

    @Resource
    private ResourceService resourceService;
    @Autowired
    private UserService userService;

    /**
     * 获取菜单资源(页面左侧菜单)
     *
     * @param session
     * @return
     */
    @RequestMapping("/getMenuResources")
    public List<SysResource> getMenuResources(HttpSession session) {

        SysUser user = userService.getUser(session);

        if (user == null) {
            throw new SystemException(ErrorCode.UNKNOWN, "用户没有登录");
        }

        return resourceService.getMenuResources(user);
    }

    /**
     * 获取后台资源(资源新增左侧菜单)
     *
     * @return
     */
    @RequestMapping("/getAllBackResources")
    public List<SysResource> getAllBackResources() {
        return resourceService.getAllBackResources();
    }

    /**
     * 获取资源树(角色授权资源树)
     *
     * @return
     */
    @RequestMapping("/getResourcesForTree")
    public List<SysResource> getResourcesForTree() {
        return resourceService.getResourcesForTree();
    }

    /**
     * 保存资源
     *
     * @param resource
     * @param session
     * @return
     */
    @RequestMapping("/saveResource")
    public SysResource saveResource(@RequestBody SysResource resource, HttpSession session) {
        SysUser operator = userService.getUser(session);
        return resourceService.saveResource(resource, operator);
    }
}
