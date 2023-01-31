package com.kvest.compose_sandbox

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.kvest.compose_sandbox.ui.animated_counter.AnimatedCounterPanel
import com.kvest.compose_sandbox.ui.theme.ComposesandboxTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposesandboxTheme {
                // A surface container using the 'background' color from the theme
                Box(
                    modifier = Modifier.fillMaxSize(),
                ) {
                    AnimatedCounterPanel(modifier = Modifier.align(Alignment.Center))
                }
            }
        }
    }
}