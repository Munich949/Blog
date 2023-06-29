package com.dlnu.controller;

import com.dlnu.pojo.RespBean;
import com.dlnu.pojo.User;
import com.dlnu.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import static com.dlnu.utils.CommunityConstant.ACTIVATION_REPEAT;
import static com.dlnu.utils.CommunityConstant.ACTIVATION_SUCCESS;

/**
 * 这是一个登录和注册控制器类，包含了登录、注册和相关的请求处理方法。
 * loginError方法在登录失败时返回错误信息，
 * loginSuccess方法在登录成功时返回成功信息，
 * loginPage方法用于处理未登录时的跳转页面，
 * reg方法用于处理用户注册请求。
 */
@Controller
@Slf4j
public class LoginRegisterController {

    @Autowired
    UserService userService;

    /**
     * 登录失败时返回错误信息
     *
     * @return 响应实体类
     */
    @ResponseBody
    @RequestMapping("/login_error")
    public RespBean loginError() {
        return new RespBean("error", "登录失败!");
    }

    /**
     * 登录成功时返回成功信息
     *
     * @return 响应实体类
     */
    @ResponseBody
    @RequestMapping("/login_success")
    public RespBean loginSuccess() {
        return new RespBean("success", "登录成功!");
    }

    /**
     * 如果自动跳转到这个页面，说明用户未登录，返回相应的提示信息
     *
     * @return 响应实体类
     */
    @ResponseBody
    @RequestMapping("/login_page")
    public RespBean loginPage() {
        return new RespBean("error", "尚未登录，请登录!");
    }

    /**
     * 用户注册
     *
     * @param user 用户对象
     * @return 响应实体类
     */
    @ResponseBody
    @PostMapping("/register")
    public RespBean register(User user) {
        int result = userService.register(user);
        if (result == 0) {
            // 注册成功
            return new RespBean("activation", "待激活!");
        } else if (result == 1) {
            // 用户名重复，注册失败
            return new RespBean("error", "用户名重复，注册失败!");
        } else if (result == 2) {
            // 邮箱重复，注册失败
            return new RespBean("error", "邮箱重复，注册失败!");
        } else {
            // 注册失败
            return new RespBean("error", "注册失败!");
        }
    }

    @GetMapping("/activation/{userId}/{activationCode}")
    public String activation(Model model, @PathVariable("userId") Long userId, @PathVariable("activationCode") String activationCode) {
        int result = userService.activation(userId, activationCode);
        model.addAttribute("target", "/index.html");
        if (result == ACTIVATION_SUCCESS) {
            model.addAttribute("msg", "激活成功,您的账号已经可以正常使用了!");
        } else if (result == ACTIVATION_REPEAT) {
            model.addAttribute("msg", "无效操作,该账号已经激活过了!");
        } else {
            model.addAttribute("msg", "激活失败,您提供的激活码不正确!");
        }
        return "result";
    }
}