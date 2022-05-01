package tn.esprit.chicky.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.facebook.shimmer.ShimmerFrameLayout
import tn.esprit.chicky.R

class ChatFragment : Fragment() {

    private var conversationsRV: RecyclerView? = null
    private var shimmerFrameLayout: ShimmerFrameLayout? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_chat, container, false)

        // VIEW BINDING
        shimmerFrameLayout = view.findViewById(R.id.shimmerLayout)
        conversationsRV = view.findViewById(R.id.conversationsRV)

        shimmerFrameLayout!!.startShimmer();
        conversationsRV!!.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)


        /*Handler().postDelayed({
            conversationsRV!!.adapter = ConversationAdapter(
                listOf(
                    Conversation("", "Nom 1", "Message 1"),
                    Conversation("", "Nom 2", "Message 2"),
                    Conversation("", "Nom 3", "Message 3"),
                    Conversation("", "Nom 4", "Message 4"),
                ) as MutableList<Conversation>
            )
            shimmerFrameLayout!!.stopShimmer()
            shimmerFrameLayout!!.visibility = View.GONE

        }, 3000)*/

        return view
    }
}