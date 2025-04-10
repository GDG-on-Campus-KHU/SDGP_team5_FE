package com.example.resq.navigation.share

sealed class ShareNavigationItem(val route: String) {
    data object Room : ShareNavigationItem("share_rooms")
}