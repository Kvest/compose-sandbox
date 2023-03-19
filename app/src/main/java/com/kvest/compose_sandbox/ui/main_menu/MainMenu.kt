package com.kvest.compose_sandbox.ui.main_menu

import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.kvest.compose_sandbox.ui.spline.BezierCurveType

@Composable
fun MainMenu(
    menuNavigator: MenuNavigator,
    modifier: Modifier = Modifier
) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier.fillMaxSize()
    ) {
        val spacerModifier = Modifier.height(12.dp)
        MenuButton("Cubic Spline") { menuNavigator.showSpline(BezierCurveType.CUBIC) }
        Spacer(modifier = spacerModifier)
        MenuButton("Quadratic Spline") { menuNavigator.showSpline(BezierCurveType.QUADRATIC) }
        Spacer(modifier = spacerModifier)
        MenuButton("Chess") { menuNavigator.showChess() }
        Spacer(modifier = spacerModifier)
        MenuButton("Animated Counter") { menuNavigator.showAnimatedCounter() }
        Spacer(modifier = spacerModifier)
        MenuButton("Custom Modifier") { menuNavigator.showCustomModifier() }
        Spacer(modifier = spacerModifier)
    }
}

@Composable
fun MenuButton(
    text: String,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        modifier = modifier
            .width(250.dp),
    ) {
        Text(text = text)
    }
}