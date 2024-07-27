package com.huggydugy.wifistand.ui.navgraph

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import com.huggydugy.wifistand.R

sealed class Screen (
    val route: String,
    val icon: Any
){
    data object Wifi : Screen(
        route = "wifiScreen",
        icon = R.drawable.wifi
    )

    data object Bluetooth : Screen(
        route = "bluetoothScreen",
        icon = R.drawable.bluetooth
    )

    data object Settings : Screen(
        route = "settingsScreen",
        icon = Icons.Default.Settings
    )

}