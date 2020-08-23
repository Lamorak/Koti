package cz.lamorak.koti.favourites

import cz.lamorak.koti.database.CatsDatabase
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val favouritesModule = module {

    viewModel { FavouritesViewModel(get()) }

    single { get<CatsDatabase>().favouritesDao() }
}