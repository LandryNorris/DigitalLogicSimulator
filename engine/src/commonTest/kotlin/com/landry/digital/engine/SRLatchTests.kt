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
        srCircuit.tick()
        srCircuit.tick()
        assertTrue(srCircuit.outputs.first().state)
        assertFalse(srCircuit.outputs.last().state)

        //maintain state
        srCircuit.tick()
        srCircuit.tick()
        assertTrue(srCircuit.outputs.first().state)
        assertFalse(srCircuit.outputs.last().state)

        //reset
        srCircuit.inputs.first().state = true
        srCircuit.inputs.last().state = false
        srCircuit.tick()
        srCircuit.tick()
        assertFalse(srCircuit.outputs.first().state)
        assertTrue(srCircuit.outputs.last().state)

        //latch
        srCircuit.inputs.first().state = false
        srCircuit.inputs.last().state = false
        srCircuit.tick()
        srCircuit.tick()
        assertFalse(srCircuit.outputs.first().state)
        assertTrue(srCircuit.outputs.last().state)

        //maintain state
        srCircuit.tick()
        srCircuit.tick()
        assertFalse(srCircuit.outputs.first().state)
        assertTrue(srCircuit.outputs.last().state)

        //set
        srCircuit.inputs.first().state = false
        srCircuit.inputs.last().state = true
        srCircuit.tick()
        srCircuit.tick()
        assertTrue(srCircuit.outputs.first().state)
        assertFalse(srCircuit.outputs.last().state)
    }

    @Test
    fun testUndefinedSRLatchDoesntCrash() {
        val latch = makeSRLatch()
        latch.inputs.first().state = true
        latch.inputs.last().state = true

        latch.tick()
        latch.tick()
        latch.tick()
        latch.tick()
    }

    /**
     * Creates a nor-based SR latch. Inputs[0] represents R and inputs[1] represents S,
     * outputs[0] represents Q and outputs[1] represents Q not
     */
    private fun makeSRLatch(): Circuit {
        val circuit = Circuit()
        val nor1 = NorGate()
        val nor2 = NorGate()
        val output1 = nor1.outputs.first()
        val output2 = nor2.outputs.first()

        nor2.input1 = nor1.outputs.first()
        nor1.input2 = nor2.outputs.first()

        circuit.addGate(nor1)
        circuit.addGate(nor2)

        circuit.addInput(nor1.inputs.first())
        circuit.addInput(nor2.inputs.last())

        circuit.addOutput(output1)
        circuit.addOutput(output2)

        return circuit
    }
}