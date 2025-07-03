package com.zhangzc.blog.blogcommon.exception.exceptionClasses;


import com.zhangzc.blog.blogcommon.exception.BaseExceptionInterface;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BizException extends RuntimeException {
    // 异常码
    private String errorCode;
    // 错误信息
    private String Message;

    public BizException(BaseExceptionInterface baseExceptionInterface) {
        this.errorCode = baseExceptionInterface.getCode();
        this.Message = baseExceptionInterface.getMessage();
    }

    public BizException(String errorCode, String message) {
        this.errorCode = errorCode;
        this.Message = message;
    }
}
