package com.landry.digital.engine.component

class Pin {
    var state: Boolean = false
        set(value) { field = value; callbacks.forEach { it.invoke(value) } }

    private var callbacks = arrayListOf<(Boolean) -> Unit>()

    fun propagate(other: Pin) {
        callbacks.add { other.state = it }
    }
}
