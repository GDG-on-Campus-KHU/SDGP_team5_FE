package com.example.resq.navigation.user

sealed class UserNavigationItem(val route: String) {
    data object User : UserNavigationItem("user_page")
}