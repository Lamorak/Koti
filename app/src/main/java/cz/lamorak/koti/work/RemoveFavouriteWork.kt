package cz.lamorak.koti.work

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import cz.lamorak.koti.favourites.FavouritesDao
import cz.lamorak.koti.service.CatApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class RemoveFavouriteWork(
    appContext: Context,
    workerParameters: WorkerParameters
) : CoroutineWorker(appContext, workerParameters),
    KoinComponent {

    private val catApi by inject<CatApi>()
    private val favouritesDao by inject<FavouritesDao>()

    override suspend fun doWork(): Result = withContext(Dispatchers.IO) {
        val catId = inputData.getString(CAT_ID)!!
        favouritesDao.deleteFavourite(catId)

        val favouriteId = favouritesDao.getFavouriteId(catId)
        val response = catApi.removeFavourite(favouriteId)
        if (response.isSuccessful) {
            favouritesDao.deleteFavouriteId(catId)
            Result.success()
        } else {
            Result.retry()
        }
    }

    companion object {
        const val CAT_ID = "cat_id"
    }
}