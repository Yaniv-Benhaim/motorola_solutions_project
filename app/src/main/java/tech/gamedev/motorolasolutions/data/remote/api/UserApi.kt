package tech.gamedev.motorolasolutions.data.remote.api

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
import tech.gamedev.motorolasolutions.data.other.Constants.USER_API
import tech.gamedev.motorolasolutions.data.remote.responses.UserResult

interface UserApi {

    @GET(USER_API)
    suspend fun getUser(
        @Query("results") results: Int
    ) : Response<UserResult>
}