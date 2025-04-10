package com.example.resq.presentaion.component

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.resq.navigation.home.HomeNavigationItem
import com.example.resq.navigation.share.ShareNavigationItem
import com.example.resq.navigation.user.UserNavigationItem

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(navController: NavHostController) {
    TopAppBar(
        navigationIcon = {
            val route = navController.currentBackStackEntryAsState().value?.destination?.route ?: ""
            val isButton =
                route != HomeNavigationItem.ResQ.route && route != ShareNavigationItem.Room.route && route != UserNavigationItem.User.route
            IconButton(
                onClick = { navController.popBackStack() },
                colors = IconButtonDefaults.iconButtonColors(
                    contentColor = Color.Black,
                    disabledContentColor = Color.Transparent
                ),
                enabled = isButton
            ) {
                Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "ArrowBack")
            }
        },
        title = {
            Text(text = "ResQ")
        },
        actions = {
            IconButton(onClick = { /* Search Action */ }) {
                Icon(imageVector = Icons.Default.Search, contentDescription = "Search")
            }
        },
    )
}

@Preview(showBackground = true)
@Composable
fun TopBarPreview() {
    TopBar(rememberNavController())
}