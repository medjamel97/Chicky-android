package tn.esprit.chicky.service

import com.google.gson.annotations.SerializedName
import retrofit2.Call
import retrofit2.http.*
import tn.esprit.chicky.models.Conversation
import okhttp3.RequestBody

import okhttp3.MultipartBody

import retrofit2.http.POST

import retrofit2.http.Multipart


interface ConversationService {

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

    data class ConversationBody(val title: String, val description: String)

    @Multipart
    @POST("/conversation")
    fun addConversation(
        @Part file: MultipartBody.Part?,
        @Part("filename") name: RequestBody?
    ): Call<ConversationResponse>?

    @GET("/conversation")
    fun getConversations(): Call<ConversationsResponse>

    @FormUrlEncoded
    @HTTP(method = "DELETE", path = "/conversation", hasBody = true)
    fun deleteConversation(@Field("_id") _id: String?): Call<MessageResponse?>?
}