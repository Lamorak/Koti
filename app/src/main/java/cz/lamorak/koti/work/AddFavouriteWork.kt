package cz.lamorak.koti.work

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import androidx.work.workDataOf
import cz.lamorak.koti.model.FavouriteRequest
import cz.lamorak.koti.USER_ID
import cz.lamorak.koti.database.model.FavouriteId
import cz.lamorak.koti.extensions.toInstant
import cz.lamorak.koti.favourites.FavouritesDao
import cz.lamorak.koti.favourites.model.FavouriteCat
import cz.lamorak.koti.service.CatApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.koin.core.KoinComponent
import org.koin.core.inject
import org.koin.core.qualifier.named

class AddFavouriteWork(appContext: Context,
                       workerParameters: WorkerParameters): CoroutineWorker(appContext, workerParameters), KoinComponent {

    private val catApi by inject<CatApi>()
    private val favouritesDao by inject<FavouritesDao>()
    private val userId by inject<String>(named(USER_ID))

    override suspend fun doWork(): Result = withContext(Dispatchers.IO) {
        val cat = cat()
        favouritesDao.insertFavoutite(cat)

        val response = catApi.addFavourite(FavouriteRequest(cat.id, userId))
        if (response.isSuccessful) {
            response.body()?.run {
                favouritesDao.insertFavoutiteId(FavouriteId(cat.id, this.id))
            }
            Result.success()
        } else {
            Result.retry()
        }
    }

    private fun cat() = FavouriteCat(
        inputData.getString(FavouriteCat::id.name)!!,
        inputData.getString(FavouriteCat::imageUrl.name)!!,
        inputData.getLong(FavouriteCat::favouritedAt.name, 0).toInstant()
    )

    companion object {
        fun inputData(cat: FavouriteCat) = workDataOf(
            FavouriteCat::id.name to cat.id,
            FavouriteCat::imageUrl.name to cat.imageUrl,
            FavouriteCat::favouritedAt.name to cat.favouritedAt.toEpochMilli()
        )
    }
}