package tn.esprit.chicky.ui.activities

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen

@SuppressLint("CustomSplashScreen")
class SplashActivity : Activity() {
    lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        installSplashScreen()
        checkUser()
    }

    private fun checkUser() {


        sharedPreferences = getSharedPreferences("DATA_USER", MODE_PRIVATE);
        if (sharedPreferences.getString(USERNAME_SHARED, "")!! != "")  {
            val mainIntent = Intent(this, LoginActivity::class.java)
            startActivity(mainIntent)
        }else {
            val mainIntent = Intent(this, MainActivity::class.java)
            startActivity(mainIntent)
        }



       /* if (true) {
            val intent = Intent(this@SplashActivity, MainActivity::class.java)
            startActivity(intent)
            finish()
        } else {
            val intent = Intent(this@SplashActivity, RegisterActivity::class.java)
            startActivity(intent)
            finish()
        }*/
    }
}