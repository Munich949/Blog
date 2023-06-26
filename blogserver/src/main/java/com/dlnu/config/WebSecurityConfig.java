package com.dlnu.config;

import com.dlnu.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

import javax.sql.DataSource;
import java.io.PrintWriter;

/**
 * 这是一个Web安全配置类，继承自WebSecurityConfigurerAdapter。
 * 它配置了Spring Security的认证、授权、登录、记住我、注销等相关功能。
 * 其中，configure(HttpSecurity http)方法配置了不同路径的访问权限，
 * configure(WebSecurity web)方法配置了忽略安全验证的路径，
 * getAccessDeniedHandler()方法返回了自定义的访问拒绝处理器。
 */
@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    UserService userService;

    @Autowired
    private DataSource dataSource;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/register").permitAll()   // 注册可以不用登录就能访问
                .antMatchers("/activation/**").permitAll()
                .antMatchers("/admin/category/all").authenticated()
                .antMatchers("/admin/**").hasRole("超级管理员") // /admin/**的URL都需要有超级管理员角色，如果使用.hasAuthority()方法来配置，需要在参数中加上ROLE_,如下.hasAuthority("ROLE_超级管理员")
                .anyRequest().authenticated()//其他的路径都是登录后即可访问
                .and().formLogin().loginPage("/login_page").successHandler((httpServletRequest, httpServletResponse, authentication) -> {
                    httpServletResponse.setContentType("application/json;charset=utf-8");
                    PrintWriter out = httpServletResponse.getWriter();
                    out.write("{\"status\":\"success\",\"msg\":\"登录成功\"}");
                    out.flush();
                    out.close();
                }).failureHandler((httpServletRequest, httpServletResponse, e) -> {
                    httpServletResponse.setContentType("application/json;charset=utf-8");
                    PrintWriter out = httpServletResponse.getWriter();
                    out.write("{\"status\":\"error\",\"msg\":\"登录失败\"}");
                    out.flush();
                    out.close();
                }).loginProcessingUrl("/login")
                .usernameParameter("username").passwordParameter("password").permitAll()
                .and().logout().permitAll()
                .and().csrf().disable()
                .exceptionHandling()
                .accessDeniedHandler(getAccessDeniedHandler());
        http.rememberMe()
                .tokenRepository(persistentTokenRepository())	//持久
                .tokenValiditySeconds(7 * 24 * 60 * 60)	//过期时间
                .userDetailsService(userService); //用来加载用户认证信息的
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/blogimg/**", "/index.html", "/css/**", "/js/**", "/fonts/**", "/img/**");
    }

    @Bean
    AccessDeniedHandler getAccessDeniedHandler() {
        return new AuthenticationAccessDeniedHandler();
    }

    @Bean
    public PersistentTokenRepository persistentTokenRepository() {
        JdbcTokenRepositoryImpl jdbcTokenRepository = new JdbcTokenRepositoryImpl();
        jdbcTokenRepository.setDataSource(dataSource);
        jdbcTokenRepository.setCreateTableOnStartup(false);
        return jdbcTokenRepository;
    }
}