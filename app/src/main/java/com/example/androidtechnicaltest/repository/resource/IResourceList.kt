package com.example.androidtechnicaltest.repository.resource

import com.example.androidtechnicaltest.repository.league.LeagueList
import com.example.androidtechnicaltest.repository.core.Result
import com.example.androidtechnicaltest.repository.team.TeamList

/**
 * Interface for Repository (fetching App Content).
 * */
interface IResourceList {

    /**
     * Fetches LeagueList
     *
     */
    suspend fun fetchLeagueList(): Result<LeagueList>

    /**
     * Fetches TeamsList
     *
     */
    suspend fun fetchTeamsList(leagueName: String): Result<TeamList>
}