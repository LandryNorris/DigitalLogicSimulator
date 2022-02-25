package com.landry.digital.engine.component

class Buffer: LogicGate {
    val input = Pin()
    override val inputs = listOf(input)
    override val outputs = arrayListOf<Pin>()

    override fun update() {
        val result = input.state
        outputs.forEach {
            it.state = result
        }
    }

    override fun addOutput(pin: Pin) { outputs.add(pin) }
}
