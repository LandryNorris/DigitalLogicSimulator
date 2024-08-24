package com.landry.digital.engine.ui

import com.landry.digital.engine.component.LogicGate

class UISimulator {
    private val gates: List<LogicGate> = mutableListOf()
    private val wires: List<WireUIState> = mutableListOf()

    fun getUIState(): UICircuit {
        val gateUIs = mutableListOf<GateUIState>()

        for(gate in gates) {

        }

        TODO("Build circuit")
    }
}

