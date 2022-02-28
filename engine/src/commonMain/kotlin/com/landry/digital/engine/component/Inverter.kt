package com.landry.digital.engine.component

class Inverter: LogicGate {
    val input = Pin()
    override val inputs = listOf(input)
    override val outputs = arrayListOf<Pin>()
    override var nextState = false

    override fun update() {
        nextState = !input.state
    }

    override fun addOutput(pin: Pin) { outputs.add(pin) }
}
