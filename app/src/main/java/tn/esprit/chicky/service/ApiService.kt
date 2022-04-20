package tn.esprit.chicky.service

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiService {

    private const val BASE_URL = "http://10.0.2.2:5000/"
    //private const val BASE_URL = "http://192.168.43.37:5000/"

    private fun retrofit(): Retrofit {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .build()
    }

    val userService: UserService by lazy {
        retrofit().create(UserService::class.java)
    }

    val postService: PostService by lazy {
        retrofit().create(PostService::class.java)
    }

    val conversationService: ConversationService by lazy {
        retrofit().create(ConversationService::class.java)
    }

}