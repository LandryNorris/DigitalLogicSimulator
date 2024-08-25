package com.landry.digital.engine.ui

data class UICircuit(
    val gates: List<GateUIState>,
    val wires: List<WireUIState>
)
