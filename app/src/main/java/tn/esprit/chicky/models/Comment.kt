package tn.esprit.chicky.models

import java.io.Serializable
import java.util.*

data class Comment(
    var date: Date,
    var user: String?,
    var userEmail: String?,
    var post: String?,
    var description: String?
) : Serializable