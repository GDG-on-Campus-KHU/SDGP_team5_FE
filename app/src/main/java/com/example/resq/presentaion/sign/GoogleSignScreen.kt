@file:Suppress("DEPRECATION")

package com.example.resq.presentaion.sign

import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.resq.MainActivity.Companion.USER_DISPLAY_NAME
import com.example.resq.MainActivity.Companion.USER_TOKEN
import com.example.resq.MainActivity.Companion.googleSignInClient
import com.example.resq.R
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.common.api.ApiException

@Composable
fun GoogleSignInScreen(
    viewModel: GoogleSignViewModel = viewModel(),
    onSignIn: () -> Unit
) {
    val context = LocalContext.current
    var isLoading by remember { mutableStateOf(false) }

    val launcher =
        rememberLauncherForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            try {
                val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
                val account = task.getResult(ApiException::class.java)
                val serverAuthCode = account.serverAuthCode
                serverAuthCode?.let { viewModel.signIn(it, context) }
                USER_DISPLAY_NAME = account.displayName.toString()
                onSignIn()
            } catch (e: Exception) {
                Log.d("signInTest", e.message.toString())
            }
        }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        if (isLoading)
            CircularProgressIndicator()
        else
            Button(onClick = {
                googleSignInClient.signOut()
                launcher.launch(googleSignInClient.signInIntent)
                isLoading = true
            }) {
                Text(stringResource(R.string.google_login))
            }
    }
}