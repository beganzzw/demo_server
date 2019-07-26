package org.gwhere.permission.service.impl;

import org.gwhere.permission.cache.PermissionCache;
import org.gwhere.permission.model.SysUser;
import org.gwhere.permission.service.PermissionService;
import org.gwhere.permission.service.UserService;
import org.gwhere.permission.vo.PermissionVO;
import org.gwhere.utils.Converter;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Service
public class PermissionServiceImpl implements PermissionService {

    @Override
    public boolean authentication(HttpServletRequest request) {
        String requestUri = request.getRequestURI();
        String contextPath = request.getContextPath();
        String url = requestUri.substring(contextPath.length());

        if (url.contains("/p/") || url.contains("/wx/") || url.contains("/customer/")) {
            return true;
        }

        //系统中不存在对应权限则验证失败
        PermissionVO permission = PermissionCache.get(url);
        if (permission == null) {
            return false;
        }
        //直接通过options访问的验证
        if("OPTIONS".equals(request.getMethod())){
           return true;
        }
        Boolean isApp = Converter.to(Boolean.class).convert(request, "isApp");
        Boolean needLogin = permission.getNeedLogin();
        needLogin = needLogin == null ? false : needLogin;
        Boolean needPermission = permission.getNeedPermission();
        needPermission = needPermission == null ? false : needPermission;

        //需要用户登录或者需要验证权限
        if (needLogin || needPermission) {
            SysUser user = (SysUser) request.getSession().getAttribute(UserService.SESSION_USER);
            //没有登录
            if (user == null) {
                return false;
            }

            if (!needPermission) {
                return true;
            }

            List<String> permissions = user.getPermissions();
            return permissions.contains(url);
        } else {
            return true;
        }

    }
}
