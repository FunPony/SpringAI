package com.xjtu.controller;


import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("vector")
public class VectorController {

    @Autowired
    private ChatClient chatClient;
    @Autowired
    private VectorStore vectorStore;



}
