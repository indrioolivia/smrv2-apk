package com.id.smrv2

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.id.smrv2.presentation.components.ScheduleItem
import com.id.smrv2.presentation.components.ScheduleItemSkeleton
import com.id.smrv2.presentation.viewmodel.ScheduleViewModel
import com.id.smrv2.ui.theme.Smrv2Theme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val viewModel: ScheduleViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Smrv2Theme {
                ScheduleScreen(viewModel)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScheduleScreen(viewModel: ScheduleViewModel) {
    val uiState by viewModel.uiState.collectAsState()
    var isProdiExpanded by remember { mutableStateOf(false) }
    var isDayExpanded by remember { mutableStateOf(false) }

    Scaffold(
        modifier = Modifier.fillMaxSize()
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            item {
                Text(
                    text = "Smrv2",
                    fontSize = 32.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
                
                Text(
                    text = "Sistem Manajemen Ruang Universitas Ahmad Dahlan",
                    fontSize = 14.sp,
                    modifier = Modifier.padding(bottom = 24.dp)
                )

                if (uiState.isLoading) {
                    CircularProgressIndicator(
                        modifier = Modifier.padding(16.dp)
                    )
                }

                uiState.error?.let { error ->
                    Text(
                        text = error,
                        color = MaterialTheme.colorScheme.error,
                        modifier = Modifier.padding(bottom = 16.dp)
                    )
                }

                // Searchable Prodi Dropdown
                ExposedDropdownMenuBox(
                    expanded = isProdiExpanded,
                    onExpandedChange = { isProdiExpanded = it },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp)
                ) {
                    OutlinedTextField(
                        value = uiState.selectedProgram,
                        onValueChange = { 
                            viewModel.onProgramChanged(it)
                            isProdiExpanded = true
                        },
                        label = { Text("Prodi") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .menuAnchor()
                    )

                    ExposedDropdownMenu(
                        expanded = isProdiExpanded,
                        onDismissRequest = { isProdiExpanded = false }
                    ) {
                        uiState.filteredPrograms.forEach { program ->
                            DropdownMenuItem(
                                text = { Text(program) },
                                onClick = {
                                    viewModel.onProgramChanged(program)
                                    isProdiExpanded = false
                                }
                            )
                        }
                    }
                }

                // Day Dropdown
                ExposedDropdownMenuBox(
                    expanded = isDayExpanded,
                    onExpandedChange = { isDayExpanded = it },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp)
                ) {
                    OutlinedTextField(
                        value = uiState.selectedDay,
                        onValueChange = {},
                        readOnly = true,
                        label = { Text("Hari") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .menuAnchor()
                    )

                    ExposedDropdownMenu(
                        expanded = isDayExpanded,
                        onDismissRequest = { isDayExpanded = false }
                    ) {
                        uiState.days.forEach { day ->
                            DropdownMenuItem(
                                text = { Text(day) },
                                onClick = {
                                    viewModel.onDayChanged(day)
                                    isDayExpanded = false
                                }
                            )
                        }
                    }
                }

                Button(
                    onClick = { viewModel.searchSchedule() },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp)
                ) {
                    Text("Search")
                }

                Spacer(modifier = Modifier.height(16.dp))
            }

            // Show loading skeletons or results
            if (uiState.isLoading) {
                items(5) {
                    ScheduleItemSkeleton()
                }
            } else {
                items(uiState.schedules) { schedule ->
                    ScheduleItem(schedule = schedule)
                }
            }
        }
    }
}