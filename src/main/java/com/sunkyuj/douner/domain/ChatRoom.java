package com.sunkyuj.douner.domain;

import com.sunkyuj.douner.domain.user.Requester;
import com.sunkyuj.douner.domain.user.User;
import com.sunkyuj.douner.domain.user.Volunteer;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class ChatRoom {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "chatroom_id")
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    private Requester requester;

    @OneToOne(fetch = FetchType.LAZY)
    private Volunteer volunteer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id") // FK, 연관관계의 주인
    private Post post;
}
