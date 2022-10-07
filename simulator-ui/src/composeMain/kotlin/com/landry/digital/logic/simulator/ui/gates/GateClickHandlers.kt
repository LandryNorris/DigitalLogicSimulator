package com.landry.digital.logic.simulator.ui.gates

import androidx.compose.ui.input.pointer.PointerEvent


fun twoInOneOut4x4Click(pointerEvent: PointerEvent,
                        gridSize: Int,
                        onInputClicked: (Int) -> Unit = {},
                        onOutputClicked: (Int) -> Unit = {}) {
    val position = pointerEvent.changes.first().position
    val w = 4 * gridSize
    val h = 4 * gridSize
    val r = h/10

    var y = gridSize
    var i = 0
    while(y < h) {
        val x = 0

        val d2 = (position.x - x)*(position.x - x) + (position.y - y)*(position.y - y)
        if(d2 < r*r) onInputClicked(i)

        y += gridSize*2
        i++
    }

    val outputY = h/2

    val d2 = (position.x - w) * (position.x - w) + (position.y - outputY)*(position.y - outputY)
    if(d2 < r*r) onOutputClicked(0)
}

fun oneInOneOut4x2Click(pointerEvent: PointerEvent,
                        gridSize: Int,
                        onInputClicked: (Int) -> Unit = {},
                        onOutputClicked: (Int) -> Unit = {}) {
    val position = pointerEvent.changes.first().position
    val w = 4 * gridSize
    val h = 2 * gridSize
    val r = h/7

    val halfH = h/2

    val inputDistance2 = (position.x - w) * (position.x - w) + (position.y - halfH)*(position.y - halfH)
    if(inputDistance2 < r*r) onInputClicked(0)

    val outputDistance2 = (position.x) * (position.x) + (position.y - halfH)*(position.y - halfH)
    if(outputDistance2 < r*r) onOutputClicked(0)
}
