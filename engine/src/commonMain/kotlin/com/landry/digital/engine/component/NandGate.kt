package com.landry.digital.engine.component

class NandGate: LogicGate {
    private val input1 = Pin()
    private val input2 = Pin()
    override val inputs = listOf(input1, input2)
    override val outputs = arrayListOf<Pin>()
    override var nextState = false

    override fun update() {
        nextState = !(input1.state && input2.state)
    }

    override fun addOutput(pin: Pin) { outputs.add(pin) }
}
