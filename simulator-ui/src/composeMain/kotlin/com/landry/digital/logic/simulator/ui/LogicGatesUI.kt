package com.landry.digital.logic.simulator.ui

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.drawOutline
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.DrawStyle
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.TextMeasurer
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.drawText
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.landry.digital.engine.component.*
import com.landry.digital.engine.ui.GateUIProperties
import com.landry.digital.engine.ui.GateUIState
import com.landry.digital.engine.ui.PinUIState
import com.landry.digital.engine.ui.Position

private const val stroke = 2f
private val onColor = Color(0x32, 0xCD, 0x32)
private val offColor = Color(0x00, 0x64, 0x00)
val strokeStyle = Stroke(stroke)

private val textStyle = TextStyle()

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

fun DrawScope.drawPins(gatePosition: Position, gridSize: Float, pins: List<PinUIState>, radius: Float) {
    for(pin in pins) {
        val x = pin.position.x - gatePosition.x
        val y = pin.position.y - gatePosition.y
        val color = if(pin.state) onColor else offColor
        drawCircle(color = color, radius = radius, style = strokeStyle,
            center = Offset(gridSize*x, gridSize*y))
    }
}

@Composable
fun GateUIState.draw(gridSize: Dp = 10.dp, offsetX: Dp = 0.dp, offsetY: Dp = 0.dp) {
    val gate = this
    val size = gateSize
    val position = gatePosition

    Box(modifier = Modifier.width(gridSize*size.width).height(gridSize*size.height)
        .offset((gridSize*position.x) + offsetX, (gridSize*position.y) + offsetY)) {
        Canvas(modifier = Modifier.matchParentSize()) {
            when(type) {
                AndGate::class -> andGateUI(gate)
                NandGate::class -> nandGateUI(gate)
                XorGate::class -> xorGateUI(gate)
                OrGate::class -> orGateUI(gate)
                NorGate::class -> norGateUI(gate)
                Inverter::class -> inverterUI(gate)
                Buffer::class -> bufferUI(gate)
                Switch::class -> switch(gate)
            }
        }
        // Switches have text
        if(type == Switch::class) {
            val text = if(gate.outputPins[0].state) "1" else "0"
            Text(modifier = Modifier.matchParentSize()
                .wrapContentHeight(align = Alignment.CenterVertically),
                text = text,
                // TODO(Landry): Is there a better way to convert dp to a text unit?
                fontSize = gridSize.value.sp,
                textAlign = TextAlign.Center)
        }
    }
}

fun DrawScope.andGateUI(gate: GateUIState) {
    val width = this.size.width
    val height = this.size.height
    val gridSize = height/gate.gateSize.height

    val startX = 0f
    val startY = 0f
    val conversionX = width/2

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

    val arcWidth = (width - conversionX) * 2
    drawArc(color = Color.Black,
        startAngle = -90f,
        sweepAngle = 180f,
        useCenter = false,
        topLeft = Offset(conversionX - arcWidth / 2, startY),
        size = Size(arcWidth, height),
        style = strokeStyle
    )

    drawPins(gate.gatePosition, gridSize, gate.inputPins, radius = height/10)
    drawPins(gate.gatePosition, gridSize, gate.outputPins, radius = height / 10)
}

fun DrawScope.nandGateUI(gate: GateUIState) {
    val width = this.size.width
    val height = this.size.height
    val gridSize = height/gate.gateSize.height

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

    drawPins(gate.gatePosition, gridSize, gate.inputPins, radius = height/10)
    drawPins(gate.gatePosition, gridSize, gate.outputPins, radius = height / 10)
}

fun DrawScope.orGateUI(gate: GateUIState) {
    val width = this.size.width
    val height = this.size.height
    val gridSize = height/gate.gateSize.height

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

    drawPins(gate.gatePosition, gridSize, gate.inputPins, radius = height/10)
    drawPins(gate.gatePosition, gridSize, gate.outputPins, radius = height/10)
}

fun DrawScope.norGateUI(gate: GateUIState) {
    val width = this.size.width
    val height = this.size.height
    val gridSize = height/gate.gateSize.height

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

    drawPins(gate.gatePosition, gridSize, gate.inputPins, radius = height/10)
    drawPins(gate.gatePosition, gridSize, gate.outputPins, radius = height / 10)
}

fun DrawScope.xorGateUI(gate: GateUIState) {
    val width = this.size.width
    val height = this.size.height
    val gridSize = height/gate.gateSize.height

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

    drawPins(gate.gatePosition, gridSize, gate.inputPins, radius = height/10)
    drawPins(gate.gatePosition, gridSize, gate.outputPins, radius = height/10)
}

fun DrawScope.inverterUI(gate: GateUIState) {
    val width = this.size.width
    val height = this.size.height
    val conversionX = width*8/10
    val gridSize = height/gate.gateSize.height

    drawLine(Color.Black, start = Offset(0f, 0f), end = Offset(0f, height), strokeWidth = stroke)
    drawLine(Color.Black, start = Offset(0f, 0f), end = Offset(conversionX, height/2), strokeWidth = stroke)
    drawLine(Color.Black, start = Offset(0f, height), end = Offset(conversionX, height/2), strokeWidth = stroke)

    val r = (width-conversionX)/2
    drawCircle(Color.Black, center = Offset(width-r, height/2), radius = r, style = strokeStyle)

    drawPins(gate.gatePosition, gridSize, gate.inputPins, radius = height/5)
    drawPins(gate.gatePosition, gridSize, gate.outputPins, radius = height/5)
}

fun DrawScope.bufferUI(gate: GateUIState) {
    val width = this.size.width
    val height = this.size.height
    val gridSize = height/gate.gateSize.height

    drawLine(Color.Black, start = Offset(0f, 0f), end = Offset(0f, height), strokeWidth = stroke)
    drawLine(Color.Black, start = Offset(0f, 0f), end = Offset(width, height/2), strokeWidth = stroke)
    drawLine(Color.Black, start = Offset(0f, height), end = Offset(width, height/2), strokeWidth = stroke)

    drawPins(gate.gatePosition, gridSize, gate.inputPins, radius = height/5)
    drawPins(gate.gatePosition, gridSize, gate.outputPins, radius = height/5)
}

fun DrawScope.switch(gate: GateUIState) {
    val width = this.size.width
    val height = this.size.height
    val gridSize = height/gate.gateSize.height

    val rectangle = Outline.Rectangle(
        Rect(Offset(0f, 0f), Size(width, height))
    )
    drawOutline(rectangle, Color.Black, style = Stroke(width = stroke))

    drawPins(gate.gatePosition, gridSize, gate.outputPins, radius = height/5)
}
