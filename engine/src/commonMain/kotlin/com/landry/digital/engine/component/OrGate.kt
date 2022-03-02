package com.landry.digital.engine.component

class OrGate: TwoInput, SingleOutput {
    override val inputs = mutableListOf(Pin(), Pin())
    override val outputs = listOf(Pin())
    override var nextState = false

    override fun update() {
        nextState = inputs.first().state || inputs.last().state
    }
}
