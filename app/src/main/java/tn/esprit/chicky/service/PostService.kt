package tn.esprit.chicky.service

import com.google.gson.annotations.SerializedName
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*
import tn.esprit.chicky.models.Like
import tn.esprit.chicky.models.Music
import tn.esprit.chicky.models.Post
import tn.esprit.chicky.models.User
import java.util.*


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

    data class LikesResponse(
        @SerializedName("message")
        val message: String
    )

    data class LikeBody(
        val idUser: String,
        val idPost: String,
    )


    data class PostBody(val title: String, val description: String)

    @Multipart
    @POST("/post")
    fun addPost(
        @Part file: MultipartBody.Part,
        @Part title: MultipartBody.Part,
        @Part desctiption: MultipartBody.Part,
        @Part("filename") name: RequestBody
    ): Call<PostResponse>

    @GET("/post")
    fun getPosts(): Call<PostsResponse>

    @FormUrlEncoded
    @HTTP(method = "DELETE", path = "/post", hasBody = true)
    fun deletePost(@Field("_id") _id: String?): Call<MessageResponse?>?

    @POST("/like")
    fun addlike(@Body likeBody: LikeBody): Call<LikesResponse>

}