package com.id.smrv2.data.repository

import com.id.smrv2.data.remote.ApiService
import com.id.smrv2.domain.model.Schedule
import com.id.smrv2.domain.repository.ScheduleRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class ScheduleRepositoryImpl @Inject constructor(
    private val apiService: ApiService
) : ScheduleRepository {
    override suspend fun getSchedules(program: String, day: String): Flow<List<Schedule>> {
        return flow {
            try {
                val cleanProgram = program
                    .substringBefore(" (")
                    .lowercase()

                val response = apiService.getSchedule(
                    studyProgram = cleanProgram,
                    day = day.lowercase()
                )
                val schedules = response.data.map { item ->
                    Schedule(
                        day = item.day,
                        code = item.code,
                        course = item.course,
                        className = item.className,
                        credits = item.credits,
                        hours = item.hours,
                        semester = item.semester,
                        lecturer = item.lecturer,
                        room = item.room
                    )
                }
                emit(schedules)
            } catch (e: Exception) {
                emit(emptyList())
                // In a real app, you might want to handle errors differently
            }
        }
    }

    override suspend fun getStudyPrograms(): Flow<List<String>> {
        return flow {
            try {
                val response = apiService.getStudyPrograms()
                val programNames = response.data.map { it.name + " (${it.faculty})" }
                emit(programNames)
            } catch (e: Exception) {
                emit(emptyList())
            }
        }
    }
} 