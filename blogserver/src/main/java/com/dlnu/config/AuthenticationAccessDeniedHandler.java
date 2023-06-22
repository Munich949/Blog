package com.dlnu.config;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * 这是一个自定义的访问拒绝处理器类，
 * 实现了Spring Security的AccessDeniedHandler接口。
 * 当用户访问被拒绝时，该处理器会返回一个403 Forbidden的响应，
 * 并输出"权限不足，请联系管理员！"的错误信息。
 */
public class AuthenticationAccessDeniedHandler implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest httpServletRequest, HttpServletResponse resp, AccessDeniedException e) throws IOException, ServletException {
        resp.setStatus(HttpServletResponse.SC_FORBIDDEN); // 设置响应状态为403 Forbidden
        resp.setCharacterEncoding("UTF-8"); // 设置响应字符编码为UTF-8
        PrintWriter out = resp.getWriter(); // 获取响应输出流
        out.write("权限不足,请联系管理员!"); // 输出错误信息
        out.flush(); // 刷新输出流
        out.close(); // 关闭输出流
    }
}
