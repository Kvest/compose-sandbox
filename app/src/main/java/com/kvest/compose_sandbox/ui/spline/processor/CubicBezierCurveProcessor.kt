package com.kvest.compose_sandbox.ui.spline.processor

import androidx.compose.ui.geometry.Offset
import com.kvest.compose_sandbox.ui.spline.curve.BezierCurve
import com.kvest.compose_sandbox.ui.spline.curve.CubicBezierCurve

class CubicBezierCurveProcessor : BezierCurveProcessor() {
    override fun addPoint(point: Offset) {
        when (points.size) {
            0 -> points.add(point)
            1 -> {
                val p0 = points.last()
                val p1 = p0 + (point - p0) / 3f
                val p2 = point - (point - p0) / 3f

                points.add(p1)
                points.add(p2)
                points.add(point)
            }
            else -> {
                val p0 = points.last()

                val p1 = p0 + (p0 - points[points.lastIndex - 1])
                val p2 = point - (point - p0) / 3f

                points.add(p1)
                points.add(p2)
                points.add(point)
            }
        }
    }

    override fun getCurves(): List<BezierCurve> {
        return when (points.size) {
            0 -> emptyList()
            1 -> listOf(CubicBezierCurve(points[0], points[0], points[0], points[0])) //We need to draw a point
            else -> points.windowed(size = 4, step = 3, partialWindows = false)
                .map { (p0, p1, p2, p3) -> CubicBezierCurve(p0, p1, p2, p3) }
        }
    }

    override fun onDrag(pointIndex: Int, dragAmount: Offset) {
        points[pointIndex] += dragAmount

        if (pointIndex % 3 == 0) {
            if (pointIndex > 0) {
                points[pointIndex - 1] += dragAmount
            }
            if (pointIndex < points.lastIndex) {
                points[pointIndex + 1] += dragAmount
            }
        } else {
            val mirrorControlPointIndex = if ((pointIndex + 1) % 3 == 0) pointIndex + 2 else pointIndex - 2
            val basePointIndex = if ((pointIndex + 1) % 3 == 0) pointIndex + 1 else pointIndex - 1

            if (mirrorControlPointIndex in points.indices) {
                points[mirrorControlPointIndex] = points[basePointIndex] + (points[basePointIndex] - points[pointIndex])
            }
        }
    }
}