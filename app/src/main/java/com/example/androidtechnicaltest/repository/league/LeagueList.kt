package com.example.androidtechnicaltest.repository.league

import com.google.gson.annotations.SerializedName

data class LeagueList (
    @SerializedName("leagues" ) var leagues : ArrayList<LeagueInfo> = arrayListOf()
)