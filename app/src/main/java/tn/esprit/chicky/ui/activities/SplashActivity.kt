package tn.esprit.chicky.ui.activities

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen

class SplashActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        installSplashScreen()

        val intent = Intent(this@SplashActivity, RegisterActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun checkUser() {

    }
}