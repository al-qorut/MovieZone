package smk.adzikro.moviezone.core.data.source.local.datastore

data class Params(
    val isAdult : Boolean? = false,
    val sortBy  : String? = "sort_by",
    val imageQuality : String? = "w500",
    val language : String? = "en"
)