package smk.adzikro.moviezone.core.data.source.local.datastore

import kotlinx.coroutines.flow.Flow

interface PrefHelper {
    fun getParams() : Flow<Params>
    suspend fun setParams(params: Params)
}