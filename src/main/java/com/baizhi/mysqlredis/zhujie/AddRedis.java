package com.baizhi.mysqlredis.zhujie;


import java.lang.annotation.*;


@Retention(RetentionPolicy.RUNTIME)  //注解的声明周期，用于定义注解的存活阶段，可以存活在源码级别、编译级别(字节码级别)、运行时级别。
@Target({ElementType.METHOD})  //用于定义注解可以在什么地方使用 方法上使用
public @interface AddRedis {
}
