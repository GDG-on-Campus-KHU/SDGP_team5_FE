package com.example.resq.presentaion.roomadd

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
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
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.resq.R
import com.example.resq.presentaion.component.CenterCircularProgress
import com.example.resq.presentaion.roomadd.component.AddMemberDialog
import com.example.resq.ui.theme.InnerPadding

@Composable
fun RoomAddScreen(
    navController: NavController,
    padding: PaddingValues,
    viewModel: RoomAddViewModel = viewModel()
) {
    val isLoading by viewModel.isLoading.collectAsState()
    var roomTitle by remember { mutableStateOf("") }
    var members by remember { mutableStateOf(emptyList<String>()) }
    var isExpended by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(padding)
            .padding(InnerPadding),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (isLoading)
            CenterCircularProgress()
        else {
            OutlinedTextField(
                value = roomTitle,
                onValueChange = { roomTitle = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = InnerPadding),
                placeholder = {
                    Text(
                        text = stringResource(R.string.room_title),
                        color = Color.LightGray
                    )
                }
            )

            Spacer(Modifier.height(16.dp))
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .padding(horizontal = InnerPadding)
                    .border(0.dp, Color.Black, RoundedCornerShape(8.dp))
                    .clickable(
                        onClick = { isExpended = !isExpended },
                        interactionSource = null,
                        indication = null
                    ),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                if (members.isEmpty())
                    item {
                        Text(
                            text = stringResource(R.string.member_add_placeholder),
                            modifier = Modifier.padding(16.dp),
                            color = Color.LightGray
                        )
                    }

                item { Spacer(Modifier.height(8.dp)) }
                items(members) { member ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 8.dp)
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(text = member)
                            IconButton(onClick = { members -= member }) {
                                Icon(
                                    imageVector = Icons.Default.Close,
                                    contentDescription = "Delete"
                                )
                            }
                        }
                    }
                }
            }

            Spacer(Modifier.height(16.dp))
            Button(
                onClick = {
                    viewModel.addMembers(roomTitle, members)
                    navController.popBackStack()
                }
            ) { Text(stringResource(R.string.add_button)) }
        }
    }

    if (isExpended)
        AddMemberDialog(
            viewModel = viewModel,
            onDismissRequest = { isExpended = !isExpended },
            onClickEmail = {
                if (!members.contains(it))
                    members += it
                isExpended = !isExpended
            }
        )
}

@Preview(showBackground = true)
@Composable
fun RoomAddPreview() {
    RoomAddScreen(rememberNavController(), PaddingValues(0.dp), viewModel())
}