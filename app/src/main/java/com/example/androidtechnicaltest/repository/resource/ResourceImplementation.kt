package com.example.androidtechnicaltest.repository.resource

import com.example.androidtechnicaltest.repository.league.LeagueList
import com.example.androidtechnicaltest.service.content.ContentService
import com.example.androidtechnicaltest.repository.core.Result
import com.example.androidtechnicaltest.repository.team.TeamList


class ResourceImplementation(
    private val contentService: ContentService
): IResourceList {

    override suspend fun fetchLeagueList(
    ): Result<LeagueList> {
        val result = contentService.fetchLeagueList()
        return when (result.status) {
            Result.Status.SUCCESS -> {
                Result.success(result.data)
            }
            else -> Result(Result.Status.ERROR, null, result.throwable)
        }
    }

    override suspend fun fetchTeamsList(
       leagueName: String
    ): Result<TeamList> {
        val result = contentService.fetchTeamsList(leagueName)
        return when (result.status) {
            Result.Status.SUCCESS -> {
                Result.success(result.data)
            }
            else -> Result(Result.Status.ERROR, null, result.throwable)
        }
    }
}