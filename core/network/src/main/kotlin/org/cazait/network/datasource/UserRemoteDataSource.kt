package org.cazait.network.datasource

import org.cazait.network.model.DataResponse
import org.bmsk.domain.model.Role
import org.cazait.network.model.request.IsNicknameDupReq
import org.cazait.network.model.request.SignInReq
import org.cazait.network.model.request.SignUpReq
import org.cazait.network.model.response.RefreshTokenRes
import org.cazait.network.model.response.SignInRes
import org.cazait.network.model.response.SignUpRes

interface UserRemoteDataSource {
    suspend fun postSignUp(email: String, password: String, nickname: String): DataResponse<SignUpRes>
    suspend fun postSignIn(email: String, password: String): DataResponse<SignInRes>
    suspend fun getRefreshToken(
        userIdx: Long,
        role: Role,
    ): DataResponse<RefreshTokenRes>
    suspend fun postIsEmailDup(email: String): DataResponse<IsNicknameDupReq>
}