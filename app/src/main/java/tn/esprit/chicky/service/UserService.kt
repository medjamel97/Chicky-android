package tn.esprit.chicky.service

import com.google.gson.annotations.SerializedName
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.POST
import tn.esprit.chicky.models.User

interface UserService {

    data class UserResponse(
        @SerializedName("user")
        val user: User
    )

    data class LoginBody(val email: String, val password: String)
    data class UserBody(val email: String, val password: String, val username: String)
    data class OneUserBody(val _id: String)
    data class ForgetBody(val email: String)


    @POST("/user/login")
    fun login(@Body loginBody: LoginBody): Call<UserResponse>

    @POST("/user/register")
    fun register(@Body userBody: UserBody): Call<UserResponse>

    @POST("/user/one")
    fun getUser(@Body userBody: OneUserBody): Call<UserResponse>

    @DELETE("/one")
    fun deleteUser(@Body userBody: OneUserBody): Call<UserResponse>

    @POST("/user/forgot-password")
    fun forgetpassword(@Body userBody: ForgetBody): Call<UserResponse>
}