package com.zhangzc.blog.blogcommon.Aop.Aspect;

import com.zhangzc.blog.blogcommon.Aop.AfterBlogSetting;
import com.zhangzc.blog.blogcommon.Service.TBlogSettingsService;
import com.zhangzc.blog.blogcommon.Utils.CommentUtil;
import com.zhangzc.blog.blogcommon.pojo.domain.TBlogSettings;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.stereotype.Component;

@Aspect
@Component
@RequiredArgsConstructor
public class AfterBlogSettingAspect {

    private final CommentUtil commentUtil;

    @Pointcut("@annotation(com.zhangzc.blog.blogcommon.Aop.AfterBlogSetting)")
    public void addPointByArtID() {
    }

    @After("addPointByArtID()")
    public void afterAdvice(JoinPoint joinPoint) {
        try {
            // 获取方法签名
            MethodSignature signature = (MethodSignature) joinPoint.getSignature();
            // 获取注解
            AfterBlogSetting annotation = signature.getMethod().getAnnotation(AfterBlogSetting.class);
            if (annotation != null) {
                ExpressionParser parser = new SpelExpressionParser();
                StandardEvaluationContext context = new StandardEvaluationContext();
                // 将方法参数放入上下文
                String[] parameterNames = signature.getParameterNames();
                Object[] args = joinPoint.getArgs();
                for (int i = 0; i < parameterNames.length; i++) {
                    context.setVariable(parameterNames[i], args[i]);
                }
                // 解析表达式获取 isCommentSensiWordOpen 属性值
                Expression exp1 = parser.parseExpression(annotation.isCommentSensiWordOpen());
                boolean isCommentSensiWordOpen = (Boolean) exp1.getValue(context);
                // 解析表达式获取 isCommentExamineOpen 属性值
                Expression exp2 = parser.parseExpression(annotation.isCommentExamineOpen());
                boolean isCommentExamineOpen = (Boolean) exp2.getValue(context);

                commentUtil.setCommentExamineOpen(isCommentExamineOpen ? 1 : 0);
                commentUtil.setCommentSensiWordOpen(isCommentSensiWordOpen ? 1 : 0);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
