package cz.lamorak.koti.favourites

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.IGNORE
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import cz.lamorak.koti.database.model.FavouriteId
import cz.lamorak.koti.favourites.model.FavouriteCat
import kotlinx.coroutines.flow.Flow

@Dao
abstract class FavouritesDao {

    @Insert
    abstract fun insertFavoutite(cat: FavouriteCat)

    @Insert(onConflict = REPLACE)
    abstract fun insertFavoutites(cats: List<FavouriteCat>)

    @Query("DELETE FROM FavouriteCat WHERE id = :catId")
    abstract fun deleteFavourite(catId: String)

    @Query("DELETE FROM FavouriteCat WHERE id NOT IN (:currentIds)")
    abstract fun cleanupFavouriteCats(currentIds: List<String>)

    @Query("SELECT COUNT() = 1 FROM FavouriteCat WHERE id = :catId")
    abstract fun isCatFavourite(catId: String): Flow<Boolean>

    @Query("SELECT * FROM FavouriteCat ORDER BY favouritedAt DESC")
    abstract fun getFavouriteCats(): Flow<List<FavouriteCat>>

    @Insert
    abstract fun insertFavoutiteId(favoriteId: FavouriteId)

    @Insert(onConflict = IGNORE)
    abstract fun insertFavoutiteIds(favoriteIds: List<FavouriteId>)

    @Query("DELETE FROM FavouriteId WHERE id = :catId")
    abstract fun deleteFavouriteId(catId: String)

    @Query("SELECT favouriteId FROM FavouriteId WHERE id = :catId")
    abstract fun getFavouriteId(catId: String): String
}