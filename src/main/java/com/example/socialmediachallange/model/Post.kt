package com.example.socialmediachallange.model

import com.fasterxml.jackson.annotation.JsonIgnore
import javax.persistence.*

@Table(name = "posts")
@Entity
class Post constructor(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id:Long? = null,
    val content:String? = "",
    @JsonIgnore
    @OneToMany(mappedBy = "post", cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    val comment: List<Comment> = emptyList(),
) {
    data class Builder(
        var id:Long? = null,
        var content:String? = "",
        var comment: List<Comment> = emptyList(),
    ) {
        fun id(id:Long) = apply { this.id = id }
        fun content(content:String?) = apply { this.content = content }
        fun comment(comment: List<Comment>) = apply { this.comment = comment }
        fun build() = Post(id, content, comment)
    }
}
