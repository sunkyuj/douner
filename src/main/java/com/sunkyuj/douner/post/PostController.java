package com.sunkyuj.douner.post;

import com.sunkyuj.douner.errors.CustomException;
import com.sunkyuj.douner.errors.ErrorCode;
import com.sunkyuj.douner.post.model.Post;
import com.sunkyuj.douner.post.model.PostRequest;
import com.sunkyuj.douner.post.model.PostResponse;
import com.sunkyuj.douner.security.JwtService;
import com.sunkyuj.douner.utils.ApiResult;
import com.sunkyuj.douner.utils.ApiUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RequestMapping("/api/v1/posts")
@RestController
@RequiredArgsConstructor
public class PostController {
    private final PostService postService;
    private final JwtService jwtService;


//    @PostMapping("")
//    public Long addPost(Post post){
//        return postService.addPost(post);
//    }

    /*
     * 게시물 등록
     * [GET] /posts
     * @return PostDto
     * */
    @PostMapping("")
    public ApiResult<Long> addPost(@RequestBody PostRequest postRequest) throws Exception {
        //토큰 유효기간 파악
        try {
            Date current = new Date(System.currentTimeMillis());
            if(current.after(jwtService.getExp())){
                throw new CustomException(ErrorCode.EXPIRED_ACCESS_TOKEN);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        Long userId = jwtService.getUserId();
        Long postId = postService.addPost(userId, postRequest);
        return ApiUtils.success(postId);

    }


    /*
     * 전체 게시물 조회
     *
     * */
    @GetMapping("")
    public ApiResult<List<PostResponse>> posts() {
        List<Post> posts = postService.findPosts();
        List<PostResponse> postResponseList = new ArrayList<>();

        for (Post post : posts) {
            PostResponse postResponse = PostResponse.builder()
                    .userId(post.getUser().getId())
                    .title(post.getTitle())
                    .address(post.getAddress())
                    .content(post.getContents())
                    .postCategory(post.getPostCategory())
                    .startTime(post.getStartTime())
                    .endTime(post.getEndTime())
                    .build();
            postResponseList.add(postResponse);
        }

        return ApiUtils.success(postResponseList);
    }

    /*
     * 단일 게시물 조회
     *
     * */
    @GetMapping("/{postId}")
    public ApiResult<PostResponse> post(@PathVariable("postId") Long postId) {
        Post post = postService.findOne(postId);
        PostResponse postResponse = PostResponse.builder()
                .userId(post.getUser().getId())
                .title(post.getTitle())
                .address(post.getAddress())
                .content(post.getContents())
                .postCategory(post.getPostCategory())
                .startTime(post.getStartTime())
                .endTime(post.getEndTime())
                .build();
        return ApiUtils.success(postResponse);
    }

}


//@Operation(summary = "전체 게시물 조회 요청", description = "전체 게시물 정보가 반환됩니다.", tags = { "Post Controller" })
//@ApiResponses({
//        @ApiResponse(responseCode = "200", description = "OK",
//                content = @Content(schema = @Schema(implementation = List.class))
//        ),
//        @ApiResponse(responseCode = "400", description = "BAD REQUEST"),
//        @ApiResponse(responseCode = "404", description = "NOT FOUND"),
//        @ApiResponse(responseCode = "500", description = "INTERNAL SERVER ERROR")
//})
