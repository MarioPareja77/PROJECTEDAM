package com.example.incidentmanager.network

import java.io.DataInputStream
import java.io.DataOutputStream
import java.net.Socket

class SocketHelper (private val socket: Socket) {

    private val output = DataOutputStream(socket.getOutputStream())
    private val input = DataInputStream(socket.getInputStream())

    suspend fun sendData(data: String) {
        output.writeUTF(data)
    }

    fun receiveData(): String {
        return input.readUTF()
    }

    suspend fun sendRequest(request: String): String {
        sendData(request)
        return receiveData() // Get the response after sending data
    }

    fun close() {
        input.close()
        output.close()
        socket.close()

    }
}















