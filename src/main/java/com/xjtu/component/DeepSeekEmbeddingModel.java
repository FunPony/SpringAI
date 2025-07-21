package com.xjtu.component;

import jakarta.annotation.Nonnull;
import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.ai.embedding.EmbeddingRequest;
import org.springframework.ai.embedding.EmbeddingResponse;
import org.springframework.ai.embedding.Embedding;
import org.springframework.ai.embedding.EmbeddingOptionsBuilder;
import org.springframework.ai.document.Document;
import org.springframework.ai.embedding.BatchingStrategy;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/*// 自定义嵌入模型
@Component
public class DeepSeekEmbeddingModel implements EmbeddingModel {

    private static final int VECTOR_DIM = 1536; // 向量维度
    private final Random random = new Random();

    @Override
    public EmbeddingResponse call(@Nonnull EmbeddingRequest request) {
        // 从 request 中通过 getInstructions() 获取文本输入列表
        List<String> inputs = request.getInstructions();

        List<float[]> embeddings = new ArrayList<>(inputs.size());
        for (String text : inputs) {
            embeddings.add(randomVector());
        }

        List<Embedding> results = new ArrayList<>();
        for (int i = 0; i < embeddings.size(); i++) {
            results.add(new Embedding(embeddings.get(i), i));
        }

        return new EmbeddingResponse(results);
    }

    @Override
    public float[] embed(Document document) {
        Assert.notNull(document, "Document must not be null");
        return randomVector();
    }

    private float[] randomVector() {
        float[] vector = new float[VECTOR_DIM];
        for (int i = 0; i < VECTOR_DIM; i++) {
            vector[i] = random.nextFloat();
        }
        return vector;
    }

    @Override
    public int dimensions() {
        return VECTOR_DIM;
    }
}*/

