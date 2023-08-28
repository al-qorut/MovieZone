package smk.adzikro.moviezone.core.data.source.local.room

import androidx.room.Database
import androidx.room.RoomDatabase
import smk.adzikro.moviezone.core.data.source.local.entity.MovieItem
import smk.adzikro.moviezone.core.data.source.local.entity.RemoteKeys

@Database(entities = [MovieItem::class, RemoteKeys::class], version = 1, exportSchema = false)
abstract class MovieDatabase : RoomDatabase() {
    abstract fun getMovieDao() : MovieDao
    abstract fun getRemoteKeysDao(): RemoteKeysDao
}
