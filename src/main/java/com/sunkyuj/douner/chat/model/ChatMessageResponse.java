package com.sunkyuj.douner.chat.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.Date;

@Getter
@Builder
@AllArgsConstructor
public class ChatMessageResponse {
    private Long chatContentId;
    private Long chatRoomId;
    private Long userId;
    private String message;
    private Date created;
}
