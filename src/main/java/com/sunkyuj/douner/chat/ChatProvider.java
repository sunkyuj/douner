package com.sunkyuj.douner.chat;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sunkyuj.douner.chat.model.*;
import com.sunkyuj.douner.security.JwtService;
import com.sunkyuj.douner.user.UserRepository;

import com.sunkyuj.douner.user.model.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.util.*;

//Provider : 비즈니스 로직 처리
@Service
@Transactional(readOnly = true)
@Slf4j
@RequiredArgsConstructor
public class ChatProvider {
//    private final JwtService jwtService;
    private final ChatRepository chatRepository;
    private final UserRepository userRepository;

    public List<ChatRoomResponse> findAllChatRoom(Long userId) {
        User user = userRepository.findOne(userId);
        List<ChatRoom> chatRooms = chatRepository.findAllChatRoom(user);

        List<ChatRoomResponse> chatRoomResponseList = new ArrayList<>();

        for (ChatRoom chatRoom : chatRooms) {
            ChatRoomResponse chatRoomResponse = ChatRoomResponse.builder()
                    .chatRoomId(chatRoom.getId())
                    .build();
            chatRoomResponseList.add(chatRoomResponse);
        }
        return chatRoomResponseList;
    }

    public List<ChatContentResponse> getAllChatContent(Long chatRoomId) {
        ChatRoom chatRoom = chatRepository.findOneChatRoom(chatRoomId);
        List<ChatContent> chatContents = chatRepository.getAllChatContent(chatRoom);//getUser(userIdx)를 반환받아서 반환한다.
        List<ChatContentResponse> chatContentResponseList = new ArrayList<>();

        for (ChatContent chatContent : chatContents) {
            ChatContentResponse chatContentResponse = ChatContentResponse.builder()
                    .chatContentId(chatContent.getId())
                    .build();
            chatContentResponseList.add(chatContentResponse);
        }
        return chatContentResponseList;
    }
}


