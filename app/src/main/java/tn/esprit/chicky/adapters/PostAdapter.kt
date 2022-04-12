package tn.esprit.curriculumvitaev2medjameleddinebouassida.adapters;

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

        private val postTitleTV: TextView = itemView.findViewById(R.id.postTitleTV)
        private val postDescriptionTV: TextView = itemView.findViewById(R.id.postDescriptionTV)
        private val deleteButton: TextView = itemView.findViewById(R.id.deleteButton)
        private val reportButton: TextView = itemView.findViewById(R.id.reportButton)


        fun bindView(item: Post) {
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
                                Snackbar.make(itemView, "Post Deleted", Snackbar.LENGTH_SHORT).show()
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
            itemView.setOnClickListener {

            }
        }
    }
}