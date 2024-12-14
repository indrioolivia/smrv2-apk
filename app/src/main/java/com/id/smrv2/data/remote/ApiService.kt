package com.id.smrv2.data.remote

import com.id.smrv2.data.model.ScheduleResponse
import com.id.smrv2.data.model.StudyProgramResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("api/v1/study-programs")
    suspend fun getStudyPrograms(): StudyProgramResponse

    @GET("api/v1/schedule")
    suspend fun getSchedule(
        @Query("study_programs") studyProgram: String,
        @Query("day") day: String
    ): ScheduleResponse
} 