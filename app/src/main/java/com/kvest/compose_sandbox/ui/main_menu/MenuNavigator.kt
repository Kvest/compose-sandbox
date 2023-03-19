package com.kvest.compose_sandbox.ui.main_menu

import com.kvest.compose_sandbox.ui.spline.BezierCurveType

interface MenuNavigator {
    fun showSpline(curveType: BezierCurveType)
    fun showChess()
    fun showAnimatedCounter()
    fun showCustomModifier()
}