package com.example.socialmediachallange.dto

import com.example.socialmediachallange.model.Comment

data class PostListResponse(
    val id: Long?,
    val content: String? = "",
    val comment: List<Comment> = emptyList(),
) {
    companion object {
        fun from(post: com.example.socialmediachallange.model.Post) = PostListResponse(
            id = post.id,
            content = post.content,
            comment = post.comment,
        )
    }

    fun toPost() = com.example.socialmediachallange.model.Post(
        id = id,
        content = content,
        comment = comment,
    )
}
