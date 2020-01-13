package pl.norb.marvelcomics.modules

import org.koin.android.viewmodel.ext.koin.viewModel
import org.koin.dsl.module.module
import org.koin.sampleapp.util.rx.ApplicationSchedulerProvider
import org.koin.sampleapp.util.rx.SchedulerProvider
import pl.norb.marvelcomics.viewmodels.MarvelViewModel

val viewModelModules = module {

    viewModel { MarvelViewModel(get()) }
}

val rxModule = module {
    single<SchedulerProvider> { ApplicationSchedulerProvider() }
}

val marvelApp = listOf(viewModelModules, rxModule)