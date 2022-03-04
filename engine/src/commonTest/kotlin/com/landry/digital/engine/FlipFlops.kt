package com.landry.digital.engine

import com.landry.digital.engine.component.*

fun makeDFlipFlop(): Circuit {
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

fun makeTFlipFlop(): Circuit {
    val circuit = Circuit()
    val dff = makeDFlipFlop()
    circuit.addCircuit(dff)

    val xor = XorGate()
    val t = xor.input2
    xor.input1 = dff.outputs[0]
    xor.output.propagate(dff.inputs[0])

    circuit.addInput(t)
    circuit.addInput(dff.inputs[1])

    circuit.addGate(xor)

    circuit.addOutput(dff.outputs[0])
    return circuit
}

/**
 * Creates a nor-based SR latch. Inputs[0] represents R and inputs[1] represents S,
 * outputs[0] represents Q and outputs[1] represents Q not
 */
fun makeSRLatch(): Circuit {
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
