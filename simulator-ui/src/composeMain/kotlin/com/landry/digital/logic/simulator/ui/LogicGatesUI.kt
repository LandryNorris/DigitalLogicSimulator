package com.landry.digital.logic.simulator.ui

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.PointerEventType
import androidx.compose.ui.input.pointer.onPointerEvent
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.landry.digital.engine.component.*
import com.landry.digital.logic.simulator.ui.gates.twoInOneOut4x4Click
import com.landry.digital.logic.simulator.ui.gates.andGateUI

const val stroke = 2f
private val onColor = Color(0x32, 0xCD, 0x32)
private val offColor = Color(0x00, 0x64, 0x00)
val strokeStyle = Stroke(stroke)

fun DrawScope.drawPins(values: List<Boolean>, x: Float, startY: Float, spacing: Float = 0f, radius: Float) {
    var y = startY
    for(i in values.indices) {
        val value = values[i]
        val color = if(value) onColor else offColor
        drawCircle(color = color, radius = radius, style = strokeStyle,
            center = Offset(x, y))
        y += spacing
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun Gate.draw(gridSize: Dp = 10.dp,
              onInputClicked: (Int) -> Unit = {},
              onOutputClicked: (Int) -> Unit = {}) {
    val size = size()
    Canvas(
        modifier = Modifier
            .width(gridSize*size.first)
            .height(gridSize*size.second)
            .offset((gridSize*x), (gridSize*y))
            .onPointerEvent(PointerEventType.Press) {
                when(gate) {
                    is AndGate -> twoInOneOut4x4Click(it, gridSize.roundToPx(), onInputClicked, onOutputClicked)
                }
            }) {
        when(gate) {
            is AndGate -> andGateUI(gate)
            is NandGate -> nandGateUI(gate)
            is XorGate -> xorGateUI(gate)
            is OrGate -> orGateUI(gate)
            is NorGate -> norGateUI(gate)
            is Inverter -> inverterUI(gate)
            is Buffer -> bufferUI(gate)
        }
    }
}

fun DrawScope.nandGateUI(gate: NandGate) {
    val width = this.size.width
    val height = this.size.height
    val gridSize = height/4

    val startX = 0f
    val startY = 0f
    val arcEndX = width*7/10
    val conversionX = arcEndX/2

    drawLine(Color.Black,
        start = Offset(startX, startY),
        end = Offset(startX, height),
        strokeWidth = stroke)

    drawLine(Color.Black,
        start = Offset(startX, startY),
        end = Offset(conversionX, startY),
        strokeWidth = stroke)

    drawLine(color = Color.Black,
        start = Offset(startX, height),
        end = Offset(conversionX, height),
        strokeWidth = stroke)

    val arcWidth = (arcEndX - conversionX)*2
    drawArc(color = Color.Black,
        startAngle = -90f,
        sweepAngle = 180f,
        useCenter = false,
        topLeft = Offset(conversionX - arcWidth / 2, startY),
        size = Size(arcWidth, height),
        style = strokeStyle
    )

    drawCircle(color = Color.Black,
        radius = (width - arcEndX) / 2, center = Offset(width - (width - arcEndX) / 2, height / 2),
        style = Stroke(width = stroke)
    )

    drawPins(gate.inputs.map { it.state }, startX, startY+gridSize, gridSize*2, radius = height/10)
    drawPins(gate.outputs.map { it.state }, width, height / 2, radius = height / 10)
}

fun DrawScope.orGateUI(gate: LogicGate) {
    val width = this.size.width
    val height = this.size.height
    val gridSize = height/4

    val startX = 0f
    val startY = 0f
    val endX = width*9/10
    val conversionX = width/2

    val orArcWidth = width/5
    drawArc(color = Color.Black,
        startAngle = -90f,
        sweepAngle = 180f,
        useCenter = false,
        topLeft = Offset(startX - orArcWidth/2, startY),
        size = Size(orArcWidth, height),
        style = strokeStyle
    )

    drawLine(Color.Black,
        start = Offset(startX, startY),
        end = Offset(conversionX, startY),
        strokeWidth = stroke)

    drawLine(color = Color.Black,
        start = Offset(startX, height),
        end = Offset(conversionX, height),
        strokeWidth = stroke)

    val arcWidth = (endX - conversionX)*2
    drawArc(color = Color.Black,
        startAngle = -90f,
        sweepAngle = 180f,
        useCenter = false,
        topLeft = Offset(conversionX - arcWidth / 2, startY),
        size = Size(arcWidth, height),
        style = strokeStyle
    )

    drawPins(gate.inputs.map { it.state }, startX, startY+gridSize, gridSize*2, radius = height/10)
    drawPins(gate.outputs.map { it.state }, endX, height/2, radius = height/10)
}

fun DrawScope.norGateUI(gate: NorGate) {
    val width = this.size.width
    val height = this.size.height
    val gridSize = height/4

    val startX = 0f
    val startY = 0f
    val arcEndX = width*7/10
    val conversionX = arcEndX/2

    val orArcWidth = width/5
    drawArc(color = Color.Black,
        startAngle = -90f,
        sweepAngle = 180f,
        useCenter = false,
        topLeft = Offset(startX - orArcWidth/2, startY),
        size = Size(orArcWidth, height),
        style = strokeStyle
    )

    drawLine(Color.Black,
        start = Offset(startX, startY),
        end = Offset(conversionX, startY),
        strokeWidth = stroke)

    drawLine(color = Color.Black,
        start = Offset(startX, height),
        end = Offset(conversionX, height),
        strokeWidth = stroke)

    val arcWidth = (arcEndX - conversionX)*2
    drawArc(color = Color.Black,
        startAngle = -90f,
        sweepAngle = 180f,
        useCenter = false,
        topLeft = Offset(conversionX - arcWidth / 2, startY),
        size = Size(arcWidth, height),
        style = strokeStyle
    )

    drawCircle(color = Color.Black,
        radius = (width - arcEndX) / 2, center = Offset(width - (width - arcEndX) / 2, height / 2),
        style = Stroke(width = stroke)
    )

    drawPins(gate.inputs.map { it.state }, startX, startY+gridSize, gridSize*2, radius = height/10)
    drawPins(gate.outputs.map { it.state }, width, height / 2, radius = height / 10)
}

fun DrawScope.xorGateUI(gate: LogicGate) {
    val width = this.size.width
    val height = this.size.height
    val gridSize = height/4

    val startX = width/6f
    val startY = 0f
    val endX = width*9/10
    val conversionX = width/2

    val orArcWidth = width/5
    drawArc(color = Color.Black,
        startAngle = -90f,
        sweepAngle = 180f,
        useCenter = false,
        topLeft = Offset(-orArcWidth/2, startY),
        size = Size(orArcWidth, height),
        style = strokeStyle
    )

    drawArc(color = Color.Black,
        startAngle = -90f,
        sweepAngle = 180f,
        useCenter = false,
        topLeft = Offset(startX-orArcWidth/2, startY),
        size = Size(orArcWidth, height),
        style = strokeStyle
    )

    drawLine(Color.Black,
        start = Offset(startX, startY),
        end = Offset(conversionX, startY),
        strokeWidth = stroke)

    drawLine(color = Color.Black,
        start = Offset(startX, height),
        end = Offset(conversionX, height),
        strokeWidth = stroke)

    val arcWidth = (endX - conversionX)*2
    drawArc(color = Color.Black,
        startAngle = -90f,
        sweepAngle = 180f,
        useCenter = false,
        topLeft = Offset(conversionX - arcWidth / 2, startY),
        size = Size(arcWidth, height),
        style = strokeStyle
    )

    drawPins(gate.inputs.map { it.state }, 0f, startY+gridSize, gridSize*2, radius = height/10)
    drawPins(gate.outputs.map { it.state }, endX, height/2, radius = height/10)
}

fun DrawScope.inverterUI(gate: Inverter) {
    val width = this.size.width
    val height = this.size.height
    val conversionX = width*8/10

    drawLine(Color.Black, start = Offset(0f, 0f), end = Offset(0f, height), strokeWidth = stroke)
    drawLine(Color.Black, start = Offset(0f, 0f), end = Offset(conversionX, height/2), strokeWidth = stroke)
    drawLine(Color.Black, start = Offset(0f, height), end = Offset(conversionX, height/2), strokeWidth = stroke)

    val r = (width-conversionX)/2
    drawCircle(Color.Black, center = Offset(width-r, height/2), radius = r, style = strokeStyle)

    drawPins(gate.inputs.map { it.state }, 0f, height/2, radius = height/7)
    drawPins(gate.outputs.map { it.state }, width, height/2, radius = height/7)
}

fun DrawScope.bufferUI(gate: Buffer) {
    val width = this.size.width
    val height = this.size.height

    drawLine(Color.Black, start = Offset(0f, 0f), end = Offset(0f, height), strokeWidth = stroke)
    drawLine(Color.Black, start = Offset(0f, 0f), end = Offset(width, height/2), strokeWidth = stroke)
    drawLine(Color.Black, start = Offset(0f, height), end = Offset(width, height/2), strokeWidth = stroke)

    drawPins(gate.inputs.map { it.state }, 0f, height/2, radius = height/7)
    drawPins(gate.outputs.map { it.state }, width, height/2, radius = height/7)
}
