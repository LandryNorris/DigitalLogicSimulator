package com.landry.digital.engine.component

class Buffer: SingleOutput, SingleInput {
    override val inputs = mutableListOf(Pin())
    override val outputs = listOf(Pin())
    override var nextState = false

    override fun update() {
        nextState = inputs.first().state
    }
}
