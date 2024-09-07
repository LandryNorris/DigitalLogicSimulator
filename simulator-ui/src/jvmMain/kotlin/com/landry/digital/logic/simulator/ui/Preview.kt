package com.landry.digital.logic.simulator.ui

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.unit.dp
import com.landry.digital.engine.component.*
import com.landry.digital.engine.ui.GateUIProperties
import com.landry.digital.engine.ui.Position
import com.landry.digital.engine.ui.getUIState

@Composable
@Preview
fun AndGatePreview() {
    val gate = AndGate()
    gate.input1.state = false
    gate.input1.state = true
    gate.output.state = false

    gate.gateProperties = GateUIProperties(
        Position(1, 1),
        com.landry.digital.engine.ui.Size(4, 4))
    gate.getUIState()?.draw()
}

@Composable
@Preview
fun OrGatePreview() {
    val gate = OrGate()
    gate.input1.state = false
    gate.input1.state = true
    gate.output.state = true

    gate.gateProperties = GateUIProperties(
        Position(1, 1),
        com.landry.digital.engine.ui.Size(4, 4))
    gate.getUIState()?.draw()
}

@Composable
@Preview
fun XorGatePreview() {
    val gate = XorGate()
    gate.input1.state = false
    gate.input1.state = true
    gate.output.state = true

    gate.gateProperties = GateUIProperties(
        Position(1, 1),
        com.landry.digital.engine.ui.Size(4, 4))
    gate.getUIState()?.draw()
}

@Composable
@Preview
fun SwitchOffPreview() {
    val gate = Switch()
    gate.output.state = false

    gate.gateProperties = GateUIProperties(
        Position(1, 1),
        Switch.defaultSize
    )
    gate.getUIState()?.draw()
}

@Composable
@Preview
fun SwitchOnPreview() {
    val gate = Switch()
    gate.output.state = true
    gate.click()

    gate.gateProperties = GateUIProperties(
        Position(1, 1),
        Switch.defaultSize
    )
    gate.getUIState()?.draw()
}

@Composable
@Preview
fun GateColumn() {
    Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
        Spacer(modifier = Modifier.height(2.dp))
        AndGatePreview()
        OrGatePreview()
        XorGatePreview()
        SwitchOffPreview()
        SwitchOnPreview()
    }
}
