package smk.adzikro.moviezone.core.di

import dagger.hilt.EntryPoint
import dagger.hilt.components.SingletonComponent
import smk.adzikro.moviezone.core.domain.usecase.MovieUseCase
import dagger.hilt.InstallIn
import smk.adzikro.moviezone.core.data.repositories.PrefRepository

@EntryPoint
@InstallIn(SingletonComponent::class)
interface SearchModuleDependencies {
    fun movieUseCase(): MovieUseCase
    fun pref(): PrefRepository
}