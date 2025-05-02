package com.example.resq.presentaion.component

import androidx.compose.foundation.layout.Row
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.AddCircle
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.resq.R
import com.example.resq.navigation.home.HomeNavigationItem
import com.example.resq.navigation.share.ShareNavigationItem
import com.example.resq.navigation.user.UserNavigationItem

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(navController: NavController, onClick: () -> Unit) {
    val route = navController.currentBackStackEntryAsState().value?.destination?.route.toString()
    val isButton =
        route != HomeNavigationItem.ResQ.route && route != ShareNavigationItem.Rooms.route && route != UserNavigationItem.User.route

    TopAppBar(
        navigationIcon = {
            IconButton(
                onClick = { navController.popBackStack() },
                colors = IconButtonDefaults.iconButtonColors(
                    contentColor = Color.Black,
                    disabledContentColor = Color.Transparent
                ),
                enabled = isButton
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "ArrowBack"
                )
            }
        },
        title = { Text(stringResource(R.string.app_name)) },
        actions = {
            when (route) {
                HomeNavigationItem.ResQ.route ->
                    IconButton(onClick = { navController.navigate(HomeNavigationItem.ResQBookmark.route) }) {
                        Icon(
                            imageVector = Icons.Default.Star,
                            contentDescription = "AddCircle"
                        )
                    }

                ShareNavigationItem.Rooms.route ->
                    Row {
                        IconButton(onClick = { onClick() }) {
                            Icon(
                                imageVector = Icons.Default.Notifications,
                                contentDescription = "Notifications"
                            )
                        }
                        IconButton(onClick = { navController.navigate(ShareNavigationItem.RoomAdd.route) }) {
                            Icon(
                                imageVector = Icons.Outlined.AddCircle,
                                contentDescription = "AddCircle"
                            )
                        }
                    }

                ShareNavigationItem.RoomDetail.route + "/{roomId}" ->
                    IconButton(onClick = { onClick() }) {
                        Icon(
                            painter = painterResource(R.drawable.bootstrap_globe2),
                            contentDescription = "bootstrap_globe2"
                        )
                    }

                UserNavigationItem.User.route ->
                    IconButton(onClick = {
                        navController.navigate("setting")
                    }) {
                        Icon(
                            imageVector = Icons.Default.Settings,
                            contentDescription = "Settings"
                        )
                    }
            }
        },
        modifier = Modifier.shadow(4.dp)
    )
}

@Preview(showBackground = true)
@Composable
fun TopBarPreview() {
    TopBar(rememberNavController()) {}
}