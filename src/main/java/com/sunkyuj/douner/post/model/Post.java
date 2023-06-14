package com.sunkyuj.douner.post.model;

import com.sunkyuj.douner.address.Address;
import com.sunkyuj.douner.common.Gender;
import com.sunkyuj.douner.post.PostCategory;
import com.sunkyuj.douner.post.PostStatus;
import com.sunkyuj.douner.user.model.User;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id") // FK, 연관관계의 주인
    private User user;

    private String title;
    private String contents;
    private Address address;
    private PostCategory postCategory;
    private Date startTime;
    private int duration; // data type Duration?
    private Date endTime;
    private Gender preferGender;

    @OneToMany(mappedBy = "post")
    private List<PostImage> postImages = new ArrayList<>();

    @OneToMany(mappedBy = "post")
    private List<PostImage> chatRooms = new ArrayList<>();

    private PostStatus postStatus;

}
