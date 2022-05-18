package tn.esprit.chicky.service

import com.google.gson.annotations.SerializedName
import retrofit2.Call
import retrofit2.http.*
import tn.esprit.chicky.models.User

interface UserService {

    data class UserResponse(
        @SerializedName("user")
        val user: User
    )

    data class UsersResponse(
        @SerializedName("users")
        val users: List<User>
    )

    data class LoginBody(val email: String, val password: String)
    data class UserBody(val email: String, val password: String, val username: String)
    data class UserupdateBody(val email: String, val username: String)
    data class OneUserBody(val _id: String)
    data class ResetBody(val resetCode: String, val email: String)
    data class UpdatePasswordBody(val email: String, val newPassword: String)

    @GET("/user")
    fun getAll(): Call<UsersResponse>

    @POST("/user/login")
    fun login(@Body loginBody: LoginBody): Call<UserResponse>

    @POST("/user/register")
    fun register(@Body userBody: UserBody): Call<UserResponse>

    @POST("/user/one")
    fun getUser(@Body userBody: OneUserBody): Call<UserResponse>

    @DELETE("/one")
    fun deleteUser(@Body userBody: OneUserBody): Call<UserResponse>
    data class MessageResponse(
        val message: String
    )

    @POST("/users/forgotPassword")
    fun forgotPassword(@Body resetBody: ResetBody): Call<MessageResponse>

    @POST("/users/updatePassword")
    fun updatePassword(@Body updatePasswordBody: UpdatePasswordBody): Call<MessageResponse>
    
    @PUT("/user/update-profile")
    fun updateProfile(@Body userBody: UserupdateBody): Call<UserResponse>
}