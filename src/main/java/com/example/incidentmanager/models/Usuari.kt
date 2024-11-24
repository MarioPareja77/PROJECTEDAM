package com.example.incidentmanager.models

import java.io.Serializable

/**
 * @author Xavier Comi
 */

data class Usuari(
    val email: String,
    val contrasenya: String,
    val cap: String = "",
    val area: String,
    val rol: String,
    val intentsFallits: Int = 3,
) : Serializable