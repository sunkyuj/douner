package com.sunkyuj.douner.chat.model;

import com.sunkyuj.douner.user.model.User;
import com.sunkyuj.douner.post.model.Post;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChatRoom {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "chatroom_id")
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    private User requester;

    @OneToOne(fetch = FetchType.LAZY)
    private User volunteer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id") // FK, 연관관계의 주인
    private Post post;
}
