package com.sunkyuj.douner.post;

import com.sunkyuj.douner.errors.CustomException;
import com.sunkyuj.douner.errors.ErrorCode;
import com.sunkyuj.douner.post.model.Post;
import com.sunkyuj.douner.post.model.PostRequest;
import com.sunkyuj.douner.post.model.PostResponse;
import com.sunkyuj.douner.security.JwtService;
import com.sunkyuj.douner.utils.ApiResult;
import com.sunkyuj.douner.utils.ApiUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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


    /*
     * 게시물 등록
     * [GET] /posts
     * @return PostDto
     * */
    @Operation(summary = "게시물 등록", description = "사용자가 게시물을 등록한다(도움을 요청한다)", tags = { "Post" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation", content = @Content(schema = @Schema(implementation = Long.class)))
    })
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
    @Operation(summary = "전체 게시물 조회", description = "모든 게시물(도움)을 조회한다", tags = { "Post" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation", content = @Content(array = @ArraySchema(schema = @Schema(implementation = PostResponse.class))))
    })
    @GetMapping("")
    public ApiResult<List<PostResponse>> getPosts() {
        List<Post> posts = postService.findPosts();
        List<PostResponse> postResponseList = new ArrayList<>();

        for (Post post : posts) {
            PostResponse postResponse = PostResponse.builder()
                    .postId(post.getId())
                    .userId(post.getUser().getId())
                    .title(post.getTitle())
                    .address(post.getAddress())
                    .content(post.getContent())
                    .postCategory(post.getPostCategory())
                    .startTime(post.getStartTime())
                    .endTime(post.getEndTime())
                    .preferGender(post.getPreferGender())
                    .build();
            postResponseList.add(postResponse);
        }

        return ApiUtils.success(postResponseList);
    }

    /*
     * 단일 게시물 조회
     *
     * */
    @Operation(summary = "단일 게시물 조회", description = "한 게시물(도움)을 조회한다", tags = { "Post" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation", content = @Content(schema = @Schema(implementation = PostResponse.class)))
    })
    @GetMapping("/{postId}")
    public ApiResult<PostResponse> getPost(@PathVariable("postId") Long postId) {
        Post post = postService.findOne(postId);
        PostResponse postResponse = PostResponse.builder()
                .userId(post.getUser().getId())
                .title(post.getTitle())
                .address(post.getAddress())
                .content(post.getContent())
                .postCategory(post.getPostCategory())
                .startTime(post.getStartTime())
                .endTime(post.getEndTime())
                .build();
        return ApiUtils.success(postResponse);
    }

    @Operation(summary = "게시물 수정", description = "게시물을 수정한다", tags = { "Post" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation", content = @Content(schema = @Schema(implementation = PostResponse.class)))
    })
    @PatchMapping("/{postId}")
    public ApiResult<Long> updatePost(@PathVariable("postId") Long postId, @RequestBody PostRequest postRequest) throws Exception {

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
        postService.updatePost(postId, postRequest);
        return ApiUtils.success(200L);
    }

    @Operation(summary = "게시물 삭제", description = "게시물을 삭제한다", tags = { "Post" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation", content = @Content(schema = @Schema(implementation = PostResponse.class)))
    })
    @DeleteMapping("/{postId}")
    public ApiResult<Long> deletePost(@PathVariable("postId") Long postId) {
        postService.deletePost(postId);
        return ApiUtils.success(200L);
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
