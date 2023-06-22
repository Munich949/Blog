package com.dlnu.controller;

import com.dlnu.pojo.RespBean;
import com.dlnu.service.UserService;
import com.dlnu.utils.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 这是一个用户控制器类，
 * 包含了获取当前用户的昵称、ID、邮箱等信息的请求处理方法，
 * 以及判断当前用户是否为超级管理员的方法。还包括更新用户邮箱的方法
 */
@RestController
public class UserController {

    @Autowired
    UserService userService;

    /**
     * 获取当前用户的昵称
     * @return 当前用户的昵称
     */
    @RequestMapping("/currentUserName")
    public String currentUserName() {
        return Util.getCurrentUser().getNickname();
    }

    /**
     * 获取当前用户的ID
     * @return 当前用户的ID
     */
    @RequestMapping("/currentUserId")
    public Long currentUserId() {
        return Util.getCurrentUser().getId();
    }

    /**
     * 获取当前用户的邮箱
     * @return 当前用户的邮箱
     */
    @RequestMapping("/currentUserEmail")
    public String currentUserEmail() {
        return Util.getCurrentUser().getEmail();
    }

    /**
     * 判断当前用户是否为超级管理员
     * @return 如果当前用户是超级管理员，则返回true；否则返回false
     */
    @RequestMapping("/isAdmin")
    public Boolean isAdmin() {
        List<GrantedAuthority> authorities = Util.getCurrentUser().getAuthorities();
        for (GrantedAuthority authority : authorities) {
            if (authority.getAuthority().contains("超级管理员")) {
                return true;
            }
        }
        return false;
    }

    /**
     * 更新用户邮箱
     * @param email 新的邮箱
     * @return 响应实体类
     */
    @RequestMapping(value = "/updateUserEmail",method = RequestMethod.PUT)
    public RespBean updateUserEmail(String email) {
        if (userService.updateUserEmail(email) == 1) {
            return new RespBean("success", "开启成功!");
        }
        return new RespBean("error", "开启失败!");
    }
}