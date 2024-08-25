package com.landry.digital.engine.ui

import com.landry.digital.engine.component.LogicGate

class UISimulator {
    private val gates: MutableList<LogicGate> = mutableListOf()
    private val wires: MutableList<WireUIState> = mutableListOf()

    fun getUIState(): UICircuit {
        return UICircuit(
            gates = gates.mapNotNull { it.getUIState() },
            wires = wires.map { it.copy() } // copy so Compose knows it's maybe changed
        )
    }

    operator fun plusAssign(gate: LogicGate) {
        gates.add(gate)
    }

    operator fun plusAssign(wire: WireUIState) {
        wires.add(wire)
    }

    fun addPositionToWire(wire: WireUIState, position: Position): WireUIState? {
        val index = wires.indexOf(wire)
        if(index == -1) return null

        wires[index] = wire.copy(positions = wire.positions + position)

        return wires[index]
    }

    fun moveGate(gate: LogicGate, position: Position) {
        val currentProperties = gate.gateProperties as? GateUIProperties
            ?: return
        gate.gateProperties = currentProperties.copy(position = position)
    }

    fun getGateAt(position: Position): LogicGate? {
        for(gate in gates) {
            val gateProperties = gate.gateProperties as? GateUIProperties
                ?: continue

            // TODO(Landry): Check if position is inside of gate
            if(gateProperties.position == position) {
                return gate
            }
        }
        return null
    }
}

fun LogicGate.getUIState(): GateUIState? {
    if(gateProperties !is GateUIProperties) {
        return null
    }

    val uiGateProperties = gateProperties as GateUIProperties
    return GateUIState(
        this::class,
        uiGateProperties.position, uiGateProperties.size,
        getInputPinStates(this),
        getOutputPinStates(this)
    )
}
