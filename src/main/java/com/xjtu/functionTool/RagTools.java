package com.xjtu.functionTool;


import com.xjtu.entity.HandleMessageResult;
import com.xjtu.sevice.MessageHandleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Slf4j
// 判断是否需要调用向量数据库
public class RagTools {

/*    @Autowired
    private MessageHandleService messageHandleService;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Tool(description = "这个方法用于需要调用向量数据库的场景")
    public HandleMessageResult isRag(@ToolParam(description = "该参数为sessionID")String sessionId
            ,@ToolParam(description = "该参数为用户消息")String message){
        stringRedisTemplate.opsForValue().set("rag","true");
        log.info("调用向量数据库tool...");
        HandleMessageResult prompt = messageHandleService.handleMessage(sessionId, message);
        return prompt;
    }*/
}
