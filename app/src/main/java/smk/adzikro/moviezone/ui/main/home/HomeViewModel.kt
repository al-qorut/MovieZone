package smk.adzikro.moviezone.ui.main.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import smk.adzikro.moviezone.core.data.repositories.PrefRepository
import smk.adzikro.moviezone.core.domain.model.Movie
import smk.adzikro.moviezone.core.domain.usecase.MovieUseCase
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val movieUseCase: MovieUseCase,
    private val pref : PrefRepository
) : ViewModel() {

    fun getParams() = pref.getParams()

    fun getMovieAll(hashMap: HashMap<String, String>) =
        movieUseCase.getAllMovie(hashMap).asLiveData()

    fun favoriteMovie(movie: Movie) =
        movieUseCase.setFavorite(movie)

}