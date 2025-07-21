package com.xjtu.conf;

import org.springframework.ai.document.DocumentTransformer;
import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.ai.embedding.TokenCountBatchingStrategy;
import org.springframework.ai.transformer.splitter.TokenTextSplitter;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.ai.vectorstore.redis.RedisVectorStore;
import org.springframework.ai.vectorstore.redis.autoconfigure.RedisVectorStoreAutoConfiguration;
import org.springframework.ai.vectorstore.redis.autoconfigure.RedisVectorStoreProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import redis.clients.jedis.JedisPooled;

// 手动配置redis实现向量数据库
@Configuration
public class RedisVectorConfig {

    // 这里使用的是ollma的nomic-embed-text嵌入模型
    @Bean
    public VectorStore vectorStore(JedisPooled jedisPooled, EmbeddingModel embeddingModel) {
        return RedisVectorStore.builder(jedisPooled, embeddingModel)
                .indexName("custom-index")                // rediSearch索引名称，默认 "spring-ai-index"
                .prefix("custom-prefix:")                  // redis key前缀 defaults to "embedding:"
                .metadataFields(                         // 定义想要支持的元数据字段
                        RedisVectorStore.MetadataField.tag("country"),
                        RedisVectorStore.MetadataField.numeric("year"))
                .initializeSchema(true)                   // 是否在启动时自动创建rediSearch索引 defaults to false
                .batchingStrategy(new TokenCountBatchingStrategy()) // 分批嵌入策略
                .build();
    }

    @Bean
    public JedisPooled jedisPooled() {
        return new JedisPooled("192.168.88.128", 6380);
    }

}

