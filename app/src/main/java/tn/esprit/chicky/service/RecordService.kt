package tn.esprit.chicky.service

import com.google.gson.annotations.SerializedName
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import tn.esprit.chicky.models.Record

interface RecordService {

    data class RecordResponse(
        @SerializedName("record")
        val record: Record
    )

    data class RecordsResponse(
        @SerializedName("records")
        val records: List<Record>
    )

    data class RecordBody(
        val idUser: String,
        val longitude: Double,
        val lattitude: Double,
        val locationName: String
    )

    @GET("/record")
    fun getAll(): Call<RecordsResponse>

    @POST("/record/add-or-update")
    fun addOrUpdate(@Body recordBody: RecordBody): Call<RecordResponse>
}