package com.landry.digital.engine.component

interface LogicGate {
    val inputs: List<Pin>
    val outputs: List<Pin>
    var nextState: Boolean

    fun update()
    fun apply() {
        outputs.forEach {
            it.state = nextState
        }
    }
    fun addOutput(pin: Pin)
}
