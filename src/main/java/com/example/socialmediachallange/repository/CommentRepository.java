package com.example.socialmediachallange.repository;

import com.example.socialmediachallange.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}
