package com.sunkyuj.douner.chat.model;

import com.sunkyuj.douner.user.model.User;
import com.sunkyuj.douner.post.model.Post;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

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

    @OneToMany(mappedBy = "chatRoom", // ChatContent의  chatRoom에 의해 매핑됨
            cascade = CascadeType.ALL) // 모든 경우에 CASCADE 해서 다 같이 묶여서 처리 (ALL, DELETE, UPDATE 등 있음)
    private List<ChatMessage> messages = new ArrayList<>();


}
