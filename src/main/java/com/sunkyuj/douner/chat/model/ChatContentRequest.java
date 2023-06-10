package com.sunkyuj.douner.chat.model;

import lombok.Getter;

import java.util.Date;

@Getter
public class ChatContentRequest {
    private Long userId;
    private String content;
}
