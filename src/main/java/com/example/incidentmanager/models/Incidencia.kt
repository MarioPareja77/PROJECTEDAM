package com.example.incidentmanager.models

import java.io.Serializable
import java.util.Date

/**
 * @author Xavier Comí
 */

data class Incidencia(
    val idIncidencia: Int = 0,
    val tipus: String,
    val prioritat: String,
    val descripcio: String = "",
    val emailCreador: String? = "",
    val data_creacio: Date? = Date()
) : Serializable
