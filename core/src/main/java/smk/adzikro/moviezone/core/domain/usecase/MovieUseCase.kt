package smk.adzikro.moviezone.core.domain.usecase

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import smk.adzikro.moviezone.core.data.Resource
import smk.adzikro.moviezone.core.data.source.remote.response.ApiResponse
import smk.adzikro.moviezone.core.domain.model.Actor
import smk.adzikro.moviezone.core.domain.model.Movie

interface MovieUseCase {
    fun getMovie(hashMap: HashMap<String, String>) : Flow<Resource<List<Movie>>>
    fun searchMovie(hashMap: HashMap<String, String>) : Flow<Resource<List<Movie>>>
    fun getActor(movieId :String?) : Flow<Resource<List<Actor>>>
    fun setFavorite(movieList: Movie)
    fun getFavorite() : Flow<List<Movie>>
    suspend fun deleteAllMovie()
    fun getAllMovie(hashMap: HashMap<String, String>) : Flow<PagingData<Movie>>
}