package com.example.githubappcompose.uiux

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.githubappcompose.model.Bottom

@Composable
fun BottomNavigation(navController: NavController,modifier: Modifier = Modifier) {
    var bottomList= listOf(
        Bottom("Home", Icons.Default.Home,"Home"),
        Bottom("Favorite",Icons.Default.Favorite,"Favorite"),
    )
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute=navBackStackEntry?.destination?.route

    NavigationBar {
        bottomList.forEachIndexed { index, bottom ->
            NavigationBarItem(
                selected = currentRoute==bottom.route,
                onClick = {
                    navController.navigate(bottom.route){
                        launchSingleTop=true
                        restoreState=true

                        popUpTo(bottom.route){
                            saveState=true
                        }
                    }
                },
                icon = { Icon(bottom.icon,null) },
                label = { Text(bottom.label) },
                colors = NavigationBarItemDefaults.colors(Color.Green),
            )
        }
    }
}