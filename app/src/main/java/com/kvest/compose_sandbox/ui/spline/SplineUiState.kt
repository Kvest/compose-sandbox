package com.kvest.compose_sandbox.ui.spline

import androidx.compose.ui.geometry.Offset
import com.kvest.compose_sandbox.ui.spline.curve.BezierCurve

data class SplineUiState(
    val curves: List<BezierCurve>,
    //screen transformation
    val scale: Float,
    val translation: Offset,
)
