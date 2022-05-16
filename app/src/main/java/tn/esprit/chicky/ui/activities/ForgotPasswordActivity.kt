package tn.esprit.chicky.ui.activities

import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputEditText
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import tn.esprit.chicky.R
import tn.esprit.chicky.service.ApiService
import tn.esprit.chicky.service.UserService

class ForgotPasswordActivity : AppCompatActivity() {

    var btnemail: Button? = null
    //
    var emailid: TextInputEditText? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forgot_password)

        btnemail = findViewById(R.id.btnemail)
        emailid = findViewById(R.id.emailid)
        btnemail!!.setOnClickListener {
            println("--------------------")
println(emailid!!.text.toString())
            println("--------------------")
            ApiService.userService.forgetpassword(
                UserService.ForgetBody(
                    emailid!!.text.toString(),
                )
            ).enqueue(
                object : Callback<UserService.UserResponse> {
                    override fun onResponse(
                        call: Call<UserService.UserResponse>,
                        response: Response<UserService.UserResponse>
                    ) {
                        if (response.code() == 200) {

                        } else {
                            Log.d("HTTP ERROR", "status code is " + response.code())
                        }
                    }

                    override fun onFailure(
                        call: Call<UserService.UserResponse>,
                        t: Throwable
                    ) {
                        Log.d("FAIL", "fail")
                    }
                }
            )

        }


    }
}