@file:Suppress("DEPRECATION")

package com.example.resq

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.resq.ui.theme.ResQTheme
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions

class MainActivity : ComponentActivity() {

    companion object {
        @SuppressLint("StaticFieldLeak")
        lateinit var googleSignInClient: GoogleSignInClient
        lateinit var USER_EMAIL: String
        lateinit var USER_ID: String
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .requestId()
            .build()

        googleSignInClient = GoogleSignIn.getClient(this, gso)

        enableEdgeToEdge()
        setContent {
            ResQTheme {
                ResQApp()
            }
        }
    }
}