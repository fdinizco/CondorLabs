package com.example.androidtechnicaltest.domain.base.team

import com.example.androidtechnicaltest.domain.base.core.BaseUseCase
import com.example.androidtechnicaltest.domain.base.core.Failure
import com.example.androidtechnicaltest.extension.notNull
import com.example.androidtechnicaltest.repository.core.Result
import com.example.androidtechnicaltest.repository.league.LeagueList
import com.example.androidtechnicaltest.repository.resource.ResourceImplementation
import com.example.androidtechnicaltest.repository.team.TeamList
import com.example.androidtechnicaltest.service.content.ContentService
import com.example.androidtechnicaltest.util.EMPTY_STRING
import com.example.androidtechnicaltest.util.Either
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class GetTeamListUseCase (
    private val service: ContentService,
    private val leagueName: String
) : BaseUseCase<TeamList, Any?>() {

    override suspend fun run(params: Any?): Either<Failure, TeamList> {
        var teamList: Result<TeamList>?
        try {
            teamList = withContext(Dispatchers.IO) { ResourceImplementation(service).fetchTeamsList(leagueName)}
        } catch (exp: Exception) {
            return Either.Fail(GetTeamListUseCase(exp))
        }
        teamList.notNull {
            return when (this.status) {
                Result.Status.SUCCESS -> {
                    when {
                        data != null -> {
                            Either.Success(data)
                        }
                        else -> {
                            Either.Fail(GetTeamListUseCase(Exception(EMPTY_STRING)))
                        }
                    }
                }
                else -> Either.Fail(GetTeamListUseCase(Exception(this.throwable)))
            }
        }
        return Either.Fail(GetTeamListUseCase(Exception(EMPTY_STRING)))
    }

    data class GetTeamListUseCase(val error: Exception) : Failure.FeatureFailure(error)
}