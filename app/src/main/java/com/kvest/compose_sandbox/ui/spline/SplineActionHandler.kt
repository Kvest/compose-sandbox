package com.kvest.compose_sandbox.ui.spline

import androidx.compose.ui.geometry.Offset

interface SplineActionHandler {
    fun onAddPoint(point: Offset)

    fun onDragStart(position: Offset, screenDensity: Float)
    fun onDrag(dragAmount: Offset)
    fun onDragEnd()

    fun onScale(scaleFactor: Float)
}