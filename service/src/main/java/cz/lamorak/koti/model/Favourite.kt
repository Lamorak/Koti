package cz.lamorak.koti.model

import com.squareup.moshi.Json
import cz.lamorak.koti.Cat
import java.time.Instant

data class Favourite(
    val id: String,
    @Json(name = "created_at")
    val createdAt: Instant,
    @Json(name = "image")
    val cat: Cat
)