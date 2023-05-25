package com.sunkyuj.douner.user.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sunkyuj.douner.location.Location;
import com.sunkyuj.douner.post.model.Post;
import com.sunkyuj.douner.user.UserType;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE) // 싱글테이블은 한 테이블에 다 때려박기
@DiscriminatorColumn(name = "dtype")
@Getter @Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="user_id")
    private Long id;
    private String name;
    private String phoneNumber;

    @JsonIgnore
    private String password;

    private String email;
    private String imageURL;
    private UserType userType;

    @Embedded
    private Location location;

    @OneToMany(mappedBy = "user") // Post의 user
    private List<Post> posts = new ArrayList<>();

}
