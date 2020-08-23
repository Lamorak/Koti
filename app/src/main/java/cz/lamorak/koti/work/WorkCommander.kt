package cz.lamorak.koti.work

import androidx.work.*
import cz.lamorak.koti.favourites.model.FavouriteCat
import cz.lamorak.koti.work.RemoveFavouriteWork.Companion.CAT_ID
import java.time.Duration
import java.util.concurrent.TimeUnit

class WorkCommander(private val workManager: WorkManager) {

    fun addToFavourites(cat: FavouriteCat) {
        val inputData = AddFavouriteWork.inputData(cat)
        workManager.enqueue(
            createWorkRequest<AddFavouriteWork>(inputData)
        )
    }

    fun removeFromFavourites(catId: String) {
        val inputData = workDataOf(CAT_ID to catId)
        workManager.enqueue(
                createWorkRequest<RemoveFavouriteWork>(inputData)
        )
    }

    private inline fun <reified T : ListenableWorker> createWorkRequest(inputData: Data = Data.EMPTY,
                                                                        initialDelay: Duration = Duration.ZERO,
                                                                        requiresNetwork: Boolean = true): OneTimeWorkRequest {
        return OneTimeWorkRequestBuilder<T>()
            .setBackoffCriteria(
                BackoffPolicy.EXPONENTIAL,
                OneTimeWorkRequest.MIN_BACKOFF_MILLIS,
                TimeUnit.MILLISECONDS)
            .setConstraints(
                Constraints.Builder().apply {
                    if (requiresNetwork) setRequiredNetworkType(NetworkType.CONNECTED)
                }.build()
            )
            .setInputData(inputData)
            .setInitialDelay(initialDelay)
            .build()
    }
}