package tn.esprit.chicky.ui.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.widget.Button
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import tn.esprit.chicky.R
import tn.esprit.chicky.models.User
import tn.esprit.chicky.service.ApiService
import tn.esprit.chicky.service.UserService

class RegisterActivity : AppCompatActivity() {

    var usernameid: TextInputEditText? = null
    var emailid: TextInputEditText? = null
    var mdpid: TextInputEditText? = null

    private var usernamelayout: TextInputLayout? = null
    private var emaillayout: TextInputLayout? = null
    private var mdplayout: TextInputLayout? = null


    //var mdpid: TextInputEditText? = null
    var btncnx: Button? = null

    override fun onCreate(savedInstanceState: Bundle?) {
      //  usernamelayout = findViewById(R.id.username)

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        usernameid = findViewById(R.id.usernameid)
        emailid = findViewById(R.id.emailid)
        mdpid = findViewById(R.id.mdpid)
        btncnx = findViewById(R.id.btncnx)


        /*
        if (usernameid?.text!!.isEmpty()) {
            usernamelayout!!.error = getString(R.string.mustNotBeEmpty)
            return false
        }

        if (txtFullName?.length()!! < 3) {
            txtLayoutFullName!!.error = getString(R.string.mustBeAtLeast3)
            return false
        }

        if (txtEmail?.text!!.isEmpty()) {
            usernamelayout!!.error = getString(R.string.mustNotBeEmpty)
            return false
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(txtEmail?.text!!).matches()) {
            txtLayoutEmail!!.error = getString(R.string.checkYourEmail)
            return false
        }

        if (txtAge?.text!!.isEmpty()) {
            txtLayoutAge!!.error = getString(R.string.mustNotBeEmpty)
            return false
        }
*/

        btncnx!!.setOnClickListener {
            ApiService.userService.register(
                UserService.UserBody(
                    emailid!!.text.toString(),
                    mdpid!!.text.toString(),
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
                                    Intent(this@RegisterActivity, LoginActivity::class.java)
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