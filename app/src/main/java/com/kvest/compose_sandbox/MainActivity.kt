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
                val menuNavigator = remember {
                    object : MenuNavigator {
                        override fun showSpline(curveType: BezierCurveType) {
                            navController.navigate("spline/${curveType}")
                        }

                        override fun showChess() {
                            navController.navigate("chess")
                        }

                        override fun showAnimatedCounter() {
                            navController.navigate("animated_counter")
                        }

                        override fun showCustomModifier() {
                            navController.navigate("custom_modifier")
                        }
                    }
                }

                NavHost(navController = navController, startDestination = "main_menu") {
                    composable("main_menu") { MainMenu(menuNavigator, Modifier.fillMaxSize()) }
                    composable("spline/{curveType}") { backStackEntry ->
                        val curveTypeStr =
                            backStackEntry.arguments?.getString("curveType").orEmpty()
                        val curveType = BezierCurveType.valueOf(curveTypeStr)
                        Spline(
                            curveType = curveType,
                            modifier = Modifier.fillMaxSize(),
                        )
                    }
                    composable("chess") { ChessBoardDemo() }
                    composable("animated_counter") { AnimatedCounterPanel(Modifier.fillMaxSize()) }
                    composable("custom_modifier") { CustomModifierDemo(Modifier.fillMaxSize()) }
                }

            }
        }
    }
}