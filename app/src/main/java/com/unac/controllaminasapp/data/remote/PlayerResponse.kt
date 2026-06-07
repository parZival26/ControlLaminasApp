package com.unac.controllaminasapp.data.remote

import com.google.gson.annotations.SerializedName

data class PlayerResponse(
    val player: List<Player>?
)

data class Player(
    val strPlayer: String?,
    val strTeam: String?,
    val strThumb: String?,
    val strPosition: String?,
    val strNationality: String?,
    val strSport: String?
)
