package com.sunkyuj.douner.chat.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Builder
@AllArgsConstructor
public class ChatRoomResponse {
    private Long chatRoomId;
}
