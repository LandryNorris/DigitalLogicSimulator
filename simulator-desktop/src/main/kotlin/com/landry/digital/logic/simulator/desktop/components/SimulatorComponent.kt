package com.landry.digital.logic.simulator.desktop.components

import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.input.key.*
import androidx.compose.ui.input.pointer.PointerEvent
import androidx.compose.ui.unit.dp
import com.arkivanov.decompose.ComponentContext
import com.landry.digital.engine.component.*
import com.landry.digital.engine.ui.*
import com.landry.digital.logic.simulator.ui.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlin.math.ceil
import kotlin.math.floor

interface SimulatorUiLogic {
    val state: MutableStateFlow<SimulatorState>

    fun initializeDensity(density: Float)
    fun onKeyPressed(keyEvent: KeyEvent): Boolean
    fun onPointerMove(event: PointerEvent)
    fun onClick()
}

class SimulatorComponent(context: ComponentContext): SimulatorUiLogic, ComponentContext by context {
    override val state = MutableStateFlow(SimulatorState())
    private var currentGate: LogicGate? = null
    private var currentWire: WireUIState? = null
    private var currentCoordinate: Coordinate? = null
    private val simulator = UISimulator()

    override fun onKeyPressed(keyEvent: KeyEvent): Boolean {
        if(keyEvent.type == KeyEventType.KeyUp) {
            when(keyEvent.key) {
                Key.A ->
                    if(keyEvent.isShiftPressed) addGate(NandGate(), NandGate.defaultSize)
                    else addGate(AndGate(), AndGate.defaultSize)
                Key.O ->
                    if(keyEvent.isShiftPressed) addGate(NorGate(), NandGate.defaultSize)
                    else addGate(OrGate(), OrGate.defaultSize)
                Key.X -> addGate(XorGate(), XorGate.defaultSize)
                Key.I -> addGate(Inverter(), Inverter.defaultSize)
                Key.B -> addGate(Buffer(), Buffer.defaultSize)
                Key.Escape -> {
                    if(currentGate != null) {
                        currentGate = null
                        state.update {
                            it.copy(circuit = it.circuit.copy(gates = it.circuit.gates.dropLast(1)))
                        }
                    }
                    if(currentWire != null) {
                        currentWire = null
                    }
                }
                Key.W -> addWire()
            }
        }

        return false
    }

    override fun initializeDensity(density: Float) {
        state.update { it.copy(layoutState = it.layoutState.copy(density = density)) }
    }

    private fun addWire() {
        val wire = WireUIState(listOf(), false)
        currentWire = wire
        currentGate = null
        simulator += wire
        state.update {
            it.copy(circuit = simulator.getUIState())
        }
    }

    private fun addGate(gate: LogicGate, size: Size) {
        currentWire = null
        currentGate = gate

        gate.gateProperties = GateUIProperties(position = Position(0, 0), size = size)
        simulator += gate

        state.update { it.copy(circuit = simulator.getUIState()) }
        println("Gate count is now ${state.value.circuit.gates.size}")
    }

    override fun onPointerMove(event: PointerEvent) {
        if(currentGate != null) {
            state.update {
                val position = event.changes.first().position
                val gridX = floor(position.x / it.layoutState.gridSizePx) + it.layoutState.currentX
                val gridY = floor(position.y / it.layoutState.gridSizePx) + it.layoutState.currentY
                currentCoordinate = Coordinate(gridX.toInt(), gridY.toInt())
                simulator.moveGate(currentGate!!, currentCoordinate!!.asPosition())
                it.copy(circuit = simulator.getUIState())
            }
        } else {
            val currentState = state.value
            val currentLayoutState = currentState.layoutState
            val position = event.changes.first().position
            val gridX = floor(position.x / currentLayoutState.gridSizePx) + currentLayoutState.currentX
            val gridY = floor(position.y / currentLayoutState.gridSizePx) + currentLayoutState.currentY
            currentCoordinate = Coordinate(gridX.toInt(), gridY.toInt())
        }
    }

    override fun onClick() {
        if(currentGate != null) currentGate = null

        if(currentWire != null) {
            addCoordinate(currentCoordinate!!)
        }
    }

    private fun addCoordinate(coordinate: Coordinate) {
        currentWire?.let {
            currentWire = simulator.addPositionToWire(it, coordinate.asPosition())
        }

        state.update { it.copy(circuit = simulator.getUIState()) }
    }
}

fun <T> List<T>.copyAndSet(index: Int, value: T): List<T> {
    val result = toMutableList()
    result[index] = value
    return result
}

fun Coordinate.asPosition() = Position(x = x, y = y)

data class SimulatorState(val circuit: UICircuit = UICircuit(listOf(), listOf()),
                          val layoutState: SimulatorLayoutState =
                              SimulatorLayoutState(density = 1.0f))
