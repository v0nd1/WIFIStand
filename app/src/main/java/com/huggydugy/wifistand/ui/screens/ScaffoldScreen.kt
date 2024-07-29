package com.huggydugy.wifistand.ui.screens

import android.icu.text.IDNA.Info
import androidx.compose.animation.core.TweenSpec
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.geometry.Offset
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
import com.huggydugy.wifistand.ui.navgraph.NavHostApp
import com.huggydugy.wifistand.ui.navgraph.Screen

@Composable
fun ScaffoldScreen(
    navController: NavHostController
){
    Scaffold(
        bottomBar = {
            CustomBottomAppBar(navController)
        }
    ) {
        Box(modifier = Modifier.padding(it)){
            NavHostApp(navController)
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

        CustomNavBar(navController, screens)
    }

}
@Composable
fun CustomNavBar(navController: NavController, screens: List<Screen>) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination
    val currentScreen = screens.find { it.route == currentDestination?.route } ?: screens.first()

    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CustomItem(
            screen = currentScreen,
            isSelected = true,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 40.dp, start = 20.dp, end = 20.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            screens.forEach { screen ->
                if (screen != currentScreen) {
                    CustomItem(
                        screen = screen,
                        isSelected = false,
                        onClick = { navController.navigate(screen.route) }
                    )
                }
            }
        }
    }
}

@Composable
fun CustomItem(
    screen: Screen,
    isSelected: Boolean,
    modifier: Modifier = Modifier,
    onClick: (() -> Unit)? = null
) {
    val scale = animateFloatAsState(targetValue = if (isSelected) 1.5f else 1f)

    Box(
        modifier = modifier
            .size(45.dp)
            .scale(scale.value)
            .clip(CircleShape)
            .background(if (isSelected) MaterialTheme.colorScheme.primary else Color.Gray)
            .clickable(onClick != null) { onClick?.invoke() },
        contentAlignment = Alignment.Center
    ) {
        if (screen.icon is Int) {
            Icon(painter = painterResource(id = screen.icon), contentDescription = null)
        } else {
            Icon(imageVector = screen.icon as ImageVector, contentDescription = null)
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