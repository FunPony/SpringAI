package com.xjtu.component;

import org.springframework.ai.document.Document;
import org.springframework.ai.reader.TextReader;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class MyTextReader {

    private final Resource resource;

    MyTextReader(@Value("classpath:info.txt") Resource resource) {
        this.resource = resource;
    }

    public List<Document> loadText() {
        TextReader textReader = new TextReader(this.resource);
        textReader.getCustomMetadata().put("filename", "info.txt");

        return textReader.read();
    }
}
