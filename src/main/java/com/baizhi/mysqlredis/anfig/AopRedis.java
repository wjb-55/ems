package com.baizhi.mysqlredis.anfig;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import javax.annotation.Resource;
import java.util.Set;

@Aspect
@Configuration
public class AopRedis {

    @Resource
    RedisTemplate redisTemplate;

    @Resource
    StringRedisTemplate stringRedisTemplate;

    @Around("@annotation(com.baizhi.mysqlredis.zhujie.AddRedis)")
    public Object addRedis(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        System.out.println("进入环绕通知");
        /**
         解决缓存乱码问题
         * */
        StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();
        redisTemplate.setKeySerializer(stringRedisSerializer);
        redisTemplate.setHashKeySerializer(stringRedisSerializer);

        /**
         key: 类的全限定名+方法名+实参
         value：缓存数据（String）
         * */
        //创建字符串拼接对象
        StringBuilder stringBuilder = new StringBuilder();
        //获取类的全限定名
        String className = proceedingJoinPoint.getTarget().getClass().getName();
        stringBuilder.append(className);
        //获取方法名
        String methodname = proceedingJoinPoint.getSignature().getName();
        stringBuilder.append(methodname);
        //获取切面切到的方法 多个参数是个数组
        Object[] args = proceedingJoinPoint.getArgs();
        for (Object arg : args) {
            stringBuilder.append(arg);
        }


        //获取key
        String key = stringBuilder.toString();
        //获取String类型的操作对象
        ValueOperations valueOperations = redisTemplate.opsForValue();

        //从redis取出缓存判断缓存是不是存在
        Boolean aBoolean = redisTemplate.hasKey(key);

        Object resultw =null;
        //判断是否存在
        if (aBoolean){
            //存在  取出缓存
            resultw = valueOperations.get(key);
        }else{
            //不存在 查询数据库 加入缓存
            //放行切面切到的方法 获取结果
            resultw = proceedingJoinPoint.proceed();
            //加入缓存
            valueOperations.set(key,resultw);
        }
        System.out.println("出去环绕通知");
        System.out.println(resultw);
        return resultw;
    }
    @AfterReturning("@annotation(com.baizhi.mysqlredis.zhujie.DeleRedis)")
    public void DeleRedis(JoinPoint joinPoint){
        System.out.println("进入删除的aop");
        //获取类的全限类名
        String classname = joinPoint.getTarget().getClass().getName();
        Set<String> keys = stringRedisTemplate.keys("*");
        for (String key : keys) {
            //判断key是否符合要求
            if (key.startsWith(classname)){
                stringRedisTemplate.delete(key);
            }

        }
        System.out.println("出环绕通知");
    }

}
