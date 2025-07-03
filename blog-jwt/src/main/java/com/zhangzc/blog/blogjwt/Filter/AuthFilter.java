package com.zhangzc.blog.blogjwt.Filter;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


public class AuthFilter extends AbstractAuthenticationProcessingFilter  {
    /**
     * 指定用户登录的访问地址
     */
    public AuthFilter() {
        super(new AntPathRequestMatcher("/login", "POST"));
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException, ServletException {
        ObjectMapper mapper = new ObjectMapper();
        try {
            // 解析提交的 JSON 数据
            JsonNode jsonNode = mapper.readTree(request.getInputStream());
            String username = jsonNode.get("username").asText();
            String password = jsonNode.get("password").asText();
            UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken  = new UsernamePasswordAuthenticationToken(username, password);
            return getAuthenticationManager().authenticate(usernamePasswordAuthenticationToken);
        }catch (Exception e){
            throw new UsernameNotFoundException(e.getMessage());
        }
    }
}
