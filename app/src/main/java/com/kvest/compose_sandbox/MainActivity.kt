package com.kvest.compose_sandbox

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.kvest.compose_sandbox.ui.chess.ChessBoardDemo
import com.kvest.compose_sandbox.ui.theme.ComposesandboxTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposesandboxTheme {
                ChessBoardDemo()

                /*
                Box(
                    modifier = Modifier.fillMaxSize(),
                ) {
                    AnimatedCounterPanel(modifier = Modifier.align(Alignment.Center))
                }
                 */
            }
        }
    }
}