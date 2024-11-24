package com.example.incidentmanager.viewModels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.incidentmanager.models.Actiu
import com.example.incidentmanager.models.Usuari
import com.example.incidentmanager.repository.ActiuRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch


class ActiuViewModel (private val actiuRepository: ActiuRepository): ViewModel() {

    var loggedInUser by mutableStateOf<Usuari?>(null)
        private set

    var loginError by mutableStateOf<String?>(null)
        private set

    private val _actius = MutableLiveData<List<Actiu>>()
    val actius: LiveData<List<Actiu>> = _actius

    // Canvia l'estat i conte el filtratge de la cerca d'actius a LazyColumn
    var query by mutableStateOf("")

    // Mante l'estat per a tots els actius
    var totsActius by mutableStateOf<List<Actiu>>(emptyList())
        private set

    // Estat per als actius filtrats
    private val _filteredActius = MutableStateFlow<List<Actiu>>(emptyList())
    val filteredActius: StateFlow<List<Actiu>> get() = _filteredActius

    // Inicialitza dades que no depenen de parametres exters o accions d'usuari
    init {
        fetchAllActius()
    }

    fun clearLoginState() {
        loggedInUser = null
        loginError = null
    }

    fun clearLoginError() {
        loginError = null
    }

    fun fetchAllActius() {
        viewModelScope.launch {
            val fetchedActius = actiuRepository.fetchAllActius()
            _actius.postValue(fetchedActius)
        }
    }

    fun addActiu(actiu: Actiu) {
        viewModelScope.launch {
            actiuRepository.addActiu(actiu)
            fetchAllActius() // Refresh the list
        }
    }

    fun updateActiu(actiu: Actiu) {
        viewModelScope.launch {
            actiuRepository.updateActiu(actiu)
            fetchAllActius() // Refresh the list
        }
    }

    fun deleteActiu(actiu: Actiu) {
        viewModelScope.launch {
            actiuRepository.deleteActiu(actiu)
            fetchAllActius() // Refresh the list
        }
    }

    // Funcions per a cercar i filtrar actius de LazyColumn
    // Inicialitza dades que no depenen de parametres exters o accions d'usuari


    fun updateFilteredActius(query: String) {
        _filteredActius.value = filterActius(totsActius, query)
    }

    // Aquesta funció manté la lógica del filtratge al viewModel
    // per tal d'evitar repetides cerques a la base de dades
    fun onQueryChange(newQuery: String) {
        query = newQuery
        updateFilteredActius(query)
    }

    // Funció que filtra la llista de actius utilitzant el query
    fun filterActius(actius: List<Actiu>, query: String): List<Actiu> {
        return if (query.isBlank()) {
            actius
        } else {
            actius.filter { actiu ->
                actiu.nom.contains(query, ignoreCase = true) ||
                actiu.marca.contains(query, ignoreCase = true) ||
                actiu.area.contains(query, ignoreCase = true) ||
                actiu.tipus.contains(query, ignoreCase = true)

            }
        }
    }

}