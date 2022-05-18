package tn.esprit.chicky.models

import java.io.Serializable
import java.util.*

data class Like(
    var date: Date,
    var user: User?,
    var post: Post?
) : Serializable