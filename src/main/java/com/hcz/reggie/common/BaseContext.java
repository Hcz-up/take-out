package com.hcz.reggie.common;

import lombok.extern.slf4j.Slf4j;

/**
 * @title: BaseContext
 * @Author Tan
 * @Date: 2022/4/26 10:26
 * @Version 1.0
 */
/*
* BaseContext类封装了ThreadLocal类，用来保存登录用户的id
* */
    @Slf4j
public class BaseContext {
    private static ThreadLocal<Long> threadLocal = new ThreadLocal<>();


    public static void setId(Long id){
//        log.info(id + "");
        threadLocal.set(id);
    }

    public static Long getId(){
        return threadLocal.get();
    }
}
