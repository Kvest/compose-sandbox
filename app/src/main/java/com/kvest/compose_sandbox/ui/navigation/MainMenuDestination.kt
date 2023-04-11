package com.kvest.compose_sandbox.ui.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.kvest.compose_sandbox.ui.main_menu.MainMenu
import com.kvest.compose_sandbox.ui.main_menu.MenuNavigator
import com.kvest.compose_sandbox.ui.spline.BezierCurveType

object MainMenuDestination : ComposeSandboxDestination {
    override val route = "main_menu"
}

fun NavGraphBuilder.addMainMenuToNavGraph(navController: NavHostController) {
    composable(MainMenuDestination.route) {
        val menuNavigator = remember {
            object : MenuNavigator {
                override fun showSpline(curveType: BezierCurveType) {
                    navController.navigate(SplineDestination.buildRout(curveType))
                }

                override fun showChess() {
                    navController.navigate(ChessDestination.route)
                }

                override fun showAnimatedCounter() {
                    navController.navigate(AnimatedCounterDestination.route)
                }

                override fun showCustomModifier() {
                    navController.navigate(CustomModifierDestination.route)
                }
            }
        }

        MainMenu(menuNavigator, Modifier.fillMaxSize())
    }
}