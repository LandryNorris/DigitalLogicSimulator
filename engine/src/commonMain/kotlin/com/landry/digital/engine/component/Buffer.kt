package com.landry.digital.engine.component

import com.landry.digital.engine.properties.GateProperties
import com.landry.digital.engine.ui.Size

class Buffer: SingleOutput, SingleInput {
    override val inputs = mutableListOf(Pin())
    override val outputs = listOf(Pin())
    override var nextState = false
    override var gateProperties: GateProperties? = null

    companion object;

    override fun update() {
        nextState = inputs.first().state
    }
}

val Buffer.Companion.defaultSize: Size
    get() = Size(4, 4)
