package org.gwhere.permission.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.gwhere.exception.ErrorCode;
import org.gwhere.exception.SystemException;
import org.gwhere.permission.mapper.*;
import org.gwhere.permission.model.*;
import org.gwhere.permission.service.UserService;
import org.gwhere.utils.*;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class UserServiceImpl implements UserService {

    @Resource
    private SysUserMapper sysUserMapper;

    @Resource
    private SysRoleMapper sysRoleMapper;

    @Resource
    private SysUserRoleMapper sysUserRoleMapper;

    @Resource
    private SysResourceMapper sysResourceMapper;

    @Resource
    private SysInterfaceMapper sysInterfaceMapper;

    @Override
    public SysUser execLogin(String username, String password, HttpSession session) {
        Example example = new Example(SysUser.class);
        example.createCriteria().andEqualTo("status", 1)
                .andEqualTo("username", username);
        List<SysUser> users = sysUserMapper.selectByExample(example);
        if (users == null || users.isEmpty()) {
            throw new SystemException(ErrorCode.UNKNOWN, "用户名或密码错误！");
        }
        SysUser user = users.get(0);
        String salt = user.getSalt();

        if (!PasswordHelper.generatePassword(password, salt).equals(user.getPassword())) {
            throw new SystemException(ErrorCode.UNKNOWN, "用户名或密码错误！");
        }

        user.setLastLoginTime(new Date());
        sysUserMapper.updateByPrimaryKey(user);
        assambleRoleAndPermission(user);
        session.setAttribute(UserService.SESSION_USER, user);
        return user;
    }

    @Override
    public SysUser execSSOLogin(String operator, String tenantId, String timestamp, String sign, HttpSession session) {
        Properties prop = PropertiesUtils.load("config/ssologin.properties");
        if(StringUtils.isEmpty(operator)){
            throw new SystemException(ErrorCode.UNKNOWN,"用户名为空！");
        }
        Long interval = (System.currentTimeMillis() - Long.parseLong(timestamp)) / (1000 * 60);
        if(interval > 10){
            throw new SystemException(ErrorCode.UNKNOWN,"登录信息超时！");
        }
        String secret = prop.getProperty("sso.secret");
        String concat = "operator" + operator + "tenantId" + tenantId + "timestamp" + timestamp;
        String keys = secret + concat + secret;
        String md5Str = MD5.getMD5(keys);
        if(md5Str.equals(sign)){
            Example example = new Example(SysUser.class);
            example.createCriteria().
                    andEqualTo("status", 1).
                    andEqualTo("username", operator).
                    andEqualTo("userSource","2");
            List<SysUser> users = sysUserMapper.selectByExample(example);
            if (users == null || users.isEmpty()) {
                throw new SystemException(ErrorCode.UNKNOWN,"用户不存在！");
            }
            SysUser user = users.get(0);
            user.setLastLoginTime(new Date());
            sysUserMapper.updateByPrimaryKey(user);
            session.setAttribute(UserService.SESSION_USER, user);
            return user;
        }else{
            throw new SystemException(ErrorCode.UNKNOWN,"登陆失败！");
        }
    }

    @Override
    public SysUser getUser(HttpSession session) {
        return (SysUser) session.getAttribute(UserService.SESSION_USER);
    }

    @Override
    public void execLogout(HttpSession session) {
        session.removeAttribute(UserService.SESSION_USER);
        session.invalidate();
    }

    @Override
    public PageInfo getUsers(Map map) {
        Integer page = Integer.valueOf(String.valueOf(map.get("page")));
        Integer pageSize = Integer.valueOf(String.valueOf(map.get("pageSize")));
        PageHelper.startPage(page, pageSize);
        Example example = new Example(SysUser.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("status", 1);
        if (!StringUtils.isEmpty(map.get("username"))) {
            criteria.andEqualTo("username", map.get("username"));
        }
        if (!StringUtils.isEmpty(map.get("phoneNumber"))) {
            criteria.andEqualTo("phoneNumber", map.get("phoneNumber"));
        }
        if (!StringUtils.isEmpty(map.get("realName"))) {
            criteria.andEqualTo("realName", map.get("realName"));
        }
        if (!StringUtils.isEmpty(map.get("nickname"))) {
            criteria.andEqualTo("nickname", map.get("nickname"));
        }
        List<SysUser> users = sysUserMapper.selectByExample(example);
        for (SysUser user : users) {
            user.setRoles(sysRoleMapper.getRolesByUserId(user.getId()));
        }
        return new PageInfo(users, pageSize);
    }

    @Override
    public void saveUsers(@RequestBody List<SysUser> users, SysUser operator) {
        String operatorName = operator.getUsername();
        Date operateDate = new Date();
        for (SysUser user : users) {
            //脏数据
            if (user.getId() == null && user.getStatus() != 1) {
                continue;
            }

            validateUser(user);

            if (user.getId() == null) {
                //新增
                //生成默认密码111111
                user.setSalt(user.getUsername());
                user.setPassword(PasswordHelper.generatePassword("gw123456", user.getSalt()));
                user.setCreateTime(operateDate);
                user.setCreateUser(operatorName);
                user.setLastUpdateTime(operateDate);
                user.setLastUpdateUser(operatorName);
                user.setUserSource("1");
                sysUserMapper.insertSelective(user);
                List<SysRole> roles = user.getRoles();
                if (roles != null) {
                    for (SysRole role : roles) {
                        SysUserRole sysUserRole = new SysUserRole();
                        sysUserRole.setUserId(user.getId());
                        sysUserRole.setRoleId(role.getId());
                        sysUserRole.setStatus(1);
                        sysUserRole.setCreateTime(operateDate);
                        sysUserRole.setCreateUser(operatorName);
                        sysUserRole.setLastUpdateTime(operateDate);
                        sysUserRole.setLastUpdateUser(operatorName);
                        sysUserRoleMapper.insertSelective(sysUserRole);
                    }
                }
            } else if (user.getStatus() == 0) {
                //删除
                user.setLastUpdateTime(operateDate);
                user.setLastUpdateUser(operatorName);
                sysUserMapper.updateByPrimaryKeySelective(user);
            } else {
                user.setLastUpdateTime(operateDate);
                user.setLastUpdateUser(operatorName);
                sysUserMapper.updateByPrimaryKeySelective(user);
                //修改
                List<SysRole> roles = user.getRoles();
                Example example = new Example(SysUserRole.class);
                example.createCriteria().andEqualTo("userId", user.getId()).andEqualTo("status", 1);
                List<SysUserRole> sysUserRoles = sysUserRoleMapper.selectByExample(example);

                HashSet<Long> roleIds = new HashSet<>();
                if (roles != null) {
                    for (SysRole role : roles) {
                        roleIds.add(role.getId());
                    }
                }
                HashMap<Long, SysUserRole> sysUserRoleMap = new HashMap<>();
                for (SysUserRole userRole : sysUserRoles) {
                    sysUserRoleMap.put(userRole.getRoleId(), userRole);
                }
                HashSet<Long> duplicateIds = new HashSet<>();
                //去除前台接口ID集合与已经存在接口ID集合重复部分，前台剩余为新增，后台剩余为删除
                for (Long roleId : roleIds) {
                    if (sysUserRoleMap.keySet().contains(roleId)) {
                        duplicateIds.add(roleId);
                    }
                }

                for (Long roleId : duplicateIds) {
                    roleIds.remove(roleId);
                    sysUserRoleMap.remove(roleId);
                }

                for (Long roleId : roleIds) {
                    SysUserRole sysUserRole = new SysUserRole();
                    sysUserRole.setUserId(user.getId());
                    sysUserRole.setRoleId(roleId);
                    sysUserRole.setStatus(1);
                    sysUserRole.setCreateTime(operateDate);
                    sysUserRole.setCreateUser(operatorName);
                    sysUserRole.setLastUpdateTime(operateDate);
                    sysUserRole.setLastUpdateUser(operatorName);
                    sysUserRoleMapper.insertSelective(sysUserRole);
                }

                for (SysUserRole sysUserRole : sysUserRoleMap.values()) {
                    sysUserRole.setStatus(0);
                    sysUserRole.setLastUpdateTime(operateDate);
                    sysUserRole.setLastUpdateUser(operatorName);
                    sysUserRoleMapper.updateByPrimaryKeySelective(sysUserRole);
                }
            }
        }
    }

    /**
     * 验证用户
     *
     * @param user
     */
    private void validateUser(SysUser user) {
        //删除不做验证
        if (user.getStatus() == 0) {
            return;
        }
        if (user.getUsername() == null || "".equals(user.getUsername())) {
            throw new SystemException(ErrorCode.MODIFY_DATA_FAILED, "用户名不能为空！");
        }
        if (user.getPhoneNumber() == null || "".equals(user.getPhoneNumber())) {
            throw new SystemException(ErrorCode.MODIFY_DATA_FAILED, "手机号不能为空！");
        }
        Pattern pattern = Pattern.compile("^1[34578]\\d{9}$");
        Matcher matcher = pattern.matcher(user.getPhoneNumber());
        if (!matcher.find()) {
            throw new SystemException(ErrorCode.MODIFY_DATA_FAILED, "手机号格式不正确！");
        }
        if (user.getRealName() == null || "".equals(user.getRealName())) {
            throw new SystemException(ErrorCode.MODIFY_DATA_FAILED, "真实姓名不能为空！");
        }
        if (user.getNickname() == null || "".equals(user.getNickname())) {
            throw new SystemException(ErrorCode.MODIFY_DATA_FAILED, "昵称不能为空！");
        }

        //不能存在相同用户名的有效用户
        Example nameExample = new Example(SysUser.class);
        Example.Criteria nameCriteria = nameExample.createCriteria();
        nameCriteria.andEqualTo("status", 1);
        nameCriteria.andEqualTo("username", user.getUsername());
        if (user.getId() != null) {
            nameCriteria.andNotEqualTo("id", user.getId());
        }
        List<SysUser> users = sysUserMapper.selectByExample(nameExample);
        if (users.size() > 0) {
            throw new SystemException(ErrorCode.MODIFY_DATA_FAILED, "用户名已经存在不能使用！");
        }

        //一个手机号只能被一个有效用户使用
        Example phoneExample = new Example(SysUser.class);
        Example.Criteria phoneCriteria = phoneExample.createCriteria();
        phoneCriteria.andEqualTo("status", 1);
        phoneCriteria.andEqualTo("phoneNumber", user.getPhoneNumber());
        if (user.getId() != null) {
            phoneCriteria.andNotEqualTo("id", user.getId());
        }
        List<SysUser> users2 = sysUserMapper.selectByExample(phoneExample);
        if (users2.size() > 0) {
            throw new SystemException(ErrorCode.MODIFY_DATA_FAILED, "手机号已经被其他用户使用！");
        }
    }

    @Override
    public List<SysUser> getGrantAbleUsers() {
        return sysUserMapper.getGrantAbleUsers();
    }

    public void assambleRoleAndPermission(SysUser user){
        List<SysRole> roles;
        List<String> resourcePaths;
        List<String> interfacePaths;

        roles = sysRoleMapper.getRolesByUserId(user.getId());
        resourcePaths = sysResourceMapper.getResourcePathsByUserId(user.getId());
        interfacePaths = sysInterfaceMapper.getInterfacePathsByUserId(user.getId());

        user.setRoles(roles);
        List<String> allPermission = new ArrayList<>();
        allPermission.addAll(resourcePaths);
        allPermission.addAll(interfacePaths);
        user.setPermissions(allPermission);
    }

}