package com.example.resq.navigation.user

sealed class UserNavigationItem(val route: String) {
    data object Home : UserNavigationItem("")
    data object Share : UserNavigationItem("")
    data object User : UserNavigationItem("")
}