package com.kvest.compose_sandbox

import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.kvest.compose_sandbox.ui.spline.BezierCurveType

interface ComposeSandboxDestination {
    val route: String
}

object MainMenu : ComposeSandboxDestination {
    override val route = "main_menu"
}

object Spline : ComposeSandboxDestination {
    override val route = "spline"
    const val curveTypeArg = "curve_type"
    val routeWithArgs = "${route}/{${curveTypeArg}}"
    val arguments = listOf(
        navArgument(curveTypeArg) { type = NavType.StringType }
    )

    fun buildRout(curveType: BezierCurveType): String {
        return "${route}/${curveType}"
    }
}

object Chess : ComposeSandboxDestination {
    override val route = "chess"
}

object AnimatedCounter : ComposeSandboxDestination {
    override val route = "animated_counter"
}

object CustomModifier : ComposeSandboxDestination {
    override val route = "custom_modifier"
}