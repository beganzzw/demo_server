package org.gwhere.permission.controller;

import com.github.pagehelper.PageInfo;
import org.gwhere.permission.model.SysInterface;
import org.gwhere.permission.model.SysUser;
import org.gwhere.permission.service.InterfaceService;
import org.gwhere.permission.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/interface")
public class InterfaceController {

    @Resource
    private InterfaceService interfaceService;
    @Autowired
    private UserService userService;

    /**
     * 获取接口
     *
     * @param map
     * @return
     */
    @RequestMapping("/getInterfaces")
    public PageInfo getInterfaces(@RequestBody Map map) {
        return interfaceService.getInterfaces(map);
    }

    /**
     * 保存接口
     *
     * @param interfaces
     * @param session
     */
    @RequestMapping("/saveInterfaces")
    public void saveInterfaces(@RequestBody List<SysInterface> interfaces, HttpSession session) {
        SysUser operator = userService.getUser(session);
        interfaceService.saveInterfaces(interfaces, operator);
    }


}
