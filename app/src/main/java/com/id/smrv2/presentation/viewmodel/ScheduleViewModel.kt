package com.id.smrv2.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.id.smrv2.domain.model.Schedule
import com.id.smrv2.domain.usecase.GetSchedulesUseCase
import com.id.smrv2.domain.usecase.GetStudyProgramsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class ScheduleUiState(
    val selectedProgram: String = "",
    val selectedDay: String = "",
    val programs: List<String> = emptyList(),
    val filteredPrograms: List<String> = emptyList(),
    val schedules: List<Schedule> = emptyList(),
    val days: List<String> = listOf("Senin", "Selasa", "Rabu", "Kamis", "Jumat", "Sabtu"),
    val isLoading: Boolean = false,
    val error: String? = null
)

@HiltViewModel
class ScheduleViewModel @Inject constructor(
    private val getStudyProgramsUseCase: GetStudyProgramsUseCase,
    private val getSchedulesUseCase: GetSchedulesUseCase
) : ViewModel() {
    private val _uiState = MutableStateFlow(ScheduleUiState())
    val uiState: StateFlow<ScheduleUiState> = _uiState.asStateFlow()

    init {
        fetchStudyPrograms()
    }

    private fun fetchStudyPrograms() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            try {
                getStudyProgramsUseCase().collect { programs ->
                    _uiState.update { 
                        it.copy(
                            programs = programs,
                            filteredPrograms = programs,
                            isLoading = false,
                            error = null
                        )
                    }
                }
            } catch (e: Exception) {
                _uiState.update { 
                    it.copy(
                        isLoading = false,
                        error = "Failed to load study programs: ${e.message}"
                    )
                }
            }
        }
    }

    fun onProgramChanged(program: String) {
        _uiState.update { currentState ->
            currentState.copy(
                selectedProgram = program,
                filteredPrograms = if (program.isBlank()) {
                    currentState.programs
                } else {
                    currentState.programs.filter { 
                        it.contains(program, ignoreCase = true) 
                    }
                }
            )
        }
    }

    fun onDayChanged(day: String) {
        _uiState.update { it.copy(selectedDay = day) }
    }

    fun searchSchedule() {
        val program = _uiState.value.selectedProgram
        val day = _uiState.value.selectedDay
        
        if (program.isBlank() || day.isBlank()) {
            _uiState.update { 
                it.copy(error = "Please select both program study and day")
            }
            return
        }

        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null) }
            try {
                getSchedulesUseCase(program, day).collect { schedules ->
                    _uiState.update { 
                        it.copy(
                            schedules = schedules,
                            isLoading = false,
                            error = if (schedules.isEmpty()) "No schedules found" else null
                        )
                    }
                }
            } catch (e: Exception) {
                _uiState.update { 
                    it.copy(
                        isLoading = false,
                        error = "Failed to load schedules: ${e.message}"
                    )
                }
            }
        }
    }
} 