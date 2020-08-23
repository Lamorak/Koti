package cz.lamorak.koti.work

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import cz.lamorak.koti.USER_ID
import cz.lamorak.koti.database.model.FavouriteId
import cz.lamorak.koti.favourites.FavouritesDao
import cz.lamorak.koti.favourites.model.FavouriteCat
import cz.lamorak.koti.model.Favourite
import cz.lamorak.koti.service.CatApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.koin.core.KoinComponent
import org.koin.core.inject
import org.koin.core.qualifier.named

class FavouritesSyncWork(appContext: Context,
                         workerParameters: WorkerParameters) : CoroutineWorker(appContext, workerParameters), KoinComponent {

    private val catApi by inject<CatApi>()
    private val favouritesDao by inject<FavouritesDao>()
    private val userId by inject<String>(named(USER_ID))

    override suspend fun doWork(): Result = withContext(Dispatchers.IO) {
        val response = catApi.getFavourites(userId)
        if (response.isSuccessful) {
            response.body()?.run {
                favouritesDao.insertFavoutites(mapFavouriteCats())
                favouritesDao.insertFavoutiteIds(mapFavouriteIds())
                favouritesDao.cleanupFavouriteCats(map { it.cat.id })
            }

            Result.success()
        } else {
            Result.retry()
        }
    }

    private fun List<Favourite>.mapFavouriteCats() = map {
        FavouriteCat(it.cat.id, it.cat.url, it.createdAt)
    }

    private fun List<Favourite>.mapFavouriteIds() = map {
        FavouriteId(it.cat.id, it.id)
    }
}