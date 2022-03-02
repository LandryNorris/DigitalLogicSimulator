package com.landry.digital.engine.component

interface LogicGate {
    val inputs: MutableList<Pin>
    val outputs: List<Pin>
    var nextState: Boolean

    fun update()
    fun apply() {
        outputs.forEach {
            it.state = nextState
        }
    }
}
