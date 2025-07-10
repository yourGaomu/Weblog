package com.zhangzc.blog.blogai.Tools;

import com.zhangzc.blog.blogai.Pojo.domain.TAi;
import com.zhangzc.blog.blogai.Service.TAiService;
import com.zhangzc.blog.blogai.Utils.RedisUtil;
import dev.langchain4j.agent.tool.P;
import dev.langchain4j.agent.tool.Tool;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TaiServiceTool {
    private final TAiService tAiService;
    private final RedisUtil redisUtil;

    @Tool("根据用户想要查找的相关学术文献进行查找")
    public String findArt(@P("用户输入的相关的关键词") String keyWord) {
        return null;
    }
}

