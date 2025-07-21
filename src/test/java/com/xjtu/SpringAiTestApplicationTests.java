package com.xjtu;

import com.xjtu.component.MyTextReader;
import org.junit.jupiter.api.Test;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.vectorstore.QuestionAnswerAdvisor;
import org.springframework.ai.document.Document;
import org.springframework.ai.document.DocumentReader;
import org.springframework.ai.document.DocumentTransformer;
import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.ai.reader.TextReader;
import org.springframework.ai.transformer.splitter.TokenTextSplitter;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.ai.vectorstore.redis.RedisVectorStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.Resource;

import javax.print.Doc;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@SpringBootTest
class SpringAiTestApplicationTests {
    @Autowired
    private VectorStore vectorStore;

    @Autowired
    // @Qualifier("myCustomDeepSeekChatClient")
    private ChatClient chatClient;

    // ollama嵌入模型
    @Autowired
    private EmbeddingModel embeddingModel;

    // langchain4j的文本分割器
    @Autowired
    private DocumentTransformer sentenceSplitter;

    @Autowired
    private MyTextReader textReader;

    @Value("classpath:info.txt")
    private Resource resource;

    @Test
    void contextLoads() {
    }
    // 文档构造方法
    private List<Document> generateSampleDocuments() {
        List<Document> docs = new ArrayList<>();
        Map<Integer, String> categories = Map.of(
                1, "Spring 框架", 2, "Redis 数据库", 3, "MySQL 数据库",
                4, "Java 基础", 5, "多线程并发", 6, "微服务",
                7, "HTTP 协议", 8, "数据结构", 9, "算法基础", 10, "网络安全"
        );
        for (int category = 1; category <= 10; category++) {
            String topic = categories.get(category);
            for (int i = 1; i <= 10; i++) {
                String text = """
                    这是关于【%s】的第 %d 条知识。
                    %s 是一个重要的技术方向，具有广泛的应用场景。
                    比如说第 %d 条，主要讲解了 %s 的一个核心概念或实践案例。
                    """.formatted(topic, i, topic, i, topic);
                String id = "doc-%d-%02d".formatted(category, i);
                docs.add(new Document(text, Map.of("id", id, "category", topic)));
            }
        }
        return docs;
    }

    @Test
    void addToVector(){
        List<Document> docs = generateSampleDocuments();
        vectorStore.add(docs);
        System.out.println("✅ 插入文档成功，总数：" + docs.size());
    }

    @Test
    void testVector(){
        /*Document d1 = new Document("今天晚上天气真不错！", Map.of("time", "19:00"));
        Document d2 = new Document("适合散步或者骑车锻炼，温度刚好合适！");
        Document d3 = new Document("刘慈欣是一名著名的科幻小说作家！");
        Document prom = new Document("今年的雨果奖是谁呢？");
        List<Document> ls = List.of(d1,d2,d3);
        vectorStore.add(ls);
        String formattedContent = d1.getFormattedContent();
        System.out.println(formattedContent);
        Map<String, Object> metadata = d1.getMetadata();
        for(Map.Entry<String,Object> entry: metadata.entrySet()){
            System.out.println(entry.getKey()+":"+entry.getValue());
        }*/
        Document doc = new Document("Spring AI is awesome", Map.of("id", "doc123"));
        // vectorStore.add(List.of(doc));
        vectorStore.delete(List.of("doc123"));
        System.out.println("---------");
        List<Document> results = vectorStore.similaritySearch(SearchRequest.builder().query("今年的雨果奖是谁呢？").topK(2).build());
        results.stream().forEach(item-> System.out.println(item.getFormattedContent()));
    }

    @Test
    public void testRAG(){

        String question = "网络安全相关案例";
        for (Document item : vectorStore.similaritySearch(question)) {
            System.out.println(item.getFormattedContent().toString());
        }

        String content = chatClient.prompt()
                .advisors(new QuestionAnswerAdvisor(vectorStore))
                .user(question)
                .call()
                .content();
        System.out.println("AI:"+content);
    }

    @Test
    public void testSplitter() {
        TextReader textReader = new TextReader(resource);
        List<Document> documents = textReader.get();
        for(Document doc:documents){
            System.out.println(doc.getText());
            System.out.println("-----");
        }
        System.out.println(documents.size());
        System.out.println("==========");
        List<Document> apply = sentenceSplitter.transform(documents);
        //List<Document> apply = tokenTextSplitter.apply(documents);
        for (Document doc : apply) {
            System.out.println(doc.getText());
            System.out.println("----");
        }
        System.out.println(apply.size());
        vectorStore.add(apply);
        List<Document> documents1 = vectorStore.similaritySearch(SearchRequest
                .builder()
                .query("技能")
                .topK(1)
                .build());
        for(Document doc:documents1){
            System.out.println(doc.getText());
            System.out.println("-----------------------");
        }
    }

    @Test
    public void test2(){

        Document doc1 = new Document("This is a long piece of text that needs to be split into smaller chunks for processing.",
                Map.of("source", "example.txt"));
        Document doc2 = new Document("Another document with content that will be split based on token count.",
                Map.of("source", "example2.txt"));

        TokenTextSplitter splitter = new TokenTextSplitter();
        List<Document> docs = List.of(doc1, doc2);
        for (Document doc : docs) {
            System.out.println("Chunk: " + doc.getText());
            System.out.println("Metadata: " + doc.getMetadata());
        }
        System.out.println("=======================");
        List<Document> splitDocuments = splitter.apply(docs);

        for (Document doc : splitDocuments) {
            System.out.println("Chunk: " + doc.getText());
            System.out.println("Metadata: " + doc.getMetadata());
        }
    }

    @Test
    public void testEmbed(){
        String content = chatClient.prompt()
                .user("你好")
                .call()
                .content();
        System.out.println(content);
    }

}
