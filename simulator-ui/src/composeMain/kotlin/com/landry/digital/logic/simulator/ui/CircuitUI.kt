package com.landry.digital.logic.simulator.ui

data class CircuitUI(val gates: List<Gate>)

operator fun CircuitUI.plus(gate: Gate) = CircuitUI(this.gates + gate)
