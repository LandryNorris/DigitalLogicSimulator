package com.landry.digital.engine.component

import com.landry.digital.engine.properties.GateProperties

interface LogicGate {
    val inputs: MutableList<Pin>
    val outputs: List<Pin>
    var gateProperties: GateProperties?

    fun update()
    fun apply()
}
