package org.gwhere.permission.controller;

import com.github.pagehelper.PageInfo;
import org.gwhere.permission.cache.PermissionCache;
import org.gwhere.permission.model.SysUser;
import org.gwhere.permission.service.UserService;
import org.gwhere.utils.PropertiesUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;
import java.util.Properties;

@RestController
@RequestMapping("/user")
public class UserController {

    @Resource
    private UserService userService;

    /**
     * 后端登录
     *
     * @param map
     * @param session
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public SysUser login(@RequestBody Map map, HttpSession session)
            throws Exception {
        String username = (String)map.get("username");
        String password = (String)map.get("password");
        SysUser user = userService.execLogin(username, password, session);
        return user;
    }

    /**
     * 后端退出
     *
     * @param session
     */
    @RequestMapping(value = "/logout", method = RequestMethod.POST)
    public void logout(HttpSession session) {
        userService.execLogout(session);
    }

    @RequestMapping("/getCurrentUser")
    public SysUser getCurrentUser(HttpSession session) {
        return userService.getUser(session);
    }

    /**
     * 获取用户
     * @param map
     * @return
     */
    @RequestMapping("/getUsers")
    public PageInfo getUsers(@RequestBody Map map) {
        return userService.getUsers(map);
    }

    /**
     * 保存用户
     *
     * @param users
     */
    @RequestMapping("/saveUsers")
    public void saveUsers(@RequestBody List<SysUser> users, HttpSession session) {
        SysUser operator = userService.getUser(session);
        userService.saveUsers(users, operator);
    }


    /**
     * 获取可授权用户
     */
    @RequestMapping("/getGrantAbleUsers")
    public List<SysUser> getGrantAbleUsers() {
        return userService.getGrantAbleUsers();
    }

    @RequestMapping("/execUserChoose")
    public void execUserChoose(Long id,HttpSession session){
        SysUser user = userService.getUser(session);
        if(id > 0){
            user.setBrandId(id);
        }
        userService.assambleRoleAndPermission(user);
    }

    @RequestMapping("/refreshCache")
    public void refreshCache(){
        PermissionCache.refresh();
    }

}