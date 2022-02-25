package com.landry.digital.engine.component

interface LogicGate {
    val inputs: List<Pin>
    val outputs: List<Pin>
    fun update()
    fun addOutput(pin: Pin)
}
