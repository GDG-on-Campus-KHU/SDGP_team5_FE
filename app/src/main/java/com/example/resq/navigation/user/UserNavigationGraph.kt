package com.example.resq.navigation.user

import androidx.compose.foundation.layout.PaddingValues
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.resq.presentaion.user.UserScreen
import com.example.resq.presentaion.usermedicalinfo.UserMedicalInfoViewModel
import com.example.resq.presentaion.usermedicalinfoedit.UserMedicalInfoEditScreen

fun NavGraphBuilder.userNavigationGraph(navController: NavController, padding: PaddingValues) {
    composable(UserNavigationItem.User.route) {
        UserScreen(navController, padding)
    }
    composable(UserNavigationItem.UserMedicalInfoEdit.route) {
        val viewModel: UserMedicalInfoViewModel = viewModel()
        UserMedicalInfoEditScreen(navController, padding, viewModel)
    }
}