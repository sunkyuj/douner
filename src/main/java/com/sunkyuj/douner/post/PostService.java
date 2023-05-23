package com.sunkyuj.douner.post;

import com.sunkyuj.douner.post.model.Post;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;

    @Transactional
    public Long addPost(Post post) {
        // 여기에 UserType 체크하는 로직 (Requester 일때만 Post 가능)
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
