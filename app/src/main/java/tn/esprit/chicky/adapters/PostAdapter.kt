package tn.esprit.chicky.adapters;

import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import tn.esprit.chicky.models.Post
import tn.esprit.chicky.R
import tn.esprit.chicky.service.ApiService
import tn.esprit.chicky.service.PostService
import android.widget.VideoView

class PostAdapter(var items: MutableList<Post>) :

    RecyclerView.Adapter<PostAdapter.PostViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.element_post, parent, false)
        return PostViewHolder(view)
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        holder.bindView(items[position])
    }

    override fun getItemCount(): Int = items.size

    class PostViewHolder(view: View) :
        RecyclerView.ViewHolder(view) {

        private val videoView: VideoView = itemView.findViewById(R.id.videoView)
        private val postTitleTV: TextView = itemView.findViewById(R.id.postTitleTV)
        private val postDescriptionTV: TextView = itemView.findViewById(R.id.postDescriptionTV)
        private val deleteButton: TextView = itemView.findViewById(R.id.deleteButton)
        private val reportButton: TextView = itemView.findViewById(R.id.reportButton)
        private val progressBar: ProgressBar = itemView.findViewById(R.id.progressBar)

        fun bindView(item: Post) {

            itemView.setOnClickListener {

            }

            videoView.setVideoURI(Uri.parse("http://10.0.2.2:5000/vid/bmw.mp4"))
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

            postTitleTV.text = item.title
            postDescriptionTV.text = item.description

            deleteButton.setOnClickListener {
                ApiService.postService.deletePost(item._id)?.enqueue(
                    object : Callback<PostService.MessageResponse?> {
                        override fun onResponse(
                            call: Call<PostService.MessageResponse?>,
                            response: Response<PostService.MessageResponse?>
                        ) {
                            if (response.code() == 200) {
                                Snackbar.make(itemView, "Post Deleted", Snackbar.LENGTH_SHORT)
                                    .show()
                            } else {
                                Log.d("BODY", "id code is " + item._id)
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
        }
    }
}