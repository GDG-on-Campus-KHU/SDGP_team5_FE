package com.example.resq

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.LocalActivity
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.example.resq.navigation.home.HomeNavigationItem
import com.example.resq.navigation.home.homeNavigationGraph
import com.example.resq.navigation.share.shareNavigationGraph
import com.example.resq.navigation.user.userNavigationGraph
import com.example.resq.presentaion.component.BottomBar
import com.example.resq.presentaion.component.TopBar
import com.example.resq.presentaion.resq.ResQViewModel
import com.example.resq.presentaion.sign.GoogleSignInScreen
import com.example.resq.presentaion.sign.GoogleSignViewModel
import com.example.resq.service.RecordingService

@Composable
fun ResQApp() {
    val context = LocalContext.current
    val activity = LocalActivity.current
    val navController = rememberNavController()
    var isRecording by remember { mutableStateOf(false) }
    val isSignIn by remember { mutableStateOf(GoogleSignViewModel().getUserInfo(context)) }
    val isExpanded = remember { mutableStateOf(false) }
    val emerNumber = ResQViewModel().getEmerNumber()

    if (isSignIn)
        Scaffold(
            topBar = { TopBar(navController) { isExpanded.value = true } },
            bottomBar = { BottomBar(navController) },
            floatingActionButton = {
                FloatingActionButton(
                    onClick = {
                        if (isRecording)
                            stopRecording(context).apply {
                                isRecording = !this
                                Toast.makeText(context, "음성 녹음이 저장되었습니다.", Toast.LENGTH_SHORT)
                                    .show()
                            }
                        else {
                            showOnClickCheckDialog(activity) {
                                isRecording = startRecording(context)
                            }
//                                    "ko" -> {
                        }

                        // 앱 로컬 데이터 베이스에 유저 정보 삭제 및 로그아웃
//                        val viewModel = GoogleSignViewModel()
//                        viewModel.removeUserInfo(context)
//                        viewModel.signOut(googleSignInClient)
//                        (context as Activity).finishAffinity()
                    }
                ) {
                    Icon(
                        painter = painterResource(
                            if (isRecording) R.drawable.baseline_stop_24
                            else R.drawable.baseline_call_24
                        ),
                        tint = if (isRecording) Color.Red else Color.Black,
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
                shareNavigationGraph(navController, paddingValues, isExpanded)
                userNavigationGraph(navController, paddingValues)
            }
        }
    else
        GoogleSignInScreen()
}

private fun startRecording(context: Context): Boolean {
    val serviceIntent = Intent(context, RecordingService::class.java)
    return context.startService(serviceIntent) != null
}

private fun stopRecording(context: Context): Boolean {
    val serviceIntent = Intent(context, RecordingService::class.java)
    return context.stopService(serviceIntent)
}

private fun showOnClickCheckDialog(activity: Activity?, onPositive: () -> Unit) {
    AlertDialog.Builder(activity)
        .setTitle("긴급 전화 및 녹음")
        .setMessage("긴급 전화를 걸고 녹음을 시작 하시겠습니까?")
        .setPositiveButton("확인") { _, _ -> onPositive() }
        .setNegativeButton("취소") { dialog, _ -> dialog.dismiss() }
        .setOnDismissListener { it.dismiss() }
        .show()
}