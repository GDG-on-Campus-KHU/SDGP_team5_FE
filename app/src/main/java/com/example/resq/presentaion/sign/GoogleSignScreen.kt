@file:Suppress("DEPRECATION")

package com.example.resq.presentaion.sign

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.resq.MainActivity.Companion.googleSignInClient
import com.example.resq.ResQApp
import com.example.resq.ui.theme.ResQTheme
import com.google.android.gms.auth.api.signin.GoogleSignIn

@Composable
fun GoogleSignInScreen(viewModel: GoogleSignViewModel = viewModel()) {
    val context = LocalContext.current
    val isAccount by viewModel.isAccount.collectAsState()
    var userEmail by remember { mutableStateOf<String?>(null) }
    var userId by remember { mutableStateOf<String?>(null) }

    val launcher =
        rememberLauncherForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            val account = GoogleSignIn.getSignedInAccountFromIntent(result.data).result
            userEmail = account?.email
            userId = account?.id
            viewModel.saveUserInfo(context, account)
        }

    if (isAccount)
        ResQTheme {
            ResQApp()
        }
    else
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Button(onClick = {
                val signInIntent = googleSignInClient.signInIntent
                launcher.launch(signInIntent)
            }) {
                Text(text = "Google 로그인")
            }
        }
}