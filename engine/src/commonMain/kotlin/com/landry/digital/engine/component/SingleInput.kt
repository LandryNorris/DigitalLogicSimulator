package com.landry.digital.engine.component

interface SingleInput: LogicGate {
    var input
        get() = inputs.first()
        set(value) { inputs[0] = value }
}
