package com.zhangzc.blog.blogjwt.Handle;


import com.zhangzc.blog.blogcommon.Utils.R;
import com.zhangzc.blog.blogcommon.enums.ResponseCodeEnum;
import com.zhangzc.blog.blogjwt.Exception.UsernameOrPasswordNullException;
import com.zhangzc.blog.blogjwt.Utils.AuthResponseResultUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@Component
public class RestAuthenticationFailureHandler implements AuthenticationFailureHandler {


    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        log.warn(exception.getMessage());
        if (exception instanceof UsernameNotFoundException) {
            // 用户名或密码为空
            AuthResponseResultUtil.fail(response, R.Faile(exception.getMessage()));
            return;
        } else if (exception instanceof BadCredentialsException) {
            // 用户名或密码错误
            AuthResponseResultUtil.fail(response, R.Faile("用户名或密码错误"));
            return;
        }

        // 登录失败
        AuthResponseResultUtil.fail(response, R.Faile(ResponseCodeEnum.LOGIN_FAIL));
    }

}
