package com.landry.digital.engine.ui

import com.landry.digital.engine.component.*

fun getInputPinStates(logicGate: LogicGate): List<PinUIState> {
    val uiProperties = logicGate.gateProperties as? GateUIProperties
        ?: return listOf()

    return when(logicGate) {
        is TwoInput -> evenlyPlacedVertical(uiProperties, logicGate.inputStates(), 1, true)
        is SingleInput -> singlePinInCenter(uiProperties, logicGate.inputStates().first(), true)
        else -> listOf()
    }
}

fun getOutputPinStates(logicGate: LogicGate): List<PinUIState> {
    val uiProperties = logicGate.gateProperties as? GateUIProperties
        ?: return listOf()

    return when(logicGate) {
        is SingleOutput -> singlePinInCenter(uiProperties, logicGate.outputStates().first(), false)
        else -> listOf()
    }
}

fun LogicGate.inputStates(): List<Boolean> {
    return inputs.map { it.state }
}

fun LogicGate.outputStates(): List<Boolean> {
    return outputs.map { it.state }
}

fun singlePinInCenter(uiProperties: GateUIProperties,
                      state: Boolean, atStart: Boolean): List<PinUIState> {
    val position = uiProperties.position
    val size = uiProperties.size

    val x = if(atStart) {
        position.x
    } else {
        position.x + size.width
    }

    val y = position.y + size.height/2
    return listOf(PinUIState(Position(x, y), state))
}

fun evenlyPlacedVertical(uiProperties: GateUIProperties,
                         states: List<Boolean>,
                         spaceAround: Int,
                         atStart: Boolean): List<PinUIState> {
    val position = uiProperties.position
    val size = uiProperties.size

    return states.mapIndexed { i, state ->
        val spacing = if(size.height > spaceAround*2) {
            states.size / (size.height - spaceAround*2)
        } else {
            1
        }

        val x = if(atStart) {
            position.x
        } else {
            position.x + size.width
        }
        val y = position.y + i*(spacing + 1) + spaceAround

        PinUIState(Position(x, y), state)
    }
}

operator fun Position.plus(other: Position): Position {
    return Position(this.x + other.x, this.y + other.y)
}
