package com.zhangzc.blog.blogcommon.Aop.Aspect;


import com.zhangzc.blog.blogcommon.Aop.ApiOperationLog;
import com.zhangzc.blog.blogcommon.Utils.JsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;

import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.stream.Collectors;

@Aspect
@Component
@Slf4j
public class ApiOperationLogAspect {
    @Pointcut("@annotation(com.zhangzc.blog.blogcommon.Aop.ApiOperationLog)")
    public void apiOperationLogPointcut() {}

    @Around("apiOperationLogPointcut()")
    public Object  around(ProceedingJoinPoint joinPoint) throws Throwable {
        Long startTime = System.currentTimeMillis();
        //获取参数
        Object[] args = joinPoint.getArgs();
        //获取被请求类的名字
        String className = joinPoint.getTarget().getClass().getName();
        //获取被请求的方法
        String methodName = joinPoint.getSignature().getName();
        String argsJsonStr = Arrays.stream(args).map(JsonUtil::toJson).collect(Collectors.joining(", "));
        //获取功能描述
        String description = describe(joinPoint);
        log.info("====== 请求开始: [{}], 入参: {}, 请求类: {}, 请求方法: {} =================================== ",
                description, argsJsonStr, className, methodName);

        // 执行切点方法
        Object result = joinPoint.proceed();

        // 执行耗时
        long executionTime = System.currentTimeMillis() - startTime;

        // 打印出参等相关信息
        log.info("====== 请求结束: [{}], 耗时: {}ms, 出参: {} =================================== ",
                description, executionTime, JsonUtil.toJson(result));
        return result;
    }

    private String describe(ProceedingJoinPoint joinPoint) {
        //转换为MethodSignature
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        //获取注解对象
        ApiOperationLog annotation = signature.getMethod().getAnnotation(ApiOperationLog.class);
        return annotation.description();
    }

}
