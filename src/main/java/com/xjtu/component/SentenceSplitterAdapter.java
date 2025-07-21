package com.xjtu.component;

import dev.langchain4j.data.document.splitter.DocumentBySentenceSplitter;
import dev.langchain4j.data.segment.TextSegment;
import org.springframework.ai.document.Document;
import org.springframework.ai.document.DocumentTransformer;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class SentenceSplitterAdapter implements DocumentTransformer {

    // 这里使用langchain4j的文本分割器来自定义splitter
    private final DocumentBySentenceSplitter splitter = new DocumentBySentenceSplitter(200,10);

    @Override
    public List<Document> transform(List<Document> input) {
        List<Document> result = new ArrayList<>();
        for (Document doc : input) {
            dev.langchain4j.data.document.Document l4jDoc = new dev.langchain4j.data.document.Document(doc.getText());
            List<TextSegment> segments = splitter.split(l4jDoc);
            for (TextSegment segment : segments) {
                result.add(new Document(segment.text(), doc.getMetadata()));
            }
        }
        return result;
    }

    @Override
    public List<Document> apply(List<Document> documents) {
        return transform(documents);
    }
}

