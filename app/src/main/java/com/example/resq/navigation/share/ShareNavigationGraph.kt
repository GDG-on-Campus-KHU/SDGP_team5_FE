package com.example.resq.navigation.share

import androidx.compose.foundation.layout.PaddingValues
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.resq.presentaion.rooms.RoomsScreen

fun NavGraphBuilder.shareNavigationGraph(navController: NavController, padding: PaddingValues) {
    composable(ShareNavigationItem.Room.route) {
        RoomsScreen(navController, padding)
    }
}