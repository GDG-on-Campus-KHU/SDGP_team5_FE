package com.example.resq.presentaion.roomadd.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.resq.presentaion.component.CenterCircularProgress
import com.example.resq.presentaion.roomadd.RoomAddViewModel
import com.example.resq.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddMemberDialog(
    viewModel: RoomAddViewModel,
    onDismissRequest: () -> Unit,
    onClickEmail: (String) -> Unit
) {
    val isLoading by viewModel.isLoading.collectAsState()
    val members by viewModel.members.collectAsState()
    var email by remember { mutableStateOf("") }

    BasicAlertDialog(onDismissRequest = { onDismissRequest() }) {
        Surface(
            modifier = Modifier
                .fillMaxHeight(0.5f)
                .fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            shadowElevation = 8.dp
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                OutlinedTextField(
                    value = email,
                    onValueChange = {
                        if (it.length < 40) {
                            email = it
                            viewModel.getMembers(it)
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

                if (isLoading)
                    CenterCircularProgress()
                else
                    LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        item { Spacer(Modifier.height(8.dp)) }
                        items(members) { member ->
                            Card(
                                modifier = Modifier.fillMaxWidth(),
                                onClick = { onClickEmail(member) }
                            ) {
                                Text(
                                    text = member,
                                    modifier = Modifier.padding(16.dp)
                                )
                            }
                        }
                    }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AddMemberDialogPreview() {
    AddMemberDialog(viewModel(), {}, {})
}