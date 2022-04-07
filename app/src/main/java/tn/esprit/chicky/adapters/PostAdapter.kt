package tn.esprit.curriculumvitaev2medjameleddinebouassida.adapters;

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.VideoView
import androidx.recyclerview.widget.RecyclerView
import tn.esprit.chicky.models.Post
import tn.esprit.chicky.R

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
        //private val userAvatar: ImageView = itemView.findViewById(R.id.userAvatarFeed)
        //private val text: TextView = itemView.findViewById(R.id.DescriptionPost)
        //private val userName: TextView = itemView.findViewById(R.id.UserNamePublication)
        //private val video: VideoView = itemView.findViewById(R.id.videoFeed)
        //private val progressbar: ProgressBar = itemView.findViewById(R.id.progressvideo)
        //private val probleem: ImageView = itemView.findViewById(R.id.errorVideo)
        //private val likesnumber: TextView = itemView.findViewById(R.id.likesnumber)
        //private val likeIcon: ImageView = itemView.findViewById(R.id.likeIcon)
        //private val likeIconRed: ImageView = itemView.findViewById(R.id.likeIconRed)

        fun bindView(item: Post) {
            postTitleTV.text = item.title

            itemView.setOnClickListener {

            }
        }
    }
}