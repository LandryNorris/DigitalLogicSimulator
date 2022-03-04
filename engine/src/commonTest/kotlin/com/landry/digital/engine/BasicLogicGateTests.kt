package com.landry.digital.engine

import com.landry.digital.engine.component.*
import kotlin.test.Test

class BasicLogicGateTests {
    @Test
    fun testAndGateTruthTable() {
        val gate = AndGate()

        testTruthTable(gate, listOf(
            listOf(false, false) to listOf(false),
            listOf(true, false) to listOf(false),
            listOf(false, true) to listOf(false),
            listOf(true, true) to listOf(true),
        ))
    }

    @Test
    fun testOrGateTruthTable() {
        val gate = OrGate()

        testTruthTable(gate, listOf(
            listOf(false, false) to listOf(false),
            listOf(true, false) to listOf(true),
            listOf(false, true) to listOf(true),
            listOf(true, true) to listOf(true),
        ))
    }

    @Test
    fun testInverterTruthTable() {
        val gate = Inverter()

        testTruthTable(gate, listOf(
            listOf(false) to listOf(true),
            listOf(true) to listOf(false),
        ))
    }

    @Test
    fun testBufferTruthTable() {
        val gate = Buffer()

        testTruthTable(gate, listOf(
            listOf(false) to listOf(false),
            listOf(true) to listOf(true),
        ))
    }

    @Test
    fun testXorGateTruthTable() {
        val gate = XorGate()

        testTruthTable(gate, listOf(
            listOf(false, false) to listOf(false),
            listOf(true, false) to listOf(true),
            listOf(false, true) to listOf(true),
            listOf(true, true) to listOf(false),
        ))
    }

    @Test
    fun testNandGateTruthTable() {
        val gate = NandGate()

        testTruthTable(gate, listOf(
            listOf(false, false) to listOf(true),
            listOf(true, false) to listOf(true),
            listOf(false, true) to listOf(true),
            listOf(true, true) to listOf(false),
        ))
    }

    @Test
    fun testNorGateTruthTable() {
        val gate = NorGate()

        testTruthTable(gate, listOf(
            listOf(false, false) to listOf(true),
            listOf(true, false) to listOf(false),
            listOf(false, true) to listOf(false),
            listOf(true, true) to listOf(false),
        ))
    }
}