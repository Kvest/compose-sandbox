package com.kvest.compose_sandbox.ui.spline.processor

import androidx.compose.ui.geometry.Offset
import com.kvest.compose_sandbox.ui.spline.curve.BezierCurve
import kotlin.math.pow
import kotlin.math.sqrt

private const val MIN_POINT_DISTANCE = 25f

abstract class BezierCurveProcessor {
    private var draggedPointIndex = -1
    protected val points = mutableListOf<Offset>()

    abstract fun addPoint(point: Offset)
    abstract fun getCurves(): List<BezierCurve>
    abstract fun onDrag(pointIndex: Int, dragAmount: Offset)

    fun onDragStart(position: Offset, screenDensity: Float) {
        var pointIndex = -1
        var minDistance = Float.MAX_VALUE

        points.forEachIndexed { index, point ->
            val distance = point.distanceTo(position)

            if (distance < (MIN_POINT_DISTANCE * screenDensity) && distance <= minDistance) {
                minDistance = distance
                pointIndex = index
            }
        }

        draggedPointIndex = pointIndex
    }

    fun consumeDrag(dragAmount: Offset): Boolean {
        if (draggedPointIndex == -1) {
            return false
        }

        onDrag(draggedPointIndex, dragAmount)
        return true
    }

    fun onDragEnd() {
        draggedPointIndex = -1
    }
}

private fun Offset.distanceTo(other: Offset): Float = sqrt((x - other.x).pow(2) + (y - other.y).pow(2))