package com.kvest.compose_sandbox.ui.spline.curve

import androidx.compose.ui.geometry.Offset

interface BezierCurve {
    fun iteratePoints(action: (Offset) -> Unit)
    fun iterateTangent(action: (start: Offset, end: Offset) -> Unit)
    fun lerp(t: Float): Offset
}