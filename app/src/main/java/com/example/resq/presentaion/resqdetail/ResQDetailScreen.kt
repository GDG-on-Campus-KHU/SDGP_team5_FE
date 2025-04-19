package com.example.resq.presentaion.resqdetail

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.intl.Locale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.resq.navigation.home.HomeNavigationItem
import com.example.resq.presentaion.component.CenterCircularProgress
import com.example.resq.presentaion.component.SearchBar
import com.example.resq.ui.theme.InnerPadding
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

@Composable
fun ResQDetailScreen(
    navController: NavController,
    padding: PaddingValues,
    resQ: String,
    viewModel: ResQDetailViewModel = viewModel()
) {
    val isLoading by viewModel.isLoading.collectAsState()
    val resQDetail by viewModel.resQDetail.collectAsState()
    var searchText by remember { mutableStateOf("") }
    val language = Locale.current.language

    LaunchedEffect(resQ) {
        viewModel.getResQDetail(resQ, language)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(padding)
            .padding(InnerPadding)
    ) {
        SearchBar(
            searchText = searchText,
            onValueChange = { searchText = it },
            onClickSearch = {
                val encodedSearchText =
                    URLEncoder.encode(searchText, StandardCharsets.UTF_8.toString())
                navController.navigate(HomeNavigationItem.ResQDetail.route + "/$encodedSearchText")
            }
        )

        if (isLoading) {
            CenterCircularProgress()
        } else {
            Spacer(Modifier.height(InnerPadding))
            LazyColumn(modifier = Modifier.padding(horizontal = InnerPadding)) {
                item { Text(text = resQDetail.resQTitle?.korean.toString(), fontSize = 30.sp) }

                item { Spacer(Modifier.height(8.dp)) }
                resQDetail.description?.let {
                    items(it.korean) { description ->
                        Row {
                            Icon(imageVector = Icons.Outlined.Info, contentDescription = "Info")
                            Text(text = description, fontSize = 16.sp)
                        }
                        Spacer(Modifier.height(4.dp))
                    }
                }

                item { Spacer(Modifier.height(16.dp)) }
                resQDetail.resQActions?.let {
                    itemsIndexed(it.resQDetail) { index, resQ ->
                        Text(text = "${index + 1}. ${resQ.step}", fontSize = 20.sp)
                        resQ.detail.forEach { detail ->
                            Row {
                                Spacer(Modifier.width(20.dp))
                                Text(text = detail)
                            }
                        }
                        Spacer(Modifier.height(8.dp))
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ResQDetailPreview() {
    ResQDetailScreen(rememberNavController(), PaddingValues(0.dp), "test")
}