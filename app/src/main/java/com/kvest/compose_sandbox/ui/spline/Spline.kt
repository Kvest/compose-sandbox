package com.kvest.compose_sandbox.ui.spline

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.calculateZoom
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.lifecycle.viewmodel.viewModelFactory
import kotlin.math.abs

private const val CURVE_DRAW_STEP = 0.01f

@Composable
fun Spline(
    curveType: BezierCurveType,
    modifier: Modifier = Modifier,
    splineViewModel: SplineViewModel = viewModel(factory = viewModelFactory {
        addInitializer(SplineViewModel::class) {
            SplineViewModel(curveType = curveType)
        }
    })
) {
    val splineUiState by splineViewModel.uiState.collectAsStateWithLifecycle()

    Spline(
        splineUiState = splineUiState,
        actionHandler = splineViewModel,
        modifier = modifier
    )
}

@Composable
fun Spline(
    splineUiState: SplineUiState,
    actionHandler: SplineActionHandler,
    modifier: Modifier = Modifier
) {
    val currentActionHandler by rememberUpdatedState(actionHandler)

    Canvas(
        modifier = modifier
            .pointerInput(Unit) {
                detectTapGestures(onTap = { currentActionHandler.onAddPoint(it) })
            }
            .pointerInput(Unit) {
                awaitPointerEventScope {
                    while (true) {
                        val event = awaitPointerEvent()
                        val zoom = event.calculateZoom()

                        if (abs(zoom - 1f) > 0.01f) {
                            currentActionHandler.onScale(zoom)

                            event.changes.forEach { it.consume() }
                        }
                    }
                }
            }
            .pointerInput(Unit) {
                detectDragGestures(
                    onDragStart = { currentActionHandler.onDragStart(it, 1.dp.toPx()) },
                    onDragEnd = { currentActionHandler.onDragEnd() },
                    onDragCancel = { currentActionHandler.onDragEnd() },
                ) { change, dragAmount ->
                    change.consume()

                    currentActionHandler.onDrag(dragAmount)
                }
            }
            .graphicsLayer(
                scaleX = splineUiState.scale,
                scaleY = splineUiState.scale,
                translationX = splineUiState.translation.x,
                translationY = splineUiState.translation.y,
                transformOrigin = TransformOrigin(0f, 0f)
            ),
        onDraw = {
            splineUiState.curves.forEach { curve ->
                curve.iteratePoints {
                    drawCircle(Color.Green, 5.dp.toPx(), it)
                }

                curve.iterateTangent { start, end ->
                    drawLine(
                        color = Color.LightGray,
                        start = start,
                        end = end,
                        strokeWidth = 1.dp.toPx(),
                    )
                }
            }

            splineUiState.curves.forEach { curve ->
                var t = CURVE_DRAW_STEP
                var start = curve.lerp(0f)

                while (t <= 1) {
                    val end = curve.lerp(t)

                    drawLine(
                        color = Color.Blue,
                        start = start,
                        end = end,
                        strokeWidth = 2.dp.toPx(),
                    )

                    start = end
                    t += CURVE_DRAW_STEP
                }
            }
        })
}