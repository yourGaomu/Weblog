package com.zhangzc.blog.blogweb;

import lombok.RequiredArgsConstructor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication(scanBasePackages = {"com.zhangzc.blog.blogadmin"
        , "com.zhangzc.blog.blogcommon"
        , "com.zhangzc.blog.blogjwt"
        , "com.zhangzc.blog.blogweb"
        , "com.zhangzc.blog.blogxxljob"
        , "com.zhangzc.blog.blogsearch"
})
@MapperScan("com.zhangzc.blog.blogcommon.Mapper")
@EnableAsync
@EnableConfigurationProperties
@RequiredArgsConstructor
@EnableCaching
public class BlogWebApplication {


    public static void main(String[] args) {
        ConfigurableApplicationContext run = SpringApplication.run(BlogWebApplication.class, args);
    }

}
