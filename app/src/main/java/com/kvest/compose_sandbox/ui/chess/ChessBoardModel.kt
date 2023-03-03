package com.kvest.compose_sandbox.ui.chess

private const val BOARD_SIZE = 8

class ChessBoardModel {
    val size: Int
        get() = BOARD_SIZE

    operator fun get(row: Int, column: Int): String {
        require(row in 0 until BOARD_SIZE)
        require(column in 0 until BOARD_SIZE)

        return ('a'.code + column).toChar() + (BOARD_SIZE - row).toString()
    }

    fun getRow(square: String): Int {
        return BOARD_SIZE - square[1].digitToInt()
    }

    fun getColumn(square: String): Int {
        return square[0].code - 'a'.code
    }

    fun isLightSquare(row: Int, column: Int): Boolean {
        return (row % 2 + column) % 2 == 0
    }
}