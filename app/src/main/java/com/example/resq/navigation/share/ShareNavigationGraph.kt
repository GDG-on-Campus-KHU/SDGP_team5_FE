package com.example.resq.navigation.share

import androidx.compose.foundation.layout.PaddingValues
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.resq.presentaion.roomadd.RoomAddScreen
import com.example.resq.presentaion.rooms.RoomsScreen
import com.example.resq.presentaion.roomdetail.RoomDetailScreen
import com.example.resq.presentaion.roomnotify.RoomNotifyScreen

fun NavGraphBuilder.shareNavigationGraph(navController: NavController, padding: PaddingValues) {
    composable(ShareNavigationItem.Rooms.route) {
        RoomsScreen(navController, padding)
    }
    composable(ShareNavigationItem.RoomDetail.route) {
        RoomDetailScreen(navController, padding)
    }
    composable(ShareNavigationItem.RoomAdd.route) {
        RoomAddScreen(navController, padding)
    }
    composable(ShareNavigationItem.RoomNotify.route) {
        RoomNotifyScreen(navController)
    }
}