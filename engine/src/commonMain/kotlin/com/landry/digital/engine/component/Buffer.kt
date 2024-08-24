package com.landry.digital.engine.component

import com.landry.digital.engine.properties.GateProperties

class Buffer: SingleOutput, SingleInput {
    override val inputs = mutableListOf(Pin())
    override val outputs = listOf(Pin())
    override var nextState = false
    override var gateProperties: GateProperties? = null

    override fun update() {
        nextState = inputs.first().state
    }
}
