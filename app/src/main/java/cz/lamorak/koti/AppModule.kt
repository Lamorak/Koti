package cz.lamorak.koti

import android.annotation.SuppressLint
import android.provider.Settings
import android.provider.Settings.Secure
import androidx.room.Room
import androidx.work.WorkManager
import cz.lamorak.koti.database.CatsDatabase
import cz.lamorak.koti.service.ApiBuilder
import cz.lamorak.koti.work.WorkCommander
import org.koin.android.ext.koin.androidContext
import org.koin.core.qualifier.named
import org.koin.dsl.module

@SuppressLint("HardwareIds")
val appModule = module {

    single<String>(named(USER_ID)) { Secure.getString(androidContext().contentResolver, Secure.ANDROID_ID) }

    single { WorkCommander(WorkManager.getInstance(androidContext())) }

    single {
        Room.databaseBuilder(androidContext(), CatsDatabase::class.java, "cats-db")
            .build()
    }

    single { ApiBuilder.catApi() }
}

const val USER_ID = "user_id"