package com.sunkyuj.douner.post;

import com.sunkyuj.douner.post.model.Post;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/posts")
@RestController
public class PostController {
    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    /*
    * 게시물 등록
    * [GET] /posts
    * @return
    * */
    @PostMapping("")
    public Long addPost(Post post){
        return postService.addPost(post);
    }

    /*
     * 전체 게시물 조회
     *
     * */
    @GetMapping("")
    public List<Post> posts() {
        return postService.findPosts();
    }

    /*
     * 단일 게시물 조회회     *
     * */
    @GetMapping("/{postId}")
    public Post post(@PathVariable Long postId) {
        return postService.findOne(postId);
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