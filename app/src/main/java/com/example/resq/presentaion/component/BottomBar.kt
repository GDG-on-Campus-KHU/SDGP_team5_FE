package com.example.resq.presentaion.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.BottomNavigation
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.BottomNavigationItem
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.resq.navigation.BottomNavigationItem

@Composable
fun BottomBar(navController: NavController) {
    var selectedItem by rememberSaveable { mutableStateOf("") }
    val currentBackStackEntry by navController.currentBackStackEntryAsState()
    val bottomItems = listOf(
        BottomNavigationItem.Home,
        BottomNavigationItem.Share,
        BottomNavigationItem.User,
    )

    LaunchedEffect(currentBackStackEntry) {
        selectedItem = currentBackStackEntry?.destination?.route ?: ""
    }

    BottomNavigation{
        bottomItems.forEach { item ->
            BottomNavigationItem(
                icon = {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.clickable(
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
                    ) {
                        Icon(
                            imageVector = item.icon,
                            contentDescription = item.route,
                        )
                        Text(text = item.route,)
                    }
                },
                selected = selectedItem == item.route,
                onClick = { },
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