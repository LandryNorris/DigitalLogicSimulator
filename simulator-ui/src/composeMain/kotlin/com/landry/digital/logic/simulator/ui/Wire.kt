package com.landry.digital.logic.simulator.ui

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp

data class Wire(val coordinates: List<Coordinate> = listOf(),
                val state: Boolean = false)

operator fun Wire.plus(coordinate: Coordinate) = Wire(coordinates + coordinate)

class Coordinate(val x: Int, val y: Int)

@Composable
fun Wire.draw(gridSize: Dp) {
    if(coordinates.isEmpty()) return
    val startX = coordinates.minOf { it.x }
    val startY = coordinates.minOf { it.y }
    Canvas(modifier = androidx.compose.ui.Modifier.width(gridSize*width()).height(gridSize*height())) {
        coordinates.windowed(size = 2) {
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

fun Wire.width() = coordinates.maxOf { it.x } - coordinates.minOf { it.x }

fun Wire.height() = coordinates.maxOf { it.y } - coordinates.minOf { it.y }
