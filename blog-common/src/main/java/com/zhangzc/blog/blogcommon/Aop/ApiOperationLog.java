package com.zhangzc.blog.blogcommon.Aop;

import java.lang.annotation.*;

@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ApiOperationLog {
    /**
     *  功能描述
     * */
    String description() default "";
}
