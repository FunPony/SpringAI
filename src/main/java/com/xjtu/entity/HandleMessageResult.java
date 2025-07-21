package com.xjtu.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.ai.chat.messages.Message;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HandleMessageResult {
    private MessageType type;
    private List<Message> prompt;
}
