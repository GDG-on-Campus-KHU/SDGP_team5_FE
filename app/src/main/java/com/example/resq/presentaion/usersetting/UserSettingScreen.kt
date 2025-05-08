package com.example.resq.presentaion.usersetting

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.resq.R
import com.example.resq.network.model.CountryRequest
import com.example.resq.presentaion.usersetting.component.SelectCountry
import com.example.resq.presentaion.usersetting.component.SettingItem
import com.example.resq.presentaion.usersetting.component.SettingSection

@Composable
fun UserSettingScreen(
    navController: NavController,
    padding: PaddingValues,
) {
    val viewModel: UserSettingViewModel = viewModel()
    var showCountryAlertDialog by remember { mutableStateOf(false) }

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
            SettingItem(text = "meolon@gmail.com", enabled = false) // 추후 USER_EMAIL
            SettingItem(text = stringResource(R.string.sign_out), onClick = {
                // 로그아웃 fun SettingSignOut
            })
            SettingItem(text = stringResource(R.string.delete_account), onClick = {
                // 회원탈퇴 fun SettingDeleteAccount
            })
        }

        // 앱 기본 설정
        SettingSection(title = stringResource(R.string.app_settings)) {
            SettingItem(text = stringResource(R.string.language), onClick = {
                // 언어 선택 fun SettingLanguage
            })
            SettingItem(text = stringResource(R.string.country),
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
        SelectCountry(
            onDismiss = { showCountryAlertDialog = false },
            onCountrySelected = { selectedCountry ->
                viewModel.updateCountry(CountryRequest(selectedCountry))
            }
        )
    }
}