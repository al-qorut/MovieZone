package smk.adzikro.moviezone.core.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import smk.adzikro.moviezone.core.data.repositories.MovieRepository
import smk.adzikro.moviezone.core.data.repositories.PrefRepository
import smk.adzikro.moviezone.core.data.repositories.PrefRepositoryImpl
import smk.adzikro.moviezone.core.domain.repository.IMovieRepository
import smk.adzikro.moviezone.core.domain.usecase.MovieInteractor
import smk.adzikro.moviezone.core.domain.usecase.MovieUseCase
import smk.adzikro.moviezone.core.utils.AppExecutors
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class RepositoryModule {

    @Singleton
    @Provides
    fun appExecutors() = AppExecutors()

    @Singleton
    @Provides
    fun provideMovieRepository(movieRepository: MovieRepository): IMovieRepository =
        movieRepository



    @Singleton
    @Provides
    fun provideMovieUseCaseRepository(movieInteractor: MovieInteractor): MovieUseCase =
        movieInteractor

    @Singleton
    @Provides
    fun providePrefRepository(prefRepositoryImpl: PrefRepositoryImpl): PrefRepository =
        prefRepositoryImpl
}