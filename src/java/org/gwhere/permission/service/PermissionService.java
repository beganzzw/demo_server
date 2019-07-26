package org.gwhere.permission.service;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by duan on 2016/9/26.
 */
public interface PermissionService {

    /**
     * 权限认证
     *
     * @param request
     * @return  true代表验证通过，false代表验证不通过
     */
    boolean authentication(HttpServletRequest request);
}
