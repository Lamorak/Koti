package cz.lamorak.koti.database.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class FavouriteId(
        @PrimaryKey val id: String,
        val favouriteId: String
)