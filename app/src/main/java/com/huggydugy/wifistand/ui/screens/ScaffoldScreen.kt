package com.huggydugy.wifistand.ui.screens

import android.icu.text.IDNA.Info
import android.util.Log
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.TweenSpec
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.animateValueAsState
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Settings
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
import androidx.compose.ui.graphics.Color.Companion.Gray
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalConfiguration
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
import com.huggydugy.wifistand.ui.theme.Green




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
fun MovingCircleDemo() {
    val bottom = 0.dp
    val top = (-50.2).dp

    val xTop = (0).dp
    val xStart = (-166).dp
    val xEnd = (166).dp


    val x1Start = 15.dp
    val x1EndTop = (160).dp
    val x1EndNext = (328).dp

    val x2Start = (-328).dp
    val x2End = (-15).dp
    val x2StartTop = (-160).dp

    val small = 50.dp
    val big = 70.dp

    var offsetX by remember { mutableStateOf(xTop) }
    var offsetY by remember { mutableStateOf(top) }
    var offsetX1 by remember { mutableStateOf(x1Start) }
    var offsetY1 by remember { mutableStateOf(bottom) }
    var offsetX2 by remember { mutableStateOf(x2End) }
    var offsetY2 by remember { mutableStateOf(bottom) }

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
    val bigIcon by remember {
        mutableStateOf(size == 70.dp)
    }
    val bigIcon1 by remember {
        mutableStateOf(size1 == 70.dp)
    }
    val bigIcon2 by remember {
        mutableStateOf(size2 == 70.dp)
    }
    var sizeIc by remember { mutableStateOf(if (bigIcon) 50.dp else 30.dp) }
    var sizeIc1 by remember { mutableStateOf(if (bigIcon1) 50.dp else 30.dp) }
    var sizeIc2 by remember { mutableStateOf(if (bigIcon2) 50.dp else 30.dp) }
    val animatedSizeIcon by animateDpAsState(
        targetValue = sizeIc,
        animationSpec = tween(durationMillis = 300)
    )
    val animatedSizeIcon1 by animateDpAsState(
        targetValue = sizeIc1,
        animationSpec = tween(durationMillis = 300)
    )
    val animatedSizeIcon2 by animateDpAsState(
        targetValue = sizeIc2,
        animationSpec = tween(durationMillis = 300)
    )

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(100.dp)
            .padding(
                bottom = 30.dp,
            ),
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        // ГРИН
        Box(
            modifier = Modifier
                .size(animatedSize1)
                .offset(x = animatedOffsetX1, y = animatedOffsetY1)
                .clip(RoundedCornerShape(100))
                .background(Green)
                .clickable {
                    if (offsetX1 == x1Start && offsetY1 == bottom) {
                        if (offsetX == xTop && offsetY == top) {
                            offsetX1 = x1EndTop
                            offsetY1 = top
                            offsetX = xStart
                            offsetY = bottom
                            size = small
                            size1 = big
                            sizeIc = 30.dp
                            sizeIc1 = 50.dp
                        } else if (offsetX2 == x2StartTop && offsetY2 == top) {
                            offsetX1 = x1EndTop
                            offsetY1 = top
                            offsetX2 = x2Start
                            offsetY2 = bottom
                            offsetX = 146.dp
                            size1 = big
                            size2 = small
                            sizeIc1 = 50.dp
                            sizeIc2 = 30.dp
                        }

                    } else if (offsetX1 == x1EndNext && offsetY1 == bottom) {
                        if (offsetX == xTop && offsetY == top) {
                            offsetX1 = x1EndTop
                            offsetY1 = top
                            offsetX = 146.dp
                            offsetY = bottom
                            size1 = big
                            size = small
                            sizeIc1 = 50.dp
                            sizeIc = 30.dp
                        } else if (offsetX2 == x2StartTop && offsetY2 == top) {
                            offsetX1 = x1EndTop
                            offsetY1 = top
                            offsetX2 = x2End
                            offsetY2 = bottom
                            offsetX = xStart
                            size1 = big
                            size2 = small
                            sizeIc1 = 50.dp
                            sizeIc2 = 30.dp
                        }

                    }
                    Log.d(
                        "Here",
                        "blue = $offsetX $offsetY green = $offsetX1 $offsetY1 yellow = $offsetX2 $offsetY2\n" +
                                "icon size blue = $animatedSizeIcon green = $animatedSizeIcon1 yellow = $animatedSizeIcon2"
                    )
                },
            contentAlignment = Alignment.Center
        ){

            Icon(
                imageVector = Icons.Default.Edit,
                contentDescription = null,
                modifier = Modifier.size(animatedSizeIcon1),
                tint = White
            )
        }


        // БЛЮ
        Box(
            modifier = Modifier
                .size(animatedSize)
                .offset(x = animatedOffsetX, y = animatedOffsetY)
                .clip(RoundedCornerShape(100))
                .background(Green)
                .clickable {
                    if (offsetX == xEnd && offsetY == bottom || offsetX == 146.dp) {
                        if (offsetX1 == x1EndTop && offsetY1 == top) {
                            offsetX = xTop
                            offsetY = top
                            offsetX1 = x1EndNext
                            offsetY1 = bottom
                            size = big
                            size1 = small
                            sizeIc = 50.dp
                            sizeIc1 = 30.dp
                        } else if (offsetX2 == x2StartTop && offsetY2 == top) {
                            offsetX = xTop
                            offsetY = top
                            offsetX2 = x2End
                            offsetY2 = bottom
                            size = big
                            size2 = small
                            sizeIc = 50.dp
                            sizeIc2 = 30.dp
                        }

                    } else if (offsetX == xStart && offsetY == bottom || offsetX == -146.dp) {
                        if (offsetX2 == x2StartTop && offsetY2 == top) {
                            offsetX = xTop
                            offsetY = top
                            offsetX2 = x2Start
                            offsetY2 = bottom
                            size = big
                            size2 = small
                            sizeIc = 50.dp
                            sizeIc2 = 30.dp
                        } else if (offsetX1 == x1EndTop && offsetY1 == top) {
                            offsetX = xTop
                            offsetY = top
                            offsetX1 = x1Start
                            offsetY1 = bottom
                            size = big
                            size1 = small
                            sizeIc = 50.dp
                            sizeIc1 = 30.dp
                        }

                    } else if (offsetX == xEnd && offsetY == bottom) {
                        if (offsetX2 == x2StartTop && offsetY2 == top) {
                            offsetX = xTop
                            offsetY = top
                            offsetX2 = x2End
                            offsetY2 = bottom
                            size = big
                            size2 = small
                            sizeIc = 50.dp
                            sizeIc2 = 30.dp
                        }
                    }
                    Log.d(
                        "Here",
                        "blue = $offsetX $offsetY green = $offsetX1 $offsetY1 yellow = $offsetX2 $offsetY2\n" +
                                "icon size blue = $animatedSizeIcon green = $animatedSizeIcon1 yellow = $animatedSizeIcon2"
                    )

                },
            contentAlignment = Alignment.Center
        ){


            Icon(
                imageVector = Icons.Default.Settings,
                contentDescription = null,
                tint = White,
                modifier = Modifier
                    .size(animatedSizeIcon)
            )
        }

        // ЕЛЛОУ
        Box(
            modifier = Modifier
                .size(animatedSize2)
                .offset(x = animatedOffsetX2, y = animatedOffsetY2)
                .clip(RoundedCornerShape(100))
                .background(Green)
                .clickable {
                    if (offsetX2 == x2End && offsetY2 == bottom) {
                        if (offsetX == xTop && offsetY == top) {
                            offsetX2 = x2StartTop
                            offsetY2 = top
                            offsetX = xEnd
                            offsetY = bottom
                            size2 = big
                            size = small
                            sizeIc2 = 50.dp
                            sizeIc = 30.dp
                        } else if (offsetX1 == x1EndTop && offsetY1 == top) {
                            offsetX2 = x2StartTop
                            offsetY2 = top
                            offsetX1 = x1EndNext
                            offsetY1 = bottom
                            offsetX = -146.dp
                            size2 = big
                            size1 = small
                            sizeIc2 = 50.dp
                            sizeIc1 = 30.dp
                        }
                    } else if (offsetX2 == x2Start && offsetY2 == bottom) {
                        if (offsetX == xTop && offsetY == top) {
                            offsetX2 = x2StartTop
                            offsetY2 = top
                            offsetX = -146.dp
                            offsetY = bottom
                            size2 = big
                            size = small
                            sizeIc2 = 50.dp
                            sizeIc = 30.dp
                        } else if (offsetX1 == x1EndTop && offsetY1 == top) {
                            offsetX2 = x2StartTop
                            offsetY2 = top
                            offsetX1 = x1Start
                            offsetY1 = bottom
                            offsetX = xEnd
                            size2 = big
                            size1 = small
                            sizeIc2 = 50.dp
                            sizeIc1 = 30.dp
                        }

                    }
                    Log.d(
                        "Here",
                        "blue = $offsetX $offsetY green = $offsetX1 $offsetY1 yellow = $offsetX2 $offsetY2 \n" +
                                "icon size blue = $animatedSizeIcon green = $animatedSizeIcon1 yellow = $animatedSizeIcon2"
                    )

                },
            contentAlignment = Alignment.Center
        ){

            Icon(
                imageVector = Icons.Default.Build,
                contentDescription = null,
                tint = White,
                modifier = Modifier.size(animatedSizeIcon2)
            )
        }
    }

}