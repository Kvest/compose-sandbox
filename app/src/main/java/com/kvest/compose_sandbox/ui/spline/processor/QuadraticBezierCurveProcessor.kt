package com.kvest.compose_sandbox.ui.spline.processor

import androidx.compose.ui.geometry.Offset
import com.kvest.compose_sandbox.ui.spline.curve.BezierCurve
import com.kvest.compose_sandbox.ui.spline.curve.QuadraticBezierCurve

class QuadraticBezierCurveProcessor : BezierCurveProcessor() {
    override fun addPoint(point: Offset) {
        when (points.size) {
            0 -> points.add(point)
            1 -> {
                val middle = points.last() + (point - points.last()) / 2f

                points.add(middle)
                points.add(point)
            }
            else -> {
                val mirroredPoint = points.last() + (points.last() - points[points.lastIndex - 1])

                points.add(mirroredPoint)
                points.add(point)
            }
        }
    }

    override fun getCurves(): List<BezierCurve> {
        return when (points.size) {
            0 -> emptyList()
            1 -> listOf(QuadraticBezierCurve(points[0], points[0], points[0])) //We need to draw a point
            else -> points.windowed(size = 3, step = 2, partialWindows = false)
                .map { (p0, p1, p2) -> QuadraticBezierCurve(p0, p1, p2) }
        }
    }

    override fun onDrag(pointIndex: Int, dragAmount: Offset) {
        points[pointIndex] += dragAmount
    }
}