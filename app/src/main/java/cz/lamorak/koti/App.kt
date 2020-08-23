package cz.lamorak.koti

import android.app.Application
import cz.lamorak.koti.allcat.allCatModule
import cz.lamorak.koti.detail.detailModule
import cz.lamorak.koti.favourites.favouritesModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger(Level.ERROR)
            androidContext(this@App)
            modules(
                listOf(
                    appModule,
                    allCatModule,
                    detailModule,
                    favouritesModule
                )
            )
        }
    }
}