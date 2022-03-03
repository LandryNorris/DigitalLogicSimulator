package com.landry.digital.engine

import com.landry.digital.engine.component.Inverter
import com.landry.digital.engine.component.NandGate
import com.landry.digital.engine.component.Pin
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

    private fun createDFlipFlop(): Circuit {
        val circuit = Circuit()
        val nand1 = NandGate()
        val nand2 = NandGate()
        val nand3 = NandGate()
        val nand4 = NandGate()
        val inverter = Inverter()
        val d = Pin()
        val clk = Pin()
        clk.state = false

        nand1.input1 = d
        nand1.input2 = clk

        inverter.input = d

        nand2.input1 = inverter.output
        nand2.input2 = clk

        nand3.input1 = nand1.output
        nand3.input2 = nand4.output

        nand4.input1 = nand3.output
        nand4.input2 = nand2.output

        circuit.addGate(nand1)
        circuit.addGate(nand2)
        circuit.addGate(nand3)
        circuit.addGate(nand4)
        circuit.addGate(inverter)

        circuit.addInput(d)
        circuit.addInput(clk)

        circuit.addOutput(nand3.output)
        circuit.addOutput(nand4.output)

        return circuit
    }
}