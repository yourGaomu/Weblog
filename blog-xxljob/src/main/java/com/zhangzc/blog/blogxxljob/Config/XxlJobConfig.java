package com.zhangzc.blog.blogxxljob.Config;

import com.xxl.job.core.executor.impl.XxlJobSpringExecutor;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class XxlJobConfig {

    private final XxlJobProperties xxlJobProperties;

    @Bean
    public XxlJobSpringExecutor xxlJobExecutor() {
        log.info(">>>>>>>>>>> xxl-job config init.");
        XxlJobSpringExecutor executor = new XxlJobSpringExecutor();

        executor.setAdminAddresses(xxlJobProperties.getAdmin().getAddresses());
        executor.setAppname(xxlJobProperties.getExecutor().getAppname());
        executor.setAddress(xxlJobProperties.getExecutor().getAddress());
        executor.setIp(xxlJobProperties.getExecutor().getIp());
        executor.setPort(xxlJobProperties.getExecutor().getPort());
        executor.setAccessToken(xxlJobProperties.getAccessToken());
        executor.setLogPath(xxlJobProperties.getExecutor().getLogpath());
        executor.setLogRetentionDays(xxlJobProperties.getExecutor().getLogretentiondays());

        return executor;
    }
}