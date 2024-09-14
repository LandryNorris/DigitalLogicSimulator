package com.landry.digital.logic.simulator.ui

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import com.landry.digital.engine.ui.WireUIState

@Composable
fun WireUIState.draw(gridSize: Dp, offsetX: Dp, offsetY: Dp) {
    if(positions.isEmpty()) return
    Canvas(modifier = Modifier
        .width(gridSize*width()).height(gridSize*height())
        .offset(offsetX, offsetY)
    ) {
        val positionsToDraw = (positions + unfinalizedPosition).filterNotNull()

        positionsToDraw.windowed(size = 2) {
            val x1 = it.first().x * gridSize.roundToPx()
            val y1 = it.first().y * gridSize.roundToPx()
            val x2 = it.last().x * gridSize.roundToPx()
            val y2 = it.last().y * gridSize.roundToPx()
            drawLine(color = Color.Black,
                start = Offset(x1.toFloat(), y1.toFloat()),
                end = Offset(x2.toFloat(), y2.toFloat()), strokeWidth = strokeStyle.width)
        }
    }
}

fun WireUIState.width() = positions.maxOf { it.x } - positions.minOf { it.x }

fun WireUIState.height() = positions.maxOf { it.y } - positions.minOf { it.y }
