package com.example.androidtechnicaltest.domain.base.league

import com.example.androidtechnicaltest.domain.base.core.BaseUseCase
import com.example.androidtechnicaltest.domain.base.core.Failure
import com.example.androidtechnicaltest.extension.notNull
import com.example.androidtechnicaltest.repository.core.Result
import com.example.androidtechnicaltest.repository.league.LeagueList
import com.example.androidtechnicaltest.repository.resource.ResourceImplementation
import com.example.androidtechnicaltest.service.content.ContentService
import com.example.androidtechnicaltest.util.EMPTY_STRING
import com.example.androidtechnicaltest.util.Either
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class GetLeagueListUseCase(
    private val service: ContentService
) : BaseUseCase<LeagueList, Any?>() {

    override suspend fun run(params: Any?): Either<Failure, LeagueList> {
        var leagueList: Result<LeagueList>?
        try {
            leagueList = withContext(Dispatchers.IO) {ResourceImplementation(service).fetchLeagueList()}
        } catch (exp: Exception) {
            return Either.Fail(GetLeagueListUseCase(exp))
        }
        leagueList.notNull {
            return when (this.status) {
                Result.Status.SUCCESS -> {
                    when {
                        data != null -> {
                            Either.Success(data)
                        }
                        else -> {
                            Either.Fail(GetLeagueListUseCase(Exception(EMPTY_STRING)))
                        }
                    }
                }
                else -> Either.Fail(GetLeagueListUseCase(Exception(this.throwable)))
            }
        }
        return Either.Fail(GetLeagueListUseCase(Exception(EMPTY_STRING)))
    }

    data class GetLeagueListUseCase(val error: Exception) : Failure.FeatureFailure(error)
}