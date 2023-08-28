package smk.adzikro.moviezone.core.data.source.remote.api

import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.QueryMap
import smk.adzikro.moviezone.core.data.source.remote.response.CastAndCrewResponse
import smk.adzikro.moviezone.core.data.source.remote.response.MovieImagesResponse
import smk.adzikro.moviezone.core.data.source.remote.response.MovieResponse

interface ApiService {

    @GET("discover/movie")
    suspend fun getMovies(
        @QueryMap hashMap: HashMap<String, String> = HashMap()): MovieResponse

    @GET("{path}")
    suspend fun getMovie(
        @Path("path") path: String?,
        @QueryMap hashMap: HashMap<String, String> = HashMap()): MovieResponse



    @GET("movie/{movie_id}/credits")
    suspend fun getMovieCredits(
        @Path("movie_id") movieId: String?) : CastAndCrewResponse


    @GET("movie/{movie_id}/images")
    suspend fun getMovieImages(@Path("movie_id") movieId: String): MovieImagesResponse
}