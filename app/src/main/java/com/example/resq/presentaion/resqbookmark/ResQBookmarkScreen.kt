package com.example.resq.presentaion.resqbookmark

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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.resq.navigation.home.HomeNavigationItem
import com.example.resq.presentaion.component.CenterCircularProgress
import com.example.resq.presentaion.component.ConfirmDialog
import com.example.resq.presentaion.resqbookmark.component.BookmarkOptions
import com.example.resq.ui.theme.InnerPadding
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

@Composable
fun ResQBookmarkScreen(
    navController: NavController,
    padding: PaddingValues,
    viewModel: ResQBookmarkViewModel = viewModel()
) {
    val isLoading by viewModel.isLoading.collectAsState()
    val bookmarks by viewModel.bookmarks.collectAsState()
    val isExpanded = remember { mutableStateMapOf<String, Boolean>() }
    var isDialogExpended by remember { mutableStateOf(false) }
    val bookmarkOption = remember { mutableStateOf("") }
    val bookmarkTitle = remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(padding)
            .padding(horizontal = InnerPadding)
    ) {
        if (isLoading)
            CenterCircularProgress()
        else
            LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                item { Spacer(Modifier.height(4.dp)) }
                items(bookmarks) { bookmark ->
                    val isBookmarkOptions = isExpanded[bookmark.title] ?: false
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .heightIn(min = 80.dp)
                            .background(Color.LightGray, RoundedCornerShape(16.dp))
                            .padding(8.dp)
                            .clickable(
                                onClick = {
                                    val encodedBookmarkTitle =
                                        URLEncoder.encode(
                                            bookmark.title,
                                            StandardCharsets.UTF_8.toString()
                                        )
                                    navController.navigate(HomeNavigationItem.ResQDetail.route + "/$encodedBookmarkTitle")
                                },
                                interactionSource = null,
                                indication = null
                            )
                    ) {
                        Text(
                            text = bookmark.title,
                            modifier = Modifier.align(Alignment.CenterStart)
                        )
                        Row(
                            modifier = Modifier.align(Alignment.CenterEnd),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center
                        ) {
                            if (isBookmarkOptions)
                                BookmarkOptions {
                                    bookmarkOption.value = it
                                    bookmarkTitle.value = bookmark.title
                                    isDialogExpended = !isDialogExpended
                                }
                            IconButton(onClick = {
                                isExpanded.keys.forEach { isExpanded[it] = false }
                                isExpanded[bookmark.title] = !isBookmarkOptions
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

    if (isDialogExpended)
        ConfirmDialog(
            title = bookmarkOption.value,
            onDismissRequest = { isDialogExpended = !isDialogExpended },
            onClick = { viewModel.deleteBookmark(bookmarkTitle.value) }
        )
}

@Preview(showBackground = true)
@Composable
fun ResQBookmarkPreview() {
    ResQBookmarkScreen(rememberNavController(), PaddingValues(0.dp))
}