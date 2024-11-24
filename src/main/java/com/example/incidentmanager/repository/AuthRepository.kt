package com.example.incidentmanager.repository

import com.example.incidentmanager.network.SocketHelper
import com.example.incidentmanager.utils.status.RegistrationStatus
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import java.net.Socket

/**
 * @author Xavier Comi
 */

class AuthRepository {
    private var socket: Socket? = null
    private var socketHelper: SocketHelper? = null

    private val _registrationStatus = MutableStateFlow<RegistrationStatus?>(null)
    val registrationStatus: StateFlow<RegistrationStatus?> = _registrationStatus

    fun connectToServer(ip: String, port: Int): Boolean {
        return try {
            socket = Socket(ip, port)
            socketHelper = SocketHelper(socket!!)
            true
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }

    suspend fun authenticateUser(email: String, password: String): Boolean {
        return try {
            socketHelper?.sendData(email)
            socketHelper?.sendData(password)
            val response = socketHelper?.receiveData()
            response == "OK"
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }

    suspend fun registerUser(email: String, password: String): RegistrationStatus {
        return try {
            val requestData = "REGISTER|$email|$password"
            val response = socketHelper?.sendRequest(requestData) // Sends via socket
            if (response == "SUCCESS") {
                RegistrationStatus.Success
            } else {
                RegistrationStatus.Error(response ?: "Unknow error")
            }
        } catch (e: Exception) {
            RegistrationStatus.Error(e.message ?: "Unknown socket error")
        }
    }

    fun closeConnection() {
        socketHelper?.close()
        socketHelper = null
        socket = null
    }
}