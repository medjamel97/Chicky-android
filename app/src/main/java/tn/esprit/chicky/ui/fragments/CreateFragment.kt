package tn.esprit.chicky.ui.fragments

import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.database.Cursor
import android.graphics.Bitmap
import android.media.MediaMetadataRetriever
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputEditText
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import tn.esprit.chicky.R
import tn.esprit.chicky.service.ApiService
import tn.esprit.chicky.service.PostService
import java.io.File

class CreateFragment : Fragment() {

    var postIV: ImageView? = null
    var titelTI: TextInputEditText? = null
    var descriptionTI: TextInputEditText? = null
    var addPostBtn: Button? = null
    var chooseVideoButton: Button? = null


    companion object {
        const val GALLERY_RESULT = 2
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_create, container, false)

        postIV = view.findViewById(R.id.postIV)
        titelTI = view.findViewById(R.id.titleTI)
        descriptionTI = view.findViewById(R.id.descriptionTI)
        addPostBtn = view.findViewById(R.id.addPostBtn)
        chooseVideoButton = view.findViewById(R.id.chooseVideoButton)

        chooseVideoButton!!.setOnClickListener {
            openSystemGalleryToSelectAVideo()
        }

        addPostBtn!!.setOnClickListener {
            ApiService.postService.addPost(
                PostService.PostBody(
                    titelTI!!.text.toString(),
                    descriptionTI!!.text.toString()
                )
            ).enqueue(
                object : Callback<PostService.PostResponse> {
                    override fun onResponse(
                        call: Call<PostService.PostResponse>,
                        response: Response<PostService.PostResponse>
                    ) {
                        if (response.code() == 200) {
                            Snackbar.make(view, "Post added", Snackbar.LENGTH_SHORT).show()
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

    private fun openSystemGalleryToSelectAVideo() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.setDataAndType(MediaStore.Video.Media.EXTERNAL_CONTENT_URI, "video/*")

        try {
            startActivityForResult(intent, GALLERY_RESULT)
        } catch (ex: ActivityNotFoundException) {
            Toast.makeText(
                requireContext(),
                "No Gallery APP installed",
                Toast.LENGTH_LONG
            ).show()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == Activity.RESULT_OK && requestCode == GALLERY_RESULT) {
            val videoUri = data?.data ?: return
            val bitmap = getVideoThumbnail(requireContext(), videoUri, 500, 500)

            postIV?.setImageBitmap(bitmap)
            //val mimeType: String = contentResolver.getType(videoUri)
            //val file = saveVideoToAppScopeStorage(this, videoUri, mimeType)
        }
    }

    private fun getVideoThumbnail(
        context: Context,
        videoUri: Uri,
        imageWidth: Int,
        imageHeight: Int
    ): Bitmap? {
        var bitmap: Bitmap? = null
        val mMMR = MediaMetadataRetriever()
        mMMR.setDataSource(context, videoUri)
        bitmap = mMMR.getScaledFrameAtTime(
            -1,
            MediaMetadataRetriever.OPTION_CLOSEST_SYNC,
            imageWidth,
            imageHeight
        )

        return bitmap
    }
}