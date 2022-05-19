package tn.esprit.chicky.ui.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.gson.Gson
import tn.esprit.chicky.R
import tn.esprit.chicky.models.User
import tn.esprit.chicky.utils.Constants

class ChatActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)

        val sharedPreferences = getSharedPreferences(Constants.SHARED_PREF_SESSION, MODE_PRIVATE)
        val userData = sharedPreferences.getString("USER_DATA", null)

        val sessionUser: User? = Gson().fromJson(userData, User::class.java)
        val currentUser = intent.getSerializableExtra("user") as User?


    }
}