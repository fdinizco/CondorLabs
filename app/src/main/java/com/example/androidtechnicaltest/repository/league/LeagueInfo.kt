package com.example.androidtechnicaltest.repository.league

import com.google.gson.annotations.SerializedName

data class LeagueInfo(
    @SerializedName("idLeague")
    var idLeague : String? = null,
    @SerializedName("strLeague")
    var strLeague : String? = null,
    @SerializedName("strSport")
    var strSport : String? = null,
    @SerializedName("strLeagueAlternate")
    var strLeagueAlternate : String? = null
)
