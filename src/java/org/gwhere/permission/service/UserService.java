package org.gwhere.permission.service;

import com.github.pagehelper.PageInfo;
import org.gwhere.permission.model.SysUser;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

public interface UserService {

    //session中的用户信息
    String SESSION_USER = "user";

    /**
     * 后端登录
     *
     * @param username
     * @param password
     * @return
     */
    SysUser execLogin(String username, String password, HttpSession session);

    /**
     * 获取session中的用户
     *
     * @param session
     */
    SysUser getUser(HttpSession session);

    /**
     * 后端退出
     *
     * @param session
     */
    void execLogout(HttpSession session);

    /**
     * 获取用户
     *
     * @return
     */
    PageInfo getUsers(Map map);

    /**
     * 保存用户
     *
     * @param users
     * @param operator
     */
    void saveUsers(List<SysUser> users, SysUser operator);

    /**
     * 获取可授权用户
     * @return
     */
    List<SysUser> getGrantAbleUsers();

    /**
     * 获取单点登录用户
     * @return
     */
    SysUser execSSOLogin(String operator, String tenantId, String timestamp, String sign, HttpSession session);

    public void assambleRoleAndPermission(SysUser user);
}
