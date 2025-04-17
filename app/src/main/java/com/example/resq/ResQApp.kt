package com.example.resq

import android.app.Activity
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Call
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.example.resq.MainActivity.Companion.googleSignInClient
import com.example.resq.navigation.home.HomeNavigationItem
import com.example.resq.navigation.home.homeNavigationGraph
import com.example.resq.navigation.share.shareNavigationGraph
import com.example.resq.navigation.user.userNavigationGraph
import com.example.resq.presentaion.component.BottomBar
import com.example.resq.presentaion.component.TopBar
import com.example.resq.presentaion.sign.GoogleSignInScreen
import com.example.resq.presentaion.sign.GoogleSignViewModel

@Composable
fun ResQApp() {
    val context = LocalContext.current
    val navController = rememberNavController()
    val isSignIn by remember { mutableStateOf(GoogleSignViewModel().getUserInfo(context)) }

    if (isSignIn)
        Scaffold(
            topBar = { TopBar(navController) },
            bottomBar = { BottomBar(navController) },
            floatingActionButton = {
                FloatingActionButton(
                    onClick = {
                        // Remove UserInfo & SignOut test
                        val viewModel = GoogleSignViewModel()
                        viewModel.removeUserInfo(context)
                        viewModel.signOut(googleSignInClient)
                        (context as Activity).finishAffinity()
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
    else
        GoogleSignInScreen()
}