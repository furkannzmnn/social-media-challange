package com.example.socialmediachallange.model

import com.fasterxml.jackson.annotation.JsonIgnore
import javax.persistence.Entity
import javax.persistence.FetchType
import javax.persistence.GeneratedValue
import javax.persistence.Id
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne
import javax.persistence.Table

@Entity
@Table(name = "comments")
class Comment @JvmOverloads constructor(
    @Id
    @GeneratedValue(strategy = javax.persistence.GenerationType.IDENTITY)
    val id:Long? = null,
    val description:String? = "",
    val author:String? = "",
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    @JsonIgnore
    val post: Post
) {
    // builder
    data class Builder(
        var id:Long? = null,
        var description:String? = "",
        var author:String? = "",
        var post: Post? = null,
    ) {
        fun id(id:Long?) = apply { this.id = id }
        fun description(description:String?) = apply { this.description = description }
        fun author(author:String?) = apply { this.author = author }
        fun post(post: Post?) = apply { this.post = post }
        fun build() = Comment(id, description, author, post!!)
    }
}
