package com.kvest.compose_sandbox.ui.chess

data class PieceOnSquare(
    val id: Int,
    val pieceType: PieceType,
    val square: String,
)