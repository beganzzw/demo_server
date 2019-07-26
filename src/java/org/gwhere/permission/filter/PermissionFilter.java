package org.gwhere.permission.filter;

import org.gwhere.permission.service.PermissionService;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class PermissionFilter implements Filter {

    private PermissionService permissionService;

    @Override
    public void init(FilterConfig config) throws ServletException {
        WebApplicationContext wc = WebApplicationContextUtils.getWebApplicationContext(config.getServletContext());
        permissionService = (PermissionService) wc.getBean("permissionServiceImpl");
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain filterChain)
            throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;
        if (!permissionService.authentication(request)) {
            response.setHeader("Access-Control-Allow-Credentials", "true");
            response.setHeader("Access-Control-Allow-Origin", request.getHeader("origin"));
            response.setStatus(401);
            return;
        }
        filterChain.doFilter(req, res);
    }

    @Override
    public void destroy() {

    }
}
