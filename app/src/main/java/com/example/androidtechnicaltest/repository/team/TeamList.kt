package com.example.androidtechnicaltest.repository.team

import com.google.gson.annotations.SerializedName

data class TeamList(
    @SerializedName("teams") var teams : ArrayList<TeamInfo> = arrayListOf()
)
