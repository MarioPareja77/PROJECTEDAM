package com.example.incidentmanager.repository

import com.example.incidentmanager.models.Usuari
import com.example.incidentmanager.network.SocketHelper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * @author Xavier Comi
 */

class UsuariRepository(private val socketHelper: SocketHelper) {

    private var cachedUsers: List<Usuari> = emptyList()

    // Fetch all users from the server
    suspend fun fetchAllUsers(): List<Usuari> = withContext(Dispatchers.IO) {
        if (cachedUsers.isEmpty()) {
            try {
                val request = "FETCH_USERS"
                val response = socketHelper.sendRequest(request)
                cachedUsers = parseUsersFromResponse(response)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
        cachedUsers
    }

    // Add a user to the server
    suspend fun addUser(user: Usuari) = withContext(Dispatchers.IO) {
        try {
            val request = "ADD_USER|${user.email}|${user.contrasenya}"
            socketHelper.sendRequest(request)
            cachedUsers = emptyList() // Clear cache to refresh data
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    // Update user information on the server
    suspend fun updateUser(user: Usuari) = withContext(Dispatchers.IO) {
        try {
            val request = "UPDATE_USER|${user.email}|${user.email}|${user.contrasenya}"
            socketHelper.sendRequest(request)
            cachedUsers = emptyList() // Clear cache
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    // Delete a user on the server
    suspend fun deleteUser(user: Usuari) = withContext(Dispatchers.IO) {
        try {
            val request = "DELETE_USER|${user.email}"
            socketHelper.sendRequest(request)
            cachedUsers = emptyList() // Clear cache
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    // Find user by email and password (authentication)
    suspend fun findUserByCredentials(email: String, password: String): Usuari? = withContext(Dispatchers.IO) {
        try {
            val request = "FIND_USER|$email|$password"
            val response = socketHelper.sendRequest(request)
            parseUserFromResponse(response)
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    private fun parseUsersFromResponse(response: String): List<Usuari> {
        return response.split('|').drop(1).mapNotNull { entry ->
            val parts = entry.trim('[', ']').split(',')
            if (parts.size >= 2) Usuari(
                email = parts[0],
                contrasenya = parts[1],
                area = parts[2],
                rol = parts[3]
            ) else null
        }
    }

    private fun parseUserFromResponse(response: String): Usuari? {
        val parts = response.split('|').drop(1)
        return if (parts.size >= 2) {
            Usuari(
                email = parts[0],
                contrasenya = parts[1],
                area = parts[2],
                rol = parts[3]
            )
        } else {
            null
        }
    }
}