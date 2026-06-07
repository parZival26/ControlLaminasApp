package com.unac.controllaminasapp.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "laminas")
data class Lamina(
    @PrimaryKey
    val id: String,
    val nombre_jugador: String,
    val estado: Int = 0,
    val cantidad_repetidas: Int = 0
)
