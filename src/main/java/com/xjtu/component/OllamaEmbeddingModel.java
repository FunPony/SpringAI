package com.xjtu.component;

import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.ai.embedding.Embedding;
import org.springframework.ai.embedding.EmbeddingRequest;
import org.springframework.ai.embedding.EmbeddingResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.*;

import java.util.*;

/*@Component
public class OllamaEmbeddingModel implements EmbeddingModel {

    private final RestTemplate restTemplate = new RestTemplate();
    private final String ollamaUrl = "http://localhost:11434/api/embeddings";
    private final String model = "nomic-embed-text";

    // Spring AI 1.0.0 要求实现 call 方法
    @Override
    public EmbeddingResponse call(EmbeddingRequest request) {
        List<String> inputs = request.getInstructions();
        List<Embedding> embeddings = embed(inputs);
        return new EmbeddingResponse(embeddings);
    }

    // 实现 embed(List<String>) 方法
    @Override
    public List<Embedding> embed(List<String> texts) {
        List<Embedding> embeddings = new ArrayList<>();

        for (String input : texts) {
            Map<String, Object> body = new HashMap<>();
            body.put("model", model);
            body.put("prompt", input);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            HttpEntity<Map<String, Object>> entity = new HttpEntity<>(body, headers);

            ResponseEntity<Map> response = restTemplate.exchange(ollamaUrl, HttpMethod.POST, entity, Map.class);

            if (response.getStatusCode() == HttpStatus.OK) {
                Map<String, Object> responseBody = response.getBody();
                List<Double> embeddingValues = (List<Double>) responseBody.get("embedding");
                embeddings.add(new Embedding(embeddingValues));
            } else {
                throw new RuntimeException("Failed to get embedding from Ollama, status = " + response.getStatusCode());
            }
        }

        return embeddings;
    }
}*/

