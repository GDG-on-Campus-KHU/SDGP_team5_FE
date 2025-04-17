package com.example.resq

import android.content.Context
import android.content.res.Configuration
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Call
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.example.resq.navigation.home.HomeNavigationItem
import com.example.resq.navigation.home.homeNavigationGraph
import com.example.resq.navigation.share.shareNavigationGraph
import com.example.resq.navigation.user.userNavigationGraph
import com.example.resq.presentaion.component.BottomBar
import com.example.resq.presentaion.component.CenterCircularProgress
import com.example.resq.presentaion.component.TopBar
import com.example.resq.presentaion.sign.GoogleSignInScreen
import com.example.resq.presentaion.sign.GoogleSignViewModel
import java.util.Locale

@Composable
fun ResQApp() {
    val context = LocalContext.current
    val navController = rememberNavController()
    val isSignIn by remember { mutableStateOf(GoogleSignViewModel().getUserInfo(context)) }
    var isLocale by remember { mutableStateOf(true) }
    var currentLanguage by remember { mutableStateOf(Locale.getDefault().language) }

    if (isSignIn)
        if (isLocale)
            Scaffold(
                topBar = { TopBar(navController) },
                bottomBar = { BottomBar(navController) },
                floatingActionButton = {
                    FloatingActionButton(
                        onClick = {
                            // 앱 글로벌 언어 변경
//                            CoroutineScope(Dispatchers.Main).launch {
//                                isLocale = false
//                                delay(100)
//                                when (currentLanguage) {
//                                    "ko" -> {
//                                        currentLanguage = "en"
//                                        setLocale(context, "en")
//                                    }
//
//                                    "en" -> {
//                                        currentLanguage = "ko"
//                                        setLocale(context, "ko")
//                                    }
//                                }
//                                isLocale = true
//                            }

                            // 앱 로컬 데이터 베이스에 유저 정보 삭제 및 로그아웃
//                        val viewModel = GoogleSignViewModel()
//                        viewModel.removeUserInfo(context)
//                        viewModel.signOut(googleSignInClient)
//                        (context as Activity).finishAffinity()

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
            CenterCircularProgress()
    else
        GoogleSignInScreen()
}

fun setLocale(context: Context, localeCode: String) {
    val locale = Locale(localeCode)
    Locale.setDefault(locale)
    val config = Configuration(context.resources.configuration)
    config.setLocale(locale)
    context.resources.updateConfiguration(config, context.resources.displayMetrics)
}