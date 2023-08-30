package smk.adzikro.moviezone.ui.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import smk.adzikro.moviezone.core.domain.model.Movie
import smk.adzikro.moviezone.core.domain.usecase.MovieUseCase
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val movieUseCase: MovieUseCase
) : ViewModel() {

    fun favoriteMovie(movie: Movie) =
        viewModelScope.launch {
            movieUseCase.addMovie(movie)
        }



    fun getActor(movieId: String?) =
        movieUseCase.getActor(movieId).asLiveData()
}