package tn.esprit.chicky.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import tn.esprit.chicky.R
import tn.esprit.chicky.adapters.ConversationAdapter
import tn.esprit.chicky.models.Conversation

class ChatFragment : Fragment() {

    var conversationsRV: RecyclerView? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_chat, container, false)

        conversationsRV = view.findViewById(R.id.conversationsRV)

        conversationsRV!!.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        conversationsRV!!.adapter = ConversationAdapter(
            listOf(
                Conversation("", "Nom 1", "Message 1"),
                Conversation("", "Nom 2", "Message 2"),
                Conversation("", "Nom 3", "Message 3"),
                Conversation("", "Nom 4", "Message 4"),
            ) as MutableList<Conversation>
        )

        return view
    }
}