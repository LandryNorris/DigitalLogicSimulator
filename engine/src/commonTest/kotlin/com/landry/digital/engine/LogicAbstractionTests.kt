package com.landry.digital.engine

import com.landry.digital.engine.component.*
import kotlin.test.Test
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class LogicAbstractionTests {
    @Test
    fun testDemorgansLawAnd2Or() {
        val circuit = Circuit()
        val and = AndGate()
        val inverter1 = Inverter()
        val inverter2 = Inverter()
        val inverter3 = Inverter()

        circuit.addGate(and)
        circuit.addGate(inverter1)
        circuit.addGate(inverter2)
        circuit.addGate(inverter3)

        circuit.addInput(inverter1.input)
        circuit.addInput(inverter2.input)
        circuit.setAsOutput(inverter3)

        inverter1.addOutput(and.inputs.first())
        inverter2.addOutput(and.inputs.last())
        and.addOutput(inverter3.input)

        circuit.inputs.first().state = false
        circuit.inputs.last().state = false
        //takes 2 cycles to stabilize
        circuit.update()
        circuit.update()
        assertFalse(circuit.outputs.first().state)
    }

    @Test
    fun testNandAsInverter() {
        val buffer = Buffer()
        val gate = NandGate()
        val output = Pin()

        buffer.addOutput(gate.inputs.first())
        buffer.addOutput(gate.inputs.last())
        gate.addOutput(output)

        buffer.input.state = false
        buffer.update()
        gate.update()

        assertTrue(output.state)

        buffer.input.state = false
        buffer.update()
        gate.update()

        assertTrue(output.state)

        buffer.input.state = true
        buffer.update()
        gate.update()

        assertFalse(output.state)
    }
}