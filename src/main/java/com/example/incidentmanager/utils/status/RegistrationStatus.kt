package com.example.incidentmanager.utils.status

sealed class RegistrationStatus {
    object Success : RegistrationStatus()
    data class Error(val message: String) : RegistrationStatus()
}