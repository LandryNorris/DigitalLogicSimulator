package com.landry.digital.logic.simulator.ui

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.focusable
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.layout.*
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kotlin.math.ceil

data class SimulatorLayoutState(val currentX: Double = 0.0, val currentY: Double = 0.0, val gridSize: Dp = 10.dp)

@Composable
fun SimulatorLayout(modifier: Modifier = Modifier,
                    layoutState: SimulatorLayoutState = SimulatorLayoutState(),
                    circuit: CircuitUI) {
    Canvas(modifier) {
        drawGrid(layoutState, Color.DarkGray)
    }

    for(gate in circuit.gates) {
        gate.draw(layoutState.gridSize)
    }
}

fun DrawScope.drawGrid(simulatorLayoutState: SimulatorLayoutState, color: Color) {
    var x = 0f
    while(x < size.width) {
        drawLine(color, start = Offset(x, 0f), end = Offset(x, size.height))
        x += simulatorLayoutState.gridSize.roundToPx()
    }

    var y = 0f
    while(y < size.height) {
        drawLine(color, start = Offset(0f, y), end = Offset(size.width, y))
        y += simulatorLayoutState.gridSize.roundToPx()
    }
}
