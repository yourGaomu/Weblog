package com.zhangzc.blog.blogjwt.Handle;

import com.zhangzc.blog.blogcommon.Utils.R;
import com.zhangzc.blog.blogcommon.enums.ResponseCodeEnum;
import com.zhangzc.blog.blogjwt.Utils.AuthResponseResultUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@Slf4j
public class RestAccessDeniedHandler implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException)
            throws IOException, ServletException {
        log.warn("登录成功访问收保护的资源，但是权限不够: ", accessDeniedException);
        AuthResponseResultUtil.fail(response, R.Faile(ResponseCodeEnum.UNAUTHORIZED));
    }
}
