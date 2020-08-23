package cz.lamorak.koti.extensions

import java.time.Instant

fun Long.toInstant() = Instant.ofEpochMilli(this)