package com.example.resq.presentaion.resqdetail

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.resq.presentaion.component.CenterCircularProgress
import com.example.resq.presentaion.component.SearchBar
import com.example.resq.ui.theme.InnerPadding

@Composable
fun ResQDetailScreen(
    padding: PaddingValues,
    resQ: String,
    viewModel: ResQDetailViewModel = viewModel()
) {
    val isLoading by viewModel.isLoading.collectAsState()
    val resQDetail = viewModel.resQDetail.collectAsState()
    var searchText by remember { mutableStateOf("") }

    LaunchedEffect(resQ) {
        viewModel.getResQDetail(resQ)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(padding)
            .padding(InnerPadding)
    ) {
        SearchBar(searchText) { searchText = it }

        if (isLoading) {
            CenterCircularProgress()
        } else {
            Spacer(Modifier.height(InnerPadding))
            LazyColumn {
                items(resQDetail.value) {
                    Text(text = it.title)
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ResQDetailPreview() {
    ResQDetailScreen(PaddingValues(0.dp), "test")
}