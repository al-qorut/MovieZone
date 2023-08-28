package smk.adzikro.moviezone.core.data.source.local.entity

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "movie")
data class MovieItem(
    @ColumnInfo(name = "adult")
    val adult: Boolean? = false,

    @ColumnInfo(name = "backdrop_path")
    val backdrop_path: String? = null,

    @PrimaryKey
    @ColumnInfo(name = "id")
    val id: String,

    @ColumnInfo(name = "original_language")
    val original_language: String?= null,

    @ColumnInfo(name = "original_title")
    val original_title: String?= null,

    @ColumnInfo(name = "overview")
    val overview: String?= null,

    @ColumnInfo(name = "popularity")
    val popularity: Double?= null,

    @ColumnInfo(name = "poster_path")
    val poster_path: String?= null,

    @ColumnInfo(name = "release_date")
    val release_date: String? = null,

    @ColumnInfo(name = "title")
    val title: String? = null,

    @ColumnInfo(name = "video")
    val video: Boolean? = null,


    @ColumnInfo(name = "vote_average")
    val vote_average: Double? = null,

    @ColumnInfo(name = "vote_count")
    val vote_count: Int? = null,

    @ColumnInfo(name = "favorite")
    var favorite: Boolean = false
): Parcelable