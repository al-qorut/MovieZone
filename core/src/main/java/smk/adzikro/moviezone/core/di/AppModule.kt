package smk.adzikro.moviezone.core.di

import android.content.Context
import android.content.res.AssetManager
import android.content.res.Resources
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStoreFile
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import net.sqlcipher.database.SQLiteDatabase
import net.sqlcipher.database.SupportFactory
import smk.adzikro.moviezone.core.utils.constant.Constants
import smk.adzikro.moviezone.core.data.source.local.datastore.AppPrefs
import smk.adzikro.moviezone.core.data.source.local.datastore.PrefHelper
import smk.adzikro.moviezone.core.data.source.local.room.MovieDao
import smk.adzikro.moviezone.core.data.source.local.room.MovieDatabase
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class AppModule {
    @Singleton
    @Provides
    fun provideAppContext(@ApplicationContext context: Context): Context = context

    @Singleton
    @Provides
    fun provideResources(context: Context): Resources = context.resources

    @Singleton
    @Provides
    fun provideAssetManager(context: Context): AssetManager = context.assets


    @Singleton
    @Provides
    fun providePreferencesDataStore(@ApplicationContext context: Context): DataStore<Preferences> {
        return PreferenceDataStoreFactory.create(
            produceFile = { context.preferencesDataStoreFile(context.packageName) }
        )
    }


    @Singleton
    @Provides
    fun provideAppDatabase(context: Context): MovieDatabase {
        val passphrase: ByteArray = SQLiteDatabase.getBytes("Raja Patih".toCharArray())
        val factory = SupportFactory(passphrase)
        return Room.databaseBuilder(context, MovieDatabase::class.java, Constants.DATABASE_NAME)
            .fallbackToDestructiveMigration()
            .openHelperFactory(factory)
            .build()
    }

    @Singleton
    @Provides
    fun provideMovieDao(appDatabase: MovieDatabase): MovieDao = appDatabase.getMovieDao()


    @Singleton
    @Provides
    fun providePrefHelper(prefs: AppPrefs): PrefHelper = prefs


}
