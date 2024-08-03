package com.landry.digital.engine.component

interface SingleOutput: LogicGate {
    var nextState: Boolean
    val output: Pin
        get() = outputs.first()

    override fun apply() {
        outputs.forEach {
            it.state = nextState
        }
    }
}
