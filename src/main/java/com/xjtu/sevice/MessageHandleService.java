package com.xjtu.sevice;


import com.xjtu.component.SentenceSplitterAdapter;
import com.xjtu.entity.HandleMessageResult;
import com.xjtu.entity.MessageType;
import com.xjtu.util.QuestionDetector;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

// 使用ai判断用户发送的消息是加入到chatmemory还是vectorstore
@Service
@Slf4j
public class MessageHandleService {

    @Autowired
    private ChatClient chatClient;
    @Autowired
    private ChatMemory chatMemory;
    @Autowired
    private VectorStore vectorStore;
    @Autowired
    private SentenceSplitterAdapter sentenceSplitte;
    // 消息类型（问题/知识/未知）

    public record MessageHandlingResult(MessageType type, String decision, String aiReasoning) {}

    // 判断用户发送信息是
    public HandleMessageResult handleMessage(String sessionId, String message){
        // 提示词
        String classificationPrompt = """
        请判断下面的用户输入是一个“问题”（question）还是“知识陈述”（knowledge）。
        仅返回 "QUESTION" 或 "KNOWLEDGE"，不要附加其他内容。

        用户输入：
        "%s"
        """.formatted(message);
        String modelResponse = chatClient.prompt(classificationPrompt)
                .call()
                .content()
                .trim()
                .toUpperCase();

        MessageType messageType;
        if (modelResponse.contains("QUESTION")) {
            messageType = MessageType.QUESTION;
        } else if (modelResponse.contains("KNOWLEDGE")) {
            messageType = MessageType.KNOWLEDGE;
        } else {
            messageType = MessageType.UNKNOWN;
        }
        switch (messageType){
            case QUESTION -> {
                log.info("判断用户消息为Question");
                List<Message> prompt = handleQuestion(sessionId, message);
                return new HandleMessageResult(MessageType.QUESTION,prompt);
            }
            case KNOWLEDGE -> {
                log.info("判断用户消息为Knowledge");
                List<Message> prompt = handleKnowledge(sessionId, message);
                return new HandleMessageResult(MessageType.KNOWLEDGE,prompt);
            }
            default -> {
                log.info("未能判断用户消息是Question还是Knowledge");
                List<Message> prompt = handleUnknow(sessionId, message);
                return new HandleMessageResult(MessageType.UNKNOWN,prompt);
            }
        }
    }

    // AI判断是问题，构建prompt
    private List<Message> handleQuestion(String sessionId, String message) {
        // 1. 获取对话历史
        List<Message> history = new ArrayList<>(chatMemory.get(sessionId));
        // 2. 在vector中相似查找问题相关内容
        List<Document> transform = sentenceSplitte.transform(List.of(new Document(message)));
        transform.stream()
                .forEach(item->{
                    boolean isQuestion = QuestionDetector.isQuestion(item.getText());
                    // 只查找非问题片
                    if(!isQuestion) {
                        List<Document> documents = vectorStore.similaritySearch(new SearchRequest()
                                .builder()
                                .query(item.getText())
                                .topK(3)
                                .build());
                        List<UserMessage> collect = documents.stream()
                                .map(doc -> new UserMessage(doc.getText()))
                                .collect(Collectors.toList());
                        history.addAll(collect);
                    }
                    history.add(new UserMessage(item.getText()));
                });
        // 3. 添加到chatmemory
        log.info("已将question添加到history...");
        chatMemory.add(sessionId,new UserMessage(message));
        // 4. 返回prompt
        return history;
    }

    // AI判断是知识，构建prompt
    public List<Message> handleKnowledge(String sessionId, String message){
        // 1. 获取对话历史
        List<Message> history = new ArrayList<>(chatMemory.get(sessionId));
        // 2. 将知识内容添加到vector
        for (Document document : sentenceSplitte.transform(List.of(new Document(message)))) {
            // 非问题部分添加到vector
            if(!QuestionDetector.isQuestion(document.getText())){
                List<Document> documents = vectorStore.similaritySearch(new SearchRequest()
                        .builder()
                        .query(document.getText())
                        .similarityThreshold(0.9)
                        .topK(1)
                        .build());
                if(documents.isEmpty()){  // 说明未找到相似信息，添加到vector
                    log.info("已经将用户消息添加到vectorstore...");
                    vectorStore.add(List.of(document));
                }
            }else {
                // 问题部分添加到history
                history.add(new UserMessage(document.getText()));
            }
        }
        return history;
    }

    // 不知道是问题还是知识，直接返回历史
    public List<Message> handleUnknow(String sessionId,String message){
        // 1. 获取历史
        List<Message> history = new ArrayList<>(chatMemory.get(sessionId));
        history.add(new UserMessage(message));
        return history;
    }
}
