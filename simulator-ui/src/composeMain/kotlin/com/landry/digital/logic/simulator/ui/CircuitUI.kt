package com.landry.digital.logic.simulator.ui

import com.landry.digital.engine.Circuit

data class CircuitUI(val gates: List<Gate> = listOf(), val wires: List<Wire> = listOf())

operator fun CircuitUI.plus(gate: Gate) = copy(gates = this.gates + gate)

operator fun CircuitUI.plus(wire: Wire) = copy(wires = this.wires + wire)

fun CircuitUI.toEngineCircuit(): Circuit {
    val circuit = Circuit()

    for(gate in gates) {
        circuit.addGate(gate.gate)
    }

    for(wire in wires) {

    }
    return circuit
}