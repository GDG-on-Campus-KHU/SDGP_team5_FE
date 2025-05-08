package com.example.resq.presentaion.roomadd.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.resq.R
import com.example.resq.presentaion.roomadd.RoomAddViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddMemberDialog(
    viewModel: RoomAddViewModel,
    onDismissRequest: () -> Unit,
    onClickEmail: (String) -> Unit
) {
//    val isLoading by viewModel.isLoading.collectAsState()
//    val members by viewModel.members.collectAsState()
    var email by remember { mutableStateOf("") }

    BasicAlertDialog(onDismissRequest = { onDismissRequest() }) {
        Surface(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            shadowElevation = 8.dp
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                OutlinedTextField(
                    value = email,
                    onValueChange = {
                        if (it.length < 40) {
                            email = it
//                            viewModel.getMembers(it)
                        }
                    },
                    modifier = Modifier.fillMaxWidth(),
                    placeholder = {
                        Text(
                            text = stringResource(R.string.email_placeholder),
                            color = Color.LightGray
                        )
                    },
                    maxLines = 1
                )
                Spacer(Modifier.height(16.dp))
                Button(onClick = { onClickEmail(email) }) {
                    Text(
                        text = "추가하기",
                        color = Color.White
                    )
                }

                // 차후에 사용자를 검색을 통해 찾아서 추가할 수 있게 하기
//                if (isLoading)
//                    CenterCircularProgress()
//                else
//                    LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
//                        item { Spacer(Modifier.height(8.dp)) }
//                        items(members) { member ->
//                            Card(
//                                modifier = Modifier.fillMaxWidth(),
//                                onClick = { onClickEmail(member) }
//                            ) {
//                                Text(
//                                    text = member,
//                                    modifier = Modifier.padding(16.dp)
//                                )
//                            }
//                        }
//                    }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AddMemberDialogPreview() {
    AddMemberDialog(viewModel(), {}, {})
}