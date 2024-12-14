package com.id.smrv2.data.model

import com.google.gson.annotations.SerializedName

data class StudyProgramResponse(
    @SerializedName("data")
    val data: List<StudyProgram>
)

data class StudyProgram(
    @SerializedName("faculty")
    val faculty: String,
    @SerializedName("value")
    val value: String,
    @SerializedName("name")
    val name: String
) 