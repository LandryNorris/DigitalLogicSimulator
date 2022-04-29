package com.landry.digital.parser

import com.landry.digital.engine.Circuit
import com.landry.digital.engine.component.Inverter
import com.landry.digital.engine.component.NandGate
import com.landry.digital.engine.component.Pin
import okio.Path.Companion.toPath
import okio.fakefilesystem.FakeFileSystem
import kotlin.test.Test
import kotlin.test.assertEquals

class WriterTest {
    private val fileSystem = FakeFileSystem()
    private val filepath = "/tmp/circuit".toPath()

    @Test
    fun testWriteCircuit() {
        val circuit = makeCircuit()
        val writer = BinaryWriter(fileSystem)
        filepath.parent?.let {
            fileSystem.createDirectories(it)
        }
        writer.saveCircuit(filepath, circuit)

        fileSystem.read(filepath) {
            val magic = readLong()
            assertEquals(0x4469676974616c31L, magic)
            assertEquals(1, readInt())
        }
    }

    private fun makeCircuit(): Circuit {
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
}