package rocks.ivski.hospitals.di

import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module
import rocks.ivski.hospitals.ui.list.ListVM

val vmModule = module {
    viewModel { ListVM(get(), get()) }
}