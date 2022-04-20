package tn.esprit.chicky.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import tn.esprit.chicky.R
import tn.esprit.chicky.adapters.SearchAdapter

class SearchFragment : Fragment() {

    var postsRV: RecyclerView? = null
    var peopleRV: RecyclerView? = null
    var musicRV: RecyclerView? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_search, container, false)

        postsRV = view.findViewById(R.id.postsRV)
        peopleRV = view.findViewById(R.id.peopleRV)
        musicRV = view.findViewById(R.id.musicRV)


        postsRV!!.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        postsRV!!.adapter = SearchAdapter(
            listOf(
                "post 1",
                "post 2",
                "post 3",
                "post 4",
                "post 5"
            ) as MutableList<String>
        )

        peopleRV!!.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        peopleRV!!.adapter = SearchAdapter(
            listOf(
                "person 1",
                "person 2",
                "person 3",
                "person 4",
                "person 5"
            ) as MutableList<String>
        )

        musicRV!!.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        musicRV!!.adapter = SearchAdapter(
            listOf(
                "music 1",
                "music 2",
                "music 3",
                "music 4",
                "music 5"
            ) as MutableList<String>
        )

        return view
    }
}