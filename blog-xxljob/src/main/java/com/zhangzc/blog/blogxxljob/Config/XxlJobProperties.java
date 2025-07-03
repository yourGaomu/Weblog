package com.zhangzc.blog.blogxxljob.Config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "xxl.job")
public class XxlJobProperties {

    private Admin admin;
    private Executor executor;
    private String accessToken;

    @Data
    public static class Admin {
        private String addresses;
    }

    @Data
    public static class Executor {
        private String appname;
        private String address;
        private String ip;
        private int port;
        private String logpath;
        private int logretentiondays;
    }
}