package cz.lamorak.koti.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cz.lamorak.koti.favourites.FavouritesDao
import cz.lamorak.koti.favourites.model.FavouriteCat
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DetailViewModel(private val favouritesDao: FavouritesDao): ViewModel() {

    fun isCatFavourite(catId: String): LiveData<Boolean> {
        return favouritesDao.isCatFavourite(catId)
    }

    fun addCatToFavourites(cat: FavouriteCat) {
        viewModelScope.launch(Dispatchers.IO) {
            favouritesDao.insertFavoutite(cat)
        }
    }

    fun removeFromFavourites(catId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            favouritesDao.deleteFavourite(catId)
        }
    }
}