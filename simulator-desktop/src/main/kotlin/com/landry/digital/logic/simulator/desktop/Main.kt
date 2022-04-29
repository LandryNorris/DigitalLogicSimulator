package com.landry.digital.logic.simulator.desktop

import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowState
import androidx.compose.ui.window.singleWindowApplication
import com.landry.digital.engine.component.AndGate
import com.landry.digital.logic.simulator.ui.CircuitUI
import com.landry.digital.logic.simulator.ui.Gate
import com.landry.digital.logic.simulator.ui.SimulatorLayout

fun main() {
    uiMain()
}

fun uiMain() = singleWindowApplication(
    title = "Digital Circuit Simulator",
    state = WindowState(size = DpSize(800.dp, 800.dp))
) {
    val circuit = CircuitUI(
        listOf(
            Gate(AndGate(), 2, 10),
            Gate(AndGate(), 2, 5)
        )
    )
    SimulatorLayout(modifier = Modifier.fillMaxSize(), circuit = circuit)
}

