package com.landry.digital.engine.component

import com.landry.digital.engine.properties.GateProperties

class OrGate: TwoInput, SingleOutput {
    override val inputs = mutableListOf(Pin(), Pin())
    override val outputs = listOf(Pin())
    override var nextState = false
    override var gateProperties: GateProperties? = null

    override fun update() {
        nextState = inputs.first().state || inputs.last().state
    }
}
