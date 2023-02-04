package com.kvest.compose_sandbox.ui.chess

import androidx.compose.animation.core.animateIntOffsetAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.kvest.compose_sandbox.R
import com.kvest.compose_sandbox.ui.theme.Copper
import kotlin.math.roundToInt

@Composable
fun ChessBoardDemo() {
    val chessBoardModel = remember { ChessBoardModel() }
    val pieces = remember {
        mutableStateListOf(
            PieceOnSquare(1, PieceType.PAWN_DARK, "a7"),
            PieceOnSquare(2, PieceType.PAWN_DARK, "b7"),
            PieceOnSquare(3, PieceType.PAWN_DARK, "c7"),
            PieceOnSquare(4, PieceType.PAWN_DARK, "e3"),
            PieceOnSquare(5, PieceType.PAWN_DARK, "d4"),
            PieceOnSquare(6, PieceType.PAWN_DARK, "h1"),
        )
    }
    var selectedSquare by remember { mutableStateOf<String?>(null) }

    val onSquareClicked: (String) -> Unit = remember {
        { square ->
            if (selectedSquare == null) {
                if (pieces.any { it.square == square }) {
                    selectedSquare = square
                }
            } else if (selectedSquare == square) {
                selectedSquare = null
            } else {
                pieces.removeIf { it.square == square }

                val index = pieces.indexOfFirst { it.square == selectedSquare }
                val pieceToMove = pieces.removeAt(index)

                pieces.add( pieceToMove.copy(square = square))

                selectedSquare = null
            }
        }
    }

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        ChessBoard(
            chessBoardModel = chessBoardModel,
            pieces = pieces,
            selectedSquare = selectedSquare,
            squareSize = 48.dp,
            onSquareClicked = onSquareClicked,
            modifier = Modifier.align(Alignment.Center)
        )
    }
}

@Composable
fun ChessBoard(
    chessBoardModel: ChessBoardModel,
    pieces: List<PieceOnSquare>,
    selectedSquare: String?,
    squareSize: Dp,
    onSquareClicked: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val squareSizePx = with(LocalDensity.current) {
        squareSize.toPx()
    }

    Box(
        modifier = modifier
            .size(squareSize * 8)
            .drawWithCache {
                val size = Size(squareSizePx, squareSizePx)

                onDrawBehind {
                    drawSquares(chessBoardModel, squareSizePx, size)
                    drawSelectedSquare(chessBoardModel, selectedSquare, squareSizePx, size)
                }
            }
            .border(2.dp, color = Color.Black)
            .pointerInput(Unit) {
                detectTapGestures { offset ->
                    val row = (offset.y / squareSizePx).toInt()
                    val column = (offset.x / squareSizePx).toInt()
                    val square = chessBoardModel[row, column]
                    onSquareClicked(square)
                }
            }
    ) {
        pieces.forEach { piece ->
            key(piece.id) {
                val row = chessBoardModel.getRow(piece.square)
                val column = chessBoardModel.getColumn(piece.square)

                val offset = animateIntOffsetAsState(
                    targetValue = IntOffset(
                        x = (column * squareSizePx).roundToInt(),
                        y = (row * squareSizePx).roundToInt(),
                    )
                )

                Piece(
                    pieceType = piece.pieceType,
                    modifier = Modifier
                        .offset { offset.value }
                        .size(squareSize)
                )
            }
        }
    }
}

private fun DrawScope.drawSquares(
    chessBoardModel: ChessBoardModel,
    squareSizePx: Float,
    size: Size
) {
    for (row in 0 until chessBoardModel.size) {
        for (column in 0 until chessBoardModel.size) {
            drawRect(
                color = if (chessBoardModel.isLightSquare(row, column)) Color.White else Copper,
                topLeft = Offset(x = column * squareSizePx, y = row * squareSizePx),
                size = size
            )
        }
    }
}

private fun DrawScope.drawSelectedSquare(
    chessBoardModel: ChessBoardModel,
    selectedSquare: String?,
    squareSizePx: Float,
    size: Size,
) {
    if (selectedSquare != null) {
        val row = chessBoardModel.getRow(selectedSquare)
        val column = chessBoardModel.getColumn(selectedSquare)

        drawRect(
            color = Color.Yellow.copy(alpha = 0.6f),
            topLeft = Offset(x = column * squareSizePx, y = row * squareSizePx),
            size = size
        )
    }
}

@Composable
fun Piece(pieceType: PieceType, modifier: Modifier = Modifier) {
    Image(
        modifier = modifier,
        contentScale = ContentScale.Fit,
        painter = painterResource(getPieceTypeImageRes(pieceType)),
        contentDescription = getPieceTypeDescriptionRes(pieceType)
    )
}

private fun getPieceTypeImageRes(pieceType: PieceType): Int =
    when (pieceType) {
        PieceType.PAWN_DARK -> R.drawable.pawn_dark
    }

private fun getPieceTypeDescriptionRes(pieceType: PieceType): String =
    when (pieceType) {
        PieceType.PAWN_DARK -> "Dark pawn"
    }