package smk.adzikro.moviezone

import android.app.Application
import androidx.multidex.MultiDex
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import timber.log.Timber

@HiltAndroidApp
class MovieApplication : Application(){

    val appScope by lazy {
        CoroutineScope(SupervisorJob())
    }

    override fun onCreate() {
        super.onCreate()
        MultiDex.install(this)
        if (isDevMode()) {
            Timber.plant(Timber.DebugTree())
        } else {
        }
    }

}
fun isDevMode() = BuildConfig.BUILD_TYPE != "release"
/*
# MovieZoneKotlin
[![al-qorut](https://circleci.com/gh/al-qorut/MovieZoneKotlin.svg?style=shield)](https://circleci.com/gh/al-qorut/MovieZoneKotlin)

TheMovieDb with kotlin
Modularization
Clean Architecture
    dependency rule
    separation model.
Dependency Injection
    Dagger-hilt
Reactive Programming
   Coroutine Flow
 */