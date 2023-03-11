package com.kvest.compose_sandbox.ui.spline.curve

import androidx.compose.ui.geometry.Offset
import kotlin.math.pow

class QuadraticBezierCurve(
    private val p0: Offset,
    private val p1: Offset,
    private val p2: Offset,
) : BezierCurve {
    override fun iteratePoints(action: (Offset) -> Unit) {
        action(p0)
        action(p1)
        action(p2)
    }

    override fun iterateTangent(action: (start: Offset, end: Offset) -> Unit) {
        action(p0, p1)
        action(p1, p2)
    }

    override fun lerp(t: Float): Offset {
        return Offset(
            x = (1f - t).pow(2) * p0.x + 2 * (1f - t) * t * p1.x + t.pow(2) * p2.x,
            y = (1f - t).pow(2) * p0.y + 2 * (1f - t) * t * p1.y + t.pow(2) * p2.y,
        )
    }
}