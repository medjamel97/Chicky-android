package tn.esprit.chicky.ui.activities

import android.os.Bundle
import android.util.Log
import android.widget.FrameLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomnavigation.BottomNavigationView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import tn.esprit.chicky.R
import tn.esprit.chicky.models.Post
import tn.esprit.chicky.service.ApiService
import tn.esprit.chicky.service.PostService
import tn.esprit.chicky.ui.fragments.*
import tn.esprit.curriculumvitaev2medjameleddinebouassida.adapters.PostAdapter
const val USERNAME_SHARED = "USERNAME"
class MainActivity : AppCompatActivity() {


    var bottomNavigation: BottomNavigationView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        bottomNavigation = findViewById(R.id.bottom_navigation)

        bottomNavigation!!.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.home_page -> {
                    setFragment(PostsFragment())
                    true
                }
                R.id.search_page -> {
                    setFragment(SearchFragment())
                    true
                }
                R.id.create_page -> {
                    setFragment(CreateFragment())
                    true
                }
                R.id.chat_page -> {
                    setFragment(ChatFragment())
                    true
                }
                R.id.social_page -> {
                    setFragment(SocialFragment())
                    true
                }
                else -> false
            }
        }

        setFragment(PostsFragment())
    }

    private fun setFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.fragmentContainer, fragment)
                .setCustomAnimations(
                    R.anim.enter_from_left,
                    R.anim.exit_to_right
                ).commit()

        }
    }
}