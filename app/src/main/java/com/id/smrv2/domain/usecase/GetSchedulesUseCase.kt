package com.id.smrv2.domain.usecase

import com.id.smrv2.domain.model.Schedule
import com.id.smrv2.domain.repository.ScheduleRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetSchedulesUseCase @Inject constructor(
    private val repository: ScheduleRepository
) {
    suspend operator fun invoke(program: String, day: String): Flow<List<Schedule>> {
        return repository.getSchedules(program, day)
    }
} 