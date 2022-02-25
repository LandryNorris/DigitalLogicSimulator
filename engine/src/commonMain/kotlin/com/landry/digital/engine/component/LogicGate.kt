package com.landry.digital.engine.component

abstract class LogicGate {
    abstract val inputs: List<Pin>
    abstract val outputs: List<Pin>
    abstract fun update()
    abstract fun addOutput(pin: Pin)
}
