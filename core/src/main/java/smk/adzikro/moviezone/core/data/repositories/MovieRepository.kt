package smk.adzikro.moviezone.core.data.repositories

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import smk.adzikro.moviezone.core.data.Resource
import smk.adzikro.moviezone.core.data.source.local.LocalDataSource
import smk.adzikro.moviezone.core.data.source.remote.RemoteDataSource
import smk.adzikro.moviezone.core.data.source.remote.response.ApiResponse
import smk.adzikro.moviezone.core.domain.model.Actor
import smk.adzikro.moviezone.core.domain.model.Movie
import smk.adzikro.moviezone.core.domain.repository.IMovieRepository
import smk.adzikro.moviezone.core.utils.AppExecutors
import smk.adzikro.moviezone.core.utils.DataMapper
import javax.inject.Inject

class MovieRepository @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource,
    private val appExecutors: AppExecutors
) : IMovieRepository {


    override fun searchMovie(hashMap: HashMap<String, String>): Flow<Resource<List<Movie>>> = flow {
        emit(Resource.Loading())
        when (val apiResponse = remoteDataSource.searchMovie(hashMap).first()) {
            is ApiResponse.Success -> {
                val data = DataMapper.mapResponseToDomain(apiResponse.data)
                emit(Resource.Success(data))
            }

            is ApiResponse.Empty -> emit(Resource.Empty())
            is ApiResponse.Error -> emit(Resource.Error(apiResponse.errorMessage))
        }
    }

    override fun getActor(movieId: String?): Flow<Resource<List<Actor>>> = flow {
        emit(Resource.Loading())
        when (val apiResponse = remoteDataSource.getActor(movieId).first()) {
            is ApiResponse.Success -> {
                val data = DataMapper.mapCastToActor(apiResponse.data)
                emit(Resource.Success(data))
            }

            is ApiResponse.Empty -> emit(Resource.Empty())
            is ApiResponse.Error -> emit(Resource.Error(apiResponse.errorMessage))
        }
    }


    override fun setFavorite(movieList: Movie) {
        val movieItem = DataMapper.mapDomainToEntity(movieList)
        appExecutors.diskIO().execute {
            localDataSource.setFavorite(movieItem)
        }
    }

    override fun getFavorite(): Flow<List<Movie>> {
        return localDataSource.getFavoMovie().map {
            DataMapper.mapEntityToDomain(it)
        }
    }

    override suspend fun deleteAllMovie() {
        localDataSource.deleteAllMovie()

    }

    override fun getAllMovie(hashMap: HashMap<String, String>): Flow<PagingData<Movie>> =
        @OptIn(ExperimentalPagingApi::class)
        flow {
            emitAll(Pager(
                config = PagingConfig(
                    pageSize = 20
                ),
                remoteMediator = MovieRemoteMediator(
                    localDataSource,
                    remoteDataSource.getApiService(),
                    hashMap
                ),
                pagingSourceFactory = {
                    localDataSource.getAllMovie()
                }
            ).flow.map { paging ->
                paging.map {
                    DataMapper.mapEntiToDoma(it)
                }
            })
        }

}