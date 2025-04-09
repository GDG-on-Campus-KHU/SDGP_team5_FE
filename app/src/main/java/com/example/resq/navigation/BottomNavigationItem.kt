package com.example.resq.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.ui.graphics.vector.ImageVector

sealed class BottomNavigationItem(
    val route: String, val icon: ImageVector
) {
    data object Home : BottomNavigationItem("홈", Icons.Default.Home)
    data object Share : BottomNavigationItem("공유", Icons.AutoMirrored.Filled.List)
    data object User : BottomNavigationItem("마이페이지", Icons.Default.AccountCircle)
}