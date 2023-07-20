package org.cazait.data.caller

import kotlinx.coroutines.flow.Flow
import org.bmsk.domain.DomainResult
import org.bmsk.domain.exception.DomainError
import org.bmsk.domain.exception.ErrorType
import org.cazait.network.dto.response.CazaitResponse

interface ApiCaller {
    suspend fun safeApiCall(call: suspend () -> Flow<Result<CazaitResponse<*>>>): Flow<DomainResult<String>>
    suspend fun <T, R> safeApiCallWithData(
        call: suspend () -> Flow<Result<CazaitResponse<T>>>,
        asDomain: (T) -> R,
        onNullDataError: ErrorType? = null,
        needServerDescription: Boolean = false
    ): Flow<DomainResult<R>>
}