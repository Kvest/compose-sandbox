package com.kvest.compose_sandbox.ui.custom_modifier

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun CustomModifierDemo(
    modifier: Modifier = Modifier
) {
    Box(modifier = modifier) {
        Box(
            modifier = Modifier
                .size(100.dp)
                .offset(x = 50.dp, y = 50.dp)
                .rotateOnClick()
                .background(Color.Red)
        )

        Box(
            modifier = Modifier
                .size(100.dp)
                .offset(x = 250.dp, y = 50.dp)
                .rotateOnClick()
                .background(Color.Green)
        )

        Box(
            modifier = Modifier
                .size(100.dp)
                .offset(x = 50.dp, y = 450.dp)
                .rotateOnClick()
                .background(Color.Blue)
        )

        Box(
            modifier = Modifier
                .size(100.dp)
                .offset(x = 250.dp, y = 450.dp)
                .rotateOnClick()
                .background(Color.Magenta)
        )
    }
}

fun Modifier.rotateOnClick(): Modifier = composed {
    var rotation by remember {
        mutableStateOf(0f)
    }

    Modifier
        .rotate(rotation)
        .clickable { rotation += 10f }
        .clip(CutCornerShape(topStart = 16.dp))
}