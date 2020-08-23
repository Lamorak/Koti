package cz.lamorak.koti.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import cz.lamorak.koti.favourites.FavouritesDao
import cz.lamorak.koti.favourites.model.FavouriteCat

@Database(
    entities = [
        FavouriteCat::class
    ],
    version = 1
)
@TypeConverters(Converters::class)
abstract class CatsDatabase : RoomDatabase() {

    abstract fun favouritesDao(): FavouritesDao
}