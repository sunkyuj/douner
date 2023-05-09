package com.sunkyuj.douner.domain;

import com.sunkyuj.douner.domain.user.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter @Setter
public class ChatContent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "chat_content_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private ChatRoom chatRoom;

    @OneToOne(fetch = FetchType.LAZY)
    private User user;

    private String contents;
}
