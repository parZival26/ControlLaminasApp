package com.unac.controllaminasapp.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.unac.controllaminasapp.viewmodel.LaminaViewModel

@Composable
fun BuscadorScreen(viewModel: LaminaViewModel) {
    var query by remember { mutableStateOf("") }
    val searchResult by viewModel.searchResult.collectAsState()
    val searchError by viewModel.searchError.collectAsState()

    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp).verticalScroll(rememberScrollState())
    ) {
        Row(modifier = Modifier.fillMaxWidth()) {
            OutlinedTextField(
                value = query,
                onValueChange = { query = it },
                label = { Text("Nombre del jugador") },
                singleLine = true,
                modifier = Modifier.weight(1f)
            )
            Spacer(Modifier.width(8.dp))
            Button(onClick = { viewModel.searchPlayer(query.trim()) }) {
                Text("Buscar")
            }
        }

        Spacer(Modifier.height(16.dp))

        searchResult?.let { player ->
            Card(modifier = Modifier.fillMaxWidth()) {
                Column(modifier = Modifier.padding(16.dp)) {
                    AsyncImage(
                        model = player.strThumb,
                        contentDescription = "Foto de ${player.strPlayer}",
                        modifier = Modifier.fillMaxWidth().height(200.dp),
                        contentScale = ContentScale.Crop
                    )
                    Spacer(Modifier.height(8.dp))
                    Text("Nombre: ${player.strPlayer ?: "N/A"}", style = MaterialTheme.typography.titleMedium)
                    Text("Equipo: ${player.strTeam ?: "N/A"}")
                    Text("Posición: ${player.strPosition ?: "N/A"}")
                    Text("Nacionalidad: ${player.strNationality ?: "N/A"}")
                }
            }
        }

        if (searchError && searchResult == null) {
            Text(
                "Jugador no encontrado en la base de datos deportiva",
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.error
            )
        }
    }
}
