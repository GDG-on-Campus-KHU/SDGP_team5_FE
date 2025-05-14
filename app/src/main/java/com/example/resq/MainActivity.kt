@file:Suppress("DEPRECATION")

package com.example.resq

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
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
import com.example.resq.presentaion.resqbookmark.model.ResQBookmark
import com.example.resq.ui.theme.ResQTheme
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions

class MainActivity : ComponentActivity() {

    companion object {
        lateinit var appContext: Context

        @SuppressLint("StaticFieldLeak")
        lateinit var googleSignInClient: GoogleSignInClient
        lateinit var USER_TOKEN: String
        lateinit var USER_DISPLAY_NAME: String
        lateinit var USER_EMAIL: String
        lateinit var USER_COUNTRY_CODE: String
        lateinit var EMERGENCY_CALL_NUMBER: String
        lateinit var FAVORITE_RESQ_LIST: List<ResQBookmark>
    }

    private var isLoading by mutableStateOf(true)
    private val permissions =
        arrayOf(Manifest.permission.CALL_PHONE, Manifest.permission.RECORD_AUDIO)

    private val requestPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { permission ->
            if (permission[permissions.first()] == true &&
                permission[permissions.last()] == true
            ) {
                isLoading = false
            } else {
                showPermissionSettingsDialog(this)
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        appContext = applicationContext

        checkPermissions(
            activity = this,
            permissions = permissions,
            onPermission = { isLoading = false },
            onPermissionLauncher = { requestPermissionLauncher.launch(permissions) }
        )

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestServerAuthCode(BuildConfig.GOOGLE_AUTH_CLIENT_ID)
            .requestProfile()
            .requestEmail()
            .requestId()
            .build()
        googleSignInClient = GoogleSignIn.getClient(this, gso)

        enableEdgeToEdge()
        setContent {
            ResQTheme {
                CenterCircularProgress()
                if (!isLoading)
                    ResQApp()
            }
        }
    }
}

private fun checkPermissions(
    activity: Activity,
    permissions: Array<String>,
    onPermission: () -> Unit,
    onPermissionLauncher: () -> Unit,
) {
    if (ContextCompat.checkSelfPermission(
            activity,
            permissions.first()
        ) == PackageManager.PERMISSION_GRANTED &&
        ContextCompat.checkSelfPermission(
            activity,
            permissions.last()
        ) == PackageManager.PERMISSION_GRANTED
    ) {
        onPermission()
    } else if (shouldShowRequestPermissionRationale(
            activity, permissions.first()
        ) || shouldShowRequestPermissionRationale(
            activity, permissions.last()
        )
    ) {
        showPermissionSettingsDialog(activity)
    } else {
        onPermissionLauncher()
    }
}

private fun showPermissionSettingsDialog(activity: Activity) {
    AlertDialog.Builder(activity)
        .setTitle(activity.getString(R.string.permission_required))
        .setMessage(activity.getString(R.string.permission_denied))
        .setPositiveButton(activity.getString(R.string.go_to_settings)) { _, _ ->
            val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
            val uri = Uri.fromParts("package", activity.packageName, null)
            intent.data = uri
            activity.startActivity(intent)
        }
        .setNegativeButton(activity.getString(R.string.cancel)) { _, _ ->
            activity.finish()
        }
        .setOnDismissListener { activity.finish() }
        .show()
}