package com.landry.digital.logic.simulator.desktop.components

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.key.*
import androidx.compose.ui.input.pointer.PointerEvent
import androidx.compose.ui.unit.coerceIn
import androidx.compose.ui.unit.dp
import com.arkivanov.decompose.ComponentContext
import com.landry.digital.engine.component.*
import com.landry.digital.engine.ui.*
import com.landry.digital.logic.simulator.desktop.CursorPosition
import com.landry.digital.logic.simulator.ui.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlin.math.roundToInt

const val MIN_GRID_SIZE = 5
const val MAX_GRID_SIZE = 100
const val SCROLL_ADJUSTING_RATE = 0.1

interface SimulatorUiLogic {
    val state: MutableStateFlow<SimulatorState>

    fun initializeDensity(density: Float)
    fun onKeyPressed(keyEvent: KeyEvent): Boolean
    fun onPointerMove(event: PointerEvent)
    fun onClick()
    fun onScroll(delta: Float)
    fun onPointerDrag(offsetX: Float, offsetY: Float)
}

class SimulatorComponent(context: ComponentContext): SimulatorUiLogic, ComponentContext by context {
    override val state = MutableStateFlow(SimulatorState())
    private var currentGate: LogicGate? = null
    private var currentWire: WireUIState? = null
    private var currentCoordinate: CursorPosition? = null
    private val simulator = UISimulator()

    private val coroutineScope  = CoroutineScope(Dispatchers.Default)

    var isRunning = false; private set

    fun start(periodMs: Long) {
        coroutineScope.launch {
            isRunning = true
            while(isRunning) {
                simulator.runTick()
                state.update { it.copy(circuit = simulator.getUIState()) }
                delay(periodMs)
            }
        }
    }

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
                        simulator.setUnfinalizedPosition(currentWire!!, null)
                        currentWire = null
                    }
                }
                Key.S -> addGate(Switch(), Switch.defaultSize)
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
        val currentState = state.value
        val currentLayoutState = currentState.layoutState
        val rawPosition = event.changes.first().position
        val position = rawPosition - Offset(currentLayoutState.currentX, currentLayoutState.currentY)
        val gridX = position.x / currentLayoutState.gridSizePx
        val gridY = position.y / currentLayoutState.gridSizePx
        currentCoordinate = CursorPosition(gridX, gridY)

        if(currentGate != null) {
            state.update {
                simulator.moveGate(currentGate!!, currentCoordinate!!.asPosition())
                it.copy(circuit = simulator.getUIState())
            }
        } else if(currentWire != null) {
            state.update {
                currentWire = simulator.setUnfinalizedPosition(currentWire!!, currentCoordinate!!.asPosition())
                it.copy(circuit = simulator.getUIState())
            }
        }
    }

    override fun onPointerDrag(offsetX: Float, offsetY: Float) {
        state.update {
            val currentX = it.layoutState.currentX
            val currentY = it.layoutState.currentY

            it.copy(
                layoutState = it.layoutState.copy(
                    currentX = currentX + offsetX,
                    currentY = currentY + offsetY
                )
            )
        }
    }

    override fun onClick() {
        if(currentGate != null) currentGate = null

        val clickedGate = gateAt(currentCoordinate!!.asContinuousPosition())
        val clickedOutputPin = simulator.outputPinAt(currentCoordinate!!.asContinuousPosition())

        if(currentWire != null) {
            addCoordinate(currentCoordinate!!.asPosition())
        } else if(clickedOutputPin != null) {
            addWire()
            addCoordinate(currentCoordinate!!.asPosition())
        } else if(clickedGate != null) {
            if(clickedGate is Switch) {
                clickedGate.click()
            }
        }
    }

    override fun onScroll(delta: Float) {
        state.update {
            val adjustedDelta = delta * SCROLL_ADJUSTING_RATE
            val oldGridSize = it.layoutState.gridSize
            val newGridSize = (oldGridSize + adjustedDelta.roundToInt().dp)
                .coerceIn(MIN_GRID_SIZE.dp, MAX_GRID_SIZE.dp)

            it.copy(
                layoutState = it.layoutState.copy(
                    gridSize = newGridSize
                )
            )
        }
    }

    private fun addCoordinate(coordinate: Position) {
        currentWire?.let {
            currentWire = simulator.addPositionToWire(it, coordinate)
        }

        state.update { it.copy(circuit = simulator.getUIState()) }
    }

    private fun gateAt(coordinate: ContinuousPosition): LogicGate? {
        return simulator.getGateAt(coordinate)
    }
}

fun <T> List<T>.copyAndSet(index: Int, value: T): List<T> {
    val result = toMutableList()
    result[index] = value
    return result
}

fun CursorPosition.asPosition() = Position(x = x.roundToInt(), y = y.roundToInt())

fun CursorPosition.asContinuousPosition() = ContinuousPosition(x = x, y = y)

data class SimulatorState(val circuit: UICircuit = UICircuit(listOf(), listOf()),
                          val layoutState: SimulatorLayoutState =
                              SimulatorLayoutState(density = 1.0f))
