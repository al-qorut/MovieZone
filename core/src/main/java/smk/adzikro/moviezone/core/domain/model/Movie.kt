package smk.adzikro.moviezone.core.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Movie(
    val id: String,
    val backdrop_path: String?=null,
    val overview: String?=null,
    val poster_path: String?=null,
    val release_date: String?=null,
    val title: String?=null,
    val vote_average: Double?=null,
    var favorite: Boolean = false
) :Parcelable
