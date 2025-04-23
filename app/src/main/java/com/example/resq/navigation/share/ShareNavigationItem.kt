package com.example.resq.navigation.share

sealed class ShareNavigationItem(val route: String) {
    data object Rooms : ShareNavigationItem("share_rooms")
    data object RoomDetail : ShareNavigationItem("room_detail")
    data object RoomAdd : ShareNavigationItem("room_add")
}