package com.sunkyuj.douner.user.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sunkyuj.douner.location.Location;
import com.sunkyuj.douner.post.model.Post;
import com.sunkyuj.douner.user.UserType;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.validator.constraints.UniqueElements;
import org.springframework.lang.Nullable;

import java.util.ArrayList;
import java.util.List;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE) // 싱글테이블은 한 테이블에 다 때려박기
@DiscriminatorColumn(name = "dtype")
@Getter @Setter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="user_id")
    private Long id;
    @NotBlank
    private String name;

    @Nullable
    private String phoneNumber;

    @NotBlank
    private String password;

    // unique
    @NotBlank
    private String email;

    @Nullable
    private String imageURL;

//    @ColumnDefault("VOLUNTEER")
    private UserType userType;

    @Nullable
    @Embedded
    private Location location;

    @OneToMany(mappedBy = "user") // Post의 user
    private List<Post> posts = new ArrayList<>();

}
