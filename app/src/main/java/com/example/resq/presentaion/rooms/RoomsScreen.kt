package com.example.resq.presentaion.rooms

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateMapOf
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
import com.example.resq.navigation.share.ShareNavigationItem
import com.example.resq.presentaion.component.CenterCircularProgress
import com.example.resq.presentaion.component.ConfirmDialog
import com.example.resq.presentaion.roomnotify.RoomNotifyScreen
import com.example.resq.presentaion.rooms.component.RoomsOptions
import com.example.resq.ui.theme.InnerPadding
import com.example.resq.R

@Composable
fun RoomsScreen(
    navController: NavController,
    padding: PaddingValues,
    isExpanded: MutableState<Boolean>,
    viewModel: RoomsViewModel = viewModel()
) {
    val isLoading by viewModel.isLoading.collectAsState()
    val rooms by viewModel.rooms.collectAsState()
    val isRoomState = remember { mutableStateMapOf<String, Boolean>() }
    var isDialogExpended by remember { mutableStateOf(false) }
    val roomOption = remember { mutableStateOf("") }
    val roomId = remember { mutableStateOf("") }

    LaunchedEffect(isDialogExpended) {
        if (!isDialogExpended)
            viewModel.getRooms()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(padding)
            .padding(horizontal = InnerPadding)
    ) {
        if (isLoading) {
            CenterCircularProgress()
        } else {
            if (rooms.isEmpty())
                CenterBlankText("공유 방이 없습니다")
            else
                LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    item { Spacer(Modifier.height(4.dp)) }
                    items(rooms) { room ->
                        val isRoomOptions = isRoomState[room.roomId] ?: false
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .heightIn(min = 80.dp)
                                .background(Color.LightGray, RoundedCornerShape(16.dp))
                                .padding(8.dp)
                                .clickable(
                                    onClick = { navController.navigate(ShareNavigationItem.RoomDetail.route + "/${room.roomId}") },
                                    interactionSource = null,
                                    indication = null
                                )
                        ) {
                            Text(
                                text = room.roomTitle,
                                modifier = Modifier.align(Alignment.CenterStart)
                            )
                            Row(
                                modifier = Modifier.align(Alignment.CenterEnd),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.Center
                            ) {
                                if (isRoomOptions)
                                    RoomsOptions {
                                        roomOption.value = it
                                        roomId.value = room.roomId
                                        isDialogExpended = !isDialogExpended
                                    }
                                IconButton(onClick = {
                                    isRoomState.keys.forEach { isRoomState[it] = false }
                                    isRoomState[room.roomId] = !isRoomOptions
                                }) {
                                    Icon(
                                        imageVector = Icons.Default.MoreVert,
                                        contentDescription = "MoreVert"
                                    )
                                }
                            }
                        }
                    }
                    item { Spacer(Modifier.height(4.dp)) }
                }
        }
    }

    if (isDialogExpended) {
        val deleteCheck = stringResource(R.string.delete_check)
        val outCheck = stringResource(R.string.out_check)

        ConfirmDialog(
            title = when (roomOption.value) {
                stringResource(R.string.room_delete) -> deleteCheck
                stringResource(R.string.room_out) -> outCheck
                else -> ""
            },
            onDismissRequest = { isDialogExpended = !isDialogExpended },
            onClick = {
                when (it) {
                    deleteCheck -> viewModel.deleteRoom(roomId.value)
                    outCheck -> viewModel.outRoom(roomId.value)
                }
            }
        )
    }

    if (isExpanded.value)
        RoomNotifyScreen(onDismissRequest = { isExpanded.value = false })
}

@SuppressLint("UnrememberedMutableState")
@Preview(showBackground = true)
@Composable
fun RoomsPreview() {
    RoomsScreen(rememberNavController(), PaddingValues(0.dp), mutableStateOf(false))
}