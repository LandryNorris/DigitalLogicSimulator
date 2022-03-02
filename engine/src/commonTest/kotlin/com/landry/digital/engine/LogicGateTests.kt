package com.landry.digital.engine

import com.landry.digital.engine.component.LogicGate
import com.landry.digital.engine.component.Pin
import kotlin.test.assertContentEquals

fun testTruthTable(gateUnderTest: LogicGate, table: List<Pair<List<Boolean>, List<Boolean>>>) {
    for(test in table) {
        testCombinationalGate(gateUnderTest, test.first, test.second)
    }
}

fun testCombinationalGate(gateUnderTest: LogicGate, inputs: List<Boolean>, outputs: List<Boolean>) {
    gateUnderTest.inputs.zip(inputs).forEach {
        it.first.state = it.second
    }
    gateUnderTest.update()
    gateUnderTest.apply()


    val outputStates = gateUnderTest.outputs.map { it.state }
    assertContentEquals(outputs, outputStates)
}
