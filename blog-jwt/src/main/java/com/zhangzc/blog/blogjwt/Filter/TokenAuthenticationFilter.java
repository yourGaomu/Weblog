package com.zhangzc.blog.blogjwt.Filter;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.zhangzc.blog.blogjwt.Utils.JwtUtil;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

@Component
@RequiredArgsConstructor
@Slf4j
public class TokenAuthenticationFilter extends OncePerRequestFilter {
    private final JwtUtil jwtUtil;

    private final UserDetailsService userDetailsService;

    private final AuthenticationEntryPoint authenticationEntryPoint;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String authorization = request.getHeader("Authorization");
        System.out.println(authorization);
        if (authorization!= null && authorization.startsWith("Bearer ")) {
            String token = authorization.substring(7);
            log.info(token);

            try {
                jwtUtil.validateToken(token);
            } catch (MalformedJwtException | UnsupportedJwtException | IllegalArgumentException e) {
                authenticationEntryPoint.commence(request, response, new AuthenticationServiceException("Token 不可用"));
                return;
            } catch (ExpiredJwtException e) {
                authenticationEntryPoint.commence(request, response, new AuthenticationServiceException("Token 已失效"));
                return;
            }
            // 从 Token 中解析出用户名
            String username = jwtUtil.getUsernameByToken(token);

            if (StringUtils.isNotBlank(username)
                    && Objects.isNull(SecurityContextHolder.getContext().getAuthentication())) {
                // 根据用户名获取用户详情信息
                UserDetails userDetails = userDetailsService.loadUserByUsername(username);
                // 将用户信息存入 authentication，方便后续校验
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null,
                        userDetails.getAuthorities());
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                // 将 authentication 存入 ThreadLocal，方便后续获取用户信息
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }
        // 继续执行下一个过滤器
        filterChain.doFilter(request, response);
    }
}

