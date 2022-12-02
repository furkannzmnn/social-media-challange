package com.example.socialmediachallange.api;

import com.example.socialmediachallange.dto.CommentCreateRequest;
import com.example.socialmediachallange.dto.CommentCreateResponse;
import com.example.socialmediachallange.service.CommentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/comments")
public class CommentController {

    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping
    public ResponseEntity<CommentCreateResponse> fireComment(@RequestBody CommentCreateRequest commentCreateRequest) {
        return ResponseEntity.ok(commentService.fireComment(commentCreateRequest));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteComment(@PathVariable Long id) {
        commentService.deleteComment(id);
        return ResponseEntity.noContent().build();
    }
}
