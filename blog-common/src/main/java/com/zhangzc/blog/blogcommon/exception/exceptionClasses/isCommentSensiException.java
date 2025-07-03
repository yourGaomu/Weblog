package com.zhangzc.blog.blogcommon.exception.exceptionClasses;

import com.zhangzc.blog.blogcommon.exception.BaseExceptionInterface;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class isCommentSensiException extends Exception {
    // 异常码
    private String errorCode;
    // 错误信息
    private String Message;

    public isCommentSensiException(BaseExceptionInterface baseExceptionInterface) {
        this.errorCode = baseExceptionInterface.getCode();
        this.Message = baseExceptionInterface.getMessage();
    }

    public isCommentSensiException(String errorCode, String message) {
        this.errorCode = errorCode;
        this.Message = message;
    }
}
