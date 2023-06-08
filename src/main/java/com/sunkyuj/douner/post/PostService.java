package com.sunkyuj.douner.post;

import com.sunkyuj.douner.post.model.Post;
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
    public Long addPost(PostDto postDto) {
        // 여기에 UserType 체크하는 로직 (Requester 일때만 Post 가능)
        User user = userRepository.findOne(postDto.getUserId());
        Post post = Post.builder()
                .user(user)
                .title(postDto.getTitle())
                .contents(postDto.getContent())
                .address(postDto.getAddress())
                .startTime(postDto.getStartTime())
                .duration(postDto.getDuration())
                .endTime(postDto.getEndTime())
//                .postImages(postDto.getPostImages())
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

}
