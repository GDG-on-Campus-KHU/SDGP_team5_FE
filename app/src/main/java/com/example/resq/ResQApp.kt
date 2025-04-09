package com.example.resq

import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.resq.navigation.BottomNavigationItem
import com.example.resq.navigation.home.HomeNavigationGraph
import com.example.resq.navigation.share.ShareNavigationGraph
import com.example.resq.navigation.user.UserNavigationGraph
import com.example.resq.presentaion.component.BottomBar
import com.example.resq.presentaion.component.TopBar

@Composable
fun ResQApp() {
    val navController = rememberNavController()
    val isButton = true //by viewModel.isButton.collectAsState()

    Scaffold(
        topBar = { TopBar() },
        bottomBar = { if (isButton) BottomBar(navController) }
    ) { paddingValues ->
        NavHost(
            navController = navController,
            startDestination = BottomNavigationItem.Home.route
        ) {
            composable(BottomNavigationItem.Home.route) {
                HomeNavigationGraph(paddingValues)
            }
            composable(BottomNavigationItem.Share.route) {
                ShareNavigationGraph(paddingValues)
            }
            composable(BottomNavigationItem.User.route) {
                UserNavigationGraph(paddingValues)
            }
        }
    }
}

@Preview
@Composable
fun ResQPreview() {
    ResQApp()
}