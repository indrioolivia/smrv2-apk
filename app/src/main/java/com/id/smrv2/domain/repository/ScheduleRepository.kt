package com.id.smrv2.domain.repository

import com.id.smrv2.domain.model.Schedule
import kotlinx.coroutines.flow.Flow

interface ScheduleRepository {
    suspend fun getSchedules(program: String, day: String): Flow<List<Schedule>>
    suspend fun getStudyPrograms(): Flow<List<String>>
} 