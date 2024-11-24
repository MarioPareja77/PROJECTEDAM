package com.example.incidentmanager.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.incidentmanager.repository.AuthRepository
import com.example.incidentmanager.utils.status.RegistrationStatus
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

/**
 * @author Xavier Comi
 */

class AuthViewModel(private val authRepository: AuthRepository): ViewModel() {

    private val _registrationStatus = MutableStateFlow<RegistrationStatus?>(null)
    val registrationStatus: StateFlow<RegistrationStatus?> = _registrationStatus

    private val _isConnected = MutableLiveData<Boolean>()
    val isConnected: LiveData<Boolean> = _isConnected

    private val _authResult = MutableLiveData<Boolean>()
    val authResult: LiveData<Boolean> = _authResult

    fun connectToServer(ip: String, port: Int) {
        viewModelScope.launch {
            val success = authRepository.connectToServer(ip, port)
            _isConnected.postValue(success)
        }
    }

    fun authenticateUser(email: String, password: String) {
        viewModelScope.launch {
            val success = authRepository.authenticateUser(email, password)
                _authResult.postValue(success)
        }
    }

    fun register(email: String, password: String) {
        viewModelScope.launch {
            if (email.isBlank() || password.isBlank()) {
                _registrationStatus.value = RegistrationStatus.Error("Email and Password cannot be empty")
                return@launch
            }

            val result = authRepository.registerUser(email, password)
            _registrationStatus.value = result
        }
    }

    fun disconnect() {
        viewModelScope.launch {
            authRepository.closeConnection()
            _isConnected.postValue(false)
        }
    }
}