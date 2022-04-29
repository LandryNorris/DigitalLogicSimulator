package com.landry.digital.logic.simulator.ui

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.landry.digital.engine.component.AndGate
import com.landry.digital.engine.component.LogicGate
import com.landry.digital.engine.component.OrGate
import com.landry.digital.engine.component.XorGate

@Composable
@Preview
fun AndGatePreview() {
    val gate = AndGate()
    gate.input1.state = false
    gate.input1.state = true
    gate.output.state = false
    Gate(gate).draw()
}

@Composable
@Preview
fun OrGatePreview() {
    val gate = OrGate()
    gate.input1.state = false
    gate.input1.state = true
    gate.output.state = true
    Gate(gate).draw()
}

@Composable
@Preview
fun XorGatePreview() {
    val gate = XorGate()
    gate.input1.state = false
    gate.input1.state = true
    gate.output.state = true
    Gate(gate).draw()
}

@Composable
@Preview
fun GateColumn() {
    Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
        Spacer(modifier = Modifier.height(2.dp))
        AndGatePreview()
        OrGatePreview()
        XorGatePreview()
    }
}