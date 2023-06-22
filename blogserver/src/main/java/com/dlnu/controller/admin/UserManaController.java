package com.dlnu.controller.admin;

import com.dlnu.pojo.RespBean;
import com.dlnu.pojo.Role;
import com.dlnu.pojo.User;
import com.dlnu.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 这个控制器类处理了与用户管理相关的请求，
 * 包括获取用户列表、获取用户信息、获取角色列表、更新用户启用状态、删除用户和更新用户角色。
 * 每个请求方法都使用了不同的HTTP方法和路径来区分不同的操作。
 */
@RestController
@RequestMapping("/admin")
public class UserManaController {
    @Autowired
    UserService userService;

    /**
     * 根据昵称获取用户列表
     * @param nickname 用户昵称
     * @return 用户列表
     */
    @RequestMapping(value = "/user", method = RequestMethod.GET)
    public List<User> getUserByNickname(String nickname) {
        return userService.getUserByNickname(nickname);
    }

    /**
     * 根据用户ID获取用户信息
     * @param id 用户ID
     * @return 用户信息
     */
    @RequestMapping(value = "/user/{id}", method = RequestMethod.GET)
    public User getUserById(@PathVariable Long id) {
        return userService.getUserById(id);
    }

    /**
     * 获取所有角色列表
     * @return 角色列表
     */
    @RequestMapping(value = "/roles", method = RequestMethod.GET)
    public List<Role> getAllRole() {
        return userService.getAllRole();
    }

    /**
     * 更新用户启用状态
     * @param enabled 是否启用
     * @param uid 用户ID
     * @return 响应实体类
     */
    @RequestMapping(value = "/user/enabled", method = RequestMethod.PUT)
    public RespBean updateUserEnabled(Boolean enabled, Long uid) {
        if (userService.updateUserEnabled(enabled, uid) == 1) {
            return new RespBean("success", "更新成功!");
        } else {
            return new RespBean("error", "更新失败!");
        }
    }

    /**
     * 根据用户ID删除用户
     * @param uid 用户ID
     * @return 响应实体类
     */
    @RequestMapping(value = "/user/{uid}", method = RequestMethod.DELETE)
    public RespBean deleteUserById(@PathVariable Long uid) {
        if (userService.deleteUserById(uid) == 1) {
            return new RespBean("success", "删除成功!");
        } else {
            return new RespBean("error", "删除失败!");
        }
    }

    /**
     * 更新用户角色
     * @param rids 角色ID数组
     * @param id 用户ID
     * @return 响应实体类
     */
    @RequestMapping(value = "/user/role", method = RequestMethod.PUT)
    public RespBean updateUserRoles(Long[] rids, Long id) {
        if (userService.updateUserRoles(rids, id) == rids.length) {
            return new RespBean("success", "更新成功!");
        } else {
            return new RespBean("error", "更新失败!");
        }
    }
}