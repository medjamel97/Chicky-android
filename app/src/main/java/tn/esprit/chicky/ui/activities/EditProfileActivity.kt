package tn.esprit.chicky.ui.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import tn.esprit.chicky.R
import tn.esprit.chicky.models.User
import tn.esprit.chicky.service.ApiService
import tn.esprit.chicky.service.UserService
import tn.esprit.chicky.utils.Constants
import tn.esprit.chicky.utils.ImageLoader


class EditProfileActivity : AppCompatActivity() {




    var usernameid: TextInputEditText? = null
    var emailid: TextInputEditText? = null


    private var usernamelayout: TextInputLayout? = null
    private var emaillayout: TextInputLayout? = null

    var roundedimage: ImageView? =null

    //var mdpid: TextInputEditText? = null
    var btncnx: Button? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        //  usernamelayout = findViewById(R.id.username)

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_profile)

        usernameid = findViewById(R.id.usernameid)
        emailid = findViewById(R.id.emailid)

        btncnx = findViewById(R.id.btncnx)

        val sharedPreferences = getSharedPreferences(Constants.SHARED_PREF_SESSION, MODE_PRIVATE)
        val userData = sharedPreferences.getString("USER_DATA", null)




        roundedimage = findViewById(R.id.roundedimage)
        if (userData != null) {
            val user: User = Gson().fromJson(userData, User::class.java)
         //   fullName!!.text = user.firstname + " " + user.lastname
          //  email!!.text = user.email
            //image
            ImageLoader.setImageFromUrlWithoutProgress(
                roundedimage!!,
                Constants.BASE_URL_IMAGES + user.imageFilename
            )
        }



        btncnx!!.setOnClickListener {
            ApiService.userService.updateProfile(
                UserService.UserupdateBody(
                    emailid!!.text.toString(),
                    usernameid!!.text.toString()
                )
            ).enqueue(
                object : Callback<UserService.UserResponse> {
                    override fun onResponse(
                        call: Call<UserService.UserResponse>,
                        response: Response<UserService.UserResponse>
                    ) {
                        if (response.code() == 200) {
                            val intent =
                                Intent(this@EditProfileActivity, ProfileActivity::class.java)
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

    }
}