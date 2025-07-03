package com.zhangzc.blog.blogai.AiService;


import dev.langchain4j.service.spring.AiService;
import dev.langchain4j.service.spring.AiServiceWiringMode;

@AiService(
        wiringMode = AiServiceWiringMode.EXPLICIT,
        chatModel = "openAiChatModel"
)
public interface ChatCommonService {

    String chat(String userMessage);
}
