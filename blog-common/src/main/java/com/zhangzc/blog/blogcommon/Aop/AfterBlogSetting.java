package com.zhangzc.blog.blogcommon.Aop;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME) // 必须为 RUNTIME
public @interface AfterBlogSetting {

    String isCommentSensiWordOpen() default "false";

    String isCommentExamineOpen() default "false";
}
