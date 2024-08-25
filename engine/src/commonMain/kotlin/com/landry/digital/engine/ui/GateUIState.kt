package com.landry.digital.engine.ui

import com.landry.digital.engine.component.LogicGate
import kotlin.reflect.KClass

data class GateUIState(
    val type: KClass<out LogicGate>,
    val gatePosition: Position,
    val gateSize: Size,
    val inputPins: List<PinUIState>,
    val outputPins: List<PinUIState>
)
