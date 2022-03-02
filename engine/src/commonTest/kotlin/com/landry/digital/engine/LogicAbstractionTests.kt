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

        and.input1 = inverter1.output
        and.input2 = inverter1.output
        inverter3.input = and.outputs.first()

        circuit.inputs.first().state = false
        circuit.inputs.last().state = false
        //takes 3 cycles to stabilize
        circuit.tick()
        circuit.tick()
        circuit.tick()
        assertFalse(circuit.outputs.first().state)
    }

    @Test
    fun testNandAsInverter() {
        val circuit = Circuit()
        val buffer = Buffer()
        val gate = NandGate()

        gate.input1 = buffer.output
        gate.input2 = buffer.output
        val output = gate.outputs.first()

        circuit.addGate(gate)
        circuit.addGate(buffer)
        circuit.addInput(buffer.input)
        circuit.addInput(output)

        buffer.input.state = false
        circuit.tick()
        circuit.tick()

        assertTrue(output.state)

        buffer.input.state = false
        circuit.tick()
        circuit.tick()

        assertTrue(output.state)

        buffer.input.state = true
        circuit.tick()
        circuit.tick()

        assertFalse(output.state)
    }
}