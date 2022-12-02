package com.example.socialmediachallange.api;

import com.example.socialmediachallange.dto.PostCreateRequest;
import com.example.socialmediachallange.dto.PostCreateResponse;
import com.example.socialmediachallange.dto.PostListResponse;
import com.example.socialmediachallange.service.PostService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/posts")
public class PostController {

    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @PostMapping("/create")
    public ResponseEntity<PostCreateResponse> createPost(@RequestBody PostCreateRequest postCreateRequest) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(postService.createPost(postCreateRequest));
    }

    @GetMapping("/{page}/{size}")
    public ResponseEntity<List<PostListResponse>> getAllPosts(@PathVariable int page, @PathVariable int size) {
        return ResponseEntity.ok(postService.getAllPosts(page, size));
    }

    @GetMapping("/{id}")
    public ResponseEntity<PostListResponse> getPostById(@PathVariable Long id) {
        return ResponseEntity.ok(postService.getPostById(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePost(@PathVariable Long id) {
        postService.deletePost(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<PostListResponse> updatePost(@PathVariable Long id, @RequestBody PostCreateRequest postCreateRequest) {
        return ResponseEntity.ok(postService.updatePost(id, postCreateRequest));
    }
}
