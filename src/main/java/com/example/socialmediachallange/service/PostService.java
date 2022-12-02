package com.example.socialmediachallange.service;

import com.example.socialmediachallange.dto.PostCreateRequest;
import com.example.socialmediachallange.dto.PostCreateResponse;
import com.example.socialmediachallange.dto.PostListResponse;
import com.example.socialmediachallange.exception.ErrorCode;
import com.example.socialmediachallange.exception.GenericException;
import com.example.socialmediachallange.model.Post;
import com.example.socialmediachallange.repository.PostRepository;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PostService {
    private final PostRepository postRepository;

    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }


    @Transactional
    public PostCreateResponse createPost(PostCreateRequest postCreateRequest) {
        Post post = new Post.Builder()
                .content(postCreateRequest.getContent())
                .build();

        final Post fromDb = postRepository.save(post);

        return new PostCreateResponse(fromDb.getId(), fromDb.getContent());
    }

    public List<PostListResponse> getAllPosts(int page, int size) {
        return postRepository.findAll(Pageable.ofSize(size).withPage(page))
                .map(PostListResponse.Companion::from)
                .toList();
    }

    @Cacheable(value = "posts", key = "#id")
    public PostListResponse getPostById(Long id) {
        return PostListResponse.Companion.from(postRepository.findById(id)
                .orElseThrow(() -> GenericException.builder()
                .httpStatus(HttpStatus.NOT_FOUND)
                .errorCode(ErrorCode.POST_NOT_FOUND)
                .build()));
    }

    @CacheEvict(value = "posts", key = "#id")
    public void deletePost(Long id) {
        postRepository.deleteById(id);
    }


    @CacheEvict(value = "posts", key = "#id")
    public PostListResponse updatePost(Long id, PostCreateRequest postCreateRequest) {
        Post post = postRepository.findById(id).orElseThrow();

        Post newPost = new Post.Builder()
                .id(post.getId())
                .content(postCreateRequest.getContent())
                .build();

        final Post updatedPost = postRepository.save(newPost);
        return PostListResponse.Companion.from(updatedPost);
    }

}
