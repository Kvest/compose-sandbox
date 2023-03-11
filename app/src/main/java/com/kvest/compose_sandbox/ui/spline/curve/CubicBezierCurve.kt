package com.kvest.compose_sandbox.ui.spline.curve

import androidx.compose.ui.geometry.Offset
import kotlin.math.pow

class CubicBezierCurve(
    private val p0: Offset,
    private val p1: Offset,
    private val p2: Offset,
    private val p3: Offset,
) : BezierCurve {
    override fun iteratePoints(action: (Offset) -> Unit) {
        action(p0)
        action(p1)
        action(p2)
        action(p3)
    }

    override fun iterateTangent(action: (start: Offset, end: Offset) -> Unit) {
        action(p0, p1)
        action(p2, p3)
    }

    override fun lerp(t: Float): Offset {
        return Offset(
            x = (1f - t).pow(3) * p0.x + 3 * (1f - t).pow(2) * t * p1.x +
                    3 * (1f - t) * t.pow(2) * p2.x + t.pow(3) * p3.x,
            y = (1f - t).pow(3) * p0.y + 3 * (1f - t).pow(2) * t * p1.y +
                    3 * (1f - t) * t.pow(2) * p2.y + t.pow(3) * p3.y,
        )
    }
}