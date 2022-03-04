package com.example.androidtechnicaltest.service.content

import android.accounts.NetworkErrorException
import com.example.androidtechnicaltest.extension.buildQuery
import com.example.androidtechnicaltest.service.base.baseGetRequest
import com.example.androidtechnicaltest.repository.core.Result
import com.example.androidtechnicaltest.extension.buildURL
import com.example.androidtechnicaltest.repository.league.LeagueList
import com.example.androidtechnicaltest.repository.team.TeamList
import com.example.androidtechnicaltest.util.REQUEST_SUCCEEDED
import com.google.gson.GsonBuilder

class ContentService {

    companion object {
        private const val BASE_API_URL = "https://www.thesportsdb.com/api "
        private const val API_RESPONSE_TYPE = "json "
        private const val API_CONTROL = "2 "
        private const val API_VERSION = "v1 "
        private const val LEAGUES_ENDPOINT = "all_leagues.php"
        private const val TEAMS_ENDPOINT = "search_all_teams.php"
        private const val QUERY = "?l="
    }

    suspend fun fetchLeagueList(): Result<LeagueList> {
        val targetURL = BASE_API_URL + API_VERSION + API_RESPONSE_TYPE + API_CONTROL + LEAGUES_ENDPOINT
        val requestResponse = baseGetRequest(targetURL.buildURL())
        return when (requestResponse.code) {
            REQUEST_SUCCEEDED -> {
                val baseSettings: LeagueList =
                    GsonBuilder().create().fromJson(requestResponse.body, LeagueList::class.java)
                Result(status = Result.Status.SUCCESS, data = baseSettings, throwable = null)
            }
            else -> Result(
                status = Result.Status.ERROR, data = null, throwable = NetworkErrorException(
                    requestResponse.exception
                )
            )
        }
    }

    suspend fun fetchTeamsList(leagueName: String): Result<TeamList> {
        val targetURL = BASE_API_URL + API_VERSION + API_RESPONSE_TYPE + API_CONTROL + TEAMS_ENDPOINT + QUERY
        val requestResponse = baseGetRequest(targetURL.buildURL()+leagueName.buildQuery())
        return when (requestResponse.code) {
            REQUEST_SUCCEEDED -> {
                val baseSettings: TeamList =
                    GsonBuilder().create().fromJson(requestResponse.body, TeamList::class.java)
                Result(status = Result.Status.SUCCESS, data = baseSettings, throwable = null)
            }
            else -> Result(
                status = Result.Status.ERROR, data = null, throwable = NetworkErrorException(
                    requestResponse.exception
                )
            )
        }
    }
}