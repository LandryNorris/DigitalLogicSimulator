package com.landry.digital.parser

import com.landry.digital.engine.Circuit
import com.landry.digital.engine.component.*
import okio.BufferedSink
import okio.FileSystem
import okio.Path

class BinaryWriter(private val fileSystem: FileSystem) {

    fun saveCircuit(path: Path, circuit: Circuit) {
        fileSystem.write(path) {
            writeMagicNumber()
            writeVersion()

            writeCircuit(circuit)
        }
    }

    private fun BufferedSink.writeMagicNumber() {
        writeLong(FileProperties.magicNumber)
    }

    private fun BufferedSink.writeVersion() {
        writeInt(FileProperties.version)
    }

    private fun BufferedSink.writeCircuit(circuit: Circuit) {
        val gateCount = circuit.gates.size
        writeInt(gateCount)

        //write gate ids
        for(gate in circuit.gates) {
            val id = gate.classId()
            writeInt(id)
        }

        //write connections
        for(gate in circuit.gates) {
            writeInt(gate.inputs.size)
            for(input in gate.inputs) {
                val index = circuit.gates.indexOfFirst {
                    it.outputs.contains(input)
                }
                writeInt(index)
            }
        }
    }

    private fun LogicGate.classId() = when(this) {
        is AndGate -> 1
        is OrGate -> 2
        is XorGate -> 3
        is NorGate -> 4
        is NandGate -> 5
        is Inverter -> 6
        is Buffer -> 7
        else -> error("$this is not a valid Gate Class")
    }
}
