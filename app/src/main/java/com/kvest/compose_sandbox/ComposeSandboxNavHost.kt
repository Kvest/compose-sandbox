package com.kvest.compose_sandbox

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.kvest.compose_sandbox.ui.animated_counter.AnimatedCounterPanel
import com.kvest.compose_sandbox.ui.chess.ChessBoardDemo
import com.kvest.compose_sandbox.ui.custom_modifier.CustomModifierDemo
import com.kvest.compose_sandbox.ui.main_menu.MainMenu
import com.kvest.compose_sandbox.ui.main_menu.MenuNavigator
import com.kvest.compose_sandbox.ui.spline.BezierCurveType
import com.kvest.compose_sandbox.ui.spline.Spline

@Composable
fun ComposeSandboxNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier,
) {
    val menuNavigator = remember {
        object : MenuNavigator {
            override fun showSpline(curveType: BezierCurveType) {
                navController.navigate(Spline.buildRout(curveType))
            }

            override fun showChess() {
                navController.navigate(Chess.route)
            }

            override fun showAnimatedCounter() {
                navController.navigate(AnimatedCounter.route)
            }

            override fun showCustomModifier() {
                navController.navigate(CustomModifier.route)
            }
        }
    }

    NavHost(
        navController = navController,
        startDestination = MainMenu.route,
        modifier = modifier,
    ) {
        composable(MainMenu.route) {
            MainMenu(menuNavigator, Modifier.fillMaxSize())
        }
        composable(
            route = Spline.routeWithArgs,
            arguments = Spline.arguments,
        ) { backStackEntry ->
            val curveTypeStr = backStackEntry.arguments?.getString(Spline.curveTypeArg).orEmpty()
            val curveType = BezierCurveType.valueOf(curveTypeStr)

            Spline(
                curveType = curveType,
                modifier = Modifier.fillMaxSize(),
            )
        }
        composable(Chess.route) {
            ChessBoardDemo()
        }
        composable(AnimatedCounter.route) {
            AnimatedCounterPanel(Modifier.fillMaxSize())
        }
        composable(CustomModifier.route) {
            CustomModifierDemo(Modifier.fillMaxSize())
        }
    }
}