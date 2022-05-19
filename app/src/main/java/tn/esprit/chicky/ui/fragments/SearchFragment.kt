package tn.esprit.chicky.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.facebook.shimmer.ShimmerFrameLayout
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import tn.esprit.chicky.R
import tn.esprit.chicky.adapters.MusicAdapter
import tn.esprit.chicky.adapters.PostAdapter
import tn.esprit.chicky.adapters.UserAdapter
import tn.esprit.chicky.models.Music
import tn.esprit.chicky.models.Post
import tn.esprit.chicky.models.User
import tn.esprit.chicky.service.ApiService
import tn.esprit.chicky.service.MusicService
import tn.esprit.chicky.service.PostService
import tn.esprit.chicky.service.UserService

class SearchFragment : Fragment() {

    private var postsRV: RecyclerView? = null
    private var peopleRV: RecyclerView? = null
    private var musicRV: RecyclerView? = null
    private var postsSL: ShimmerFrameLayout? = null
    private var peopleSL: ShimmerFrameLayout? = null
    private var musicSL: ShimmerFrameLayout? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_search, container, false)

        postsRV = view.findViewById(R.id.postsRV)
        peopleRV = view.findViewById(R.id.peopleRV)
        musicRV = view.findViewById(R.id.musicRV)
        postsSL = view.findViewById(R.id.postsSL)
        peopleSL = view.findViewById(R.id.peopleSL)
        musicSL = view.findViewById(R.id.musicSL)

        postsSL!!.startShimmer()
        peopleSL!!.startShimmer()
        musicSL!!.startShimmer()

        postsRV!!.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        ApiService.postService.getPosts()
            .enqueue(
                object : Callback<PostService.PostsResponse> {
                    override fun onResponse(
                        call: Call<PostService.PostsResponse>,
                        response: Response<PostService.PostsResponse>
                    ) {
                        if (response.code() == 200) {
                            postsRV!!.adapter =
                                PostAdapter(response.body()?.posts as MutableList<Post>)

                            postsSL!!.stopShimmer()
                            postsSL!!.visibility = View.GONE
                        } else {
                            println("status code is " + response.code())
                        }
                    }

                    override fun onFailure(
                        call: Call<PostService.PostsResponse>,
                        t: Throwable
                    ) {
                        println("HTTP ERROR")
                        t.printStackTrace()
                    }

                }
            )

        peopleRV!!.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        ApiService.userService.getAll()
            .enqueue(
                object : Callback<UserService.UsersResponse> {
                    override fun onResponse(
                        call: Call<UserService.UsersResponse>,
                        response: Response<UserService.UsersResponse>
                    ) {
                        if (response.code() == 200) {
                            peopleRV!!.adapter =
                                UserAdapter(response.body()?.users as MutableList<User>)

                            peopleSL!!.stopShimmer()
                            peopleSL!!.visibility = View.GONE
                        } else {
                            println("status code is " + response.code())
                        }
                    }

                    override fun onFailure(
                        call: Call<UserService.UsersResponse>,
                        t: Throwable
                    ) {
                        println("HTTP ERROR")
                        t.printStackTrace()
                    }

                }
            )

        musicRV!!.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        ApiService.musicService.getAll()
            .enqueue(
                object : Callback<MusicService.MusicsResponse> {
                    override fun onResponse(
                        call: Call<MusicService.MusicsResponse>,
                        response: Response<MusicService.MusicsResponse>
                    ) {
                        if (response.code() == 200) {
                            musicRV!!.adapter =
                                MusicAdapter(response.body()?.musics as MutableList<Music>)

                            musicSL!!.stopShimmer()
                            musicSL!!.visibility = View.GONE
                        } else {
                            println("status code is " + response.code())
                        }
                    }

                    override fun onFailure(
                        call: Call<MusicService.MusicsResponse>,
                        t: Throwable
                    ) {
                        println("HTTP ERROR")
                        t.printStackTrace()
                    }

                }
            )


        return view
    }
}