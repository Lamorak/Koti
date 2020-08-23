package cz.lamorak.koti.service

import cz.lamorak.koti.Cat
import cz.lamorak.koti.model.FavouriteRequest
import cz.lamorak.koti.model.FavouriteResponse
import cz.lamorak.koti.model.RemoveResponse
import retrofit2.Response
import retrofit2.http.*

interface CatApi {

    @GET("images/search")
    suspend fun getAll(
            @Query("page") page: Int = 0,
            @Query("limit") limit: Int = 100,
            @Query("size") size: String = "thumb"
    ): List<Cat>

    @POST("favourites")
    suspend fun addFavourite(@Body request: FavouriteRequest): Response<FavouriteResponse>

    @DELETE("favourites/{favourite_id}")
    suspend fun removeFavourite(@Path("favourite_id") favouriteId: String): Response<RemoveResponse>
}