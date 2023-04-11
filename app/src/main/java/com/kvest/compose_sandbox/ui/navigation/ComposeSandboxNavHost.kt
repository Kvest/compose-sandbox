package com.kvest.compose_sandbox.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost

@Composable
fun ComposeSandboxNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier,
) {
    NavHost(
        navController = navController,
        startDestination = MainMenuDestination.route,
        modifier = modifier,
    ) {
        addMainMenuToNavGraph(navController)
        addSplineToNavGraph(navController)
        addChessToNavGraph(navController)
        addAnimatedCounterToNavGraph(navController)
        addCustomModifierToNavGraph(navController)
    }
}