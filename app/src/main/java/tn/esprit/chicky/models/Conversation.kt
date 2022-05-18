package tn.esprit.chicky.models

import java.io.Serializable

data class Conversation(
    val _id: String,
    val name: String,
    val lastMessage: String
) : Serializable
