package com.lei;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @Author LEI
 * @Date 2021/1/19 15:00
 * @Description SpringBoot系统启动类
 */
@SpringBootApplication
@MapperScan("com.lei.*.mapper")
@EnableSwagger2
public class KnowledgeApplication {
    public static void main(String[] args) {
        SpringApplication.run(KnowledgeApplication.class);
    }
}
