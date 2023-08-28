package smk.adzikro.moviezone.findmovie

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import smk.adzikro.moviezone.core.data.repositories.PrefRepository
import smk.adzikro.moviezone.core.domain.usecase.MovieUseCase
import javax.inject.Inject

class SearchViewModelFactory @Inject constructor(
    private val movieUseCase:  MovieUseCase,
    private val prefRepository: PrefRepository) :
    ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T =
        when {
            modelClass.isAssignableFrom(SearchViewModel::class.java) -> {
                SearchViewModel(movieUseCase, prefRepository) as T
            }
            else -> throw Throwable("Unknown ViewModel class: " + modelClass.name)
        }
}