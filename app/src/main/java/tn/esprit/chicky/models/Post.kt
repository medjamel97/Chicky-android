package tn.esprit.chicky.models

import java.io.Serializable

data class Post(
    val _id: String,
    val title: String,
    val description: String,
    val videoFilename: String,
    val user: User?
) : Serializable
