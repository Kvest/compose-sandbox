package com.kvest.compose_sandbox.ui.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.kvest.compose_sandbox.ui.chess.ChessBoardDemo

object ChessDestination : ComposeSandboxDestination {
    override val route = "chess"
}

fun NavGraphBuilder.addChessToNavGraph(navController: NavHostController) {
    composable(ChessDestination.route) {
        ChessBoardDemo()
    }
}