package tn.esprit.chicky.ui.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import com.google.android.material.textfield.TextInputEditText
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import tn.esprit.chicky.R
import tn.esprit.chicky.service.ApiService
import tn.esprit.chicky.service.UserService

class ProfileActivity : AppCompatActivity() {
    var fullname: TextView? = null
    var email: TextView? = null
    var btnlogout: Button? = null
    var btndelete: Button? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        fullname= findViewById(R.id.fullname)
        email= findViewById(R.id.email)
        btnlogout = findViewById(R.id.btnlogout)
        btndelete = findViewById(R.id.delete)
        ApiService.userService.getUser(
            UserService.oneuserBody(
                "6254e12d8c50dadefc269483"
            )
        ).enqueue(
            object : Callback<UserService.UserResponse> {
                override fun onResponse(
                    call: Call<UserService.UserResponse>,
                    response: Response<UserService.UserResponse>
                ) {
                    if (response.code() == 200) {
                        Log.d("i reponse heyy", response.body()?.user?._id.toString())
                    //    fullname!!.text = "@" + response.body()?.user?.username.toString()
                        fullname!!.text = "@" + response.body()?.user?.username.toString()
                        email!!.text = "@" + response.body()?.user?.email.toString()
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




        btnlogout!!.setOnClickListener {
           val builder = AlertDialog.Builder(this)
            builder.setTitle(getString(R.string.logoutTitle))
            builder.setMessage(R.string.logoutMessage)
            builder.setPositiveButton("Yes"){ dialogInterface, which ->
                val intent =
                    Intent(this@ProfileActivity, LoginActivity::class.java)
                startActivity(intent)
                finish()
            }
            builder.setNegativeButton("No"){dialogInterface, which ->
                dialogInterface.dismiss()
            }
            builder.create().show()



        }
        btndelete!!.setOnClickListener {
            val builder = AlertDialog.Builder(this)
            builder.setTitle("Delete")
            builder.setMessage("You want to delete this account ? ")
            builder.setPositiveButton("Yes"){
                    dialogInterface, which ->
                Log.d("west il yesssssssssssssssssssssss","dakhleet fil consommation")
                ApiService.userService.deleteUser(
                    UserService.oneuserBody(
                        "6254e20f74f4024c467dc35a"
                    )
                ).enqueue(
                    object : Callback<UserService.UserResponse> {
                        override fun onResponse(
                            call: Call<UserService.UserResponse>,
                            response: Response<UserService.UserResponse>
                        ) {
                            if (response.code() == 200) {
                                val intent =
                                    Intent(this@ProfileActivity, LoginActivity::class.java)
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
            builder.setNegativeButton("No"){dialogInterface, which ->
                dialogInterface.dismiss()
            }
            builder.create().show()






        }



    }





}