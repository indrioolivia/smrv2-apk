package com.id.smrv2.presentation.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.ui.graphics.vector.ImageVector

sealed class NavigationItem(
    val route: String,
    val title: String,
    val icon: ImageVector
) {
    data object Home : NavigationItem(
        route = "home",
        title = "Home",
        icon = Icons.Default.Home
    )
    
    data object MySchedule : NavigationItem(
        route = "my_schedule",
        title = "My Schedule",
        icon = Icons.AutoMirrored.Filled.List
    )
    
    data object Profile : NavigationItem(
        route = "profile",
        title = "Profile",
        icon = Icons.Default.Person
    )
} 