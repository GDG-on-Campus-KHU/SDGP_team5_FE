package com.example.resq

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Call
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.example.resq.navigation.home.HomeNavigationItem
import com.example.resq.navigation.home.homeNavigationGraph
import com.example.resq.navigation.share.shareNavigationGraph
import com.example.resq.navigation.user.userNavigationGraph
import com.example.resq.presentaion.component.BottomBar
import com.example.resq.presentaion.component.TopBar

@Composable
fun ResQApp() {
    val navController = rememberNavController()

    Scaffold(
        topBar = { TopBar(navController) },
        bottomBar = { BottomBar(navController) },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    // 녹음 + 신고
//                    handleButtonClick()
                }
            ) {
                Icon(
                    imageVector = Icons.Default.Call,
                    contentDescription = "OneClick"
                )
            }
        }
    ) { paddingValues ->
        NavHost(
            navController = navController,
            startDestination = HomeNavigationItem.ResQ.route
        ) {
            homeNavigationGraph(navController, paddingValues)
            shareNavigationGraph(navController, paddingValues)
            userNavigationGraph(navController, paddingValues)
        }
    }
}

@Preview
@Composable
fun ResQPreview() {
    ResQApp()
}