package com.example.resq.presentaion.usersetting

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.res.Configuration
import androidx.activity.compose.LocalActivity
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.resq.MainActivity.Companion.USER_COUNTRY_CODE
import com.example.resq.MainActivity.Companion.USER_EMAIL
import com.example.resq.MainActivity.Companion.googleSignInClient
import com.example.resq.R
import com.example.resq.presentaion.sign.GoogleSignViewModel
import com.example.resq.presentaion.usersetting.component.SelectDialog
import com.example.resq.presentaion.usersetting.component.SettingItem
import com.example.resq.presentaion.usersetting.component.SettingSection
import com.example.resq.presentaion.usersetting.model.Country
import java.util.Locale

@Composable
fun UserSettingScreen(
    padding: PaddingValues,
    viewModel: UserSettingViewModel = viewModel()
) {
    val activity = LocalActivity.current
    val context = LocalContext.current
    val countries = viewModel.countries.collectAsState()
    val languages = viewModel.languages.collectAsState()
    var showLanguageAlertDialog by remember { mutableStateOf(false) }
    var showCountryAlertDialog by remember { mutableStateOf(false) }
    var appLanguage by remember { mutableStateOf(Country("", "")) }
    var userCountry by remember { mutableStateOf("") }

    LaunchedEffect(appLanguage, USER_COUNTRY_CODE) {
        viewModel.updateTranslation(appLanguage.code)
        appLanguage = viewModel.getCountryLanguage(Locale.getDefault().language)
        userCountry = viewModel.getCountryName(USER_COUNTRY_CODE)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(padding)
            .padding(horizontal = 16.dp, vertical = 12.dp)
            .verticalScroll(rememberScrollState())
    ) {
        Text(
            text = stringResource(R.string.resq_setting),
            fontSize = 40.sp,
            fontWeight = FontWeight.Black,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 12.dp)
                .wrapContentWidth(Alignment.CenterHorizontally)
        )

        // 계정
        SettingSection(title = stringResource(R.string.account)) {
            SettingItem(text = USER_EMAIL, enabled = false)
            SettingItem(
                text = stringResource(R.string.sign_out),
                onClick = {
                    activity?.let {
                        signOut(it) {
                            GoogleSignViewModel().signOut(context, googleSignInClient)
                            it.finish()
                        }
                    }
                })
            SettingItem(text = stringResource(R.string.delete_account), onClick = {
                // 회원탈퇴 fun SettingDeleteAccount
            })
        }

        // 앱 기본 설정
        SettingSection(title = stringResource(R.string.app_settings)) {
            SettingItem(text = "${stringResource(R.string.language)}: ${appLanguage.name}",
                onClick = { showLanguageAlertDialog = true }
            )
            SettingItem(text = "${stringResource(R.string.country)}: $userCountry",
                onClick = { showCountryAlertDialog = true }
            )
        }

        // 접근 권한
        SettingSection(title = stringResource(R.string.permissions)) {
            SettingItem(text = stringResource(R.string.permissions), onClick = {
                // 접근 권한 fun SettingPermissions
            })
        }

        // 약관 및 정책
        SettingSection(title = stringResource(R.string.terms_and_policies)) {
            SettingItem(text = stringResource(R.string.terms_of_service), onClick = {
                // 이용약관 fun SettingTermsOfService
            })
            SettingItem(text = stringResource(R.string.privacy_policy), onClick = {
                // 개인정보 처리방침 fun SettingPrivacyPolicy
            })
        }
    }
    if (showCountryAlertDialog) {
        SelectDialog(
            text = stringResource(R.string.select_country),
            onDismiss = { showCountryAlertDialog = false },
            selectOptions = countries.value,
            onSelectedOptions = { selectedCountry ->
                USER_COUNTRY_CODE = selectedCountry.code
                viewModel.updateCountry(selectedCountry.code)
                GoogleSignViewModel().getEmerNumber(selectedCountry.code)
            }
        )
    }
    if (showLanguageAlertDialog) {
        SelectDialog(
            text = stringResource(R.string.select_language),
            onDismiss = { showLanguageAlertDialog = false },
            selectOptions = languages.value,
            onSelectedOptions = { selectedLanguage ->
                appLanguage.name = selectedLanguage.name
                setLocale(context, selectedLanguage.code)
            }
        )
    }
}

fun setLocale(context: Context, localeCode: String) {
    val locale = Locale(localeCode)
    Locale.setDefault(locale)
    val config = Configuration(context.resources.configuration)
    config.setLocale(locale)
    @Suppress("DEPRECATION")
    context.resources.updateConfiguration(config, context.resources.displayMetrics)
    (context as Activity).recreate()
}

private fun signOut(activity: Activity, onPositiveButton: () -> Unit) {
    AlertDialog.Builder(activity)
        .setTitle(activity.getString(R.string.sign_out))
        .setMessage(activity.getString(R.string.sign_out_confirmation))
        .setPositiveButton(activity.getString(R.string.sign_out)) { _, _ -> onPositiveButton() }
        .setNegativeButton(activity.getString(R.string.cancel)) { dialog, _ -> dialog.dismiss() }
        .setOnDismissListener { it.dismiss() }
        .show()
}