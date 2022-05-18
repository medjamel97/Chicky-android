package tn.esprit.chicky.ui.activities

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import tn.esprit.chicky.R

class CommentsModal : BottomSheetDialogFragment() {

    companion object {
        const val TAG = "CommentsModal"
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.modal_comments, container, false)

        /*commentsRV!!.adapter = CommentAdapter(commentsList as MutableList<Comment>)
        commentsRV!!.layoutManager =
            LinearLayoutManager(baseContext, LinearLayoutManager.VERTICAL, false)

        addBtn.setOnClickListener {
            val comment = Comment(
                Calendar.getInstance().time,
                sharedPreferences.getString("_id", null)!!,
                sharedPreferences.getString("EMAIL", null)!!,
                post._id!!,
                commentTIET.text.toString()
            )

            ApiService.commentService.addComment(
                CommentService.CommentBody(
                    comment.post!!,
                    comment.user!!,
                    comment.description!!
                )
            ).enqueue(object : Callback<CommentService.CommentResponse> {
                override fun onResponse(
                    call: Call<CommentService.CommentResponse>,
                    response: Response<CommentService.CommentResponse>
                ) {
                    if (response.code() == 200) {
                        (commentsList as MutableList<Comment>).add(comment)
                        commentTIET.setText("")
                        commentsRV!!.adapter?.notifyDataSetChanged()
                    }
                }

                override fun onFailure(
                    call: Call<CommentService.CommentResponse>,
                    t: Throwable
                ) {
                    t.printStackTrace()
                }
            })
        }*/

        return view;
    }
}