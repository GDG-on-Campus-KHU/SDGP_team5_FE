package com.example.resq.navigation.home

import androidx.compose.foundation.layout.PaddingValues
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.resq.presentaion.resq.ResQScreen
import com.example.resq.presentaion.resqbookmark.ResQBookmarkScreen
import com.example.resq.presentaion.resqdetail.ResQDetailScreen

fun NavGraphBuilder.homeNavigationGraph(navController: NavController, padding: PaddingValues) {
    composable(HomeNavigationItem.ResQ.route) {
        ResQScreen(navController, padding)
    }
    composable(HomeNavigationItem.ResQDetail.route + "/{resQ}") { backStackEntry ->
        val resQ = backStackEntry.arguments?.getString("resQ").toString()

        ResQDetailScreen(navController, padding, resQ)
    }
    composable(HomeNavigationItem.ResQBookmark.route) {
        ResQBookmarkScreen(navController, padding)
    }
}