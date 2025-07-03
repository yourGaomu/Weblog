package com.zhangzc.blog.blogcommon.exception.handles;


import com.zhangzc.blog.blogcommon.Utils.R;
import com.zhangzc.blog.blogcommon.enums.ResponseCodeEnum;
import com.zhangzc.blog.blogcommon.exception.exceptionClasses.BizException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import org.springframework.security.access.AccessDeniedException;

import java.io.FileNotFoundException;


@ControllerAdvice
@ResponseBody
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(value = BizException.class)
    public R handleBizException(HttpServletRequest req, BizException e) throws Exception {
        log.warn("{} request fail, errorMessage: {}", req.getRequestURI(), e.getMessage());
        return R.Faile(e);
    }

    @ExceptionHandler({ AccessDeniedException.class })
    public void throwAccessDeniedException(AccessDeniedException e) throws AccessDeniedException {
        // 捕获到鉴权失败异常，主动抛出，交给 RestAccessDeniedHandler 去处理
        log.info("============= 捕获到 AccessDeniedException");
        throw e;
    }

    @ExceptionHandler(value = {FileNotFoundException.class})
    public R handleFileNotFoundException(HttpServletRequest req,FileNotFoundException e) throws FileNotFoundException {
        log.warn("{} request fail, errorMessage: {}", req.getRequestURI(), e.getMessage());
        return R.Faile(e);
    }


    @ExceptionHandler(value = Exception.class)
    public R handleException(HttpServletRequest req, Exception e) throws Exception {
        log.warn("{} request fail, errorMessage: {}", req.getRequestURI(), e.getMessage());
        return R.Faile(e);
    }


}
