package smk.adzikro.moviezone.core.data.source.remote

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import smk.adzikro.moviezone.core.data.source.remote.api.ApiService
import smk.adzikro.moviezone.core.data.source.remote.response.ApiResponse
import smk.adzikro.moviezone.core.data.source.remote.response.Cast
import smk.adzikro.moviezone.core.data.source.remote.response.MovieListRespon
import smk.adzikro.moviezone.core.utils.debug
import javax.inject.Inject

class RemoteDataSource @Inject constructor(
    private val apiService: ApiService,
) {



    suspend fun getMovie(hashMap: HashMap<String, String> = HashMap()):
            Flow<ApiResponse<List<MovieListRespon>>> {
        return flow {
            try {
                val response = apiService.getMovie(path = "discover/movie",hashMap = hashMap)
                val dataArray = response.results
                if (dataArray.isNotEmpty()) {
                    emit(ApiResponse.Success(response.results))
                } else {
                    emit(ApiResponse.Empty)
                }
            } catch (e: Exception) {
                emit(ApiResponse.Error(e.toString()))
                debug("RemoteDataSource $e", )
            }
        }.flowOn(Dispatchers.IO)
    }

    suspend fun searchMovie(hashMap: HashMap<String, String> = HashMap()):
            Flow<ApiResponse<List<MovieListRespon>>> {
        return flow {
            try {
                val response = apiService.getMovie(path = "search/movie", hashMap = hashMap)
                val dataArray = response.results
                if (dataArray.isNotEmpty()) {
                    emit(ApiResponse.Success(response.results))
                } else {
                    emit(ApiResponse.Empty)
                }
            } catch (e: Exception) {
                emit(ApiResponse.Error(e.toString()))
                debug("RemoteDataSource $e", )
            }
        }.flowOn(Dispatchers.IO)
    }

    fun getActor(movieId: String?):
            Flow<ApiResponse<List<Cast>>> =
        flow {
            try {
                val response = apiService.getMovieCredits(movieId).cast
                if (response.isNotEmpty()) {

                    emit(ApiResponse.Success(response))
                } else {
                    emit(ApiResponse.Empty)
                }
            } catch (e: Exception) {
                emit(ApiResponse.Error(e.toString()))
            }
        }.flowOn(Dispatchers.IO)

    fun getApiService() = apiService
}