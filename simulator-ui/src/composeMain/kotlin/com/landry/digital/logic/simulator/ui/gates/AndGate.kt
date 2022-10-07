package com.landry.digital.logic.simulator.ui.gates

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.input.pointer.PointerEvent
import com.landry.digital.engine.component.AndGate
import com.landry.digital.logic.simulator.ui.drawPins
import com.landry.digital.logic.simulator.ui.stroke
import com.landry.digital.logic.simulator.ui.strokeStyle

fun DrawScope.andGateUI(gate: AndGate) {
    val width = this.size.width
    val height = this.size.height
    val gridSize = height/4

    val startX = 0f
    val startY = 0f
    val conversionX = width/2

    drawLine(
        Color.Black,
        start = Offset(startX, startY),
        end = Offset(startX, height),
        strokeWidth = stroke
    )

    drawLine(
        Color.Black,
        start = Offset(startX, startY),
        end = Offset(conversionX, startY),
        strokeWidth = stroke
    )

    drawLine(color = Color.Black,
        start = Offset(startX, height),
        end = Offset(conversionX, height),
        strokeWidth = stroke
    )

    val arcWidth = (width - conversionX) * 2
    drawArc(color = Color.Black,
        startAngle = -90f,
        sweepAngle = 180f,
        useCenter = false,
        topLeft = Offset(conversionX - arcWidth / 2, startY),
        size = Size(arcWidth, height),
        style = strokeStyle
    )

    drawPins(gate.inputs.map { it.state }, startX, startY + gridSize, gridSize*2, radius = height/10)
    drawPins(gate.outputs.map { it.state }, width, height / 2, radius = height / 10)
}
