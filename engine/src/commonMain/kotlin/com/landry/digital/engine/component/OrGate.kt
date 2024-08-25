package com.landry.digital.engine.component

import com.landry.digital.engine.properties.GateProperties
import com.landry.digital.engine.ui.Size

class OrGate: TwoInput, SingleOutput {
    override val inputs = mutableListOf(Pin(), Pin())
    override val outputs = listOf(Pin())
    override var nextState = false
    override var gateProperties: GateProperties? = null

    companion object;

    override fun update() {
        nextState = inputs.first().state || inputs.last().state
    }
}

val OrGate.Companion.defaultSize: Size
    get() = Size(4, 4)
