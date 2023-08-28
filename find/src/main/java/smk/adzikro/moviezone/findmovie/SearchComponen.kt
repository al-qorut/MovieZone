package smk.adzikro.moviezone.findmovie

import android.content.Context
import dagger.BindsInstance
import dagger.Component
import smk.adzikro.moviezone.core.di.SearchModuleDependencies

@Component(dependencies = [SearchModuleDependencies::class])
interface SearchComponen {
    fun inject(activity: SearchActivity)

    @Component.Builder
    interface Builder {
        fun context(@BindsInstance context: Context): Builder
        fun appDependencies(mapsModuleDependencies: SearchModuleDependencies): Builder
        fun build(): SearchComponen
    }
}