package cz.lamorak.koti.favourites

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import cz.lamorak.koti.favourites.model.FavouriteCat

@Dao
abstract class FavouritesDao {

    @Insert
    abstract fun insertFavoutite(cat: FavouriteCat)

    @Query("DELETE FROM FavouriteCat WHERE id = :catId")
    abstract fun deleteFavourite(catId: String)

    @Query("SELECT * FROM FavouriteCat ORDER BY favouritedAt DESC")
    abstract fun getFavouriteCats(): LiveData<List<FavouriteCat>>
}