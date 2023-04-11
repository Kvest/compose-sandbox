package com.kvest.compose_sandbox.ui.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.kvest.compose_sandbox.ui.spline.BezierCurveType
import com.kvest.compose_sandbox.ui.spline.Spline

object SplineDestination : ComposeSandboxDestination {
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

fun NavGraphBuilder.addSplineToNavGraph(navController: NavHostController) {
    composable(
        route = SplineDestination.routeWithArgs,
        arguments = SplineDestination.arguments,
    ) { backStackEntry ->
        val curveTypeStr = backStackEntry.arguments?.getString(SplineDestination.curveTypeArg).orEmpty()
        val curveType = BezierCurveType.valueOf(curveTypeStr)

        Spline(
            curveType = curveType,
            modifier = Modifier.fillMaxSize(),
        )
    }
}