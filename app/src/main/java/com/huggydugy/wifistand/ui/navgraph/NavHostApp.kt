package com.huggydugy.wifistand.ui.navgraph

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.huggydugy.wifistand.ui.screens.blutooth.BluetoothScreen
import com.huggydugy.wifistand.ui.screens.settings.SettingsScreen
import com.huggydugy.wifistand.ui.screens.wifi.WifiScreen

@Composable
fun NavHostApp(
    navController: NavHostController
){
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