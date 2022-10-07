package com.landry.digital.logic.simulator.ui

import androidx.compose.foundation.Canvas
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

data class SimulatorLayoutState(val currentX: Double = 0.0, val currentY: Double = 0.0,
                                val gridSize: Dp = 10.dp, val density: Float)

val SimulatorLayoutState.gridSizePx get() = gridSize.value * density

@Composable
fun SimulatorLayout(modifier: Modifier = Modifier,
                    layoutState: SimulatorLayoutState =
                        SimulatorLayoutState(density = 1f),
                    circuit: CircuitUI) {
    Canvas(modifier) {
        drawGrid(layoutState, Color(red = 0, green = 0, blue = 0, alpha = 0x90))
    }

    for(gate in circuit.gates) {
        gate.draw(layoutState.gridSize, onInputClicked = { println("Clicked input $it") })
    }

    for(wire in circuit.wires) {
        wire.draw(layoutState.gridSize)
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
