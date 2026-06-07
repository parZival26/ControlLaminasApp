package com.unac.controllaminasapp.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.unac.controllaminasapp.data.entity.Lamina
import kotlinx.coroutines.flow.Flow

@Dao
abstract class LaminaDao {

    @Query("SELECT * FROM laminas WHERE estado = 1")
    abstract fun getObtenidas(): Flow<List<Lamina>>

    @Query("SELECT * FROM laminas WHERE estado = 0")
    abstract fun getPendientes(): Flow<List<Lamina>>

    @Query("SELECT * FROM laminas WHERE cantidad_repetidas > 0")
    abstract fun getRepetidas(): Flow<List<Lamina>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun insertarLamina(lamina: Lamina)

    @Query("SELECT * FROM laminas WHERE id = :id")
    abstract suspend fun getLaminaById(id: String): Lamina?

    @Query("UPDATE laminas SET cantidad_repetidas = :cantidad WHERE id = :id")
    abstract suspend fun actualizarRepetidas(id: String, cantidad: Int)

    @Query("UPDATE laminas SET estado = 1 WHERE id = :id")
    abstract suspend fun marcarComoObtenida(id: String)

    @Transaction
    open suspend fun registrarObtenida(id: String, nombreJugador: String) {
        val existente = getLaminaById(id)
        if (existente == null) {
            insertarLamina(Lamina(id = id, nombre_jugador = nombreJugador, estado = 1))
        } else if (existente.estado == 1) {
            actualizarRepetidas(id, existente.cantidad_repetidas + 1)
        } else {
            marcarComoObtenida(id)
        }
    }

    @Transaction
    open suspend fun intercambiar(idEntregada: String, idRecibida: String, nombreRecibida: String) {
        val entregada = getLaminaById(idEntregada)
        if (entregada != null && entregada.cantidad_repetidas > 0) {
            actualizarRepetidas(idEntregada, entregada.cantidad_repetidas - 1)
        }
        val recibida = getLaminaById(idRecibida)
        if (recibida == null) {
            insertarLamina(Lamina(id = idRecibida, nombre_jugador = nombreRecibida, estado = 1))
        } else if (recibida.estado == 0) {
            marcarComoObtenida(idRecibida)
        }
    }

    @Query("SELECT COUNT(*) FROM laminas")
    abstract suspend fun getCount(): Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun insertarLaminas(laminas: List<Lamina>)
}
