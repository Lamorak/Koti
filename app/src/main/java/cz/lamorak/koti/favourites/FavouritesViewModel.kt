package cz.lamorak.koti.favourites

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cz.lamorak.koti.favourites.model.FavouriteCat
import kotlinx.coroutines.launch

class FavouritesViewModel(private val favouritesDao: FavouritesDao): ViewModel() {

    fun getFavouriteCats(): LiveData<List<FavouriteCat>> {
        return favouritesDao.getFavouriteCats()
    }
}