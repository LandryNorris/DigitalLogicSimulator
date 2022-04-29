package com.landry.digital.logic.simulator.ui

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.landry.digital.engine.component.AndGate
import com.landry.digital.engine.component.OrGate

@Preview
@Composable
fun SimulatorPreview() {
    val circuit = CircuitUI(
        listOf(
            Gate(AndGate(), 2, 5),
            Gate(OrGate(), 2, 7),
        )
    )
    SimulatorLayout(modifier = Modifier.fillMaxSize(), circuit = circuit)
}