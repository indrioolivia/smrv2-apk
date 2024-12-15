package com.id.smrv2.presentation.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Person
import androidx.compose.ui.graphics.vector.ImageVector

sealed class NavigationItem(
    val route: String,
    val title: String,
    val icon: ImageVector
) {
    object Home : NavigationItem(
        route = "home",
        title = "Home",
        icon = Icons.Default.Home
    )
    
    object MySchedule : NavigationItem(
        route = "my_schedule",
        title = "My Schedule",
        icon = Icons.Default.List
    )
    
    object Profile : NavigationItem(
        route = "profile",
        title = "Profile",
        icon = Icons.Default.Person
    )
} 