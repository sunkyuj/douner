package com.sunkyuj.douner.chat.model;

import com.sunkyuj.douner.user.model.User;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity
@Getter @Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ChatContent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "chat_content_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "chatroom_id") // FK, 연관관계의 주인
    private ChatRoom chatRoom;

    @OneToOne(fetch = FetchType.LAZY)
    private User user;
    private String content;
    private Date created;

}
