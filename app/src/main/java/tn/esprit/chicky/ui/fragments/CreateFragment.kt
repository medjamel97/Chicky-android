package tn.esprit.chicky.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import com.google.android.material.textfield.TextInputEditText
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import tn.esprit.chicky.R
import tn.esprit.chicky.service.ApiService
import tn.esprit.chicky.service.PostService

class CreateFragment : Fragment() {

    var titelTI: TextInputEditText? = null
    var addPostBtn: Button? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_create, container, false)

        titelTI = view.findViewById(R.id.titleTI)
        addPostBtn = view.findViewById(R.id.addPostBtn)

        addPostBtn!!.setOnClickListener {
            ApiService.postService.addPost(
                PostService.PostBody(
                    titelTI!!.text.toString(),
                    titelTI!!.text.toString()
                )
            )
                .enqueue(
                    object : Callback<PostService.PostResponse> {
                        override fun onResponse(
                            call: Call<PostService.PostResponse>,
                            response: Response<PostService.PostResponse>
                        ) {
                            if (response.code() == 200) {

                            } else {
                                Log.d("HTTP ERROR", "status code is " + response.code())
                            }
                        }

                        override fun onFailure(
                            call: Call<PostService.PostResponse>,
                            t: Throwable
                        ) {
                            Log.d("FAIL", "fail")
                        }
                    }
                )
        }

        return view
    }
}