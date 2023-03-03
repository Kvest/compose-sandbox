package com.kvest.compose_sandbox.ui.chess

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.VectorConverter
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.detectDragGestures
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
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
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

                pieces.add(pieceToMove.copy(square = square))

                selectedSquare = null
            }
        }
    }

    val onTakePiece: (String) -> Unit = remember {
        { square ->
            selectedSquare = square
        }
    }

    val onReleasePiece: (String) -> Unit = remember {
        { square ->
            if (square != selectedSquare) {
                pieces.removeIf { it.square == square }

                val index = pieces.indexOfFirst { it.square == selectedSquare }
                val pieceToMove = pieces.removeAt(index)

                pieces.add(pieceToMove.copy(square = square))

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
            onTakePiece = onTakePiece,
            onReleasePiece = onReleasePiece,
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
    onTakePiece: (String) -> Unit,
    onReleasePiece: (String) -> Unit,
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
                Piece(
                    piece = piece,
                    chessBoardModel = chessBoardModel,
                    squareSize = squareSize,
                    onTakePiece = onTakePiece,
                    onReleasePiece = onReleasePiece
                )
            }
        }
    }
}

private fun Offset.toIntOffset() = IntOffset(x.roundToInt(), y.roundToInt())

@Composable
fun Piece(
    piece: PieceOnSquare,
    chessBoardModel: ChessBoardModel,
    squareSize: Dp,
    onTakePiece: (String) -> Unit,
    onReleasePiece: (String) -> Unit,
) {
    val squareSizePx = with(LocalDensity.current) {
        squareSize.toPx()
    }

    val row = chessBoardModel.getRow(piece.square)
    val column = chessBoardModel.getColumn(piece.square)

    val x by rememberUpdatedState(column * squareSizePx)
    val y by rememberUpdatedState(row * squareSizePx)

    val offset = remember {
        Animatable(
            Offset(x = column * squareSizePx, y = row * squareSizePx),
            Offset.VectorConverter
        )
    }

    fun calculateSquare(): String {
        val row = ((offset.value.y + squareSizePx / 2) / squareSizePx).toInt()
        val column = ((offset.value.x + squareSizePx / 2) / squareSizePx).toInt()
        return chessBoardModel[row, column]
    }

    LaunchedEffect(key1 = x.roundToInt(), key2 = y.roundToInt()) {
        offset.animateTo(Offset(x, y))
    }

    PieceImage(
        pieceType = piece.pieceType,
        modifier = Modifier
            .offset { offset.value.toIntOffset() }
            .size(squareSize)
            .pointerInput(Unit) {
                coroutineScope {
                    detectDragGestures(
                        onDragStart = {
                            val square = calculateSquare()
                            onTakePiece(square)
                        },
                        onDragEnd = {
                            val square = calculateSquare()
                            onReleasePiece(square)

                            /**
                                Note: This is ugly hack. Animate this piece to it's original position
                                If the position of the piece will be changed by onReleasePiece then recomposion will happen,
                                this animation will be canceled and this piece will be animated to the center of the square.
                                If the move of this piece to the square is impossible - no recomposion will happen and
                                this piece will be moved to the original position.
                                This approach has potential bug - if the new state in onReleasePiece will be calculated too long -
                                the user will see a wrong animation
                             **/
                            //if (square == pieceSquare) {
                            launch {
                                offset.animateTo(Offset(x, y))
                            }
                            //}
                        },
                        onDragCancel = {
                            // Just move piece to it's original position
                            launch {
                                offset.animateTo(Offset(x, y))
                            }
                        }
                    ) { change, dragAmount ->
                        change.consume()

                        launch {
                            offset.snapTo(
                                Offset(
                                    x = offset.value.x + dragAmount.x,
                                    y = offset.value.y + dragAmount.y,
                                )
                            )
                        }
                    }
                }
            }
    )
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
fun PieceImage(pieceType: PieceType, modifier: Modifier = Modifier) {
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