package com.example.incidentmanager.viewModels

import com.example.incidentmanager.models.Incidencia
import com.example.incidentmanager.repository.IncidenciaRepository
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.incidentmanager.models.Usuari
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class IncidenciaViewModel(private val incidenciaRepository: IncidenciaRepository): ViewModel() {

    var loggedInUser by mutableStateOf<Usuari?>(null)
        private set

    var loginError by mutableStateOf<String?>(null)
        private set

    var totsIncidencies by mutableStateOf<List<Incidencia>>(emptyList())
        private set

    private val _incidencies = MutableLiveData<List<Incidencia>>()
    val incidencies: LiveData<List<Incidencia>> = _incidencies

    private val _filteredIncidencies = MutableStateFlow<List<Incidencia>>(emptyList())
    val filteredIncidencies: StateFlow<List<Incidencia>> get() = _filteredIncidencies

    var query by mutableStateOf("")

    fun updateFilteredIncidencies(query: String) {
        _filteredIncidencies.value = filterIncidencies(totsIncidencies, query)
    }

    fun clearLoginState() {
        loggedInUser = null
        loginError = null
    }

    fun clearLoginError() {
        loginError = null
    }


    fun fetchAllIncidencies() {
        viewModelScope.launch {
            val fetchedIncidencies = incidenciaRepository.fetchAllIncidencies()
            _incidencies.postValue(fetchedIncidencies)
        }
    }

    fun addIncidencia(incidencia: Incidencia) {
        viewModelScope.launch {
            incidenciaRepository.addIncidencia(incidencia)
            fetchAllIncidencies() // Refresh the list
        }
    }

    fun updateIncidencia(incidencia: Incidencia) {
        viewModelScope.launch {
            incidenciaRepository.updateIncidencia(incidencia)
            fetchAllIncidencies() // Refresh the list
        }
    }

    fun deleteIncidencia(incidencia: Incidencia) {
        viewModelScope.launch {
            incidenciaRepository.deleteIncidencia(incidencia)
            fetchAllIncidencies() // Refresh the list
        }
    }

    // Aquesta funció manté la lógica del filtratge al viewModel
    // per tal d'evitar repetides cerques a la base de dades
    fun onQueryChange(newQuery: String) {
        query = newQuery
        updateFilteredIncidencies(query)
    }

    fun filterIncidencies(incidencies: List<Incidencia>, query: String): List<Incidencia> {
        return if ( query.isBlank() ) {
            incidencies
        } else {
            incidencies.filter { incidencia ->
                incidencia.prioritat.contains(query, ignoreCase = true) || // enum class
                incidencia.emailCreador?.contains(query, ignoreCase = true) ?: false ||
                incidencia.descripcio.contains(query, ignoreCase = true)
            }
        }
    }
}