package com.sunkyuj.douner.chat.model;

import lombok.Getter;

import java.util.Date;

@Getter
public class ChatContentRequest {
    // 메시지  타입 : 입장, 채팅
    public enum MessageType{
        ENTER, TALK
    }
    private MessageType type; // 메시지 타입
    private Long userId;
    private String content;
}
