import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Settings
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.runtime.Composable
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.huggydugy.wifistand.ui.theme.Green

// Define colors
val Green = Color(0xFF4CAF50)
val White = Color.White

// Object to hold offset and size values
object OffsetValues {
    val bottom: Dp = 0.dp
    val top: Dp = (-50.2).dp

    val xTop: Dp = 0.dp
    val xStart: Dp = (-166).dp
    val xEnd: Dp = 166.dp

    val x1Start: Dp = 15.dp
    val x1EndTop: Dp = 160.dp
    val x1EndNext: Dp = 328.dp

    val x2Start: Dp = (-328).dp
    val x2End: Dp = (-15).dp
    val x2StartTop: Dp = (-160).dp

    val small: Dp = 50.dp
    val big: Dp = 70.dp
}

data class OffsetData(val x: Dp, val y: Dp)

@Composable
fun AnimatedNavBar(
    navController: NavController
) {

    // States for positions and sizes
    var offsetA by remember { mutableStateOf(OffsetData(OffsetValues.xTop, OffsetValues.top)) }
    var offsetB by remember { mutableStateOf(OffsetData(OffsetValues.x1Start, OffsetValues.bottom)) }
    var offsetC by remember { mutableStateOf(OffsetData(OffsetValues.x2End, OffsetValues.bottom)) }

    var sizeA by remember { mutableStateOf(OffsetValues.big) }
    var sizeB by remember { mutableStateOf(OffsetValues.small) }
    var sizeC by remember { mutableStateOf(OffsetValues.small) }

    var iconSizeA by remember { mutableStateOf(if (sizeA == OffsetValues.big) 50.dp else 30.dp) }
    var iconSizeB by remember { mutableStateOf(if (sizeB == OffsetValues.big) 50.dp else 30.dp) }
    var iconSizeC by remember { mutableStateOf(if (sizeC == OffsetValues.big) 50.dp else 30.dp) }

    val animatedOffsetA by animateDpAsState(
        targetValue = offsetA.x,
        animationSpec = tween(durationMillis = 300)
    )
    val animatedOffsetYA by animateDpAsState(
        targetValue = offsetA.y,
        animationSpec = tween(durationMillis = 300)
    )
    val animatedOffsetB by animateDpAsState(
        targetValue = offsetB.x,
        animationSpec = tween(durationMillis = 300)
    )
    val animatedOffsetYB by animateDpAsState(
        targetValue = offsetB.y,
        animationSpec = tween(durationMillis = 300)
    )
    val animatedOffsetC by animateDpAsState(
        targetValue = offsetC.x,
        animationSpec = tween(durationMillis = 300)
    )
    val animatedOffsetYC by animateDpAsState(
        targetValue = offsetC.y,
        animationSpec = tween(durationMillis = 300)
    )

    val animatedSizeA by animateDpAsState(
        targetValue = sizeA,
        animationSpec = tween(durationMillis = 300)
    )
    val animatedSizeB by animateDpAsState(
        targetValue = sizeB,
        animationSpec = tween(durationMillis = 300)
    )
    val animatedSizeC by animateDpAsState(
        targetValue = sizeC,
        animationSpec = tween(durationMillis = 300)
    )
    val animatedIconSizeA by animateDpAsState(
        targetValue = iconSizeA,
        animationSpec = tween(durationMillis = 300)
    )
    val animatedIconSizeB by animateDpAsState(
        targetValue = iconSizeB,
        animationSpec = tween(durationMillis = 300)
    )
    val animatedIconSizeC by animateDpAsState(
        targetValue = iconSizeC,
        animationSpec = tween(durationMillis = 300)
    )

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(100.dp)
            .padding(bottom = 15.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Box(
            modifier = Modifier
                .size(animatedSizeB)
                .offset(x = animatedOffsetB, y = animatedOffsetYB)
                .clip(RoundedCornerShape(100))
                .background(Green)
                .clickable {
                    when {
                        offsetB.x == OffsetValues.x1Start && offsetB.y == OffsetValues.bottom -> {
                            if (offsetA.x == OffsetValues.xTop && offsetA.y == OffsetValues.top) {
                                offsetB = OffsetData(OffsetValues.x1EndTop, OffsetValues.top)
                                offsetA = OffsetData(OffsetValues.xStart, OffsetValues.bottom)
                                sizeA = OffsetValues.small
                                sizeB = OffsetValues.big
                                iconSizeA = 30.dp
                                iconSizeB = 50.dp
                                navController.navigate("screenB")
                            } else if (offsetC.x == OffsetValues.x2StartTop && offsetC.y == OffsetValues.top) {
                                offsetB = OffsetData(OffsetValues.x1EndTop, OffsetValues.top)
                                offsetC = OffsetData(OffsetValues.x2Start, OffsetValues.bottom)
                                offsetA = OffsetData(146.dp, OffsetValues.bottom)
                                sizeB = OffsetValues.big
                                sizeC = OffsetValues.small
                                iconSizeB = 50.dp
                                iconSizeC = 30.dp
                                navController.navigate("screenB")
                            }
                        }
                        offsetB.x == OffsetValues.x1EndNext && offsetB.y == OffsetValues.bottom -> {
                            if (offsetA.x == OffsetValues.xTop && offsetA.y == OffsetValues.top) {
                                offsetB = OffsetData(OffsetValues.x1EndTop, OffsetValues.top)
                                offsetA = OffsetData(146.dp, OffsetValues.bottom)
                                sizeB = OffsetValues.big
                                sizeA = OffsetValues.small
                                iconSizeB = 50.dp
                                iconSizeA = 30.dp
                                navController.navigate("screenB")
                            } else if (offsetC.x == OffsetValues.x2StartTop && offsetC.y == OffsetValues.top) {
                                offsetB = OffsetData(OffsetValues.x1EndTop, OffsetValues.top)
                                offsetC = OffsetData(OffsetValues.x2End, OffsetValues.bottom)
                                offsetA = OffsetData(OffsetValues.xStart, OffsetValues.bottom)
                                sizeB = OffsetValues.big
                                sizeC = OffsetValues.small
                                iconSizeB = 50.dp
                                iconSizeC = 30.dp
                                navController.navigate("screenB")
                            }
                        }
                    }
                    Log.d(
                        "Here",
                        "blue = $offsetA green = $offsetB yellow = $offsetC\n" +
                                "icon size blue = $animatedIconSizeA green = $animatedIconSizeB yellow = $animatedIconSizeC"
                    )
                },
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Default.Edit,
                contentDescription = null,
                modifier = Modifier.size(animatedIconSizeB),
                tint = White
            )
        }

        Box(
            modifier = Modifier
                .size(animatedSizeA)
                .offset(x = animatedOffsetA, y = animatedOffsetYA)
                .clip(RoundedCornerShape(100))
                .background(Green)
                .clickable {
                    when {
                        (offsetA.x == OffsetValues.xEnd && offsetA.y == OffsetValues.bottom) ||
                                offsetA.x == 146.dp -> {
                            if (offsetB.x == OffsetValues.x1EndTop && offsetB.y == OffsetValues.top) {
                                offsetA = OffsetData(OffsetValues.xTop, OffsetValues.top)
                                offsetB = OffsetData(OffsetValues.x1EndNext, OffsetValues.bottom)
                                sizeA = OffsetValues.big
                                sizeB = OffsetValues.small
                                iconSizeA = 50.dp
                                iconSizeB = 30.dp
                                navController.navigate("screenA")
                            } else if (offsetC.x == OffsetValues.x2StartTop && offsetC.y == OffsetValues.top) {
                                offsetA = OffsetData(OffsetValues.xTop, OffsetValues.top)
                                offsetC = OffsetData(OffsetValues.x2End, OffsetValues.bottom)
                                sizeA = OffsetValues.big
                                sizeC = OffsetValues.small
                                iconSizeA = 50.dp
                                iconSizeC = 30.dp
                                navController.navigate("screenB")
                            }
                        }
                        (offsetA.x == OffsetValues.xStart && offsetA.y == OffsetValues.bottom) ||
                                offsetA.x == -146.dp -> {
                            if (offsetC.x == OffsetValues.x2StartTop && offsetC.y == OffsetValues.top) {
                                offsetA = OffsetData(OffsetValues.xTop, OffsetValues.top)
                                offsetC = OffsetData(OffsetValues.x2Start, OffsetValues.bottom)
                                sizeA = OffsetValues.big
                                sizeC = OffsetValues.small
                                iconSizeA = 50.dp
                                iconSizeC = 30.dp
                                navController.navigate("screenA")
                            } else if (offsetB.x == OffsetValues.x1EndTop && offsetB.y == OffsetValues.top) {
                                offsetA = OffsetData(OffsetValues.xTop, OffsetValues.top)
                                offsetB = OffsetData(OffsetValues.x1Start, OffsetValues.bottom)
                                sizeA = OffsetValues.big
                                sizeB = OffsetValues.small
                                iconSizeA = 50.dp
                                iconSizeB = 30.dp
                                navController.navigate("screenA")
                            }
                        }
                        offsetA.x == OffsetValues.xEnd && offsetA.y == OffsetValues.bottom -> {
                            if (offsetC.x == OffsetValues.x2StartTop && offsetC.y == OffsetValues.top) {
                                offsetA = OffsetData(OffsetValues.xTop, OffsetValues.top)
                                offsetC = OffsetData(OffsetValues.x2End, OffsetValues.bottom)
                                sizeA = OffsetValues.big
                                sizeC = OffsetValues.small
                                iconSizeA = 50.dp
                                iconSizeC = 30.dp
                                navController.navigate("screenA")
                            }
                        }
                    }
                    Log.d(
                        "Here",
                        "blue = $offsetA green = $offsetB yellow = $offsetC\n" +
                                "icon size blue = $animatedIconSizeA green = $animatedIconSizeB yellow = $animatedIconSizeC"
                    )
                },
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Default.Settings,
                contentDescription = null,
                tint = White,
                modifier = Modifier.size(animatedIconSizeA)
            )
        }

        Box(
            modifier = Modifier
                .size(animatedSizeC)
                .offset(x = animatedOffsetC, y = animatedOffsetYC)
                .clip(RoundedCornerShape(100))
                .background(Green)
                .clickable {
                    when {
                        offsetC.x == OffsetValues.x2End && offsetC.y == OffsetValues.bottom -> {
                            if (offsetA.x == OffsetValues.xTop && offsetA.y == OffsetValues.top) {
                                offsetC = OffsetData(OffsetValues.x2StartTop, OffsetValues.top)
                                offsetA = OffsetData(OffsetValues.xEnd, OffsetValues.bottom)
                                sizeC = OffsetValues.big
                                sizeA = OffsetValues.small
                                iconSizeC = 50.dp
                                iconSizeA = 30.dp
                                navController.navigate("screenC")
                            } else if (offsetB.x == OffsetValues.x1EndTop && offsetB.y == OffsetValues.top) {
                                offsetC = OffsetData(OffsetValues.x2StartTop, OffsetValues.top)
                                offsetB = OffsetData(OffsetValues.x1EndNext, OffsetValues.bottom)
                                offsetA = OffsetData(-146.dp, OffsetValues.bottom)
                                sizeC = OffsetValues.big
                                sizeB = OffsetValues.small
                                iconSizeC = 50.dp
                                iconSizeB = 30.dp
                                navController.navigate("screenC")
                            }
                        }
                        offsetC.x == OffsetValues.x2Start && offsetC.y == OffsetValues.bottom -> {
                            if (offsetA.x == OffsetValues.xTop && offsetA.y == OffsetValues.top) {
                                offsetC = OffsetData(OffsetValues.x2StartTop, OffsetValues.top)
                                offsetA = OffsetData(-146.dp, OffsetValues.bottom)
                                sizeC = OffsetValues.big
                                sizeA = OffsetValues.small
                                iconSizeC = 50.dp
                                iconSizeA = 30.dp
                                navController.navigate("screenC")
                            } else if (offsetB.x == OffsetValues.x1EndTop && offsetB.y == OffsetValues.top) {
                                offsetC = OffsetData(OffsetValues.x2StartTop, OffsetValues.top)
                                offsetB = OffsetData(OffsetValues.x1Start, OffsetValues.bottom)
                                offsetA = OffsetData(OffsetValues.xEnd, OffsetValues.bottom)
                                sizeC = OffsetValues.big
                                sizeB = OffsetValues.small
                                iconSizeC = 50.dp
                                iconSizeB = 30.dp
                                navController.navigate("screenC")
                            }
                        }
                    }
                    Log.d(
                        "Here",
                        "blue = $offsetA green = $offsetB yellow = $offsetC\n" +
                                "icon size blue = $animatedIconSizeA green = $animatedIconSizeB yellow = $animatedIconSizeC"
                    )
                },
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Default.Build,
                contentDescription = null,
                tint = White,
                modifier = Modifier.size(animatedIconSizeC)
            )
        }
    }
}
