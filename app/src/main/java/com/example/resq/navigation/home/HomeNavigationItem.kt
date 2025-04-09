package com.example.resq.navigation.home

sealed class HomeNavigationItem(val route: String) {
    data object Home : HomeNavigationItem("응급상황 리스트")
    data object Share : HomeNavigationItem("")
    data object User : HomeNavigationItem("")
}