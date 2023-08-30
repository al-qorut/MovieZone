package smk.adzikro.moviezone.core.data.source.local.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import smk.adzikro.moviezone.core.data.source.local.datastore.AppPrefs.Key.IMAGE_QUALITY
import smk.adzikro.moviezone.core.data.source.local.datastore.AppPrefs.Key.INCLUDE_ADULT
import smk.adzikro.moviezone.core.data.source.local.datastore.AppPrefs.Key.LANG
import smk.adzikro.moviezone.core.data.source.local.datastore.AppPrefs.Key.SORT_BY
import javax.inject.Inject

class AppPrefs @Inject constructor(
    private val dataStore: DataStore<Preferences>
) : PrefHelper {

    private object Key {
        val INCLUDE_ADULT = booleanPreferencesKey("include_adult")
        val IMAGE_QUALITY = stringPreferencesKey("image_quality")
        val SORT_BY = stringPreferencesKey("sort_by")
        val LANG = stringPreferencesKey("language")
    }


    override fun getParams(): Flow<Params> {
        return dataStore.data.map {
            Params(
                it[INCLUDE_ADULT] ?: false,
                it[SORT_BY] ?: "popularity.desc",
                it[IMAGE_QUALITY] ?: "w185",
                it[LANG] ?: "en"
            )
        }
    }

    override suspend fun setParams(params: Params) {
        dataStore.edit {
            it[INCLUDE_ADULT] = params.isAdult!!
            it[SORT_BY] = params.sortBy!!
            it[IMAGE_QUALITY] = params.imageQuality!!
            it[LANG] = params.language!!
        }
    }
}