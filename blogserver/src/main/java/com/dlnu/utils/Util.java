package com.dlnu.utils;

import com.dlnu.pojo.User;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.UUID;

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
        return (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    /**
     * 去除文章内容中的 HTML 标签，返回纯文本内容
     *
     * @param content 文章内容
     * @return 去除 HTML 标签后的纯文本内容
     */

    public static String stripHtml(String content) {
        content = content.replaceAll("<p .*?>", "");
        content = content.replaceAll("<br\\s*/?>", "");
        content = content.replaceAll("\\<.*?>", "");
        return content;
    }

    // 生成随机字符串
    public static String generateUUID() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }
}