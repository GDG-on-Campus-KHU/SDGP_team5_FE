package com.example.resq.presentaion.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.BottomNavigation
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.resq.navigation.Route
import com.example.resq.navigation.home.HomeNavigationItem
import com.example.resq.navigation.share.ShareNavigationItem
import com.example.resq.navigation.user.UserNavigationItem

@Composable
fun BottomBar(navController: NavController) {
    val bottomItems = listOf(
        Route(HomeNavigationItem.ResQ.route, "Home", Icons.Default.Home),
        Route(ShareNavigationItem.Rooms.route, "Share", Icons.AutoMirrored.Filled.List),
        Route(UserNavigationItem.User.route, "User", Icons.Default.AccountCircle)
    )
    var selectedItem by remember { mutableStateOf(bottomItems.first().tab) }

    BottomNavigation(backgroundColor = Color.White) {
        bottomItems.forEach { item ->
            BottomNavigationItem(
                icon = {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        val color = if (selectedItem == item.tab) Color.Blue else Color.Black
                        Icon(
                            imageVector = item.icon,
                            contentDescription = item.route,
                            tint = color
                        )
                        Text(
                            text = item.tab,
                            color = color
                        )
                    }
                },
                selected = selectedItem == item.tab,
                onClick = {
                    navController.navigate(item.route) {
                        selectedItem = item.tab
                        popUpTo(bottomItems.first().route) { inclusive = false }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun BottomNavigationBarPreview() {
    BottomBar(rememberNavController())
}