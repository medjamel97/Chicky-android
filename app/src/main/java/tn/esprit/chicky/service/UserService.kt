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
import tn.esprit.chicky.models.User

interface UserService {

    data class UserResponse(
        @SerializedName("user")
        val user: User
    )

    data class LoginBody(val email: String, val password: String)
    data class UserBody(val email: String, val password: String, val username: String)

    @POST("/user/login")
    fun login(@Body loginBody: LoginBody): Call<UserResponse>

    @POST("/user/register")
    fun register(@Body userBody: UserBody): Call<UserResponse>
}