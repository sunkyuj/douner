package com.sunkyuj.douner.post.model;

import com.sunkyuj.douner.address.Address;
import com.sunkyuj.douner.post.PostCategory;
import lombok.*;

import java.util.Date;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class PostResponse {
    private Long userId; // 요청자 id
    private String title; // 요청 글 제목
    private Address address; // 요청 위치
    private PostCategory postCategory; // 도움 유형 [MOVE, CARE, NONE]
    private String content; // 세부 내용
    private Date startTime; // ex "2023-06-09T09:01:58.883Z"
    private Date endTime; // ex "2023-06-09T09:01:58.883Z"

//    private int peopleNum; // 필요 인원, int? String
//    private int duration; // data type Duration?     // 예상 시간
//    private Long locationId;
//    private Gender preferGender;  // 선호 성별
//    private List<PostImage> postImages; // 사진 리스트

}


