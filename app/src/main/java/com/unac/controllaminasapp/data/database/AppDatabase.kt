package com.unac.controllaminasapp.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.unac.controllaminasapp.data.dao.LaminaDao
import com.unac.controllaminasapp.data.entity.Lamina

@Database(entities = [Lamina::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract fun laminaDao(): LaminaDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "gestor_album_db"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}

fun prepopulatedData(): List<Lamina> {
    return listOf(
        Lamina(id = "ARG1", nombre_jugador = "Lionel Messi", estado = 1, cantidad_repetidas = 2),
        Lamina(id = "ARG10", nombre_jugador = "Ángel Di María", estado = 1),
        Lamina(id = "BRA7", nombre_jugador = "Vinícius Jr.", estado = 0),
        Lamina(id = "BRA10", nombre_jugador = "Neymar Jr.", estado = 1, cantidad_repetidas = 1),
        Lamina(id = "COL10", nombre_jugador = "James Rodríguez", estado = 0),
        Lamina(id = "URU9", nombre_jugador = "Luis Suárez", estado = 1),
        Lamina(id = "URU21", nombre_jugador = "Federico Valverde", estado = 0),
        Lamina(id = "FRA10", nombre_jugador = "Kylian Mbappé", estado = 1, cantidad_repetidas = 3),
        Lamina(id = "FRA7", nombre_jugador = "Antoine Griezmann", estado = 0),
        Lamina(id = "POR7", nombre_jugador = "Cristiano Ronaldo", estado = 1, cantidad_repetidas = 1),
        Lamina(id = "POR17", nombre_jugador = "Rafael Leão", estado = 0),
        Lamina(id = "ENG10", nombre_jugador = "Jude Bellingham", estado = 1),
        Lamina(id = "ENG9", nombre_jugador = "Harry Kane", estado = 0),
        Lamina(id = "NED10", nombre_jugador = "Memphis Depay", estado = 0),
        Lamina(id = "SPA9", nombre_jugador = "Álvaro Morata", estado = 1),
    )
}
