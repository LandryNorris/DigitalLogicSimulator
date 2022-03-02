package com.landry.digital.engine.component

interface SingleOutput: LogicGate {
    val output: Pin
        get() = outputs.first()
}