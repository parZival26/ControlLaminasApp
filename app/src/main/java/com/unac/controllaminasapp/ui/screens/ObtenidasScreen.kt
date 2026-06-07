package com.unac.controllaminasapp.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.unac.controllaminasapp.viewmodel.LaminaViewModel

@Composable
fun ObtenidasScreen(viewModel: LaminaViewModel) {
    val laminas by viewModel.obtenidas.collectAsState()
    var showDialog by remember { mutableStateOf(false) }

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = { showDialog = true }) {
                Icon(Icons.Default.Add, contentDescription = "Registrar")
            }
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier.fillMaxSize().padding(padding).padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(laminas) { lamina ->
                Card(modifier = Modifier.fillMaxWidth()) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(lamina.id, style = MaterialTheme.typography.labelSmall)
                        Text(lamina.nombre_jugador, style = MaterialTheme.typography.titleMedium)
                        if (lamina.cantidad_repetidas > 0) {
                            Text("Repetidas: ${lamina.cantidad_repetidas}")
                        }
                    }
                }
            }
        }
    }

    if (showDialog) {
        var id by remember { mutableStateOf("") }
        var nombre by remember { mutableStateOf("") }
        AlertDialog(
            onDismissRequest = { showDialog = false },
            title = { Text("Registrar lámina obtenida") },
            text = {
                Column {
                    OutlinedTextField(value = id, onValueChange = { id = it }, label = { Text("ID (ej: ARG10)") }, singleLine = true)
                    Spacer(Modifier.height(8.dp))
                    OutlinedTextField(value = nombre, onValueChange = { nombre = it }, label = { Text("Nombre del jugador") }, singleLine = true)
                }
            },
            confirmButton = {
                TextButton(onClick = {
                    if (id.isNotBlank() && nombre.isNotBlank()) {
                        viewModel.registrarObtenida(id.trim(), nombre.trim())
                        showDialog = false
                    }
                }) { Text("Registrar") }
            },
            dismissButton = {
                TextButton(onClick = { showDialog = false }) { Text("Cancelar") }
            }
        )
    }
}
