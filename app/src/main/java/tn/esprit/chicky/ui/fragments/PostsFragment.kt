package tn.esprit.chicky.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import tn.esprit.chicky.R
import tn.esprit.chicky.adapters.FullPostAdapter
import tn.esprit.chicky.models.Post
import tn.esprit.chicky.service.ApiService
import tn.esprit.chicky.service.PostService

class PostsFragment : Fragment() {

    var viewPagerVideos: ViewPager2? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_posts, container, false)

        // FIND VIEWS
        viewPagerVideos = view.findViewById(R.id.viewPagerVideos);

        ApiService.postService.getPosts()
            .enqueue(
                object : Callback<PostService.PostsResponse> {
                    override fun onResponse(
                        call: Call<PostService.PostsResponse>,
                        response: Response<PostService.PostsResponse>
                    ) {
                        if (response.code() == 200) {
                            viewPagerVideos!!.adapter =
                                FullPostAdapter(response.body()?.posts!!.reversed() as MutableList<Post>)
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

        return view
    }
}