package smk.adzikro.moviezone.core.data.source.local

import kotlinx.coroutines.flow.map
import smk.adzikro.moviezone.core.data.source.local.entity.MovieItem
import smk.adzikro.moviezone.core.data.source.local.room.MovieDao
import smk.adzikro.moviezone.core.data.source.local.room.MovieDatabase
import smk.adzikro.moviezone.core.utils.DataMapper
import javax.inject.Inject

class LocalDataSource @Inject constructor(
    private val movieDao: MovieDao,
    private val db : MovieDatabase
) {


    fun deleteRemoteKeys() = db.getRemoteKeysDao()
    fun getRemoteKeysDao() = db.getRemoteKeysDao()
    fun setFavorite(movieItem: MovieItem) =
        movieDao.update(movieItem)

    suspend fun insertData(data : List<MovieItem>) = movieDao.insertData(data)
    fun getFavoMovie() = movieDao.getFavoMovie()

    fun getMovie()  = movieDao.getMovie()
    suspend fun deleteAllMovie() = movieDao.deleteAll()
    fun getAllMovie() =
        movieDao.getAllMovie()
    fun getDatabase() = db


}