package com.landry.digital.logic.simulator.desktop.components

import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.input.key.*
import androidx.compose.ui.input.pointer.PointerEvent
import androidx.compose.ui.unit.dp
import com.arkivanov.decompose.ComponentContext
import com.landry.digital.engine.component.*
import com.landry.digital.logic.simulator.ui.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlin.math.ceil
import kotlin.math.floor

interface SimulatorUiLogic {
    val state: MutableStateFlow<SimulatorState>

    fun onKeyPressed(keyEvent: KeyEvent): Boolean
    fun onPointerMove(event: PointerEvent)
    fun onClick()
}

class SimulatorComponent(context: ComponentContext): SimulatorUiLogic, ComponentContext by context {
    override val state = MutableStateFlow(SimulatorState())
    private var currentGate: Gate? = null
    private var currentWire: Wire? = null
    private var currentCoordinate: Coordinate? = null

    @OptIn(ExperimentalComposeUiApi::class)
    override fun onKeyPressed(keyEvent: KeyEvent): Boolean {

        if(keyEvent.type == KeyEventType.KeyUp) {
            when(keyEvent.key) {
                Key.A ->
                    if(keyEvent.isShiftPressed) addGate(NandGate())
                    else addGate(AndGate())
                Key.O ->
                    if(keyEvent.isShiftPressed) addGate(NorGate())
                    else addGate(OrGate())
                Key.X -> addGate(XorGate())
                Key.I -> addGate(Inverter())
                Key.B -> addGate(Buffer())
                Key.Escape -> {
                    if(currentGate != null) {
                        currentGate = null
                        state.update {
                            it.copy(circuit = it.circuit.copy(gates = it.circuit.gates.dropLast(1)))
                        }
                    }
                    if(currentWire != null) {
                        currentWire = null
                        state.update {
                            it.copy(circuit = it.circuit.copy(wires = it.circuit.wires.dropLast(1)))
                        }
                    }
                }
                Key.W -> addWire()
            }
        }
        return false
    }

    private fun addWire() {
        val wire = Wire()
        currentWire = wire
        state.update {
            it.copy(circuit = it.circuit + wire)
        }
    }

    private fun addGate(gate: LogicGate) {
        currentGate = Gate(gate)
        state.update { it.copy(circuit = it.circuit + currentGate!!) }
        println("Gate count is now ${state.value.circuit.gates.size}")
    }

    override fun onPointerMove(event: PointerEvent) {
        val position = event.changes.first().position
        val gridX = floor(position.x.dp / state.value.layoutState.gridSize) + state.value.layoutState.currentX
        val gridY = floor(position.y.dp / state.value.layoutState.gridSize) + state.value.layoutState.currentY
        currentCoordinate = Coordinate(gridX.toInt(), gridY.toInt())

        if(currentGate != null) {
            val currentIndex = state.value.circuit.gates.indexOf(currentGate)
            state.update {
                currentGate = it.circuit.gates[currentIndex].copy(x = gridX.toInt(), y = gridY.toInt())
                it.copy(circuit = it.circuit.copy(gates = it.circuit.gates.mapIndexed { index, gate ->
                    if (index == currentIndex) currentGate!!
                    else gate
                }))
            }
        }
    }

    override fun onClick() {
        if(currentGate != null) currentGate = null

        if(currentWire != null) {
            addCoordinate(currentCoordinate!!)
        }
    }

    private fun addCoordinate(coordinate: Coordinate) {
        val currentWireIndex = state.value.circuit.wires.indexOf(currentWire)
        if(currentWireIndex == -1) return
        currentWire = currentWire!! + coordinate
        state.update {
            it.copy(circuit = it.circuit.copy(wires = it.circuit.wires.copyAndSet(currentWireIndex, currentWire!!)))
        }
    }
}

fun <T> List<T>.copyAndSet(index: Int, value: T): List<T> {
    val result = toMutableList()
    result[index] = value
    return result
}

data class SimulatorState(val circuit: CircuitUI = CircuitUI(),
                          val layoutState: SimulatorLayoutState = SimulatorLayoutState())
