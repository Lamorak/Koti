package cz.lamorak.koti.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import cz.lamorak.koti.favourites.FavouritesDao
import cz.lamorak.koti.favourites.model.FavouriteCat
import cz.lamorak.koti.work.WorkCommander

class DetailViewModel(private val favouritesDao: FavouritesDao,
                      private val workCommander: WorkCommander): ViewModel() {

    fun isCatFavourite(catId: String): LiveData<Boolean> {
        return favouritesDao.isCatFavourite(catId)
    }

    fun addCatToFavourites(cat: FavouriteCat) {
        workCommander.addToFavourites(cat)
    }

    fun removeFromFavourites(catId: String) {
        workCommander.removeFromFavourites(catId)
    }
}