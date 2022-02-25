package com.landry.digital.engine

import com.landry.digital.engine.component.NorGate
import com.landry.digital.engine.component.Pin
import kotlin.test.Test
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class SRLatchTests {

    @Test
    fun testSRLatch() {
        val srCircuit = makeSRLatch()

        //set
        srCircuit.inputs.first().state = false
        srCircuit.inputs.last().state = true
        srCircuit.update()
        assertTrue(srCircuit.outputs.first().state)
        assertFalse(srCircuit.outputs.last().state)

        //maintain state
        srCircuit.update()
        assertTrue(srCircuit.outputs.first().state)
        assertFalse(srCircuit.outputs.last().state)

        //reset
        srCircuit.inputs.first().state = true
        srCircuit.inputs.last().state = false
        srCircuit.update()
        assertFalse(srCircuit.outputs.first().state)
        assertTrue(srCircuit.outputs.last().state)

        //latch
        srCircuit.inputs.first().state = false
        srCircuit.inputs.last().state = false
        srCircuit.update()
        assertFalse(srCircuit.outputs.first().state)
        assertTrue(srCircuit.outputs.last().state)

        //maintain state
        srCircuit.update()
        assertFalse(srCircuit.outputs.first().state)
        assertTrue(srCircuit.outputs.last().state)

        //set
        srCircuit.inputs.first().state = false
        srCircuit.inputs.last().state = true
        srCircuit.update()
        srCircuit.update()
        assertTrue(srCircuit.outputs.first().state)
        assertFalse(srCircuit.outputs.last().state)
    }

    /**
     * Creates a nor-based SR latch. Inputs[0] represents R and inputs[1] represents S,
     * outputs[0] represents Q and outputs[1] represents Q not
     */
    private fun makeSRLatch(): Circuit {
        val circuit = Circuit()
        val nor1 = NorGate()
        val nor2 = NorGate()
        val output1 = Pin()
        val output2 = Pin()

        nor1.addOutput(output1)
        nor2.addOutput(output2)

        nor1.addOutput(nor2.inputs.first())
        nor2.addOutput(nor1.inputs.last())

        circuit.addGate(nor1)
        circuit.addGate(nor2)

        circuit.addInput(nor1.inputs.first())
        circuit.addInput(nor2.inputs.last())

        circuit.addOutput(output1)
        circuit.addOutput(output2)

        return circuit
    }
}