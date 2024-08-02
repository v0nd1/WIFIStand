package com.huggydugy.wifistand.ui.screens

import android.icu.text.IDNA.Info
import android.util.Log
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
import androidx.compose.ui.unit.Dp
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
import com.huggydugy.wifistand.ui.theme.Purple40

@Composable
fun ScaffoldScreen(
    navController: NavHostController
){
    Scaffold(
        bottomBar = {
            MovingCircleDemo()
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
@Composable
fun MovingCirclesDemo() {
    val positions = listOf(
        0.dp to 0.dp,  // Top position
        100.dp to 0.dp, // Right position
        100.dp to 100.dp, // Bottom position
        0.dp to 100.dp // Left position
    )

    var circlePositions by remember { mutableStateOf(listOf(0, 1, 2, 3)) }

    fun moveCircles() {
        circlePositions = circlePositions.map { (it + 1) % 4 }
    }

    circlePositions.forEachIndexed { index, positionIndex ->
        val (x, y) = positions[positionIndex]
        val animatedOffsetX by animateDpAsState(targetValue = x, animationSpec = tween(durationMillis = 300))
        val animatedOffsetY by animateDpAsState(targetValue = y, animationSpec = tween(durationMillis = 300))

        Box(
            modifier = Modifier
                .size(50.dp)
                .offset(x = animatedOffsetX, y = animatedOffsetY)
                .clip(RoundedCornerShape(100))
                .background(Color.Blue)
                .clickable { moveCircles() }
        )
    }
}

@Composable
fun MovingCircleDemo() {
    var offsetX by remember { mutableStateOf(0.dp) }
    var offsetY by remember { mutableStateOf(0.dp) }
    var offsetX1 by remember { mutableStateOf(0.dp) }
    var offsetY1 by remember { mutableStateOf(0.dp) }
    var offsetX2 by remember { mutableStateOf(0.dp) }
    var offsetY2 by remember { mutableStateOf(0.dp) }
    val animatedOffsetX by animateDpAsState(
        targetValue = offsetX,
        animationSpec = tween(durationMillis = 300)
    )
    val animatedOffsetY by animateDpAsState(
        targetValue = offsetY,
        animationSpec = tween(durationMillis = 300)
    )
    val animatedOffsetX1 by animateDpAsState(
        targetValue = offsetX1,
        animationSpec = tween(durationMillis = 300)
    )
    val animatedOffsetY1 by animateDpAsState(
        targetValue = offsetY1,
        animationSpec = tween(durationMillis = 300)
    )
    val animatedOffsetX2 by animateDpAsState(
        targetValue = offsetX2,
        animationSpec = tween(durationMillis = 300)
    )
    val animatedOffsetY2 by animateDpAsState(
        targetValue = offsetY2,
        animationSpec = tween(durationMillis = 300)
    )
    var size by remember { mutableStateOf(70.dp) }
    val animatedSize by animateDpAsState(
        targetValue = size,
        animationSpec = tween(durationMillis = 300)
    )
    var size1 by remember { mutableStateOf(50.dp) }
    val animatedSize1 by animateDpAsState(
        targetValue = size1,
        animationSpec = tween(durationMillis = 300)
    )
    var size2 by remember { mutableStateOf(50.dp) }
    val animatedSize2 by animateDpAsState(
        targetValue = size2,
        animationSpec = tween(durationMillis = 300)
    )

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Box(
            modifier = Modifier
                .size(animatedSize)
                .offset(x = animatedOffsetX, y = animatedOffsetY)
                .background(Color.Blue, CircleShape)
                .clickable {
                    size = 70.dp
                    size1 = 50.dp
                    size2 = 50.dp
                    if (offsetX == (-156.2).dp && offsetY == (50.2).dp) {
                        if (offsetX1 == (156.2).dp && offsetY1 == (-50.2).dp) {
                            offsetX = (0).dp
                            offsetY = (0).dp
                            offsetX1 = (0).dp
                            offsetY1 = (0).dp
                            size = 70.dp
                            size1 = 50.dp
                            size2 = 50.dp
                        } else if (offsetX2 == (-156.2).dp && offsetY2 == (-50.2).dp) {
                            offsetX = (0).dp
                            offsetY = (0).dp
                            offsetX2 = (-312.4).dp
                            offsetY2 = (0).dp
                            size = 70.dp
                            size1 = 50.dp
                            size2 = 50.dp
                        }

                    } else if (offsetX == (156.2).dp && offsetY == (50.2).dp) {
                        if (offsetX2 == (-156.2).dp && offsetY2 == (-50.2).dp) {
                            offsetX = (0).dp
                            offsetY = (0).dp
                            offsetX2 = (0).dp
                            offsetY2 = (0).dp
                            size = 70.dp
                            size1 = 50.dp
                            size2 = 50.dp
                        } else if (offsetX1 == (156.2).dp && offsetY1 == (-50.2).dp) {
                            offsetX = (0).dp
                            offsetY = (0).dp
                            offsetX1 = (312.4).dp
                            offsetY1 = (0).dp
                            size = 70.dp
                            size1 = 50.dp
                            size2 = 50.dp
                        }

                    }

                }
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(100.dp)
                .padding(bottom = 30.dp, start = 15.dp, end = 15.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Box(
                modifier = Modifier
                    .size(animatedSize1)
                    .offset(x = animatedOffsetX1, y = animatedOffsetY1)
                    .background(Color.Green, CircleShape)
                    .clickable {
                        if (offsetX1 == 0.dp && offsetY1 == 0.dp) {
                            if (offsetX == 0.dp && offsetY == 0.dp) {
                                offsetX1 = (156.2).dp
                                offsetY1 = (-50.2).dp
                                offsetX = (-156.2).dp
                                offsetY = (50.2).dp
                                size = 50.dp
                                size1 = 70.dp
                            } else if (offsetX2 == (-156.2).dp && offsetY2 == (-50.2).dp) {
                                offsetX1 = (156.2).dp
                                offsetY1 = (-50.2).dp
                                offsetX2 = (-312.4).dp
                                offsetY2 = (0).dp
                                size1 = 70.dp
                                size2 = 50.dp
                            }

                        } else if (offsetX1 == 312.4.dp && offsetY1 == 0.dp) {
                            if (offsetX == 0.dp && offsetY == 0.dp) {
                                offsetX1 = (156.2).dp
                                offsetY1 = (-50.2).dp
                                offsetX = (156.2).dp
                                offsetY = (50.2).dp
                                size1 = 70.dp
                                size = 50.dp
                            } else if (offsetX2 == (-156.2).dp && offsetY2 == (-50.2).dp) {
                                offsetX1 = (156.2).dp
                                offsetY1 = (-50.2).dp
                                offsetX2 = (0).dp
                                offsetY2 = (0).dp
                                size1 = 70.dp
                                size2 = 50.dp
                            }

                        }
                    }
            )
            Box(
                modifier = Modifier
                    .size(animatedSize2)
                    .offset(x = animatedOffsetX2, y = animatedOffsetY2)
                    .background(Color.Yellow, CircleShape)
                    .clickable {
                        if (offsetX2 == 0.dp && offsetY2 == 0.dp) {
                            if (offsetX == 0.dp && offsetY == 0.dp) {
                                offsetX2 = (-156.2).dp
                                offsetY2 = (-50.2).dp
                                offsetX = (156.2).dp
                                offsetY = (50.2).dp
                                size2 = 70.dp
                                size = 50.dp
                            } else if (offsetX1 == 156.2.dp && offsetY1 == (-50.2).dp) {
                                offsetX2 = (-156.2).dp
                                offsetY2 = (-50.2).dp
                                offsetX1 = (312.4).dp
                                offsetY1 = (0).dp
                                size2 = 70.dp
                                size1 = 50.dp
                            }
                        } else if (offsetX2 == (-312.4).dp && offsetY2 == 0.dp) {
                            if (offsetX == 0.dp && offsetY == 0.dp) {
                                offsetX2 = (-156.2).dp
                                offsetY2 = (-50.2).dp
                                offsetX = (-156.2).dp
                                offsetY = (50.2).dp
                                size2 = 70.dp
                                size = 50.dp
                            } else if (offsetX1 == (156.2).dp && offsetY1 == (-50.2).dp) {
                                offsetX2 = (-156.2).dp
                                offsetY2 = (-50.2).dp
                                offsetX1 = (0).dp
                                offsetY1 = (0).dp
                                size2 = 70.dp
                                size1 = 50.dp
                            }

                        }
                    }
            )
        }
    }

}