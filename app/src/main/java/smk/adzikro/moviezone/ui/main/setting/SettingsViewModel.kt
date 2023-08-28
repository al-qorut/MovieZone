package smk.adzikro.moviezone.ui.main.setting

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import smk.adzikro.moviezone.core.data.source.local.datastore.Params
import smk.adzikro.moviezone.core.data.repositories.PrefRepository
import smk.adzikro.moviezone.core.domain.usecase.MovieUseCase
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val pref: PrefRepository,
    private val useCase: MovieUseCase
) : ViewModel() {

   fun getParams() = pref.getParams()

   fun setParams(params: Params)  =
       viewModelScope.launch {
           pref.setParams(params)
       }

   fun deleteAll()  =
       viewModelScope.launch {
           useCase.deleteAllMovie()
       }
}