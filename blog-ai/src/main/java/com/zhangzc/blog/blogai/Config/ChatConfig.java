package com.zhangzc.blog.blogai.Config;


import com.zhangzc.blog.blogai.Repository.RidsChatMemoryStore;
import dev.langchain4j.memory.ChatMemory;
import dev.langchain4j.memory.chat.ChatMemoryProvider;
import dev.langchain4j.memory.chat.MessageWindowChatMemory;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class ChatConfig {

    private final RidsChatMemoryStore ridsChatMemoryStore;

    @Bean
    public ChatMemory chatMemory() {
        return MessageWindowChatMemory.builder()
                .maxMessages(30)
                .build();
    }

    @Bean
    public ChatMemoryProvider chatMemoryProvider() {
        ChatMemoryProvider chatMemoryProvider = new ChatMemoryProvider() {
            @Override
            public ChatMemory get(Object memoryid) {
                return MessageWindowChatMemory.builder()
                        .maxMessages(30)
                        .id(memoryid)
                        .chatMemoryStore(ridsChatMemoryStore)
                        .build();
            }
        };
        return chatMemoryProvider;
    }
}
