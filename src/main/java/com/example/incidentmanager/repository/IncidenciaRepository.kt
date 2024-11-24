package com.example.incidentmanager.repository

import com.example.incidentmanager.models.Incidencia
import com.example.incidentmanager.network.SocketHelper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * @author Xavier Comi
 */

class IncidenciaRepository (private val socketHelper: SocketHelper) {

    private var cachedIncidencies: List<Incidencia> = emptyList()

    // Fetch all incidencies from the server
    suspend fun fetchAllIncidencies(): List<Incidencia> = withContext(Dispatchers.IO) {
        if (cachedIncidencies.isEmpty()) {
            try {
                val request = "FETCH_INCIDENCIES"
                val response = socketHelper.sendRequest(request)
                cachedIncidencies = parseIncidenciesFromResponse(response)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
        cachedIncidencies
    }

    // Add a new incidencia to the server
    suspend fun addIncidencia(incidencia: Incidencia) = withContext(Dispatchers.IO) {
        try {
            val request = "ADD_INCIDENCIA|${incidencia.idIncidencia}|${incidencia.emailCreador}|${incidencia.descripcio}"
            socketHelper.sendRequest(request)
            cachedIncidencies = emptyList() // Clear cache
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    // Update an existing incidencia on the server
    suspend fun updateIncidencia(incidencia: Incidencia) = withContext(Dispatchers.IO) {
        try {
            val request = "UPDATE_INCIDENCIA|${incidencia.idIncidencia}|${incidencia.emailCreador}|${incidencia.descripcio}"
            socketHelper.sendRequest(request)
            cachedIncidencies = emptyList() // Clear cache
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    // Delete an incidencia on the server
    suspend fun deleteIncidencia(incidencia: Incidencia) = withContext(Dispatchers.IO) {
        try {
            val request = "DELETE_INCIDENCIA|${incidencia.idIncidencia}"
            socketHelper.sendRequest(request)
            cachedIncidencies = emptyList() // Clear cache
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    // Find an incidencia by its ID on the server
    suspend fun findIncidenciaById(id: Int): Incidencia? = withContext(Dispatchers.IO) {
        try {
            val request = "FIND_INCIDENCIA|$id"
            val response = socketHelper.sendRequest(request)
            parseIncidenciaFromResponse(response)
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    private fun parseIncidenciesFromResponse(response: String): List<Incidencia> {
        return response.split('|').drop(1).mapNotNull { entry ->
            val parts = entry.trim('[', ']').split(',')
            if (parts.size >= 3) Incidencia(
                idIncidencia = parts[0].toInt(),
                emailCreador = parts[1],
                descripcio = parts[2],
                prioritat = parts[3],
                tipus = parts[4],
                ) else null
        }
    }

    private fun parseIncidenciaFromResponse(response: String): Incidencia? {
        val parts = response.split('|').drop(1)
        return if (parts.size >= 3) Incidencia (
                idIncidencia = parts[0].toInt(),
                emailCreador = parts[1],
                descripcio = parts[2],
                prioritat = parts[3],
                tipus = parts[4],
            ) else null
    }
}

