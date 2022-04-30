package com.landry.digital.logic.simulator.desktop.components

import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.input.key.*
import androidx.compose.ui.input.pointer.PointerEvent
import androidx.compose.ui.unit.dp
import com.arkivanov.decompose.ComponentContext
import com.landry.digital.engine.component.*
import com.landry.digital.logic.simulator.ui.CircuitUI
import com.landry.digital.logic.simulator.ui.Gate
import com.landry.digital.logic.simulator.ui.SimulatorLayoutState
import com.landry.digital.logic.simulator.ui.plus
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

    @OptIn(ExperimentalComposeUiApi::class)
    override fun onKeyPressed(keyEvent: KeyEvent): Boolean {

        if(keyEvent.type == KeyEventType.KeyUp) {
            val newGate: LogicGate? = when(keyEvent.key) {
                Key.A ->
                    if(keyEvent.isShiftPressed) NandGate()
                    else AndGate()
                Key.O ->
                    if(keyEvent.isShiftPressed) NorGate()
                    else OrGate()
                Key.X -> XorGate()
                Key.I -> Inverter()
                Key.B -> Buffer()
                else -> null
            }
            if(newGate != null) {
                currentGate = Gate(newGate)
                state.update { it.copy(circuit = it.circuit + currentGate!!) }
                println("Gate count is now ${state.value.circuit.gates.size}")
            }
            if(keyEvent.key == Key.Escape) {
                println("Escape pressed")
                if(currentGate != null) {
                    currentGate = null
                    state.update {
                        it.copy(circuit = it.circuit.copy(gates = it.circuit.gates.dropLast(1)))
                    }
                }
            }
            return true
        }
        return false
    }

    override fun onPointerMove(event: PointerEvent) {
        val position = event.changes.first().position
        val gridX = floor(position.x.dp / state.value.layoutState.gridSize) + state.value.layoutState.currentX
        val gridY = floor(position.y.dp / state.value.layoutState.gridSize) + state.value.layoutState.currentY

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
        currentGate = null
    }
}

data class SimulatorState(val circuit: CircuitUI = CircuitUI(listOf()),
                          val layoutState: SimulatorLayoutState = SimulatorLayoutState()
)
