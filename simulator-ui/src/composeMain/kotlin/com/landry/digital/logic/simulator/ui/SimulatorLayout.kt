package com.landry.digital.logic.simulator.ui

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.rememberScrollableState
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.offset
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.landry.digital.engine.ui.UICircuit

data class SimulatorLayoutState(
    /**
     * The current pixel-space x-coordinate of the left of the screen.
     */
    val currentX: Float = 0.0f,
    /**
     * The current pixel-space y-coordinate of the top of the screen.
     */
    val currentY: Float = 0.0f,
    val gridSize: Dp = 20.dp,
    val density: Float,
)

/**
 * The current grid-space x-coordinate of the left of the screen.
 */
val SimulatorLayoutState.currentGridX: Float get() = currentX / gridSizePx

/**
 * The current grid-space y-coordinate of the top of the screen.
 */
val SimulatorLayoutState.currentGridY: Float get() = currentY / gridSizePx

val SimulatorLayoutState.gridSizePx get() = gridSize.value * density

@Composable
fun SimulatorLayout(modifier: Modifier = Modifier,
                    layoutState: SimulatorLayoutState =
                        SimulatorLayoutState(density = 1f),
                    onScroll: (Float) -> Unit,
                    circuit: UICircuit) {
    val gridOffsetX = -layoutState.currentX % layoutState.gridSizePx
    val gridOffsetY = -layoutState.currentY % layoutState.gridSizePx
    Canvas(modifier.scrollable(
        orientation = Orientation.Vertical,
        state = rememberScrollableState { delta ->
            onScroll(delta)
            delta
        }
    ).offset(gridOffsetX.dp, gridOffsetY.dp)) {
        drawGrid(layoutState, Color(red = 0, green = 0, blue = 0, alpha = 0x90))
    }

    for(gate in circuit.gates) {
        gate.draw(layoutState.gridSize, -layoutState.currentX.dp, -layoutState.currentY.dp)
    }

    for(wire in circuit.wires) {
        wire.draw(layoutState.gridSize, -layoutState.currentX.dp, -layoutState.currentY.dp)
    }
}

fun DrawScope.drawGrid(simulatorLayoutState: SimulatorLayoutState, color: Color) {
    val gridSizePx = simulatorLayoutState.gridSize.roundToPx()
    var x = -gridSizePx.toFloat()
    while(x < size.width + gridSizePx) {
        drawLine(color, start = Offset(x, -gridSizePx.toFloat()), end = Offset(x, size.height + gridSizePx))
        x += gridSizePx
    }

    var y = -gridSizePx.toFloat()
    while(y < size.height + gridSizePx) {
        drawLine(color, start = Offset(-gridSizePx.toFloat(), y), end = Offset(size.width + gridSizePx, y))
        y += gridSizePx
    }
}
