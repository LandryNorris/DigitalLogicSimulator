package com.landry.digital.engine.component

import com.landry.digital.engine.properties.GateProperties

class XorGate: SingleOutput, TwoInput {
    override val inputs = mutableListOf(Pin(), Pin())
    override val outputs = listOf(Pin())
    override var nextState = false
    override var gateProperties: GateProperties? = null

    override fun update() {
        nextState = input1.state xor input2.state
    }
}
