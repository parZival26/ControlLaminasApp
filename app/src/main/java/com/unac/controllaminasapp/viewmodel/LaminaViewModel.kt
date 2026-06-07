package com.unac.controllaminasapp.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.unac.controllaminasapp.data.database.AppDatabase
import com.unac.controllaminasapp.data.database.prepopulatedData
import com.unac.controllaminasapp.data.entity.Lamina
import com.unac.controllaminasapp.data.remote.Player
import com.unac.controllaminasapp.data.remote.RetrofitClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class LaminaViewModel(application: Application) : AndroidViewModel(application) {

    private val dao = AppDatabase.getInstance(application).laminaDao()

    val obtenidas: StateFlow<List<Lamina>> = dao.getObtenidas()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val pendientes: StateFlow<List<Lamina>> = dao.getPendientes()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val repetidas: StateFlow<List<Lamina>> = dao.getRepetidas()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    private val _searchResult = MutableStateFlow<Player?>(null)
    val searchResult: StateFlow<Player?> = _searchResult.asStateFlow()

    private val _searchError = MutableStateFlow(false)
    val searchError: StateFlow<Boolean> = _searchError.asStateFlow()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            if (dao.getCount() == 0) {
                dao.insertarLaminas(prepopulatedData())
            }
        }
    }

    fun registrarObtenida(id: String, nombreJugador: String) {
        viewModelScope.launch {
            dao.registrarObtenida(id, nombreJugador)
        }
    }

    fun intercambiar(idEntregada: String, idRecibida: String, nombreRecibida: String) {
        viewModelScope.launch {
            dao.intercambiar(idEntregada, idRecibida, nombreRecibida)
        }
    }

    fun searchPlayer(name: String) {
        viewModelScope.launch {
            try {
                val response = RetrofitClient.api.searchPlayers(name)
                val players = response.player
                if (players.isNullOrEmpty()) {
                    _searchResult.value = null
                    _searchError.value = true
                } else {
                    _searchResult.value = players.first()
                    _searchError.value = false
                }
            } catch (_: Exception) {
                _searchResult.value = null
                _searchError.value = true
            }
        }
    }
}
