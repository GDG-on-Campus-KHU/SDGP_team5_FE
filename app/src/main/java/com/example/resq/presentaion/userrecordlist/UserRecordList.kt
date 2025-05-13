package com.example.resq.presentaion.userrecordlist

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.resq.R
import com.example.resq.presentaion.component.CenterBlankText
import com.example.resq.presentaion.component.CenterCircularProgress
import com.example.resq.ui.theme.InnerPadding

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun UserRecordList(viewModel: UserRecordListViewModel = viewModel()) {
    val isLoading by viewModel.isLoading.collectAsState()
    val records by viewModel.records.collectAsState()
    val isHeight = remember { mutableStateMapOf<String, Boolean>() }

    if (isLoading) {
        CenterCircularProgress()
    } else {
        if (records.isEmpty())
            CenterBlankText(stringResource(R.string.no_recordings))
        else
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = InnerPadding)
            ) {
                LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    item { Spacer(Modifier.height(16.dp)) }
                    items(records) { record ->
                        val isExtended = isHeight[record.id] ?: true
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .wrapContentHeight()
                                .background(Color.LightGray, RoundedCornerShape(16.dp))
                                .padding(horizontal = 8.dp, vertical = 16.dp)
                                .clickable(
                                    onClick = {
                                        isHeight.keys.forEach { isHeight[it] = true }
                                        isHeight[record.id] = !isExtended
                                    },
                                    interactionSource = null,
                                    indication = null
                                )
                        ) {
                            if (isExtended)
                                Text(
                                    text = record.recordedId,
                                    modifier = Modifier.align(Alignment.CenterStart)
                                )
                            else
                                Column {
                                    Text(text = record.recordedId)
                                    Spacer(Modifier.height(16.dp))
                                    FlowRow {
                                        Text(text = record.recordText)
                                    }
                                }
                        }
                    }
                    item { Spacer(Modifier.height(16.dp)) }
                }
            }
    }
}

@Preview(showBackground = true)
@Composable
fun UserRecordListPreview() {
    UserRecordList()
}