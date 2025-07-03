package com.zhangzc.blog.blogai;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@MapperScan("com.zhangzc.blog.blogai.Mapper")
@SpringBootApplication()
public class BlogAiApplication {
    public static void main(String[] args) {
        SpringApplication.run(BlogAiApplication.class, args);
    }
}