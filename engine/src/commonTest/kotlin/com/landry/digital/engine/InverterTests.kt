package com.landry.digital.engine

import com.landry.digital.engine.component.Inverter
import com.landry.digital.engine.component.Pin
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class InverterTests {
    @Test
    fun testInverter() {
        val inverter = Inverter()
        val pin = Pin()
        inverter.addOutput(pin)
        inverter.inputs.first().state = true
        inverter.update()
        inverter.apply()
        assertFalse(pin.state)

        inverter.inputs.first().state = false
        inverter.update()
        inverter.apply()
        assertTrue(pin.state)

        inverter.update()
        inverter.apply()
        assertTrue(pin.state)

        inverter.update()
        inverter.apply()
        assertTrue(pin.state)
    }

    @Test
    fun testLoopedInverter() {
        val inverter = Inverter()
        inverter.addOutput(inverter.input)
        var currentState = inverter.input.state //may allow randomized starting states.
        repeat(100) {
            inverter.update()
            inverter.apply()
            currentState = !currentState
            assertEquals(currentState, inverter.input.state)
        }
    }

    @Test
    fun testOddLoopedInverter() {
        val inverter1 = Inverter()
        val inverter2 = Inverter()
        val inverter3 = Inverter()
        val output = Pin()

        inverter1.addOutput(inverter2.input)
        inverter2.addOutput(inverter3.input)
        inverter3.addOutput(inverter1.input)
        inverter3.addOutput(output)

        inverter1.input.state = true
        var currentState = true

        repeat(100) {
            inverter1.update()
            inverter2.update()
            inverter3.update()

            inverter1.apply()
            inverter2.apply()
            inverter3.apply()
            println("state: ${output.state}")

            assertEquals(currentState, output.state)
            if(it % 3 == 0) currentState = !currentState
        }
    }
}