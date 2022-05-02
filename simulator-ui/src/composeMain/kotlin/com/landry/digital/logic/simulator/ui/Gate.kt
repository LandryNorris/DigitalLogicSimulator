package com.landry.digital.logic.simulator.ui

import com.landry.digital.engine.component.*

data class Gate(val gate: LogicGate, val x: Int = 0, val y: Int = 0)

fun Gate.size() = when(gate) {
    is AndGate,
    is XorGate,
    is NandGate,
    is NorGate,
    is OrGate -> 4 to 4

    is Buffer,
    is Inverter -> 4 to 2

    else -> error("Size not set for gate $gate")
}