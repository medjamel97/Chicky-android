package tn.esprit.chicky.adapters;

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import tn.esprit.chicky.R
import tn.esprit.chicky.models.Conversation
import tn.esprit.chicky.service.ApiService
import tn.esprit.chicky.service.ConversationService

class ConversationAdapter(var items: MutableList<Conversation>) :
    RecyclerView.Adapter<ConversationAdapter.ConversationViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ConversationViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.element_conversation, parent, false)
        return ConversationViewHolder(view)
    }

    override fun onBindViewHolder(holder: ConversationViewHolder, position: Int) {
        holder.bindView(items[position])
    }

    override fun getItemCount(): Int = items.size

    class ConversationViewHolder(view: View) :
        RecyclerView.ViewHolder(view) {

        private val profilePictureIV: ImageView = itemView.findViewById(R.id.profilePictureIV)
        private val conversationNameTV: TextView = itemView.findViewById(R.id.conversationNameTV)
        private val lastMessageTV: TextView = itemView.findViewById(R.id.lastMessageTV)
        private val deleteConversationButton: TextView =
            itemView.findViewById(R.id.deleteConversationButton)

        fun bindView(item: Conversation) {

            itemView.setOnClickListener {

            }

            //profilePictureIV. = item.
            conversationNameTV.text = item.name
            lastMessageTV.text = item.lastMessage

            deleteConversationButton.setOnClickListener {
                ApiService.conversationService.deleteConversation(item._id)?.enqueue(
                    object : Callback<ConversationService.MessageResponse?> {
                        override fun onResponse(
                            call: Call<ConversationService.MessageResponse?>,
                            response: Response<ConversationService.MessageResponse?>
                        ) {
                            if (response.code() == 200) {
                                Snackbar.make(
                                    itemView,
                                    "Conversation Deleted",
                                    Snackbar.LENGTH_SHORT
                                )
                                    .show()
                            } else {
                                Log.d("BODY", "id code is " + item._id)
                                Log.d("HTTP ERROR", "status code is " + response.code())
                            }
                        }

                        override fun onFailure(
                            call: Call<ConversationService.MessageResponse?>,
                            t: Throwable
                        ) {
                            Log.d("FAIL", "fail")
                        }

                    }
                )
            }
        }
    }
}