package cz.lamorak.koti

import androidx.room.Room
import cz.lamorak.koti.database.CatsDatabase
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val appModule = module {

    single {
        Room.databaseBuilder(androidContext(), CatsDatabase::class.java, "cats-db")
            .build()
    }
}