package tn.esprit.chicky.service

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import tn.esprit.chicky.utils.Constants


object ApiService {

    private fun retrofit(): Retrofit {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(Constants.BASE_URL)
            .build()
    }

    val userService: UserService by lazy {
        retrofit().create(UserService::class.java)
    }

    val postService: PostService by lazy {
        retrofit().create(PostService::class.java)
    }

    val musicService: MusicService by lazy {
        retrofit().create(MusicService::class.java)
    }

    val conversationService: ConversationService by lazy {
        retrofit().create(ConversationService::class.java)
    }

}