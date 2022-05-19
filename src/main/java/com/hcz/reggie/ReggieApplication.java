package com.hcz.reggie;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @title: ReggieApplication
 * @Author Tan
 * @Date: 2022/4/23 11:12
 * @Version 1.0
 */

@Slf4j
@SpringBootApplication
// 为了扫描到过滤器
@ServletComponentScan
// 开启事务支持
@EnableTransactionManagement
public class ReggieApplication {
    public static void main(String[] args) {
        SpringApplication.run(ReggieApplication.class, args);
        log.info("项目启动成功...");
    }
}
