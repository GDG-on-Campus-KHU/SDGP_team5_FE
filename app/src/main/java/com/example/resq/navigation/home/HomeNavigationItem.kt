package com.example.resq.navigation.home

sealed class HomeNavigationItem(val route: String) {
    data object ResQ : HomeNavigationItem("rescue_list")
    data object ResQDetail : HomeNavigationItem("resque_detail")
    data object ResQBookmark : HomeNavigationItem("resque_bookmark")
    data object ResQSearch : HomeNavigationItem("resque_search")
}