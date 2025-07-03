package com.zhangzc.blog.blogcommon.Aop.Aspect;


import com.zhangzc.blog.blogcommon.Aop.AddPointByArtID;
import com.zhangzc.blog.blogcommon.Utils.MQUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
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
@Slf4j
@RequiredArgsConstructor
public class AddPointByArtIDAspect {
    private final MQUtil mqUtil;


    @Pointcut("@annotation(com.zhangzc.blog.blogcommon.Aop.AddPointByArtID)")
    public void apiOperationLogPointcut() {
    }

    @After("apiOperationLogPointcut()")
    public void after(JoinPoint joinPoint) {
        try {
            // 获取方法签名
            MethodSignature signature = (MethodSignature) joinPoint.getSignature();
            // 获取注解
            AddPointByArtID annotation = signature.getMethod().getAnnotation(AddPointByArtID.class);
            // 获取注解中的表达式（例如："#findArticleDetailReqVO['articleId']" ）
            String expression = annotation.value();

            if (expression != null && !expression.isEmpty()) {
                ExpressionParser parser = new SpelExpressionParser();
                Expression exp = parser.parseExpression(expression);
                StandardEvaluationContext context = new StandardEvaluationContext();
                // 将方法参数放入上下文
                String[] parameterNames = signature.getParameterNames();
                Object[] args = joinPoint.getArgs();
                for (int i = 0; i < parameterNames.length; i++) {
                    context.setVariable(parameterNames[i], args[i]);
                }
                // 解析表达式获取实际的值
                Object articleId = exp.getValue(context);
                if (articleId != null) {
                    mqUtil.addPointByArtID(articleId.toString());
                } else {
                    // 处理获取值为空的情况，比如记录日志
                }
            }
        } catch (Exception e) {
            // 处理解析表达式过程中的异常，记录日志等
            e.printStackTrace();
        }
    }
}

