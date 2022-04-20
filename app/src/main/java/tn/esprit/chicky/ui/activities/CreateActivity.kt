package tn.esprit.chicky.ui.activities

import android.Manifest
import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.media.MediaMetadataRetriever
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputEditText
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import tn.esprit.chicky.R
import tn.esprit.chicky.service.ApiService
import tn.esprit.chicky.service.PostService
import java.io.*
import java.util.*

class CreateActivity : AppCompatActivity() {

    private val IMAGE_DIRECTORY = "/uploads"

    private var postIV: ImageView? = null
    private var titelTI: TextInputEditText? = null
    private var descriptionTI: TextInputEditText? = null
    private var addPostBtn: Button? = null
    private var chooseVideoButton: FloatingActionButton? = null

    private var videoPath: String? = null

    companion object {
        const val GALLERY_RESULT = 2
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create)

        ActivityCompat.requestPermissions(
            this,
            arrayOf(
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            ),
            123
        )

        postIV = findViewById(R.id.postIV)
        titelTI = findViewById(R.id.titleTI)
        descriptionTI = findViewById(R.id.descriptionTI)
        addPostBtn = findViewById(R.id.addPostBtn)
        chooseVideoButton = findViewById(R.id.chooseVideoButton)

        chooseVideoButton!!.setOnClickListener {
            openSystemGalleryToSelectAVideo()
        }

        addPostBtn!!.setOnClickListener {
            uploadVideo()
        }
    }

    private fun openSystemGalleryToSelectAVideo() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.setDataAndType(MediaStore.Video.Media.EXTERNAL_CONTENT_URI, "video/*")

        try {
            startActivityForResult(intent, GALLERY_RESULT)
        } catch (ex: ActivityNotFoundException) {
            Toast.makeText(
                baseContext,
                "No Gallery APP installed",
                Toast.LENGTH_LONG
            ).show()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode == Activity.RESULT_OK && requestCode == GALLERY_RESULT) {
            val videoUri = data?.data ?: return
            val bitmap = getVideoThumbnail(baseContext, videoUri, 500, 500)

            postIV?.setImageBitmap(bitmap)
            postIV?.scaleType = ImageView.ScaleType.CENTER_CROP

            videoPath = getFilePathFromURI(videoUri)
            Log.d("Path is", videoPath!!)
        }

        super.onActivityResult(requestCode, resultCode, data)
    }

    private fun getFilePathFromURI(contentUri: Uri?): String {
        var copyFile: File? = null
        baseContext?.let {
            if (contentUri != null) {
                copyFile = savefile(contentUri)
            }
        }
        Log.d("vPath--->", copyFile!!.absolutePath)
        return copyFile!!.absolutePath
    }

    private fun savefile(sourceuri: Uri): File {
        val sourceFilename: String? = sourceuri.path
        val destinationFilename =
            Environment.getExternalStorageDirectory().path + File.separatorChar.toString() + "abc.mp4"
        var bis: BufferedInputStream? = null
        var bos: BufferedOutputStream? = null
        try {
            bis = BufferedInputStream(FileInputStream(sourceFilename))
            bos = BufferedOutputStream(FileOutputStream(destinationFilename, false))
            val buf = ByteArray(1024)
            bis.read(buf)
            do {
                bos.write(buf)
            } while (bis.read(buf) !== -1)
        } catch (e: IOException) {
            e.printStackTrace()
        } finally {
            try {
                bis?.close()
                bos?.close()
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
        return File(destinationFilename)
    }

    @Throws(Exception::class, IOException::class)
    fun copystream(input: InputStream?, output: OutputStream?): Int {
        val buffer = ByteArray(DEFAULT_BUFFER_SIZE)
        val `in` = BufferedInputStream(input, DEFAULT_BUFFER_SIZE)
        val out = BufferedOutputStream(output, DEFAULT_BUFFER_SIZE)
        var count = 0
        var n = 0
        try {
            while (`in`.read(buffer, 0, DEFAULT_BUFFER_SIZE).also { n = it } != -1) {
                out.write(buffer, 0, n)
                count += n
            }
            out.flush()
        } finally {
            try {
                out.close()
            } catch (e: IOException) {
                Log.e(e.message, e.toString())
            }
            try {
                `in`.close()
            } catch (e: IOException) {
                Log.e(e.message, e.toString())
            }
        }
        return count
    }

    private fun uploadVideo() {
        val file = File(videoPath!!.toString())
        val requestBody = RequestBody.create(MediaType.parse("*/*"), file)
        val fileToUpload = MultipartBody.Part.createFormData("video", file.name, requestBody)
        val filename = RequestBody.create(MediaType.parse("text/plain"), "pdfname")
        Log.d("HEYYY", file.absolutePath)
        ApiService.postService.addPost(
            /*PostService.PostBody(
                titelTI!!.text.toString(),
                descriptionTI!!.text.toString()
            ),*/ fileToUpload, filename
        )?.enqueue(
            object : Callback<PostService.PostResponse> {
                override fun onResponse(
                    call: Call<PostService.PostResponse>,
                    response: Response<PostService.PostResponse>
                ) {
                    if (response.code() == 200) {
                        Toast.makeText(baseContext, "Post added", Toast.LENGTH_SHORT).show()
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