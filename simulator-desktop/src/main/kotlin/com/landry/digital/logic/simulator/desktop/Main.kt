package com.landry.digital.logic.simulator.desktop

import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.PointerEventType
import androidx.compose.ui.input.pointer.onPointerEvent
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.WindowState
import androidx.compose.ui.window.singleWindowApplication
import com.arkivanov.decompose.DefaultComponentContext
import com.arkivanov.essenty.lifecycle.LifecycleRegistry
import com.landry.digital.logic.simulator.desktop.components.SimulatorComponent
import com.landry.digital.logic.simulator.desktop.ui.MenuActions
import com.landry.digital.logic.simulator.desktop.ui.TopMenuBar
import com.landry.digital.logic.simulator.ui.SimulatorLayout

fun main() {
    uiMain()
}

const val SIMULATOR_PERIOD = 20L

val simulatorComponent = SimulatorComponent(DefaultComponentContext(LifecycleRegistry()))

val menuActions = MenuActions(
    onNew = {},
    onOpen = {},
    onSave = {},
    onSaveAs = {},
    onAbout = {},
)

@OptIn(ExperimentalComposeUiApi::class)
fun uiMain() = singleWindowApplication(
    title = "Digital Circuit Simulator",
    state = WindowState(size = DpSize(800.dp, 800.dp)),
    onKeyEvent = simulatorComponent::onKeyPressed
) {
    LaunchedEffect(Unit) {
        simulatorComponent.start(SIMULATOR_PERIOD)
    }
    TopMenuBar(menuActions)
    Box(modifier = Modifier.fillMaxSize().onPointerEvent(PointerEventType.Move) {
        simulatorComponent.onPointerMove(it)
    }.pointerInput(Unit) {
        detectDragGestures { change, dragAmount ->
            change.consume()

            simulatorComponent.onPointerDrag(-dragAmount.x, -dragAmount.y)
        }
    }.clickable(interactionSource = remember {  MutableInteractionSource() }, indication = null) {
        simulatorComponent.onClick()
    }) {
        val density = LocalDensity.current.density
        LaunchedEffect(density) {
            simulatorComponent.initializeDensity(density)
        }
        val state by simulatorComponent.state.collectAsState()
        SimulatorLayout(modifier = Modifier.fillMaxSize(),
            circuit = state.circuit,
            onScroll = simulatorComponent::onScroll,
            layoutState = state.layoutState)
    }
}

