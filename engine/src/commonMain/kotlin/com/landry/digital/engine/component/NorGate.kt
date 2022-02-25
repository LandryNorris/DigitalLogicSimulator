package com.landry.digital.engine.component

class NorGate: LogicGate {
    private val input1 = Pin()
    private val input2 = Pin()
    override val inputs = listOf(input1, input2)
    override val outputs = arrayListOf<Pin>()

    override fun update() {
        val result = !(input1.state || input2.state)
        outputs.forEach {
            it.state = result
        }
    }

    override fun addOutput(pin: Pin) { outputs.add(pin) }
}
