package com.sunkyuj.douner.chat;

import com.sunkyuj.douner.chat.model.*;
import com.sunkyuj.douner.user.UserRepository;

import com.sunkyuj.douner.user.model.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    public List<ChatMessageResponse> getAllChatMessage(Long chatRoomId) {
        ChatRoom chatRoom = chatRepository.findOneChatRoom(chatRoomId);
        List<ChatMessage> chatMessages = chatRepository.getAllChatMessage(chatRoom);//getUser(userIdx)를 반환받아서 반환한다.
        List<ChatMessageResponse> chatMessageResponseList = new ArrayList<>();

        for (ChatMessage chatMessage : chatMessages) {
            ChatMessageResponse chatMessageResponse = ChatMessageResponse.builder()
                    .chatMessageId(chatMessage.getId())
                    .chatRoomId(chatRoomId)
                    .userId(chatMessage.getUser().getId())
                    .message(chatMessage.getMessage())
                    .created(chatMessage.getCreated())
                    .build();
            chatMessageResponseList.add(chatMessageResponse);
        }
        return chatMessageResponseList;
    }
}


