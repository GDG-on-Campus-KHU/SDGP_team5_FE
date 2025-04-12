package com.example.resq.presentaion.resq

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.resq.R
import com.example.resq.navigation.home.HomeNavigationItem
import com.example.resq.presentaion.resq.model.ResQ
import com.example.resq.ui.theme.InnerPadding
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

@Composable
fun ResQScreen(
    navController: NavHostController,
    padding: PaddingValues,
) {
    val resQList =
        listOf(
            ResQ("의식장애/심정지", R.drawable.resq4),
            ResQ("호흡곤란", R.drawable.resq3),
            ResQ("출혈", R.drawable.resq2),
            ResQ("외상", R.drawable.resq1),
            ResQ("경련/발작", R.drawable.resq7),
            ResQ("화상", R.drawable.resq8),
            ResQ("온열/한랭", R.drawable.resq6),
            ResQ("정신적 응급", R.drawable.resq5)
        )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(padding)
            .padding(horizontal = InnerPadding * 2),
        verticalArrangement = Arrangement.Center
    ) {
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalArrangement = Arrangement.spacedBy(InnerPadding * 3)
        ) {
            items(resQList) {
                Column(
                    modifier = Modifier.clickable(
                        onClick = {
                            val resQEncoded =
                                URLEncoder.encode(it.resQ, StandardCharsets.UTF_8.toString())
                            navController.navigate(HomeNavigationItem.ResQDetail.route + "/$resQEncoded" + "/${it.resQImage}")
                        },
                        interactionSource = null,
                        indication = null
                    )
                ) {
                    Card(modifier = Modifier.aspectRatio(1f)) {
                        Image(
                            painter = painterResource(it.resQImage),
                            contentDescription = it.resQ,
                            contentScale = ContentScale.Fit
                        )
                    }
                    Box(
                        modifier = Modifier.fillMaxWidth(),
                        contentAlignment = Alignment.Center,
                        content = { Text(it.resQ) }
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ResQPreview() {
    ResQScreen(rememberNavController(), PaddingValues(0.dp)) //, PaddingValues(0.dp))
}