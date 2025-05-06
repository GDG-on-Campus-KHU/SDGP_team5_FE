package com.example.resq.presentaion.resq

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.intl.Locale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.resq.navigation.home.HomeNavigationItem
import com.example.resq.presentaion.component.SearchBar
import com.example.resq.ui.theme.InnerPadding
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

@Composable
fun ResQScreen(
    navController: NavController,
    padding: PaddingValues,
    viewModel: ResQViewModel = viewModel()
) {
    val resQList = viewModel.getResQList(Locale.current.language)
    var searchText by remember { mutableStateOf("") }

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
                navController.navigate(HomeNavigationItem.ResQSearch.route + "/$encodedSearchText")
            }
        )

        Spacer(Modifier.height(InnerPadding))
        resQList.chunked(2).forEach { chunk ->
            Row(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                chunk.forEach {
                    Column(
                        modifier = Modifier.weight(1f),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Card(
                            modifier = Modifier
                                .fillMaxHeight(0.8f)
                                .clickable(
                                    onClick = {
                                        val resQEncoded = URLEncoder.encode(
                                            it.resQSlug,
                                            StandardCharsets.UTF_8.toString()
                                        )
                                        navController.navigate(HomeNavigationItem.ResQDetail.route + "/$resQEncoded")
                                    },
                                    interactionSource = null,
                                    indication = null
                                )
                        ) {
                            Image(
                                painter = painterResource(it.resQImage),
                                contentDescription = it.resQ,
                                contentScale = ContentScale.Fit
                            )
                        }
                        Box(
                            modifier = Modifier.fillMaxWidth(),
                            contentAlignment = Alignment.Center,
                            content = { Text(text = it.resQ) }
                        )
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ResQPreview() {
    ResQScreen(rememberNavController(), PaddingValues(0.dp))
}