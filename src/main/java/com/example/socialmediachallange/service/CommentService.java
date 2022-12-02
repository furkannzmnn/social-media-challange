package com.example.socialmediachallange.service;

import com.example.socialmediachallange.dto.CommentCreateRequest;
import com.example.socialmediachallange.dto.CommentCreateResponse;
import com.example.socialmediachallange.dto.PostListResponse;
import com.example.socialmediachallange.exception.ErrorCode;
import com.example.socialmediachallange.exception.GenericException;
import com.example.socialmediachallange.model.Comment;
import com.example.socialmediachallange.repository.CommentRepository;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.GeneratedValue;

@Service
public class CommentService {
    private final CommentRepository commentRepository;
    private final PostService postService;

    public CommentService(CommentRepository commentRepository, PostService postService) {
        this.commentRepository = commentRepository;
        this.postService = postService;
    }


    @Transactional
    @CacheEvict(value = "posts", key = "#commentCreateRequest.postId")
    public CommentCreateResponse fireComment(CommentCreateRequest commentCreateRequest) {
        PostListResponse post = postService.getPostById(commentCreateRequest.getPostId());

        Comment comment = new Comment.Builder()
                .description(commentCreateRequest.getContent())
                .post(post.toPost())
                .author(commentCreateRequest.getAuthor())
                .build();

        commentRepository.save(comment);

        return new CommentCreateResponse(HttpStatus.CREATED.value());
    }

    @Transactional
    @CacheEvict(value = "posts", allEntries = true)
    public void deleteComment(Long id) {
        final Comment comment = commentRepository.findById(id)
                .orElseThrow(() -> GenericException.builder().errorCode(ErrorCode.COMMENT_NOT_FOUND).build());
        commentRepository.deleteById(comment.getId());
    }

}

