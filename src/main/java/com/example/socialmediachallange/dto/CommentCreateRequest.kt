package com.example.socialmediachallange.dto

data class CommentCreateRequest(
    val content: String? = "",
    val postId: Long,
    val author:String
)
