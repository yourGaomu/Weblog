package com.zhangzc.blog.blogcommon.exception.exceptionClasses;

import com.zhangzc.blog.blogcommon.exception.BaseExceptionInterface;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class isCommentExamineOpenException extends Exception {
    // 异常码
    private String errorCode;
    // 错误信息
    private String Message;

    public isCommentExamineOpenException(BaseExceptionInterface baseExceptionInterface) {
        this.errorCode = baseExceptionInterface.getCode();
        this.Message = baseExceptionInterface.getMessage();
    }

    public isCommentExamineOpenException(String errorCode, String message) {
        this.errorCode = errorCode;
        this.Message = message;
    }
}
