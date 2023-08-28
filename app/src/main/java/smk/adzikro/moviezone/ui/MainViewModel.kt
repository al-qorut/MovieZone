package smk.adzikro.moviezone.ui

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking

class MainViewModel : ViewModel() {
    fun mockDataLoading(): Boolean {
        runBlocking {
            delay(1000)
        }
        return true
    }
}