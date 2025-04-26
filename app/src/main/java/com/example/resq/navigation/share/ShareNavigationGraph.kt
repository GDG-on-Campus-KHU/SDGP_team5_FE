package com.example.resq.navigation.share

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.MutableState
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.resq.presentaion.roomadd.RoomAddScreen
import com.example.resq.presentaion.roomdetail.RoomDetailScreen
import com.example.resq.presentaion.rooms.RoomsScreen

fun NavGraphBuilder.shareNavigationGraph(
    navController: NavController,
    padding: PaddingValues,
    isExpanded: MutableState<Boolean>,
) {
    composable(ShareNavigationItem.Rooms.route) {
        RoomsScreen(navController, padding, isExpanded)
    }
    composable(ShareNavigationItem.RoomDetail.route + "/{roomId}") { backStackEntry ->
        val roomId = backStackEntry.arguments?.getString("roomId").toString()

        RoomDetailScreen(padding, roomId, isExpanded)
    }
    composable(ShareNavigationItem.RoomAdd.route) {
        RoomAddScreen(navController, padding)
    }
}