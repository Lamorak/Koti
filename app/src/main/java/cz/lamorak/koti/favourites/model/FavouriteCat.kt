package cz.lamorak.koti.favourites.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.Instant

@Entity
data class FavouriteCat(
        @PrimaryKey val id: String,
        val imageUrl: String,
        val favouritedAt: Instant
)