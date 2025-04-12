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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.resq.navigation.Route
import com.example.resq.navigation.home.HomeNavigationItem
import com.example.resq.navigation.share.ShareNavigationItem
import com.example.resq.navigation.user.UserNavigationItem

@Composable
fun BottomBar(navController: NavController) {
    var selectedItem by rememberSaveable { mutableStateOf("") }
    val currentBackStackEntry by navController.currentBackStackEntryAsState()
    val bottomItems = listOf(
        Route(HomeNavigationItem.ResQ.route, "Home", Icons.Default.Home),
        Route(ShareNavigationItem.Room.route, "Share", Icons.AutoMirrored.Filled.List),
        Route(UserNavigationItem.User.route, "User", Icons.Default.AccountCircle)
    )

    LaunchedEffect(currentBackStackEntry) {
        selectedItem = currentBackStackEntry?.destination?.route ?: ""
    }

    BottomNavigation(backgroundColor = Color.White) {
        bottomItems.forEach { item ->
            BottomNavigationItem(
                icon = {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        val color = if (selectedItem == item.route) Color.Blue else Color.Black
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
                selected = selectedItem == item.route,
                onClick = {
                    navController.navigate(item.route) {
                        selectedItem = item.route
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