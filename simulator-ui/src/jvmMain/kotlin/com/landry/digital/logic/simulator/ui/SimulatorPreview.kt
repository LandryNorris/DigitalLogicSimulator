package com.landry.digital.logic.simulator.ui

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.landry.digital.engine.component.AndGate
import com.landry.digital.engine.component.OrGate
import com.landry.digital.engine.component.defaultSize
import com.landry.digital.engine.ui.*

@Preview
@Composable
fun SimulatorPreview() {
    val circuit = UICircuit(
        listOfNotNull(
            AndGate().apply {
                input1.state = true
                input2.state = true
                output.state = true
                gateProperties = GateUIProperties(Position(2, 1), AndGate.defaultSize)
            }.getUIState(),
            OrGate().apply {
                input1.state = true
                input2.state = false
                output.state = true
                gateProperties = GateUIProperties(Position(8, 2), AndGate.defaultSize)
            }.getUIState()
        ),
        listOf(
            WireUIState(
                positions = listOf(Position(6, 3), Position(8, 3)),
                true
            )
        )
    )
    SimulatorLayout(modifier = Modifier.fillMaxSize(), onScroll = {}, circuit = circuit)
}
