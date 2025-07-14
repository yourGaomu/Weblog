package com.zhangzc.blog.blogsearch.Config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@ConfigurationProperties("lucene")
@Component
@Setter
@Getter
public class LuceneYml {
    private String indexDir;
}
