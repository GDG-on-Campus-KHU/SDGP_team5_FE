package com.example.resq.presentaion.resqdetail

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.intl.Locale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.resq.MainActivity.Companion.FAVORITE_RESQ_LIST
import com.example.resq.navigation.home.HomeNavigationItem
import com.example.resq.presentaion.component.CenterCircularProgress
import com.example.resq.presentaion.component.SearchBar
import com.example.resq.ui.theme.InnerPadding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
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
    var isFavorite by remember { mutableStateOf(false) }
    val language = Locale.current.language

    LaunchedEffect(resQ) {
        viewModel.getResQDetail(resQ, language)
        FAVORITE_RESQ_LIST.forEach {
            if (it.resQSlug == resQ) {
                isFavorite = true
                return@forEach
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(padding)
            .padding(horizontal = InnerPadding)
            .padding(top = InnerPadding)
    ) {
        SearchBar(
            searchText = searchText,
            onValueChange = { searchText = it },
            onClickSearch = {
                val encodedSearchText =
                    URLEncoder.encode(searchText, StandardCharsets.UTF_8.toString())
                navController.navigate(HomeNavigationItem.ResQSearch.route + "/$encodedSearchText")
            }
        )

        if (isLoading) {
            CenterCircularProgress()
        } else {
            Spacer(Modifier.height(4.dp))
            LazyColumn(modifier = Modifier.padding(horizontal = InnerPadding)) {
                item { Spacer(Modifier.height(20.dp)) }
                resQDetail.forEach { resQ ->
                    item {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = resQ.resQTitle?.getLocalizedTitle(language).toString(),
                                fontSize = 30.sp,
                                modifier = Modifier.weight(1f)
                            )
                            IconButton(
                                onClick = {
                                    isFavorite = !isFavorite
                                    resQ.resQIndex?.let {
                                        if (isFavorite) viewModel.addToFavoriteResQList(it)
                                        else viewModel.deleteToFavoriteResQList(it)
                                    }
                                }
                            ) {
                                Icon(
                                    imageVector = if (isFavorite) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                                    contentDescription = "Favorite",
                                )
                            }
                        }
                    }

                    item { Spacer(Modifier.height(8.dp)) }
                    resQ.description?.let {
                        items(it.getLocalizedTitle(language)) { description ->
                            Row {
                                Icon(imageVector = Icons.Outlined.Info, contentDescription = "Info")
                                Text(text = description, fontSize = 16.sp)
                            }
                            Spacer(Modifier.height(4.dp))
                        }
                    }

                    item { Spacer(Modifier.height(16.dp)) }
                    resQ.resQActions?.let {
                        itemsIndexed(it.getLocalizedTitle(language)) { index, resQ ->
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
                item { Spacer(Modifier.height(20.dp)) }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ResQDetailPreview() {
    ResQDetailScreen(rememberNavController(), PaddingValues(0.dp), "test")
}