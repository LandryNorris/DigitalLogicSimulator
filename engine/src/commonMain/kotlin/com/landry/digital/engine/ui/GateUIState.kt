package com.landry.digital.engine.ui

data class GateUIState(
    val gatePosition: Position,
    val gateSize: Position,
    val inputPins: List<PinUIState>,
    val outputPins: List<PinUIState>
)
