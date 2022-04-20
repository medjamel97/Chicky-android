package tn.esprit.chicky.ui.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import com.google.android.material.textfield.TextInputEditText
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import tn.esprit.chicky.R
import tn.esprit.chicky.models.User
import tn.esprit.chicky.service.ApiService
import tn.esprit.chicky.service.UserService

class LoginActivity : AppCompatActivity() {

    var emailid: TextInputEditText? = null
    var mdpid: TextInputEditText? = null
    var btncnx: Button? = null
    var btnforgetpassword: Button? = null
    var btnsignup: Button? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        emailid = findViewById(R.id.emailid)
        mdpid = findViewById(R.id.mdpid)
        btncnx = findViewById(R.id.btncnx)
        btnforgetpassword = findViewById(R.id.btnforgetpassword)
        btnsignup = findViewById(R.id.btnsignup)

        btncnx!!.setOnClickListener {
            ApiService.userService.login(
                UserService.LoginBody(
                    emailid!!.text.toString(),
                    mdpid!!.text.toString()
                )
            )
                .enqueue(
                    object : Callback<UserService.UserResponse> {
                        override fun onResponse(
                            call: Call<UserService.UserResponse>,
                            response: Response<UserService.UserResponse>
                        ) {
                            if (response.code() == 200) {
                                val intent = Intent(this@LoginActivity, MainActivity::class.java)
                                startActivity(intent)
                                finish()
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
        btnsignup!!.setOnClickListener{
            val intent = Intent(this@LoginActivity, RegisterActivity::class.java)
            startActivity(intent)
            finish()
        }

        btnforgetpassword!!.setOnClickListener{
            val intent = Intent(this@LoginActivity, MainActivity::class.java)
            startActivity(intent)
            finish()
        }

    }
}