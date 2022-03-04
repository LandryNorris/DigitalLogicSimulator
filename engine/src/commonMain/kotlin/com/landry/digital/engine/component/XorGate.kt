package com.landry.digital.engine.component

class XorGate: SingleOutput, TwoInput {
    override val inputs = mutableListOf(Pin(), Pin())
    override val outputs = listOf(Pin())
    override var nextState = false

    override fun update() {
        nextState = input1.state xor input2.state
    }
}
