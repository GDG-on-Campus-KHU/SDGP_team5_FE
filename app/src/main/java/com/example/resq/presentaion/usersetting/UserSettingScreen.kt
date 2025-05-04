package com.example.resq.presentaion.usersetting

import androidx.annotation.StringRes
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.resq.MainActivity.Companion.USER_EMAIL
import com.example.resq.R

@Composable
fun UserSettingScreen(
    navController: NavController,
    padding: PaddingValues,
) {
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
            SettingItem(text = stringResource(R.string.sign_out), onClick = {
                // 로그아웃 SignOut
            })
            SettingItem(text = stringResource(R.string.delete_account), onClick = {
                // 회원탈퇴 Delete Account
            })
        }

        // 앱 기본 설정
        SettingSection(title = stringResource(R.string.app_settings)) {
            SettingItem(text = stringResource(R.string.language), onClick = {
                // 언어 선택 Language
            })
            SettingItem(text = stringResource(R.string.country), onClick = {
                // 국가 선택 Country
            })
        }

        // 접근 권한
        SettingSection(title = stringResource(R.string.permissions)) {
            SettingItem(text = stringResource(R.string.permissions), onClick = {
                // 접근 권한 Permissions
            })
        }

        // 약관 및 정책
        SettingSection(title = stringResource(R.string.terms_and_policies)) {
            SettingItem(text = stringResource(R.string.terms_of_service), onClick = {
                // 이용약관 Terms of Service
            })
            SettingItem(text = stringResource(R.string.privacy_policy), onClick = {
                // 개인정보 처리방침 Privacy Policy
            })
        }
    }
}

@Composable
fun SettingSection(title: String, content: @Composable ColumnScope.() -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    ) {
        Text(
            text = title,
            fontWeight = FontWeight.Bold,
            fontSize = 24.sp,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        Surface(
            shape = RoundedCornerShape(12.dp),
            tonalElevation = 1.dp,
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.fillMaxWidth()) {
                content()
            }
        }
    }
}

@Composable
fun SettingItem(
    text: String,
    onClick: (() -> Unit)? = null,
    enabled: Boolean = true
) {
    val modifier = Modifier
        .fillMaxWidth()
        .then(
            if (onClick != null && enabled) Modifier.clickable { onClick() } else Modifier
        )
        .padding(horizontal = 16.dp, vertical = 12.dp)

    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = text)
        if (onClick != null && enabled) {
            Icon(
                painter = painterResource(R.drawable.baseline_keyboard_arrow_right_24),
                contentDescription = null
            )
        }
    }
}
