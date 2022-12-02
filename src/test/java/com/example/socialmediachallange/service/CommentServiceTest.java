package com.example.socialmediachallange.service;

import com.example.socialmediachallange.dto.CommentCreateRequest;
import com.example.socialmediachallange.dto.CommentCreateResponse;
import com.example.socialmediachallange.dto.PostListResponse;
import com.example.socialmediachallange.model.Comment;
import com.example.socialmediachallange.model.Post;
import com.example.socialmediachallange.repository.CommentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class CommentServiceTest {
    private CommentService commentService;
    private CommentRepository commentRepository;
    private PostService postService;

    @BeforeEach
    void setUp() {
        postService = Mockito.mock(PostService.class);
        commentRepository = Mockito.mock(CommentRepository.class);
        commentService = new CommentService(commentRepository, postService);
    }

    @Test
    void shouldFireComment() {
        // given
        Post post = new Post(1L, "content", List.of());
        CommentCreateRequest commentCreateRequest = new CommentCreateRequest("content", post.getId(), "user");

        // when
        Mockito.when(postService.getPostById(post.getId())).thenReturn(PostListResponse.Companion.from(post));
        Mockito.when(commentRepository.save(Mockito.any())).thenReturn(new Comment(1L, "content", post));

        // then
        final CommentCreateResponse comment = commentService.fireComment(commentCreateRequest);

        ArgumentCaptor<Comment> commentArgumentCaptor = ArgumentCaptor.forClass(Comment.class);
        Mockito.verify(commentRepository).save(commentArgumentCaptor.capture());

        Comment comment1 = commentArgumentCaptor.getValue();
        assertEquals(comment.getStatus(), HttpStatus.CREATED.value());
        assertEquals(commentCreateRequest.getContent(), comment1.getDescription());
    }

}