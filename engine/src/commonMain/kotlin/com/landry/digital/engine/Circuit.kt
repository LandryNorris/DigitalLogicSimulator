package com.landry.digital.engine

import com.landry.digital.engine.component.LogicGate
import com.landry.digital.engine.component.Pin

class Circuit {
    val gates = arrayListOf<LogicGate>()
    val inputs = arrayListOf<Pin>()
    val outputs = arrayListOf<Pin>()

    fun addGate(gate: LogicGate) {
        gates.add(gate)
    }

    fun addInput(pin: Pin) {
        inputs.add(pin)
    }

    fun addOutput(pin: Pin) {
        outputs.add(pin)
    }

    fun setAsOutput(gate: LogicGate) {
        addOutput(gate.outputs.first())
    }

    fun update() {
        gates.forEach { it.update() }
    }

    fun apply() {
        gates.forEach { it.apply() }
    }

    fun addCircuit(circuit: Circuit) {
        gates.addAll(circuit.gates)
    }

    fun tick() {
        update()
        apply()
    }
}
