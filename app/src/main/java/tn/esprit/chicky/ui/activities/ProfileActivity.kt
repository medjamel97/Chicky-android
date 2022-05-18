package tn.esprit.chicky.ui.activities

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.View
import android.widget.Button
import android.widget.GridView
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import tn.esprit.chicky.R
import tn.esprit.chicky.models.User
import tn.esprit.chicky.service.ApiService
import tn.esprit.chicky.service.PostService
import tn.esprit.chicky.utils.Constants
import tn.esprit.chicky.utils.ImageLoader

class ProfileActivity : AppCompatActivity() {

    var fullName: TextView? = null
    var email: TextView? = null
    var btnlogout: Button? = null
    var btndelete: Button? = null
    var postsGV: GridView? = null
    var profileIV: ImageView? = null

    private var currentUser: User? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        fullName = findViewById(R.id.fullName)
        email = findViewById(R.id.email)
        btnlogout = findViewById(R.id.btnlogout)
        btndelete = findViewById(R.id.delete)
        postsGV = findViewById(R.id.postsGV)
        profileIV = findViewById(R.id.profileIV)

        val sharedPreferences = getSharedPreferences(Constants.SHARED_PREF_SESSION, MODE_PRIVATE)
        val userData = sharedPreferences.getString("USER_DATA", null)

        val sessionUser: User? = Gson().fromJson(userData, User::class.java)
        currentUser = intent.getSerializableExtra("user") as User?

        if (currentUser == null) {
            currentUser = sessionUser
        }

        if (currentUser != sessionUser) {
            btnlogout!!.visibility = View.GONE
            btndelete!!.visibility = View.GONE
        }

        fullName!!.text = currentUser!!.firstname + " " + currentUser!!.lastname
        email!!.text = currentUser!!.email

        ImageLoader.setImageFromUrlWithoutProgress(
            profileIV!!, Constants.BASE_URL_IMAGES +
                    currentUser!!.imageFilename
        )

        btnlogout!!.setOnClickListener {
            val builder = AlertDialog.Builder(this)
            builder.setTitle(getString(R.string.logout))
            builder.setMessage(R.string.logout_message)
            builder.setPositiveButton("Yes") { _, _ ->
                logout()
            }
            builder.setNegativeButton("No") { dialogInterface, which ->
                dialogInterface.dismiss()
            }
            builder.create().show()
        }

        btndelete!!.setOnClickListener {
            val intent =
                Intent(this@ProfileActivity, EditProfileActivity::class.java)
            startActivity(intent)
            finish()
        }

        getMyPosts()
    }

    private fun getMyPosts() {
        ApiService.postService.getPosts()
            .enqueue(
                object : Callback<PostService.PostsResponse> {
                    override fun onResponse(
                        call: Call<PostService.PostsResponse>,
                        response: Response<PostService.PostsResponse>
                    ) {
                        if (response.code() == 200) {
                            /*postsGV!!.adapter = `GridViewAdapter.old`(
                                applicationContext,
                                response.body()?.posts as MutableList<Post>
                            )*/
                        } else {
                            Log.d("HTTP ERROR", "status code is " + response.code())
                        }
                    }

                    override fun onFailure(
                        call: Call<PostService.PostsResponse>,
                        t: Throwable
                    ) {
                        Log.d("FAIL", "fail")
                    }
                }
            )
    }

    private fun logout() {
        val sharedPreferences = getSharedPreferences(Constants.SHARED_PREF_SESSION, MODE_PRIVATE)
        val sharedPreferencesEditor: SharedPreferences.Editor = sharedPreferences.edit()
        sharedPreferencesEditor.clear().apply()

        val intent = Intent(applicationContext, LoginActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        startActivity(intent)
    }

    override fun finish() {
        super.finish()
        overridePendingTransition(R.anim.slide_in_up, R.anim.slide_out_up)
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        if (keyCode == KeyEvent.KEYCODE_VOLUME_UP) {
            val sharedPreferences = getSharedPreferences(Constants.SHARED_PREF_SESSION, MODE_PRIVATE)
            val sharedPreferencesEditor: SharedPreferences.Editor = sharedPreferences.edit()
            val json = Gson().toJson(currentUser)
            sharedPreferencesEditor.putString("USER_DATA", json)
            sharedPreferencesEditor.apply()

            val intent = Intent(this@ProfileActivity, MainActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            startActivity(intent)
        } else if (keyCode == KeyEvent.KEYCODE_BACK) {
            finish()
        }
        return true
    }
}