package smk.adzikro.moviezone.ui.main.favorite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import smk.adzikro.moviezone.core.domain.usecase.MovieUseCase
import javax.inject.Inject

@HiltViewModel
class FavoriteViewModel @Inject constructor(
    private val movieUseCase: MovieUseCase
) : ViewModel() {

    fun getFavoMovie() =
        movieUseCase.getFavorite().asLiveData()

}