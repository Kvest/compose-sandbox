package com.kvest.compose_sandbox.ui.spline

import androidx.compose.ui.geometry.Offset
import androidx.lifecycle.ViewModel
import com.kvest.compose_sandbox.ui.spline.processor.BezierCurveProcessor
import com.kvest.compose_sandbox.ui.spline.processor.CubicBezierCurveProcessor
import com.kvest.compose_sandbox.ui.spline.processor.QuadraticBezierCurveProcessor
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class SplineViewModel(curveType: BezierCurveType) : ViewModel(), SplineActionHandler {
    private val processor: BezierCurveProcessor = when (curveType) {
        BezierCurveType.QUADRATIC -> QuadraticBezierCurveProcessor()
        BezierCurveType.CUBIC -> CubicBezierCurveProcessor()
    }

    private var scale = 1f
    private var translation = Offset.Zero

    private val _uiState = MutableStateFlow(
        SplineUiState(
            curves = emptyList(),
            scale = scale,
            translation = translation,
        )
    )
    val uiState: StateFlow<SplineUiState> = _uiState.asStateFlow()

    override fun onAddPoint(originalOffset: Offset) {
        // originalOffset is an Offset with transformation. Convert to actual point
        val newPoint = (originalOffset - translation) / scale

        processor.addPoint(newPoint)

        _uiState.update { oldUiState ->
            oldUiState.copy(curves = processor.getCurves())
        }
    }

    override fun onDragStart(position: Offset, screenDensity: Float) {
        val decodedPosition = (position - translation) / scale

        processor.onDragStart(decodedPosition, screenDensity)
    }

    override fun onDrag(dragAmount: Offset) {
        val scaledDragAmount = dragAmount / scale

        if (processor.consumeDrag(scaledDragAmount)) {
            _uiState.update { oldUiState ->
                oldUiState.copy(curves = processor.getCurves())
            }
        } else {
            translation += dragAmount

            _uiState.update { oldUiState ->
                oldUiState.copy(translation = translation)
            }
        }
    }

    override fun onDragEnd() {
        processor.onDragEnd()
    }

    override fun onScale(scaleFactor: Float) {
        scale *= scaleFactor

        _uiState.update { oldUiState ->
            oldUiState.copy(scale = scale)
        }
    }
}