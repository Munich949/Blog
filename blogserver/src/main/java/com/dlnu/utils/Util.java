package com.dlnu.utils;

import com.dlnu.pojo.User;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * 这个类是 Util，提供了一个静态方法 getCurrentUser()，用于从 SecurityContextHolder 中获取当前登录的用户。
 */
public class Util {
    /**
     * 获取当前登录的用户。
     *
     * @return 当前登录的用户对象。
     */
    public static User getCurrentUser() {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return user;
    }
}