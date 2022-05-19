package com.hcz.reggie.common;

/**
 * @title: CustomException
 * @Author Tan
 * @Date: 2022/4/27 9:16
 * @Version 1.0
 */

/**
 * 自定义业务异常
 */
public class CustomException extends RuntimeException{
    public CustomException(String message){
        super(message);
    }
}
