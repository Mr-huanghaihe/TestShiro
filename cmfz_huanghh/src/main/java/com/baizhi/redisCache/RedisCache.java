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
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import javax.annotation.Resource;
import java.lang.reflect.Method;
import java.util.Set;

/**
 * @program: untitled
 * @description: 配置切面类，基于String String做缓存
 * @author: Mr.huang
 * @create: 2019-08-14 20:07
 */

/*@Configuration
@Aspect*/
public class RedisCache {

    /*@Resource
    private RedisTemplate redisTemplate;
    @Resource
    private StringRedisTemplate stringRedisTemplate;

    //添加缓存
    @Around("execution(* com.baizhi.service.*.show*(..))")
    public Object around(ProceedingJoinPoint proceedingJoinPoint)throws Throwable{
        System.out.println("进入环绕通知");
        //解决缓存乱码
        StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();
        redisTemplate.setKeySerializer(stringRedisSerializer);
        redisTemplate.setHashKeySerializer(stringRedisSerializer);

        //获取切面切的方法名
        MethodSignature signature = (MethodSignature) proceedingJoinPoint.getSignature();
        Method method = signature.getMethod();
        //判断该方法上是否有添加缓存的注解
        boolean annotationPresent = method.isAnnotationPresent(AddCache.class);
        //如果有注解
        if(annotationPresent){
            //设计一个key(类名+方法名+实参)   value(查询的结果)
            //获取类的全限定名
            String clazzName = proceedingJoinPoint.getTarget().getClass().getName();
            //获取方法名
            Signature methodName = proceedingJoinPoint.getSignature();
            //获取方法实参
            Object[] args = proceedingJoinPoint.getArgs();

            StringBuilder sb = new StringBuilder();
            for (Object arg : args) {
                sb.append(clazzName).append(methodName).append(arg);
            }
            //获取key
            String key = sb.toString();
            Boolean hasKey = redisTemplate.hasKey(key);
            Object obj =null;
            //判断缓存中该键否存在
            if(hasKey){
                //存在 查询redis 返回结果
                obj = redisTemplate.opsForValue().get(key);
            }else{
                //不存在  返回查询结果并纳入redis缓存
                obj = proceedingJoinPoint.proceed();
                redisTemplate.opsForValue().set(key,obj);
            }
            return obj;
        }else{
            //没有该注解直接放行
            Object proceed = proceedingJoinPoint.proceed();
            return proceed;
        }
    }

    @After("execution(* com.baizhi.service.*.*(..))&&!execution(* com.baizhi.service.*.show*(..))")
    public void after(JoinPoint joinPoint){
        System.out.println("进入后置通知");
        //类的全限定名
        String clazzName = joinPoint.getTarget().getClass().getName();
        //获取缓存中所有的key
        Set<String> keys = stringRedisTemplate.keys("*");
        for (String key : keys) {
            if(key.startsWith(clazzName)){
                stringRedisTemplate.delete(key);
            }
        }
    }*/
}