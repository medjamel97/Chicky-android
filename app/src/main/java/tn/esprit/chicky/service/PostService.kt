package tn.esprit.chicky.service

import com.google.gson.annotations.SerializedName
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import tn.esprit.chicky.models.Post
import org.jetbrains.annotations.NotNull
import retrofit2.http.Body
import retrofit2.http.POST

interface PostService {

    data class PostsResponse(
        @SerializedName("posts")
        val posts: List<Post>
    )

    data class PostResponse(
        @SerializedName("post")
        val post: Post
    )

    data class PostBody(val title: String, val description: String)

    @POST("/post")
    fun addPost(@Body postBody: PostBody): Call<PostResponse>

    @GET("/post")
    fun getPosts(): Call<PostsResponse>
}