package rocks.ivski.hospitals.di

import org.koin.dsl.module
import rocks.ivski.hospitals.data.repository.HospitalRepo

val repoModule = module {
    single { HospitalRepo(get()) }
}