package com.example.resq.presentaion.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.resq.navigation.home.HomeNavigationItem
import com.example.resq.navigation.Route
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

    BottomNavigation(
        modifier = Modifier.height(80.dp),
        backgroundColor = Color.White
    ) {
        bottomItems.forEach { item ->
            BottomNavigationItem(
                icon = {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center,
                        modifier = Modifier
                            .fillMaxSize()
                            .clickable(
                                onClick = {
                                    navController.navigate(item.route) {
                                        selectedItem = item.route
                                        popUpTo(bottomItems.first().route) { inclusive = false }
                                        launchSingleTop = true
                                        restoreState = true
                                    }
                                },
                                interactionSource = null,
                                indication = null
                            )
                            .padding(8.dp)
                            .background(
                                if (selectedItem == item.route) Color.Blue
                                else Color.Transparent,
                                RoundedCornerShape(20)
                            )
                    ) {
                        Icon(
                            imageVector = item.icon,
                            contentDescription = item.route,
                        )
                        Text(text = item.tab)
                    }
                },
                selected = selectedItem == item.route,
                onClick = { },
                modifier = Modifier.fillMaxSize(),
                enabled = false
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun BottomNavigationBarPreview() {
    BottomBar(rememberNavController())
}