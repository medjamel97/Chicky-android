package tn.esprit.chicky.ui.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.FrameLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
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

class MainActivity : AppCompatActivity() {

    var bottomNavigation: BottomNavigationView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setSupportActionBar(findViewById(R.id.my_toolbar))
        supportActionBar?.title = "Posts for you"

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

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
        R.id.action_profile -> {
            val intent = Intent(this@MainActivity, ProfileActivity::class.java)
            startActivity(intent)
            true
        }
        else -> {
            super.onOptionsItemSelected(item)
        }
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