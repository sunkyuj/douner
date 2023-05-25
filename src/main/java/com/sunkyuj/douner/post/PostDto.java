package com.sunkyuj.douner.post;

import com.sunkyuj.douner.common.Gender;
import com.sunkyuj.douner.location.Location;
import com.sunkyuj.douner.post.model.Post;
import com.sunkyuj.douner.post.model.PostImage;
import lombok.*;

import java.util.Date;
import java.util.List;

@Getter
//@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PostDto {
    private Long userId; // 요청자 id
    private String title; // 요청 글 제목
    private Location location; // 요청 위치
    private Long locationId;
    private PostCategory postCategory; // 도움 유형
    private String content; // 세부 내용
    private int peopleNum; // 필요 인원, int? String
    private Date startTime;
    private int duration; // data type Duration?     // 예상 시간
    private Date endTime;
    private Gender preferGender;  // 선호 성별
//    private List<PostImage> postImages; // 사진 리스트

}
