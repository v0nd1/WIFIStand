package com.huggydugy.wifistand.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHost
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import com.huggydugy.wifistand.ui.navgraph.Screen
import com.huggydugy.wifistand.ui.screens.blutooth.BluetoothScreen
import com.huggydugy.wifistand.ui.screens.settings.SettingsScreen
import com.huggydugy.wifistand.ui.screens.wifi.WifiScreen

@Composable
fun ScaffoldScreen(
    navController: NavHostController
){
    Scaffold(
        bottomBar = {
            CustomBottomAppBar(
                navController = navController
            )
        }
    ) {
        Box(modifier = Modifier.padding(it)){
            NavHost(
                navController = navController,
                startDestination = Screen.Wifi.route
            ){
                composable(route = Screen.Wifi.route){
                    WifiScreen(navController = navController)
                }
                composable(route = Screen.Bluetooth.route){
                    BluetoothScreen(navController = navController)
                }
                composable(route = Screen.Settings.route){
                    SettingsScreen(navController = navController)
                }
            }
        }

    }
}

@Composable
private fun CustomBottomAppBar(navController: NavHostController){
    val screens = listOf(
        Screen.Wifi,
        Screen.Bluetooth,
        Screen.Settings
    )
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination
    val bottomBarDestination = screens.any { it.route == currentDestination?.route }
    if (bottomBarDestination) {
        NavigationBar(

        ) {
            screens.forEach { screen ->
                AddItem(
                    screen = screen,
                    currentDestination = currentDestination,
                    navController = navController
                )

            }

        }
    }

}

@Composable
private fun RowScope.AddItem(
    screen: Screen,
    currentDestination: NavDestination?,
    navController: NavHostController
) {
    val isSelected = currentDestination?.hierarchy?.any {
        it.route == screen.route
    } == true
    NavigationBarItem(
        icon = {
            if (screen.icon is Int) {
                Icon(
                    painter = painterResource(id = screen.icon),
                contentDescription = "Navigation Icon",
                modifier = Modifier.size(35.dp)
                )
            } else {
                Icon(
                    imageVector = screen.icon as ImageVector,
                    contentDescription = "Navigation Icon",
                    modifier = Modifier.size(35.dp)
                )
            }

        },
        selected = isSelected,
        onClick = {
            navController.navigate(screen.route) {
                popUpTo(navController.graph.findStartDestination().id)
                launchSingleTop = true
            }
        },
    )
}