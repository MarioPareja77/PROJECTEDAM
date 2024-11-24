package com.example.incidentmanager.viewModels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.incidentmanager.models.Incidencia
import com.example.incidentmanager.models.Usuari
import com.example.incidentmanager.repository.UsuariRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class UsuariViewModel(private val usuariRepository: UsuariRepository): ViewModel() {

    var loggedInUser by mutableStateOf<Usuari?>(null)
        private set

    var loginError by mutableStateOf<String?>(null)
        private set

    var totsUsuaris by mutableStateOf<List<Usuari>>(emptyList())
        private set

    private val _filteredUsuaris = MutableStateFlow<List<Usuari>>(emptyList())
    val filteredUsuaris: StateFlow<List<Usuari>> get() = _filteredUsuaris

    private val _usuaris = MutableLiveData<List<Usuari>>()
    val usuaris: LiveData<List<Usuari>> = _usuaris

    var query by mutableStateOf("")

    init {
        fetchAllUsers()
    }

    fun clearLoginState() {
        loggedInUser = null
        loginError = null
    }

    fun clearLoginError() {
        loginError = null
    }


    // Aquesta funció manté la lógica del filtratge al viewModel
    // per tal d'evitar repetides peticions al servidor

    fun updateFilteredUsuaris(query: String) {
        _filteredUsuaris.value = filterUsers(totsUsuaris, query)
    }
    fun onQueryChange(newQuery: String) {
        query = newQuery
        updateFilteredUsuaris(query)
    }

    fun filterUsers(users: List<Usuari>, query: String): List<Usuari> {
        return if ( query.isBlank() ) {
            users
        } else {
            users.filter { user ->
                        user.email.contains(query, ignoreCase = true) ||
                        user.area.contains(query, ignoreCase = true)
            }
        }
    }

    // CRUD operations
    fun fetchAllUsers() {
        viewModelScope.launch {
            val fetchedUsuaris = usuariRepository.fetchAllUsers()
            _usuaris.postValue(fetchedUsuaris)
        }
    }

    fun addIncidencia(user: Usuari) {
        viewModelScope.launch {
            usuariRepository.addUser(user)
            fetchAllUsers() // Refresh the list
        }
    }

    fun updateUsuari(user: Usuari) {
        viewModelScope.launch {
            usuariRepository.updateUser(user)
            fetchAllUsers() // Refresh the list
        }
    }

    fun deleteUser(user: Usuari) {
        viewModelScope.launch {
            usuariRepository.deleteUser(user)
            fetchAllUsers() // Refresh the list
        }
    }
}