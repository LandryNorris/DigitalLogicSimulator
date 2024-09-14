package com.landry.digital.engine.ui

import com.landry.digital.engine.component.LogicGate
import com.landry.digital.engine.component.Pin

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
        println("Added a wire")
    }

    fun addPositionToWire(wire: WireUIState, position: Position): WireUIState? {
        val index = wires.indexOf(wire)
        if(index == -1) return null

        wires[index] = wire.copy(positions = wire.positions + position)

        println("Added position to wire")
        buildCircuit()
        return wires[index]
    }

    fun setUnfinalizedPosition(currentWire: WireUIState, position: Position?): WireUIState? {
        val index = wires.indexOf(currentWire)
        if(index == -1) return null

        wires[index] = currentWire.copy(unfinalizedPosition = position)
        return wires[index]
    }

    fun moveGate(gate: LogicGate, position: Position) {
        val currentProperties = gate.gateProperties as? GateUIProperties
            ?: return
        gate.gateProperties = currentProperties.copy(position = position)
    }

    fun getGateAt(position: ContinuousPosition): LogicGate? {
        for(gate in gates) {
            val gateProperties = gate.gateProperties as? GateUIProperties
                ?: continue

            val isPositionWithinGate =
                (position.x >= gateProperties.position.x && position.x <= gateProperties.position.x + gateProperties.size.width)
                        &&
                        (position.y >= gateProperties.position.y && position.y <= gateProperties.position.y + gateProperties.size.height)
            if(isPositionWithinGate) {
                return gate
            }
        }
        return null
    }

    fun outputPinAt(position: ContinuousPosition): Pin? {
        return gates.firstNotNullOfOrNull { it.findOutputPin(position) }
    }

    fun outputPinAt(position: Position): Pin? {
        return gates.firstNotNullOfOrNull { it.findOutputPin(position) }
    }

    private fun LogicGate.findOutputPin(position: Position): Pin? {
        val uiState = getUIState() ?: return null

        for((pinUIState, pin) in uiState.outputPins.zip(outputs)) {
            if(pinUIState.position == position) {
                return pin
            }
        }

        return null
    }

    private fun LogicGate.findOutputPin(position: ContinuousPosition): Pin? {
        val uiState = getUIState() ?: return null

        for((pinUIState, pin) in uiState.outputPins.zip(outputs)) {
            if(position.isCloseTo(pinUIState.position)) {
                return pin
            }
        }

        return null
    }

    private fun gateInputPinIndexAt(position: Position): Pair<LogicGate, Int>? {
        return gates.firstNotNullOfOrNull { gate ->
            val index = gate.inputPinIndexAt(position) ?: return@firstNotNullOfOrNull null
            gate to index
        }
    }

    private fun LogicGate.inputPinIndexAt(position: Position): Int? {
        val uiState = getUIState() ?: return null

        for((index, pinUIState) in uiState.inputPins.withIndex()) {
            if(pinUIState.position == position) {
                return index
            }
        }

        return null
    }

    fun buildCircuit() {
        for(wire in wires) {
            if(wire.positions.size < 2) continue

            // find the output pin for this wire
            var outputPin: Pin? = null
            for(position in wire.positions) {
                val pin = outputPinAt(position)
                if(pin != null) {
                    outputPin = pin
                    break // ensure that only one output is on any wire
                }
            }

            // if a wire has no output, there's nothing to connect inputs to
            if(outputPin == null) continue

            // connect input pins to the output pin
            for(position in wire.positions) {
                val (gate, index) = gateInputPinIndexAt(position) ?: continue

                gate.inputs[index] = outputPin
            }
        }
    }

    fun runTick() {
        for(gate in gates) {
            gate.update()
        }
        for(gate in gates) {
            gate.apply()
        }
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

private fun ContinuousPosition.isCloseTo(position: Position): Boolean {
    // shortcut for common case. Performance benefit
    if(y < position.y - 2 || y > position.y + 2 || x < position.x - 2 || x > position.x + 2) {
        return false
    }

    val dx = x - position.x
    val dy = y - position.y

    val distanceSquared = dx*dx + dy*dy

    return distanceSquared <= 0.2
}
