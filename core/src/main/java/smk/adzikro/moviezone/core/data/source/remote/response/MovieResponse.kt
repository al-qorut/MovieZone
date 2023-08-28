package smk.adzikro.moviezone.core.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class MovieResponse(
    @field:SerializedName("dates")
    val dates: Dates,

    @field:SerializedName("page")
    val page: Int,

    @field:SerializedName("results")
    val results: List<MovieListRespon>,

    @field:SerializedName("total_pages")
    val total_pages: Int,

    @field:SerializedName("total_results")
    val total_results: Int
)

data class MovieListRespon(
    @field:SerializedName("adult")
    val adult : Boolean,

    @field:SerializedName("backdrop_path")
    val backdrop_path : String,

    @field:SerializedName("genre_ids")
    val genre_ids : Array<Int>,

    @field:SerializedName("id")
    val id : String,

    @field:SerializedName("original_language")
    val original_language : String,

    @field:SerializedName("original_title")
    val original_title : String,

    @field:SerializedName("overview")
    val overview : String,

    @field:SerializedName("popularity")
    val popularity : Double,

    @field:SerializedName("poster_path")
    val poster_path : String,

    @field:SerializedName("release_date")
    val release_date : String,

    @field:SerializedName("title")
    val title : String,

    @field:SerializedName("video")
    val video : Boolean,


    @field:SerializedName("vote_average")
    val vote_average : Double,


    @field:SerializedName("vote_count")
    val vote_count : Int,
)

data class Dates(
    @field:SerializedName("maximum")
    val maximum: String,

    @field:SerializedName("minimum")
    val minimum: String
)


