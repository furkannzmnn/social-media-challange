package com.example.socialmediachallange.repository;

import com.example.socialmediachallange.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {

}
