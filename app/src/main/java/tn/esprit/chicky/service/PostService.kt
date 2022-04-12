package tn.esprit.chicky.service

import com.google.gson.annotations.SerializedName
import retrofit2.Call
import retrofit2.http.*
import tn.esprit.chicky.models.Post


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

    @GET("/post")
    fun getPosts(): Call<PostsResponse>

    @POST("/post")
    fun addPost(@Body postBody: PostBody): Call<PostResponse>

    @FormUrlEncoded
    @HTTP(method = "DELETE", path = "/post", hasBody = true)
    fun deletePost(@Field("_id") _id: String?): Call<MessageResponse?>?
}