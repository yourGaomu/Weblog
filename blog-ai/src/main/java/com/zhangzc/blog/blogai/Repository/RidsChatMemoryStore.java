package com.zhangzc.blog.blogai.Repository;


import com.zhangzc.blog.blogai.Utils.RedisUtil;
import dev.langchain4j.data.message.ChatMessage;
import dev.langchain4j.data.message.ChatMessageDeserializer;
import dev.langchain4j.data.message.ChatMessageSerializer;
import dev.langchain4j.store.memory.chat.ChatMemoryStore;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@RequiredArgsConstructor
@Repository
public class RidsChatMemoryStore implements ChatMemoryStore {

    private final RedisUtil redisUtil;

    @Override
    public List<ChatMessage> getMessages(Object memoryId) {
        String json = (String) redisUtil.get(memoryId.toString());
        List<ChatMessage> chatMessages = ChatMessageDeserializer.messagesFromJson(json);
        return chatMessages;
    }

    @Override
    public void updateMessages(Object memoryId, List<ChatMessage> list) {
        String json = ChatMessageSerializer.messagesToJson(list);
        redisUtil.set(memoryId.toString(), json,60*60*24);//一天
    }

    @Override
    public void deleteMessages(Object memoryId) {
        redisUtil.del(memoryId.toString());
    }
}
