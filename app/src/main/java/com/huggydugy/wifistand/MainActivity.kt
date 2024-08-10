package com.huggydugy.wifistand

import AnimatedNavBar
import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import android.net.wifi.WifiInfo
import android.net.wifi.WifiManager
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.core.content.ContextCompat
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.huggydugy.wifistand.ui.screens.MovingCircleDemo
import com.huggydugy.wifistand.ui.screens.ScaffoldScreen
import com.huggydugy.wifistand.ui.screens.blutooth.BluetoothScreen
import com.huggydugy.wifistand.ui.screens.settings.SettingsScreen
import com.huggydugy.wifistand.ui.screens.wifi.WifiScreen
import com.huggydugy.wifistand.ui.theme.WIFIStandTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            WIFIStandTheme {
                val navController = rememberNavController()
                Scaffold(
                    bottomBar = { AnimatedNavBar(navController) }
                ) {
                    Box(
                        modifier = Modifier.padding(it)
                    ) {
                        NavHost(navController = navController, startDestination = "screenA") {
                            composable("screenA") { SettingsScreen(navController = navController) }
                            composable("screenB") { WifiScreen(navController = navController) }
                            composable("screenC") { BluetoothScreen(navController = navController) }
                        }
                    }
                }
                
            }
        }
    }
}
