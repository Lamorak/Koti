package cz.lamorak.koti.allcat

import cz.lamorak.koti.service.ApiBuilder
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val allCatModule = module {

    viewModel { AllCatViewModel(get()) }
}