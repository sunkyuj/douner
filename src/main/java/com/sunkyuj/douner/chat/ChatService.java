package com.sunkyuj.douner.chat;

import com.sunkyuj.douner.chat.model.*;
import com.sunkyuj.douner.post.PostRepository;
import com.sunkyuj.douner.post.model.Post;
import com.sunkyuj.douner.security.JwtService;
import com.sunkyuj.douner.user.UserRepository;
import com.sunkyuj.douner.user.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

// Service: Create, Update, Delete 의 로직 처리
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ChatService {
    private final JwtService jwtService;
    private final ChatRepository chatRepository;
    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final ChatProvider chatProvider;

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
    public ChatContentResponse postChatContent(Long chatRoomId, ChatContentRequest chatContentRequest) {
        User user = userRepository.findOne(chatContentRequest.getUserId());
        ChatRoom chatRoom = chatRepository.findOneChatRoom(chatRoomId);
        ChatContent chatContent = ChatContent.builder()
                .user(user)
                .chatRoom(chatRoom)
                .content(chatContentRequest.getContent())
                .created(new Date())
                .build();

        Long chatContentId = chatRepository.createChatContent(chatContent);
        return ChatContentResponse.builder()
                .chatContentId(chatContentId)
                .chatRoomId(chatRoom.getId())
                .userId(user.getId())
                .content(chatContent.getContent())
                .created(chatContent.getCreated())
                .build();
    }
}
