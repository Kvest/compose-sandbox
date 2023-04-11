package com.kvest.compose_sandbox.ui.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.kvest.compose_sandbox.ui.custom_modifier.CustomModifierDemo

object CustomModifierDestination : ComposeSandboxDestination {
    override val route = "custom_modifier"
}

fun NavGraphBuilder.addCustomModifierToNavGraph(navController: NavHostController) {
    composable(CustomModifierDestination.route) {
        CustomModifierDemo(Modifier.fillMaxSize())
    }
}