package com.sunkyuj.douner.chat;

import com.sunkyuj.douner.chat.model.ChatMessage;
import com.sunkyuj.douner.chat.model.ChatRoom;
import com.sunkyuj.douner.user.model.User;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class ChatRepository {
    private final EntityManager em;

    public List<ChatRoom> findAllChatRoom(User user) {
        return em.createQuery("select cr from ChatRoom cr where cr.requester = :user or cr.volunteer = :user", ChatRoom.class)
                .setParameter("user", user)
                .getResultList();
    }
    public ChatRoom findOneChatRoom(Long chatRoomId) {
        return em.find(ChatRoom.class,chatRoomId);
    }
    public List<ChatMessage> getAllChatMessage(ChatRoom chatRoom) {
        return em.createQuery("select cm from ChatMessage cm where cm.chatRoom = :chatRoom", ChatMessage.class)
                .setParameter("chatRoom", chatRoom)
                .getResultList();
    }
    public Long createChatRoom(ChatRoom chatRoom) {
        em.persist(chatRoom);
        return chatRoom.getId();
    }

    public Long createChatMessage(ChatMessage chatMessage) {
        em.persist(chatMessage);
        return chatMessage.getId();
    }
}
