package smk.adzikro.moviezone.core.data.repositories

import androidx.lifecycle.LiveData
import smk.adzikro.moviezone.core.data.source.local.datastore.Params

interface PrefRepository {

    suspend fun setParams(params: Params)
    fun getParams() : LiveData<Params>
}