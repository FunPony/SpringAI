package com.xjtu.conf;

import com.xjtu.entity.Shop;
import com.xjtu.functionTool.BlogTools;
import com.xjtu.functionTool.DateTimeTools;
import com.xjtu.functionTool.ShopTool;
import com.xjtu.sevice.IBlogService;
import com.xjtu.sevice.IShopService;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.memory.ChatMemoryRepository;
import org.springframework.ai.chat.memory.InMemoryChatMemoryRepository;
import org.springframework.ai.chat.memory.MessageWindowChatMemory;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.support.ToolCallbacks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import java.util.List;

@Configuration
public class ChatClientConfig {

/*    @Autowired
    private IShopService shopService;

    @Autowired
    private IBlogService blogService;*/

    // 内存存储库，用于存储历史消息
    @Bean
    public ChatMemoryRepository chatMemoryRepository() {
        return new InMemoryChatMemoryRepository();
    }

    @Bean
    public ChatMemory chatMemory(ChatMemoryRepository repository) {
        // 使用工厂方法创建，避免直接调用私有构造器
        return MessageWindowChatMemory.builder()
                .chatMemoryRepository(repository)
                .maxMessages(10) // 保留最近10条消息
                .build();
    }

    @Bean//(name = "myCustomDeepSeekChatClient")
    public ChatClient chatClient(ChatModel chatModel, ChatMemory chatMemory) {
        return ChatClient.builder(chatModel)
                // 可选：添加自定义 Advisor（如日志记录、拦截器等）
                // .defaultAdvisors(new AbstractMessageAdvisor() { ... })
/*                .defaultToolCallbacks(ToolCallbacks.from(
                        List.of(
                               new DateTimeTools(),
                               new BlogTools(blogService),
                               new ShopTool(shopService)
                        )
                ))*/
                .defaultAdvisors(MessageChatMemoryAdvisor.builder(chatMemory).build())   // 默认的 Advisor，检索历史消息
                .build();
    }
}

