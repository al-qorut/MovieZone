package smk.adzikro.moviezone.core.domain.usecase

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import smk.adzikro.moviezone.core.data.Resource
import smk.adzikro.moviezone.core.domain.model.Movie
import smk.adzikro.moviezone.core.domain.repository.IMovieRepository
import javax.inject.Inject

class MovieInteractor @Inject constructor(
    private val iMovieRepository: IMovieRepository
) : MovieUseCase{

    override fun getMovie(hashMap: HashMap<String, String>) =
        iMovieRepository.getMovie(hashMap)

    override fun searchMovie(hashMap: HashMap<String, String>) =
        iMovieRepository.searchMovie(hashMap)

    override fun getActor(movieId: String?) =
        iMovieRepository.getActor(movieId)

    override fun setFavorite(movieList: Movie) =
        iMovieRepository.setFavorite(movieList)

    override fun getFavorite() =
        iMovieRepository.getFavorite()

    override suspend fun deleteAllMovie() {
        iMovieRepository.deleteAllMovie()
    }

    override fun getAllMovie(hashMap: HashMap<String, String>): Flow<PagingData<Movie>> =
        iMovieRepository.getAllMovie(hashMap)
}