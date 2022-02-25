package com.landry.digital.engine

import com.landry.digital.engine.component.AndGate
import com.landry.digital.engine.component.Pin
import kotlin.test.Test
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class AndGateTests {
    @Test
    fun testAndGateTruthTable() {
        val andGate = AndGate()
        val input1 = andGate.inputs.first()
        val input2 = andGate.inputs.last()
        val pin = Pin()
        andGate.addOutput(pin)

        input1.state = false
        input2.state = false
        andGate.update()

        assertFalse(pin.state)

        input1.state = false
        input2.state = true
        andGate.update()

        assertFalse(pin.state)

        input1.state = true
        input2.state = false
        andGate.update()

        assertFalse(pin.state)

        input1.state = true
        input2.state = true
        andGate.update()

        assertTrue(pin.state)
    }
}