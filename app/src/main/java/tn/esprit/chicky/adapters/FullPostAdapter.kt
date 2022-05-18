package tn.esprit.chicky.adapters;

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.VideoView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import tn.esprit.chicky.R
import tn.esprit.chicky.models.Post
import tn.esprit.chicky.models.User
import tn.esprit.chicky.service.ApiService
import tn.esprit.chicky.service.LikeService
import tn.esprit.chicky.service.PostService
import tn.esprit.chicky.ui.activities.CommentsModal
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
        private val likeCountTV: TextView = itemView.findViewById(R.id.likeCountTV)
        private val likeButton: ImageButton = itemView.findViewById(R.id.likeButton)
        private val commentsButton: ImageButton = itemView.findViewById(R.id.commentsButton)

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

            reportButton.setOnClickListener {
                Snackbar.make(itemView, "Coming soon", Snackbar.LENGTH_SHORT).show()
            }

            var userHasLike = false
            var currentLikeCount = post.likes!!.size

            val sharedPreferences =
                itemView.context.getSharedPreferences(Constants.SHARED_PREF_SESSION, Context.MODE_PRIVATE)
            val userData = sharedPreferences.getString("USER_DATA", null)

            val user: User? = if (userData != null) {
                Gson().fromJson(userData, User::class.java)
            } else {
                null
            }

            for (like in post.likes) {
                if (like.user!!._id == userData) {
                    likeButton.setImageResource(R.drawable.ic_favorite)
                    userHasLike = true
                }
            }

            likeCountTV.text = currentLikeCount.toString()

            likeButton.setOnClickListener {

                ApiService.likeService.addOrRemoveLike(
                    LikeService.LikeBody(post._id, user!!._id)
                ).enqueue(object : Callback<LikeService.LikeResponse> {
                    override fun onResponse(
                        call: Call<LikeService.LikeResponse>,
                        response: Response<LikeService.LikeResponse>
                    ) {
                        if (response.code() == 200) {
                            if (userHasLike) {
                                currentLikeCount -= 1
                                likeCountTV.text = currentLikeCount.toString()
                                likeButton.setImageResource(R.drawable.ic_favorite_border)
                            } else {
                                currentLikeCount += 1
                                likeCountTV.text = currentLikeCount.toString()
                                likeButton.setImageResource(R.drawable.ic_favorite)
                            }
                            userHasLike = !userHasLike
                        }
                    }

                    override fun onFailure(
                        call: Call<LikeService.LikeResponse>,
                        t: Throwable
                    ) {
                        t.printStackTrace()
                    }
                })
            }
            commentsButton.setOnClickListener {
                CommentsModal().apply {
                    show(activity!!.supportFragmentManager, CommentsModal.TAG)
                }
            }
        }
    }
}