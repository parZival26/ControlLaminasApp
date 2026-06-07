package com.unac.controllaminasapp.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.unac.controllaminasapp.viewmodel.LaminaViewModel

@Composable
fun RepetidasScreen(viewModel: LaminaViewModel) {
    val laminas by viewModel.repetidas.collectAsState()
    var intercambiarId by remember { mutableStateOf<String?>(null) }

    LazyColumn(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(laminas) { lamina ->
            Card(modifier = Modifier.fillMaxWidth()) {
                Row(
                    modifier = Modifier.fillMaxWidth().padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text(lamina.id, style = MaterialTheme.typography.labelSmall)
                        Text(lamina.nombre_jugador, style = MaterialTheme.typography.titleMedium)
                        Text("Repetidas: ${lamina.cantidad_repetidas}")
                    }
                    Button(onClick = { intercambiarId = lamina.id }) {
                        Text("Intercambiar")
                    }
                }
            }
        }
    }

    intercambiarId?.let { entregadaId ->
        var idRecibida by remember { mutableStateOf("") }
        var nombreRecibida by remember { mutableStateOf("") }
        AlertDialog(
            onDismissRequest = { intercambiarId = null },
            title = { Text("Intercambiar: $entregadaId") },
            text = {
                Column {
                    Text("Se restará 1 repetida de $entregadaId")
                    Spacer(Modifier.height(8.dp))
                    OutlinedTextField(value = idRecibida, onValueChange = { idRecibida = it }, label = { Text("ID recibido") }, singleLine = true)
                    Spacer(Modifier.height(8.dp))
                    OutlinedTextField(value = nombreRecibida, onValueChange = { nombreRecibida = it }, label = { Text("Nombre del jugador recibido") }, singleLine = true)
                }
            },
            confirmButton = {
                TextButton(onClick = {
                    if (idRecibida.isNotBlank() && nombreRecibida.isNotBlank()) {
                        viewModel.intercambiar(entregadaId, idRecibida.trim(), nombreRecibida.trim())
                        intercambiarId = null
                    }
                }) { Text("Confirmar") }
            },
            dismissButton = {
                TextButton(onClick = { intercambiarId = null }) { Text("Cancelar") }
            }
        )
    }
}
