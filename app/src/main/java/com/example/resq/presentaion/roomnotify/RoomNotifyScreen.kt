package com.example.resq.presentaion.roomnotify

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.resq.presentaion.component.CenterBlankText
import com.example.resq.presentaion.component.CenterCircularProgress

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RoomNotifyScreen(
    onDismissRequest: () -> Unit,
    viewModel: RoomNotifyViewModel = viewModel()
) {
    val isLoading by viewModel.isLoading.collectAsState()
    val notifications by viewModel.notifications.collectAsState()

    BasicAlertDialog(onDismissRequest = { onDismissRequest() }) {
        Surface(
            modifier = Modifier
                .fillMaxHeight(0.5f)
                .fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            shadowElevation = 8.dp
        ) {
            if (isLoading)
                CenterCircularProgress()
            else
                if (notifications.isEmpty())
                    CenterBlankText("초대받은 공유 방이 없습니다.")
                else
                    LazyColumn(
                        modifier = Modifier.padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        item {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.End
                            ) {
                                IconButton(onClick = { onDismissRequest() }) {
                                    Icon(
                                        imageVector = Icons.Default.Close,
                                        contentDescription = "Close"
                                    )
                                }
                            }
                        }
                        items(notifications) { notify ->
                            Card(modifier = Modifier.fillMaxWidth()) {
                                Row(
                                    modifier = Modifier.padding(8.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text(text = notify.roomTitle)
                                    Spacer(Modifier.weight(1f))
                                    IconButton(onClick = { viewModel.rejectNotify(notify.roomId) }) {
                                        Icon(
                                            imageVector = Icons.Default.Close,
                                            contentDescription = "Close"
                                        )
                                    }
                                    IconButton(onClick = { viewModel.acceptNotify(notify.roomId) }) {
                                        Icon(
                                            imageVector = Icons.Default.Check,
                                            contentDescription = "Check"
                                        )
                                    }
                                }
                            }
                        }
                    }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun RoomNotifyPreview() {
    RoomNotifyScreen({})
}