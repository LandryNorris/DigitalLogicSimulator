package com.landry.digital.engine

import com.landry.digital.engine.component.*

fun createDFlipFlop(): Circuit {
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

    val t = Pin()
    val clk = Pin()

    val inverter1 = Inverter()
    val and1 = AndGate()
    val and2 = AndGate()
    val or = OrGate()

    val inverter2 = Inverter()
    val inverter3 = Inverter()

    val sr1Nand1 = NandGate()
    val sr1Nand2 = NandGate()
    val sr1Nand3 = NandGate()
    val sr1Nand4 = NandGate()

    val sr2Nand1 = NandGate()
    val sr2Nand2 = NandGate()
    val sr2Nand3 = NandGate()
    val sr2Nand4 = NandGate()

    inverter1.input = t
    and1.input1 = sr2Nand4.output
    and1.input2 = t

    and2.input1 = inverter1.output
    and2.input2 = sr2Nand3.output

    or.input1 = and1.output
    or.input2 = and2.output

    inverter2.input = or.output
    inverter3.input = clk

    sr1Nand1.input1 = or.output
    sr1Nand1.input2 = clk

    sr1Nand2.input1 = inverter2.output
    sr1Nand2.input2 = clk

    sr1Nand3.input1 = sr1Nand1.output
    sr1Nand3.input2 = sr1Nand4.output

    sr1Nand4.input1 = sr1Nand3.output
    sr1Nand4.input2 = sr1Nand2.output

    sr2Nand1.input1 = sr1Nand3.output
    sr2Nand1.input2 = inverter3.output

    sr2Nand2.input1 = inverter3.output
    sr2Nand2.input2 = sr1Nand4.output

    sr2Nand3.input1 = sr2Nand1.output
    sr2Nand3.input2 = sr2Nand4.output

    sr2Nand4.input1 = sr2Nand3.output
    sr2Nand4.input2 = sr2Nand2.output

    circuit.addInput(t)
    circuit.addInput(clk)

    circuit.addGate(inverter1)
    circuit.addGate(inverter2)
    circuit.addGate(inverter3)
    circuit.addGate(and1)
    circuit.addGate(and2)
    circuit.addGate(or)
    circuit.addGate(sr1Nand1)
    circuit.addGate(sr1Nand2)
    circuit.addGate(sr1Nand3)
    circuit.addGate(sr1Nand4)
    circuit.addGate(sr2Nand1)
    circuit.addGate(sr2Nand2)
    circuit.addGate(sr2Nand3)
    circuit.addGate(sr2Nand4)

    circuit.addOutput(sr2Nand3.output)
    circuit.addOutput(sr2Nand4.output)

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
