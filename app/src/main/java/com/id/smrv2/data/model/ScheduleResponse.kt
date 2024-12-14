package com.id.smrv2.data.model

import com.google.gson.annotations.SerializedName

data class ScheduleResponse(
    @SerializedName("data")
    val data: List<ScheduleItem>
)

data class ScheduleItem(
    @SerializedName("hari")
    val day: String,
    @SerializedName("kode")
    val code: String,
    @SerializedName("matkul")
    val course: String,
    @SerializedName("kelas")
    val className: String,
    @SerializedName("sks")
    val credits: String,
    @SerializedName("jam")
    val hours: String,
    @SerializedName("semester")
    val semester: String,
    @SerializedName("dosen")
    val lecturer: String,
    @SerializedName("ruang")
    val room: String
) 