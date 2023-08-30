package smk.adzikro.moviezone.findmovie

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import smk.adzikro.moviezone.core.data.repositories.PrefRepository
import smk.adzikro.moviezone.core.domain.usecase.MovieUseCase
import javax.inject.Inject

class SearchViewModel @Inject constructor(
    private val useCase: MovieUseCase,
    private val pref: PrefRepository
) : ViewModel() {
    fun getMovie(hashMap: HashMap<String, String>) = useCase.searchMovie(hashMap).asLiveData()
    fun getParams() = pref.getParams()
}