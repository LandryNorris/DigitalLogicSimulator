package com.landry.digital.engine

import kotlin.test.Test
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class DFlipFlopTests {

    @Test
    fun testDFlipFlop() {
        val circuit = createDFlipFlop()

        val d = circuit.inputs[0]
        val q = circuit.outputs[0]

        d.state = false
        clockLow(circuit)
        clockHigh(circuit)
        assertFalse(q.state)

        d.state = true
        clockLow(circuit)
        clockHigh(circuit)
        assertTrue(q.state)

        clockLow(circuit)
        clockHigh(circuit)
        assertTrue(q.state)

        d.state = false
        clockLow(circuit)
        clockHigh(circuit)
        assertFalse(q.state)
    }

    private fun clockLow(circuit: Circuit) {
        val clk = circuit.inputs[1]
        clk.state = false
        circuit.tick()
        circuit.tick()
        circuit.tick()
        circuit.tick()
    }

    private fun clockHigh(circuit: Circuit) {
        val clk = circuit.inputs[1]
        clk.state = true
        circuit.tick()
        circuit.tick()
        circuit.tick()
        circuit.tick()
    }
}