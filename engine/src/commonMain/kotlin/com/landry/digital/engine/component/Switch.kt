package com.landry.digital.engine.component

import com.landry.digital.engine.properties.GateProperties
import com.landry.digital.engine.ui.Size

class Switch: SingleOutput {
    override var nextState = false
    override val inputs = mutableListOf<Pin>()
    override val outputs = listOf(Pin())
    override var gateProperties: GateProperties? = null

    private var currentState = false

    companion object;

    override fun update() {
        nextState = currentState
    }

    fun click() {
        currentState = !currentState
    }
}

val Switch.Companion.defaultSize get() = Size(2, 2)
