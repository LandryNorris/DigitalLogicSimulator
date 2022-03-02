package com.landry.digital.engine.component

interface TwoInput: LogicGate {
    var input1: Pin
        get() = inputs.first()
        set(value) { inputs[0] = value }
    var input2: Pin
        get() = inputs[1]
        set(value) { inputs[1] = value }
}
