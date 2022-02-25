package com.landry.digital.engine

import com.landry.digital.engine.component.LogicGate
import com.landry.digital.engine.component.Pin

class Circuit {
    private val gates = arrayListOf<LogicGate>()
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
        val pin = Pin()
        gate.addOutput(pin)
        addOutput(pin)
    }

    fun update() {
        gates.forEach { it.update() }
    }
}
