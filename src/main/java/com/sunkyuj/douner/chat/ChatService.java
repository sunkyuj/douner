package com.sunkyuj.douner.chat;

import com.sunkyuj.douner.chat.model.*;
import com.sunkyuj.douner.post.PostRepository;
import com.sunkyuj.douner.post.model.Post;
import com.sunkyuj.douner.user.UserRepository;
import com.sunkyuj.douner.user.model.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

// Service: Create, Update, Delete 의 로직 처리
@Service
@Slf4j
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ChatService {
    private final ChatRepository chatRepository;
    private final UserRepository userRepository;
    private final PostRepository postRepository;


    @Transactional
    public ChatRoomResponse createChatRoom(Long postId, Long volunteerId) {
        Post post = postRepository.findOne(postId);
        User requester = post.getUser();
        User volunteer = userRepository.findOne(volunteerId);
        ChatRoom chatRoom = ChatRoom.builder()
                .post(post)
                .requester(requester)
                .volunteer(volunteer)
                .build();
        Long chatRoomId = chatRepository.createChatRoom(chatRoom);
        return new ChatRoomResponse(chatRoomId);
    }

    @Transactional
    public ChatMessageResponse postChatMessage(Long chatRoomId, ChatMessageRequest chatMessageRequest) {
        User user = userRepository.findOne(chatMessageRequest.getUserId());
        ChatRoom chatRoom = chatRepository.findOneChatRoom (chatRoomId);
        ChatMessage chatMessage = ChatMessage.builder()
                .user(user)
                .chatRoom(chatRoom)
                .message(chatMessageRequest.getMessage())
                .created(new Date())
                .build();

        Long chatMessageId = chatRepository.createChatMessage(chatMessage);
        return ChatMessageResponse.builder()
                .chatMessageId(chatMessageId)
                .chatRoomId(chatRoom.getId())
                .userId(user.getId())
                .message(chatMessage.getMessage())
                .created(chatMessage.getCreated())
                .build();
    }
}
