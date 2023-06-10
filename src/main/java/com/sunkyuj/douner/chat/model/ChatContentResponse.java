package com.sunkyuj.douner.chat.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Builder
@AllArgsConstructor
public class ChatContentResponse {
    private Long chatContentId;
    private Long chatRoomId;
    private Long userId;
    private String content;
    private Date created;
}
