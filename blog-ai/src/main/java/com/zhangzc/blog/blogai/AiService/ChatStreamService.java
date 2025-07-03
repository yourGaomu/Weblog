package com.zhangzc.blog.blogai.AiService;


import dev.langchain4j.service.MemoryId;
import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.UserMessage;
import dev.langchain4j.service.spring.AiService;
import dev.langchain4j.service.spring.AiServiceWiringMode;
import reactor.core.publisher.Flux;


@AiService(
        wiringMode = AiServiceWiringMode.EXPLICIT,
        chatModel = "openAiChatModel",
        streamingChatModel = "openAiStreamingChatModel",
        chatMemoryProvider = "chatMemoryProvider"
)
public interface ChatStreamService {
    @SystemMessage(fromResource= "systemMessage.txt")
        //这里进行规定Ai的角色，就是提示词
    Flux<String> chat(@MemoryId String userId, @UserMessage String userMessage);
}
