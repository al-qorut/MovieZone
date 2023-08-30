package smk.adzikro.moviezone.core.data.repositories

import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import smk.adzikro.moviezone.core.data.source.local.PrefDataSource
import smk.adzikro.moviezone.core.data.source.local.datastore.Params
import javax.inject.Inject

class PrefRepositoryImpl @Inject constructor(
    private val moviePref: PrefDataSource,
) : PrefRepository {

    override suspend fun setParams(params: Params) =
        moviePref.setParams(params)

    override fun getParams() : LiveData<Params>{
        return moviePref.getParams().asLiveData()
    }


}