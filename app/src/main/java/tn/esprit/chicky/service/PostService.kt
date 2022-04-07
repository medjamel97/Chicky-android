package tn.esprit.chicky.service

import com.google.gson.annotations.SerializedName
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import tn.esprit.chicky.models.Post
import org.jetbrains.annotations.NotNull

interface PostService {

    data class PostsResponse(
        @SerializedName("posts")
        val posts: List<Post>
    )

    @GET("/post")
    fun getPosts(): Call<PostsResponse>
}