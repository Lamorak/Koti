package cz.lamorak.koti.service

import cz.lamorak.koti.Cat
import cz.lamorak.koti.model.Favourite
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
            @Query("size") size: String = "thumb",
            @Query("order") order: String = "ASC",
    ): List<Cat>

    @GET("favourites")
    suspend fun getFavourites(@Query("sub_id") userId: String): Response<List<Favourite>>

    @POST("favourites")
    suspend fun addFavourite(@Body request: FavouriteRequest): Response<FavouriteResponse>

    @DELETE("favourites/{favourite_id}")
    suspend fun removeFavourite(@Path("favourite_id") favouriteId: String): Response<RemoveResponse>
}