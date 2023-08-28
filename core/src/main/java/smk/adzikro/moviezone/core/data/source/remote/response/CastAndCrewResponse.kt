package smk.adzikro.moviezone.core.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class CastAndCrewResponse(
    @field:SerializedName("id")
    val id: Int,

    @field:SerializedName("cast")
    val cast: List<Cast>,

    @field:SerializedName("crew")
    val crew: List<Crew>,
)

data class Cast(
    @field:SerializedName("cast_id")
    val cast_id: String,

    @field:SerializedName("character")
    val character: String,

    @field:SerializedName("credit_id")
    val credit_id: String,

    @field:SerializedName("gender")
    val gender: Int,

    @field:SerializedName("id")
    val id: String,

    @field:SerializedName("name")
    val name: String,

    @field:SerializedName("order")
    val order: Int,

    @field:SerializedName("profile_path")
    val profile_path: String
)

data class Crew(
    @field:SerializedName("credit_id")
    val credit_id: String? = null,

    @field:SerializedName("department")
    val department: String? = null,


    @field:SerializedName("gender")
    val gender: Int? = null,

    @field:SerializedName("id")
    val id: String? = null,


    @field:SerializedName("job")
    val job: String? = null,

    @field:SerializedName("name")
    val name: String? = null,


    @field:SerializedName("profile_path")
    val profile_path: String? = null
)
