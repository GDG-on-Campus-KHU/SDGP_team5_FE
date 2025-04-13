package com.example.resq.navigation.home

import androidx.compose.foundation.layout.PaddingValues
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.example.resq.presentaion.resq.ResQScreen
import com.example.resq.presentaion.resq.model.ResQ
import com.example.resq.presentaion.resqdetail.ResQDetailScreen

fun NavGraphBuilder.homeNavigationGraph(navController: NavHostController, padding: PaddingValues) {
    composable(HomeNavigationItem.ResQ.route) {
        ResQScreen(navController, padding)
    }
    composable(HomeNavigationItem.ResQDetail.route + "/{resQ}/{resQImage}") { backStackEntry ->
        val resQ = backStackEntry.arguments?.getString("resQ").toString()
        val resQImage = backStackEntry.arguments?.getString("resQImage").toString()
        val resQInfo = ResQ(resQ, resQImage.toInt())

        ResQDetailScreen(navController, padding, resQInfo)
    }
}