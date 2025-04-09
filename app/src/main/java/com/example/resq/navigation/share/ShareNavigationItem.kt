package com.example.resq.navigation.share

sealed class ShareNavigationItem(val route: String) {
    data object Home : ShareNavigationItem("")
    data object Share : ShareNavigationItem("")
    data object User : ShareNavigationItem("")
}