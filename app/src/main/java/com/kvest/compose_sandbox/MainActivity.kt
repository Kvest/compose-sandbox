package com.kvest.compose_sandbox

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.kvest.compose_sandbox.ui.animated_counter.AnimatedCounterPanel
import com.kvest.compose_sandbox.ui.chess.ChessBoardDemo
import com.kvest.compose_sandbox.ui.custom_modifier.CustomModifierDemo
import com.kvest.compose_sandbox.ui.main_menu.MainMenu
import com.kvest.compose_sandbox.ui.main_menu.MenuNavigator
import com.kvest.compose_sandbox.ui.spline.BezierCurveType
import com.kvest.compose_sandbox.ui.spline.Spline
import com.kvest.compose_sandbox.ui.theme.ComposesandboxTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposesandboxTheme {
                val navController = rememberNavController()

                ComposeSandboxNavHost(navController = navController)
            }
        }
    }
}