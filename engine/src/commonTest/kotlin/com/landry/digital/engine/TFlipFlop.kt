package com.landry.digital.engine

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals

class TFlipFlop {
    @Test
    fun testTFlipFlop() {
        val circuit = makeTFlipFlop()

        repeat(15) {
            circuit.tick()
        }

        val t = circuit.inputs[0]
        val q = circuit.outputs[0]
        val initial = q.state

        t.state = false
        clockHigh(circuit)
        clockLow(circuit)
        assertEquals(q.state, initial)

        t.state = true
        clockHigh(circuit)
        clockLow(circuit)
        assertNotEquals(q.state, initial)
    }

    private fun clockLow(circuit: Circuit) {
        val clk = circuit.inputs[1]
        clk.state = false
        repeat(100) {
            circuit.tick()
        }
    }

    private fun clockHigh(circuit: Circuit) {
        val clk = circuit.inputs[1]
        clk.state = true
        repeat(10) {
            circuit.tick()
        }
    }
}