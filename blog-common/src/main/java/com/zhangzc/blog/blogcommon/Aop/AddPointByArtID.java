package com.zhangzc.blog.blogcommon.Aop;

import java.lang.annotation.*;

@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface AddPointByArtID {
    String value() default "";
}
