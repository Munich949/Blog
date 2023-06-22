package com.dlnu.controller;

import com.dlnu.pojo.RespBean;
import com.dlnu.pojo.User;
import com.dlnu.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 这是一个登录和注册控制器类，包含了登录、注册和相关的请求处理方法。
 * loginError方法在登录失败时返回错误信息，
 * loginSuccess方法在登录成功时返回成功信息，
 * loginPage方法用于处理未登录时的跳转页面，
 * reg方法用于处理用户注册请求。
 */
@RestController
public class LoginRegController {

    @Autowired
    UserService userService;

    /**
     * 登录失败时返回错误信息
     * @return 响应实体类
     */
    @RequestMapping("/login_error")
    public RespBean loginError() {
        return new RespBean("error", "登录失败!");
    }

    /**
     * 登录成功时返回成功信息
     * @return 响应实体类
     */
    @RequestMapping("/login_success")
    public RespBean loginSuccess() {
        return new RespBean("success", "登录成功!");
    }

    /**
     * 如果自动跳转到这个页面，说明用户未登录，返回相应的提示信息
     * @return 响应实体类
     */
    @RequestMapping("/login_page")
    public RespBean loginPage() {
        return new RespBean("error", "尚未登录，请登录!");
    }

    /**
     * 用户注册
     * @param user 用户对象
     * @return 响应实体类
     */
    @PostMapping("/reg")
    public RespBean reg(User user) {
        int result = userService.reg(user);
        if (result == 0) {
            // 注册成功
            return new RespBean("success", "注册成功!");
        } else if (result == 1) {
            // 用户名重复，注册失败
            return new RespBean("error", "用户名重复，注册失败!");
        } else {
            // 注册失败
            return new RespBean("error", "注册失败!");
        }
    }
}