package com.example.resq.presentaion.user


import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.resq.presentaion.sign.GoogleSignViewModel

@Composable
fun UserScreen(
    navController: NavController,
    padding: PaddingValues,
) {
    val googleSignViewModel: GoogleSignViewModel = viewModel()
    val context = LocalContext.current
    val googleName = googleSignViewModel.getUserName(context)
    val googleEmail = googleSignViewModel.getUserEmail(context)
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(padding)
    ) {
        Row(
            modifier = Modifier
                .padding(top = 26.dp, start = 14.dp)
        ) {
            Icon(
                imageVector = Icons.Default.AccountCircle,
                contentDescription = "Account",
                modifier = Modifier.size(70.dp)
            )
            Column(
                modifier = Modifier
                    .padding(start = 15.dp)
            ) {
                Row(
                    modifier = Modifier
                        .padding(top = 4.dp)
                ) {
                    Text(
                        text = googleName ?: "User",
                        fontSize = 24.sp,
                        modifier = Modifier
                            .wrapContentSize()
                    )
                    Icon(
                        imageVector = Icons.Outlined.Edit,
                        contentDescription = "Edit",
                        modifier = Modifier
                            .padding(top = 5.dp, start = 12.dp)
                            .size(28.dp)
                            .clickable(
                                onClick = {
                                    //이름 고치기 기능 구현 예정
                                }

                            )
                    )
                }
                Text(
                    text = googleEmail ?: "email",
                    fontSize = 14.sp,
                    color = Color.Gray,
                    modifier = Modifier.padding(start = 3.dp)
                )
            }

        }

        //로그아웃 임시 버튼
        /*Button(onClick = {
            viewModel.signOut(googleSignInClient)
            viewModel.removeUserInfo(context)
            Text("로그아웃")
        }*/
    }
}

@Preview(showBackground = true)
@Composable
fun UserScreenPreview() {
    UserScreen(rememberNavController(), PaddingValues(0.dp))
}