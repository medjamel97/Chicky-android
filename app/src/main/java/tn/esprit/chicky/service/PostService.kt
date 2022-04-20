package tn.esprit.chicky.service

import com.google.gson.annotations.SerializedName
import retrofit2.Call
import retrofit2.http.*
import tn.esprit.chicky.models.Post
import okhttp3.RequestBody

import okhttp3.MultipartBody

import retrofit2.http.POST

import retrofit2.http.Multipart


interface PostService {

    data class PostsResponse(
        @SerializedName("posts")
        val posts: List<Post>
    )

    data class PostResponse(
        @SerializedName("post")
        val post: Post
    )

    data class MessageResponse(
        @SerializedName("message")
        val message: String
    )

    data class PostBody(val title: String, val description: String)

    @Multipart
    @POST("/post")
    fun addPost(
        @Part file: MultipartBody.Part?,
        @Part("filename") name: RequestBody?
    ): Call<PostResponse>?

    @GET("/post")
    fun getPosts(): Call<PostsResponse>

    @FormUrlEncoded
    @HTTP(method = "DELETE", path = "/post", hasBody = true)
    fun deletePost(@Field("_id") _id: String?): Call<MessageResponse?>?
}