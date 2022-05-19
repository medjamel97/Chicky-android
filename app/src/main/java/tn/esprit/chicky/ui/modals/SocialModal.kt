package tn.esprit.chicky.ui.modals

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import tn.esprit.chicky.R
import tn.esprit.chicky.adapters.RecordAdapter
import tn.esprit.chicky.models.Record
import tn.esprit.chicky.service.ApiService
import tn.esprit.chicky.service.RecordService

class SocialModal : BottomSheetDialogFragment() {

    companion object {
        const val TAG = "SocialModal"
    }

    var recordsRV: RecyclerView? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.modal_social, container, false)

        recordsRV = view.findViewById(R.id.recordsRV)
        recordsRV!!.layoutManager =
            LinearLayoutManager(view.context, LinearLayoutManager.VERTICAL, false)

        ApiService.recordService.getAll().enqueue(object : Callback<RecordService.RecordsResponse> {
            override fun onResponse(
                call: Call<RecordService.RecordsResponse>,
                response: Response<RecordService.RecordsResponse>
            ) {
                if (response.code() == 200) {
                    recordsRV!!.adapter = RecordAdapter(response.body()?.records!! as MutableList<Record>)
                }
            }

            override fun onFailure(
                call: Call<RecordService.RecordsResponse>,
                t: Throwable
            ) {
                t.printStackTrace()
            }
        })

        return view
    }
}