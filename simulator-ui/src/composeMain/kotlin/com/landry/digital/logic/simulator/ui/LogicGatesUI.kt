package com.landry.digital.logic.simulator.ui

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.landry.digital.engine.component.*

private const val stroke = 2f
private val onColor = Color(0x32, 0xCD, 0x32)
private val offColor = Color(0x00, 0x64, 0x00)

fun DrawScope.drawPins(values: List<Boolean>, x: Float, startY: Float, endY: Float, radius: Float) {
    for(i in values.indices) {
        val value = values[i]
        val color = if(value) onColor else offColor
        drawCircle(color = color, radius = radius, style = Stroke(stroke),
            center = Offset(x, (endY-startY)*(i+1)/(values.size+1)))
    }
}

@Composable
fun Gate.draw(gridSize: Dp = 10.dp) {
    val size = size()
    Canvas(modifier = Modifier.width(gridSize*size.first).height(gridSize*size.second)
        .offset((gridSize*x), (gridSize*y))) {
        when(gate) {
            is AndGate -> andGateUI(gate)
            is NandGate -> nandGateUI(gate)
            is XorGate -> xorGateUI(gate)
            is OrGate -> orGateUI(gate)
        }
    }
}

fun DrawScope.andGateUI(gate: AndGate) {
    val width = this.size.width
    val height = this.size.height

    val startX = 0f
    val startY = 0f
    val endX = width
    val endY = height
    val conversionX = width/2

    drawLine(Color.Black,
        start = Offset(startX, startY),
        end = Offset(startX, endY),
        strokeWidth = stroke)

    drawLine(Color.Black,
        start = Offset(startX, startY),
        end = Offset(conversionX, startY),
        strokeWidth = stroke)

    drawLine(color = Color.Black,
        start = Offset(startX, endY),
        end = Offset(conversionX, endY),
        strokeWidth = stroke)

    val arcWidth = (endX - conversionX)*2
    drawArc(color = Color.Black,
        startAngle = -90f,
        sweepAngle = 180f,
        useCenter = false,
        topLeft = Offset(conversionX - arcWidth / 2, startY),
        size = Size(arcWidth, height),
        style = Stroke(width = stroke, cap = StrokeCap.Round)
    )

    drawPins(gate.inputs.map { it.state }, startX, startY, endY, radius = height/10)
    drawPins(gate.outputs.map { it.state }, endX, startY, endY, radius = height/10)
}

fun DrawScope.nandGateUI(gate: NandGate) {
    val width = this.size.width
    val height = this.size.height

    val startX = 0f
    val startY = 0f
    val endX = width
    val endY = height
    val arcEndX = width*7/10
    val conversionX = arcEndX/2

    drawLine(Color.Black,
        start = Offset(startX, startY),
        end = Offset(startX, endY),
        strokeWidth = stroke)

    drawLine(Color.Black,
        start = Offset(startX, startY),
        end = Offset(conversionX, startY),
        strokeWidth = stroke)

    drawLine(color = Color.Black,
        start = Offset(startX, endY),
        end = Offset(conversionX, endY),
        strokeWidth = stroke)

    val arcWidth = (arcEndX - conversionX)*2
    drawArc(color = Color.Black,
        startAngle = -90f,
        sweepAngle = 180f,
        useCenter = false,
        topLeft = Offset(conversionX - arcWidth / 2, startY),
        size = Size(arcWidth, height),
        style = Stroke(width = stroke, cap = StrokeCap.Round)
    )

    drawCircle(color = Color.Black,
        radius = (endX - arcEndX)/2, center = Offset(endX - (endX - arcEndX)/2, height/2),
        style = Stroke(width = stroke)
    )

    drawPins(gate.inputs.map { it.state }, startX, startY, endY, radius = height/10)
    drawPins(gate.outputs.map { it.state }, endX, startY, endY, radius = height/10)
}

fun DrawScope.orGateUI(gate: LogicGate, size: Dp = 40.dp) {
    val width = this.size.width
    val height = this.size.height

    val startX = 0f
    val startY = 0f
    val inputX = width/10
    val endX = width*9/10
    val endY = height
    val conversionX = width/2

    val orArcWidth = width/5
    drawArc(color = Color.Black,
        startAngle = -90f,
        sweepAngle = 180f,
        useCenter = false,
        topLeft = Offset(startX - orArcWidth/2, startY),
        size = Size(orArcWidth, height),
        style = Stroke(width = stroke, cap = StrokeCap.Round)
    )

    drawLine(Color.Black,
        start = Offset(startX, startY),
        end = Offset(conversionX, startY),
        strokeWidth = stroke)

    drawLine(color = Color.Black,
        start = Offset(startX, endY),
        end = Offset(conversionX, endY),
        strokeWidth = stroke)

    val arcWidth = (endX - conversionX)*2
    drawArc(color = Color.Black,
        startAngle = -90f,
        sweepAngle = 180f,
        useCenter = false,
        topLeft = Offset(conversionX - arcWidth / 2, startY),
        size = Size(arcWidth, height),
        style = Stroke(width = stroke, cap = StrokeCap.Round)
    )

    drawPins(gate.inputs.map { it.state }, inputX, startY, endY, radius = height/10)
    drawPins(gate.outputs.map { it.state }, endX, startY, endY, radius = height/10)
}

fun DrawScope.xorGateUI(gate: LogicGate, size: Dp = 40.dp) {
    val width = this.size.width
    val height = this.size.height

    val startX = width/6f
    val startY = 0f
    val endX = width*9/10
    val endY = height
    val conversionX = width/2
    val inputX = width/10

    val orArcWidth = width/5
    drawArc(color = Color.Black,
        startAngle = -90f,
        sweepAngle = 180f,
        useCenter = false,
        topLeft = Offset(-orArcWidth/2, startY),
        size = Size(orArcWidth, height),
        style = Stroke(width = stroke, cap = StrokeCap.Round)
    )

    drawArc(color = Color.Black,
        startAngle = -90f,
        sweepAngle = 180f,
        useCenter = false,
        topLeft = Offset(startX-orArcWidth/2, startY),
        size = Size(orArcWidth, height),
        style = Stroke(width = stroke, cap = StrokeCap.Round)
    )

    drawLine(Color.Black,
        start = Offset(startX, startY),
        end = Offset(conversionX, startY),
        strokeWidth = stroke)

    drawLine(color = Color.Black,
        start = Offset(startX, endY),
        end = Offset(conversionX, endY),
        strokeWidth = stroke)

    val arcWidth = (endX - conversionX)*2
    drawArc(color = Color.Black,
        startAngle = -90f,
        sweepAngle = 180f,
        useCenter = false,
        topLeft = Offset(conversionX - arcWidth / 2, startY),
        size = Size(arcWidth, height),
        style = Stroke(width = stroke, cap = StrokeCap.Round)
    )

    drawPins(gate.inputs.map { it.state }, inputX, startY, endY, radius = height/10)
    drawPins(gate.outputs.map { it.state }, endX, startY, endY, radius = height/10)
}
