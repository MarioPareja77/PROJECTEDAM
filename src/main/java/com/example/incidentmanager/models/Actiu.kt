package com.example.incidentmanager.models

import java.io.Serializable

/**
 * @author Xavier Comí
 */


data class Actiu(

    val idActiu: Int = 0,
    val nom: String,
    val tipus: String,
    val area: String,
    val marca: String,
    val dataAlta: String,
    val descripcio: String
) : Serializable