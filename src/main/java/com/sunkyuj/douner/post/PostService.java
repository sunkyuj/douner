package com.sunkyuj.douner.post;

import com.sunkyuj.douner.post.model.Post;
import com.sunkyuj.douner.post.model.PostRequest;
import com.sunkyuj.douner.post.model.PostResponse;
import com.sunkyuj.douner.user.UserRepository;
import com.sunkyuj.douner.user.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    @Transactional
    public Long addPost(Long userId, PostRequest postRequest) {
        // 여기에 UserType 체크하는 로직 (Requester 일때만 Post 가능)
        User user = userRepository.findOne(userId);
        // TODO: 사용자 존재하는지 체크
        Post post = Post.builder()
                .user(user)
                .title(postRequest.getTitle())
                .content(postRequest.getContent())
                .address(postRequest.getAddress())
                .postCategory(postRequest.getPostCategory())
                .preferGender(postRequest.getPreferGender())
                .startTime(postRequest.getStartTime())
//                .duration(postRequest.getDuration())
                .endTime(postRequest.getEndTime())
//                .postImages(postRequest.getPostImages())
                .chatRooms(new ArrayList<>())
                .postStatus(PostStatus.WAITING)
                .build();

        postRepository.save(post);
        return post.getId();
    }
    public List<Post> findPosts(){
        return postRepository.findAll();
    }
    public Post findOne(Long id){
        return postRepository.findOne(id);
    }


    @Transactional
    public void updatePost(Long postId, PostRequest postRequest){

        Post post = postRepository.findOne(postId);
        // TODO: 사용자 존재하는지 체크

        // 영속성 컨텍스트가 자동으로 변경
        if(postRequest.getTitle() != null) {post.setTitle(postRequest.getTitle());}
        if(postRequest.getAddress() != null) {post.setAddress(postRequest.getAddress());}
        if(postRequest.getPostCategory() != null) {post.setPostCategory(postRequest.getPostCategory());}
        if(postRequest.getContent() != null) {post.setContent(postRequest.getContent());}
        if(postRequest.getStartTime() != null) {post.setStartTime(postRequest.getStartTime());}
        if(postRequest.getEndTime() != null) {post.setEndTime(postRequest.getEndTime());}
        if(postRequest.getPreferGender() != null) {post.setPreferGender(postRequest.getPreferGender());}

    }

    @Transactional
    public void deletePost(Long postId){
        Post post = postRepository.findOne(postId);
        // TODO: 사용자 존재하는지 체크

        postRepository.delete(post);
    }

}
