package tn.esprit.chicky.ui.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import tn.esprit.chicky.R
import tn.esprit.chicky.models.Post
import tn.esprit.chicky.service.ApiService
import tn.esprit.chicky.service.PostService
import tn.esprit.curriculumvitaev2medjameleddinebouassida.adapters.PostAdapter

class ChatFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_chat, container, false)

        // TODO

        return view
    }
}