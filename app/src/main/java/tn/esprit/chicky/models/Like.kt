package tn.esprit.chicky.models

import java.util.*


data class Like(
    val _id: String,
    val date: Date,
    val user: User,
    val music: Music,
    val post: Post
)
