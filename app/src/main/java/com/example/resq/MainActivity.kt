@file:Suppress("DEPRECATION")

package com.example.resq

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.core.app.ActivityCompat.shouldShowRequestPermissionRationale
import androidx.core.content.ContextCompat
import com.example.resq.presentaion.component.CenterCircularProgress
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
        private var isLoading by mutableStateOf(true)
    }

    private val requestPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted: Boolean ->
            if (isGranted) {
                isLoading = false
            } else {
                showPermissionSettingsDialog(this)
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        checkPermissions(
            activity = this,
            onPermission = { isLoading = false },
            onPermissionLauncher = {
                requestPermissionLauncher.launch(
                    Manifest.permission.RECORD_AUDIO
                )
            })

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .requestId()
            .build()

        googleSignInClient = GoogleSignIn.getClient(this, gso)

        enableEdgeToEdge()
        setContent {
            ResQTheme {
                if (isLoading)
                    CenterCircularProgress()
                else
                    ResQApp()
            }
        }
    }
}

private fun checkPermissions(
    activity: Activity,
    onPermission: () -> Unit,
    onPermissionLauncher: () -> Unit
) {
    if (ContextCompat.checkSelfPermission(
            activity,
            Manifest.permission.RECORD_AUDIO
        ) == PackageManager.PERMISSION_GRANTED
    ) {
        onPermission()
    } else if (shouldShowRequestPermissionRationale(activity, Manifest.permission.RECORD_AUDIO)) {
        showPermissionSettingsDialog(activity)
    } else {
        onPermissionLauncher()
    }
}

private fun showPermissionSettingsDialog(activity: Activity) {
    AlertDialog.Builder(activity)
        .setTitle("권한이 필요합니다")
        .setMessage("마이크 권한을 거부하셨습니다. 설정에서 권한을 허용해주세요.")
        .setPositiveButton("설정으로 가기") { _, _ ->
            val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
            val uri = Uri.fromParts("package", activity.packageName, null)
            intent.data = uri
            activity.startActivity(intent)
        }
        .setNegativeButton("취소") { _, _ ->
            activity.finish()
        }
        .setOnDismissListener { activity.finish() }
        .show()
}