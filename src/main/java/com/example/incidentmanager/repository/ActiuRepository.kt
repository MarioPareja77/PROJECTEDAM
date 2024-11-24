package com.example.incidentmanager.repository

import com.example.incidentmanager.models.Actiu
import com.example.incidentmanager.network.SocketHelper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * @author Xavier Comí
 */

class ActiuRepository(private val socketHelper: SocketHelper) {

    private var cachedActius: List<Actiu> = emptyList()

    // Fetch all incidencies from the server
    suspend fun fetchAllActius(): List<Actiu> = withContext(Dispatchers.IO) {
        if (cachedActius.isEmpty()) {
            try {
                val request = "FETCH_INCIDENCIES"
                val response = socketHelper.sendRequest(request)
                cachedActius = parseActiusFromResponse(response)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
        cachedActius
    }

    // Add a new Actiu to the server
    suspend fun addActiu(Actiu: Actiu) = withContext(Dispatchers.IO) {
        try {
            val request = "ADD_INCIDENCIA|${Actiu.idActiu}|${Actiu.nom}|${Actiu.descripcio}"
            socketHelper.sendRequest(request)
            cachedActius = emptyList() // Clear cache
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    // Update an existing Actiu on the server
    suspend fun updateActiu(Actiu: Actiu) = withContext(Dispatchers.IO) {
        try {
            val request = "UPDATE_INCIDENCIA|${Actiu.idActiu}|${Actiu.nom}|${Actiu.descripcio}"
            socketHelper.sendRequest(request)
            cachedActius = emptyList() // Clear cache
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    // Delete an Actiu on the server
    suspend fun deleteActiu(Actiu: Actiu) = withContext(Dispatchers.IO) {
        try {
            val request = "DELETE_INCIDENCIA|${Actiu.idActiu}"
            socketHelper.sendRequest(request)
            cachedActius = emptyList() // Clear cache
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    // Find an Actiu by its ID on the server
    suspend fun findActiuById(id: Int): Actiu? = withContext(Dispatchers.IO) {
        try {
            val request = "FIND_INCIDENCIA|$id"
            val response = socketHelper.sendRequest(request)
            parseActiuFromResponse(response)
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    private fun parseActiusFromResponse(response: String): List<Actiu> {
        return response.split('|').drop(1).mapNotNull { entry ->
            val parts = entry.trim('[', ']').split(',')
            if (parts.size >= 3) Actiu(
                idActiu = parts[0].toInt(),
                nom = parts[1],
                descripcio = parts[2],
                marca = parts[3],
                tipus = parts[4],
                area = parts[5],
                dataAlta = parts[6]
            ) else null
        }
    }

    private fun parseActiuFromResponse(response: String): Actiu? {
        val parts = response.split('|').drop(1)
        return if (parts.size >= 3) Actiu (
            idActiu = parts[0].toInt(),
            nom = parts[1],
            descripcio = parts[2],
            marca = parts[3],
            tipus = parts[4],
            area = parts[5],
            dataAlta = parts[6]
        ) else null
    }
}

