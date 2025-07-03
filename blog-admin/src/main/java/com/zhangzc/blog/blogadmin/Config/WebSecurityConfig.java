package com.zhangzc.blog.blogadmin.Config;


import com.zhangzc.blog.blogjwt.Config.JwtAuthenticationSecurityConfig;
import com.zhangzc.blog.blogjwt.Filter.TokenAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    private final JwtAuthenticationSecurityConfig jwtAuthenticationSecurityConfig;
    private final AuthenticationEntryPoint authenticationEntryPoint;
    private final AccessDeniedHandler deniedHandler;
    private final TokenAuthenticationFilter tokenAuthenticationFilter;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable(). // 禁用 csrf
                formLogin().disable() // 禁用表单登录
                .apply(jwtAuthenticationSecurityConfig) // 设置用户登录认证相关配置
                .and()
                .authorizeHttpRequests()
                .mvcMatchers("/admin/**").authenticated() // 认证所有以 /admin 为前缀的 URL 资源
                .anyRequest().permitAll() // 其他都需要放行，无需认证
                .and()
                .httpBasic().authenticationEntryPoint(authenticationEntryPoint) // 处理用户未登录访问受保护的资源的情况
                .and()
                .exceptionHandling().accessDeniedHandler(deniedHandler) // 处理登录成功后访问受保护的资源，但是权限不够的情况
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS) // 前后端分离，无需创建会话
                .and()
                .addFilterBefore(tokenAuthenticationFilter, UsernamePasswordAuthenticationFilter.class) // 将 Token 校验过滤器添加到用户认证过滤器之前
        ;
    }



}
