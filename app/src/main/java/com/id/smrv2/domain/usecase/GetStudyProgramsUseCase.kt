package com.id.smrv2.domain.usecase

import com.id.smrv2.domain.repository.ScheduleRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetStudyProgramsUseCase @Inject constructor(
    private val repository: ScheduleRepository
) {
    suspend operator fun invoke(): Flow<List<String>> {
        return repository.getStudyPrograms()
    }
} 