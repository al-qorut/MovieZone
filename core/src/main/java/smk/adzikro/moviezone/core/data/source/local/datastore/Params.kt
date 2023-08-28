package smk.adzikro.moviezone.core.data.source.local.datastore

data class Params(
    val isAdult : Boolean? = false,
    val SortBy  : String? = "sort_by",
    val ImageQuality : String? = "w500",
    val Language : String? = "en"
)