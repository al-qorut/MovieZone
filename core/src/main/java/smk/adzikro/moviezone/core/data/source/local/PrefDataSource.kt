package smk.adzikro.moviezone.core.data.source.local

import kotlinx.coroutines.flow.Flow
import smk.adzikro.moviezone.core.data.source.local.datastore.Params
import smk.adzikro.moviezone.core.data.source.local.datastore.PrefHelper
import javax.inject.Inject

class PrefDataSource @Inject constructor(
    private val moviePref: PrefHelper
) {
    suspend fun setParams(params: Params) = moviePref.setParams(params)
    fun getParams() : Flow<Params> = moviePref.getParams()
}