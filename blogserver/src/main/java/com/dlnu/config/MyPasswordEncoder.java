package com.dlnu.config;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.util.DigestUtils;

/**
 * 这是一个自定义的密码编码器类，
 * 实现了Spring Security的PasswordEncoder接口。
 * 它使用MD5算法对原始密码进行加密，并提供了密码匹配的方法。
 */
@Component
public class MyPasswordEncoder implements PasswordEncoder {
    @Override
    public String encode(CharSequence rawPassword) {
        // 使用MD5算法对原始密码进行加密，并返回加密后的结果
        return DigestUtils.md5DigestAsHex(rawPassword.toString().getBytes());
    }

    @Override
    public boolean matches(CharSequence rawPassword, String encodedPassword) {
        // 将原始密码进行加密，并与已加密的密码进行比较，判断是否匹配
        return encodedPassword.equals(DigestUtils.md5DigestAsHex(rawPassword.toString().getBytes()));
    }
}
