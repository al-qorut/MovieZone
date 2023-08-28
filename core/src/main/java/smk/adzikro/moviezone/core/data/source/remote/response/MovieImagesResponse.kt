package smk.adzikro.moviezone.core.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class MovieImagesResponse(
    @field:SerializedName("id")
    val id: Int? = null,

    @field:SerializedName("backdrops")
    val backdrops: List<ImageMovie>? = null,

    @field:SerializedName("posters")
    val posters: List<ImageMovie>? = null,
)

data class ImageMovie(
    @field:SerializedName("aspect_ratio")
    val aspect_ratio: Double? = null,

    @field:SerializedName("file_path")
    val file_path: String? = null,

    @field:SerializedName("height")
    val height: Int? = null,

    @field:SerializedName("iso_639_1")
    val iso_639_1: String? = null,

    @field:SerializedName("vote_average")
    val vote_average: Int? = null,

    @field:SerializedName("vote_count")
    val vote_count: Int? = null,

    @field:SerializedName("width")
    val width: Int? = null
)
