package com.kvest.compose_sandbox.ui.animated_counter

import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun AnimatedCounterPanel(modifier: Modifier = Modifier) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
        modifier = modifier
    ) {
        var count by remember { mutableStateOf(0) }

        Button(onClick = { --count }) {
            Text(text = "Dec")
        }

        Spacer(modifier = Modifier.width(24.dp))

        AnimatedCounter(count = count)

        Spacer(modifier = Modifier.width(24.dp))

        Button(onClick = { ++count }) {
            Text(text = "Inc")
        }
    }
}

private const val animationDurationMillis = 400

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun AnimatedCounter(
    count: Int,
    modifier: Modifier = Modifier,
) {
    //remember old value in order to calculate slide direction
    var oldValue by remember { mutableStateOf(count) }

    val slideDown = oldValue > count

    LaunchedEffect(count) {
        oldValue = count
    }

    val countStr = count.toString()

    Row(modifier = modifier) {
        for (i in -1  until countStr.length) {
            key(countStr.length - i) {
                AnimatedContent(
                    targetState = countStr.getOrNull(i)?.toString() ?: "",
                    transitionSpec =  {
                        if (slideDown) {
                            slideInVertically(animationSpec = tween(animationDurationMillis)) { hight -> hight } + fadeIn(animationSpec = tween(animationDurationMillis)) with slideOutVertically(animationSpec = tween(animationDurationMillis)) { hight -> -hight } + fadeOut(animationSpec = tween(animationDurationMillis))
                        } else {
                            slideInVertically(animationSpec = tween(animationDurationMillis)) { hight -> -hight } + fadeIn(animationSpec = tween(animationDurationMillis)) with slideOutVertically(animationSpec = tween(animationDurationMillis)) { hight -> hight } + fadeOut(animationSpec = tween(animationDurationMillis))
                        }
                        .using (
                            SizeTransform(clip = false)
                        )
                    }
                ) { value ->
                    Text(
                        text = value,
                        fontSize = 32.sp,
                        modifier = Modifier.defaultMinSize(
                            minWidth = 16.dp
                        )
                    )
                }
           }
        }
    }
}