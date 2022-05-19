package tn.esprit.chicky.service

import com.google.gson.annotations.SerializedName
import retrofit2.Call
import retrofit2.http.*
import tn.esprit.chicky.models.Conversation


interface ChatService {

    data class ConversationsResponse(
        @SerializedName("conversations")
        val conversations: List<Conversation>
    )

    data class ConversationResponse(
        @SerializedName("conversation")
        val conversation: Conversation
    )

    data class MessageResponse(
        @SerializedName("message")
        val message: String
    )

    data class ConversationBody(val sender: String, val receiver: String)

    data class MyConversationsBody(val sender: String)

    @GET("/chat/tout-messages")
    fun getAllMessages(): Call<ConversationsResponse>

    @POST("/chat/my-conversations")
    fun getMyConversations(@Body myConversationsBody: MyConversationsBody): Call<ConversationsResponse>

    @POST("/chat/my-messages")
    fun getMyMessages(): Call<ConversationsResponse>

    @POST("/chat/creer-conversation")
    fun creerNouvelleConversation(@Body conversationBody: ConversationBody): Call<MessageResponse>

    @POST("/chat/envoyer-message")
    fun envoyerMessage(): Call<ConversationsResponse>

    @FormUrlEncoded
    @HTTP(method = "DELETE", path = "/", hasBody = true)
    fun deleteConversation(@Field("_id") _id: String?): Call<MessageResponse?>?
}