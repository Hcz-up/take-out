package com.hcz.reggie.common;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;


import java.time.LocalDateTime;

/**
 * @title: MyMetaObjectHandler
 * @Author Tan
 * @Date: 2022/4/26 10:00
 * @Version 1.0
 */
@Slf4j
@Component
public class MyMetaObjectHandler implements MetaObjectHandler {
    /**
     * 插入时对公共字段自动填充
     * @param metaObject
     */
    @Override
    public void insertFill(MetaObject metaObject) {
//        log.info("" + Thread.currentThread().getId());
        metaObject.setValue("createTime", LocalDateTime.now());
        metaObject.setValue("updateTime", LocalDateTime.now());
        metaObject.setValue("createUser", BaseContext.getId());
        metaObject.setValue("updateUser", BaseContext.getId());
    }

    /**
     * 更新时对公共字段自动填充
     * @param metaObject
     */
    @Override
    public void updateFill(MetaObject metaObject) {
        metaObject.setValue("updateTime", LocalDateTime.now());
        metaObject.setValue("updateUser", BaseContext.getId());
    }
}
