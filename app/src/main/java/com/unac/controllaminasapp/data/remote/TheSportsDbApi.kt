package com.unac.controllaminasapp.data.remote

import retrofit2.http.GET
import retrofit2.http.Query

interface TheSportsDbApi {

    @GET("searchplayers.php")
    suspend fun searchPlayers(@Query("p") playerName: String): PlayerResponse
}
