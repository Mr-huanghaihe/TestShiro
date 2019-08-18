package com.baizhi.redisCache;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import javax.annotation.Resource;
import java.lang.reflect.Method;
import java.util.Set;

/**
 * @program: untitled
 * @description: 配置切面类，基于String Hash做缓存,便于分类管理
 * @author: Mr.huang
 * @create: 2019-08-14 21:10
 */
@Configuration
@Aspect
public class HashRedisCache {

    @Resource
    private RedisTemplate redisTemplate;
    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @Around("execution(* com.baizhi.service.*.show*(..))")
    public Object around(ProceedingJoinPoint proceedingJoinPoint)throws Throwable{
        System.out.println("进入环绕通知");
        StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();
        redisTemplate.setKeySerializer(stringRedisSerializer);
        redisTemplate.setHashKeySerializer(stringRedisSerializer);

        MethodSignature signature = (MethodSignature) proceedingJoinPoint.getSignature();
        Method method = signature.getMethod();
        boolean annotationPresent = method.isAnnotationPresent(AddCache.class);
        if(annotationPresent){
            String clazzName = proceedingJoinPoint.getTarget().getClass().getName();
            Signature methodName = proceedingJoinPoint.getSignature();
            Object[] args = proceedingJoinPoint.getArgs();
            StringBuilder sb = new StringBuilder();
            sb.append(methodName);
            for (Object arg : args) {
                sb.append(arg);
            }
            String key=sb.toString();
            System.out.println(key);
            Object obj =null;
            Boolean hasKey = redisTemplate.opsForHash().hasKey(clazzName,key);
            if(hasKey){
                obj = redisTemplate.opsForHash().get(clazzName,key);
            }else {
                obj = proceedingJoinPoint.proceed();
                redisTemplate.opsForHash().put(clazzName,key,obj);
            }
            return obj;
        }else {
            Object proceed = proceedingJoinPoint.proceed();
            return proceed;
        }
    }

    @After("execution(* com.baizhi.service.*.*(..))&&!execution(* com.baizhi.service.*.show*(..))")
    public void after(JoinPoint joinPoint){
        System.out.println("进入后置通知");
        String clazzName = joinPoint.getTarget().getClass().getName();
        redisTemplate.delete(clazzName);
    }
}