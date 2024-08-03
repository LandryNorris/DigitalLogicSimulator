package com.landry.digital.engine.component

interface LogicGate {
    val inputs: MutableList<Pin>
    val outputs: List<Pin>

    fun update()
    fun apply()
}
