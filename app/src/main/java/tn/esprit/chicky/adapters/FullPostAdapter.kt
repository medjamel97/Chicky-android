package tn.esprit.chicky.adapters;

import android.content.Intent
import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.VideoView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import tn.esprit.chicky.R
import tn.esprit.chicky.models.Post
import tn.esprit.chicky.service.ApiService
import tn.esprit.chicky.service.PostService
import tn.esprit.chicky.service.UserService
import tn.esprit.chicky.ui.activities.LoginActivity
import tn.esprit.chicky.utils.Constants
import java.time.LocalDateTime

class FullPostAdapter(var items: MutableList<Post>) :

    RecyclerView.Adapter<FullPostAdapter.PostViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.single_post, parent, false)
        return PostViewHolder(view)
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        holder.bindView(items[position])
    }

    override fun getItemCount(): Int = items.size

    class PostViewHolder(view: View) :
        RecyclerView.ViewHolder(view) {
        val currentTime = LocalDateTime.now()
        private val videoView: VideoView = itemView.findViewById(R.id.videoView)
        private val postTitleTV: TextView = itemView.findViewById(R.id.postTitleTV)
        private val postDescriptionTV: TextView = itemView.findViewById(R.id.postDescriptionTV)
        private val deleteButton: TextView = itemView.findViewById(R.id.deleteButton)
        private val reportButton: TextView = itemView.findViewById(R.id.reportButton)
        private val progressBar: ProgressBar = itemView.findViewById(R.id.progressBar)
        var likebutton: Button? = itemView.findViewById(R.id.likebutton)

        fun bindView(post: Post) {

            itemView.setOnClickListener {

            }

            videoView.setVideoURI(Uri.parse(Constants.BASE_URL_VIDEOS + post.videoFilename))
            videoView.setOnPreparedListener { mp ->
                progressBar.visibility = View.GONE
                mp.start()
                val videoRatio = mp.videoWidth / mp.videoHeight.toFloat()
                val screenRatio: Float = videoView.width / videoView.height.toFloat()
                val scale = videoRatio / screenRatio
                if (scale >= 1f) {
                    videoView.scaleX = scale
                } else {
                    videoView.scaleY = 1f / scale
                }
            }
            videoView.setOnCompletionListener { mp -> mp.start() }

            postTitleTV.text = post.title
            postDescriptionTV.text = post.description

            deleteButton.setOnClickListener {
                ApiService.postService.deletePost(post._id)?.enqueue(
                    object : Callback<PostService.MessageResponse?> {
                        override fun onResponse(
                            call: Call<PostService.MessageResponse?>,
                            response: Response<PostService.MessageResponse?>
                        ) {
                            if (response.code() == 200) {
                                Snackbar.make(itemView, "Post Deleted", Snackbar.LENGTH_SHORT)
                                    .show()
                            } else {
                                Log.d("BODY", "id code is " + post._id)
                                Log.d("HTTP ERROR", "status code is " + response.code())
                            }
                        }


                        override fun onFailure(
                            call: Call<PostService.MessageResponse?>,
                            t: Throwable
                        ) {
                            Log.d("FAIL", "fail")
                        }

                    }
                )
            }
            likebutton?.setOnClickListener {
                ApiService.postService.addlike(
                    PostService.LikeBody(
                        "a", ""
                    )
                ).enqueue(
                    object : Callback<PostService.LikesResponse> {
                        override fun onResponse(
                            call: Call<PostService.LikesResponse>,
                            response: Response<PostService.LikesResponse>
                        ) {
                            if (response.code() == 200) {

                            } else {
                                Log.d("HTTP ERROR", "status code is " + response.code())
                            }
                        }

                        override fun onFailure(
                            call: Call<PostService.LikesResponse>,
                            t: Throwable
                        ) {
                            Log.d("FAIL", "fail")
                        }
                    }
                )
            }

            reportButton.setOnClickListener {
                Snackbar.make(itemView, "Coming soon", Snackbar.LENGTH_SHORT).show()
            }
        }
    }
}