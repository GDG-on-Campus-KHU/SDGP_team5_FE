package com.example.resq.navigation.user

import androidx.compose.foundation.layout.PaddingValues
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.resq.presentaion.user.UserScreen

fun NavGraphBuilder.userNavigationGraph(navController: NavController, padding: PaddingValues) {
    composable(UserNavigationItem.User.route) {
        UserScreen(navController, padding)
    }
}