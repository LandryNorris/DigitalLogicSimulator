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
}