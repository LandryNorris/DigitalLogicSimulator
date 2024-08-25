package com.landry.digital.engine.component

import com.landry.digital.engine.properties.GateProperties
import com.landry.digital.engine.ui.Size

class NorGate: TwoInput, SingleOutput {
    override val inputs = mutableListOf(Pin(), Pin())
    override val outputs = listOf(Pin())
    override var nextState = false
    override var gateProperties: GateProperties? = null

    companion object;

    override fun update() {
        nextState = !(inputs.first().state || inputs.last().state)
    }
}

val NorGate.Companion.defaultSize: Size
    get() = Size(4, 4)
