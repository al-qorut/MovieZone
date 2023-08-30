package smk.adzikro.moviezone.core.utils

import smk.adzikro.moviezone.core.data.source.local.entity.MovieItem
import smk.adzikro.moviezone.core.data.source.remote.response.Cast
import smk.adzikro.moviezone.core.data.source.remote.response.MovieListRespon
import smk.adzikro.moviezone.core.domain.model.Actor
import smk.adzikro.moviezone.core.domain.model.Movie


object DataMapper {
    fun mapResponseToEntity(input: List<MovieListRespon>): List<MovieItem> {
        val movieItem = ArrayList<MovieItem>()
        input.map {
            val movie = MovieItem(
                adult = it.adult,
                backdrop_path = it.backdrop_path,
                id = it.id,
                original_language = it.original_language,
                original_title = it.original_title,
                overview = it.overview,
                popularity = it.popularity,
                poster_path = it.poster_path,
                release_date = it.release_date,
                title = it.title,
                video = it.video,
                vote_average = it.vote_average,
                vote_count = it.vote_count,
            )
            movieItem.add(movie)
        }
        return movieItem
    }
    fun mapResponseToDomain(input: List<MovieListRespon>): List<Movie> {
       return input.map {
            Movie(
                id=it.id,
                backdrop_path = it.backdrop_path,
                overview = it.overview,
                poster_path = it.poster_path,
                release_date = it.release_date,
                title = it.title,
                vote_average = it.vote_average,
            )
        }
    }
    fun mapEntityToDomain(input: List<MovieItem>): List<Movie> =
        input.map {
            Movie(
                id=it.id,
                backdrop_path = it.backdrop_path,
                overview = it.overview,
                poster_path = it.poster_path,
                release_date = it.release_date,
                title = it.title,
                vote_average = it.vote_average,
                favorite = it.favorite
            )
        }
    fun mapEntiToDoma(it: MovieItem) : Movie {
        return Movie(
            id=it.id,
            backdrop_path = it.backdrop_path,
            overview = it.overview,
            poster_path = it.poster_path,
            release_date = it.release_date,
            title = it.title,
            vote_average = it.vote_average,
            favorite = it.favorite
        )
    }
    fun mapDomainToEntity(it: Movie) = MovieItem(
        id = it.id,
        backdrop_path = it.backdrop_path,
        overview = it.overview,
        poster_path = it.poster_path,
        release_date = it.release_date,
        title = it.title,
        vote_average = it.vote_average,
        favorite = it.favorite
    )

    fun mapCastToActor(cast: List<Cast>):List<Actor>{
        return cast.map {
            Actor(
                castId = it.cast_id,
                character = it.character,
                gender = it.gender,
                id = it.id,
                name = it.name,
                profilePath = it.profile_path
            )
        }
    }
}
