package com.landry.digital.engine.ui

data class WireUIState(val positions: List<Position>, val state: Boolean,
                       val unfinalizedPosition: Position? = null)
