package cz.lamorak.koti.service

import cz.lamorak.koti.Cat
import retrofit2.http.GET
import retrofit2.http.Query

interface CatApi {

    @GET("images/search")
    suspend fun getAll(@Query("page") page: Int = 0, @Query("limit") limit: Int = 100): List<Cat>
}