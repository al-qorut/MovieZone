package smk.adzikro.moviezone.ui.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import smk.adzikro.moviezone.core.domain.model.Movie
import smk.adzikro.moviezone.core.domain.usecase.MovieUseCase
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val movieUseCase: MovieUseCase
) : ViewModel() {

    fun favoriteMovie(movie: Movie) =
        movieUseCase.setFavorite(movie)


    fun getActor(movieId: String?) =
        movieUseCase.getActor(movieId).asLiveData()
}