package com.xjtu.component;

import org.springframework.ai.embedding.Embedding;
import org.springframework.ai.embedding.EmbeddingRequest;
import org.springframework.ai.embedding.EmbeddingResponse;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

// 嵌入模型需要单独的嵌入模型api，deepseek不支持嵌入模型

/*@Component
public class LangchainEmbeddingAdapter implements org.springframework.ai.embedding.EmbeddingModel {

    private final dev.langchain4j.model.embedding.EmbeddingModel langchainEmbedding;

    public LangChainEmbeddingAdapter() {
        this.langchainEmbedding = new AllMiniLmL6V2EmbeddingModel();
    }

    @Override
    public EmbeddingResponse call(EmbeddingRequest request) {
        List<String> texts = request.getInstructions();
        List<Embedding> embeddings = new ArrayList<>();

        for (int i = 0; i < texts.size(); i++) {
            dev.langchain4j.model.embedding.Embedding lcEmbedding =
                    langchainEmbedding.embed(texts.get(i));
            embeddings.add(new Embedding(lcEmbedding.vector(), i));
        }

        return new EmbeddingResponse(embeddings);
    }

    @Override
    public float[] embed(org.springframework.ai.document.Document document) {
        return langchainEmbedding.embed(document.getText()).vector();
    }

    @Override
    public int dimensions() {
        return langchainEmbedding.dimensions();
    }
}*/

