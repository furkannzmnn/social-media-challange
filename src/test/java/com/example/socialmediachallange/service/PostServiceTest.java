package com.example.socialmediachallange.service;

import com.example.socialmediachallange.dto.PostCreateRequest;
import com.example.socialmediachallange.dto.PostCreateResponse;
import com.example.socialmediachallange.dto.PostListResponse;
import com.example.socialmediachallange.exception.GenericException;
import com.example.socialmediachallange.model.Post;
import com.example.socialmediachallange.repository.PostRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class PostServiceTest {

    private PostService postService;
    private PostRepository postRepository;

    @BeforeEach
    void setUp() {
        postRepository = Mockito.mock(PostRepository.class);
        postService = new PostService(postRepository);
    }

    // shouldCreatePost
    @Test
    void shouldCreatePost() {
        PostCreateRequest postCreateRequest = new PostCreateRequest("content");

        Mockito.when(postRepository.save(Mockito.any(Post.class))).thenReturn(new Post(1L, "content", List.of()));

        final PostCreateResponse response = postService.createPost(postCreateRequest);
        // then

        ArgumentCaptor<Post> postArgumentCaptor = ArgumentCaptor.forClass(Post.class);
        Mockito.verify(postRepository).save(postArgumentCaptor.capture());

        Post post = postArgumentCaptor.getValue();
        assertEquals(response.getContent(), post.getContent());
    }

    @Test
    void shouldReturnAllPost() {
        // given
        Page<Post> postPage = new PageImpl<>(List.of(new Post(1L, "content", List.of())));
        Mockito.when(postRepository.findAll(Pageable.ofSize(10))).thenReturn(postPage);
        // when
        final List<PostListResponse> allPosts = postService.getAllPosts(0, 10);
        // then
        assertEquals(1, allPosts.size());
    }

    @Test
    void shouldReturnPostWhenHasId() {
        // given
        Post post = new Post(1L, "content", List.of());
        Mockito.when(postRepository.findById(1L)).thenReturn(java.util.Optional.of(post));
        // when
        final PostListResponse postById = postService.getPostById(1L);
        // then
        assertEquals(post.getContent(), postById.getContent());
    }

    @Test
    void shouldThrowExceptionWhenPostNotFound() {
        Mockito.when(postRepository.findById(Mockito.any())).thenReturn(Optional.empty());
        Assertions.assertThrows(GenericException.class, () -> postService.getPostById(new Random().nextLong()));
    }

    @Test
    void shouldDeletePost() {
        postService.deletePost(1L);
        Mockito.verify(postRepository).deleteById(Mockito.any());
    }

    @Test
    void shouldUpdatePost() {
        Post post = new Post(1L, "content", List.of());
        Mockito.when(postRepository.findById(1L)).thenReturn(Optional.of(post));
        Mockito.when(postRepository.save(Mockito.any(Post.class))).thenReturn(post);
        PostCreateRequest postCreateRequest = new PostCreateRequest("content");
        final PostListResponse updatePost = postService.updatePost(1L, postCreateRequest);
        assertEquals(postCreateRequest.getContent(), updatePost.getContent());
    }
}