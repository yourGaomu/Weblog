package com.zhangzc.blog.blogcommon.Aop.Aspect;

import com.zhangzc.blog.blogcommon.Utils.CommentUtil;
import com.zhangzc.blog.blogcommon.Utils.R;
import com.zhangzc.blog.blogcommon.pojo.domain.TBlogSettings;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
@RequiredArgsConstructor
public class AroundBlogSettingAspect {
    private final CommentUtil commentUtil;


    @Pointcut("@annotation(com.zhangzc.blog.blogcommon.Aop.AroundBlogSetting)")
    public void pointCut() {}

    @Around("pointCut()")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        //获得程序运行之后的结果
        Object proceed = joinPoint.proceed();
        //强转为我的运行之后的结果
        R r = (R) proceed;
        Object data = r.getData();
        //将data强转为TBlogSettings
        TBlogSettings myresult = (TBlogSettings) data;
        //获取值将这个设置为静态变量
        Integer isCommentExamineOpen = myresult.getIsCommentExamineOpen();
        Integer isCommentSensiWordOpen = myresult.getIsCommentSensiWordOpen();
        commentUtil.setCommentSensiWordOpen(isCommentSensiWordOpen);
        commentUtil.setCommentExamineOpen(isCommentExamineOpen);
        return proceed;
    }
}
