package com.landry.digital.logic.simulator.ui

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.layout.*
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kotlin.math.ceil

data class SimulatorLayoutState(val currentX: Double = 0.0, val currentY: Double = 0.0, val gridSize: Dp = 10.dp)

@Composable
fun SimulatorLayout(modifier: Modifier = Modifier,
                    state: SimulatorLayoutState = SimulatorLayoutState(),
                    circuit: CircuitUI) {
    Canvas(modifier) {
        drawGrid(state, Color.DarkGray)
    }

    Layout(
        modifier = modifier,
        content = {
            for(gate in circuit.gates) {
                gate.draw(state.gridSize)
            }
        },
        measurePolicy = circuitMeasurePolicy()
    )
}

@Composable
fun circuitMeasurePolicy() = MeasurePolicy { measurables, constraints ->
    val placeables = measurables.map {
        it.measure(constraints.copy(minWidth = 0, minHeight = 0))
    }

    layout(constraints.maxWidth, constraints.maxHeight) {
        placeables.forEach { placeable ->
            placeable.placeRelative(0, 0)
            println(placeable)
        }
    }
}

fun DrawScope.drawGrid(simulatorLayoutState: SimulatorLayoutState, color: Color) {
    val numCols = ceil(size.width.dp / simulatorLayoutState.gridSize).toInt()
    val numRows = ceil(size.height.dp / simulatorLayoutState.gridSize).toInt()

    for(i in 0 .. numCols) {
        val x = size.width * i/numCols
        drawLine(color, start = Offset(x, 0f), end = Offset(x, size.height))
    }
    for(i in 0 .. numRows) {
        val y = size.height * i/numRows
        drawLine(color, start = Offset(0f, y), end = Offset(size.width, y))
    }
}
