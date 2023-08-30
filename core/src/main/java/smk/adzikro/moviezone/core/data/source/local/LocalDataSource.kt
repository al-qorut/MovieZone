package smk.adzikro.moviezone.core.data.source.local

import smk.adzikro.moviezone.core.data.source.local.entity.MovieItem
import smk.adzikro.moviezone.core.data.source.local.room.MovieDao
import smk.adzikro.moviezone.core.data.source.local.room.MovieDatabase
import javax.inject.Inject

class LocalDataSource @Inject constructor(
    private val movieDao: MovieDao,
    private val db : MovieDatabase
) {


    suspend fun addMovie(movieItem: MovieItem) = movieDao.insertOrUpdate(movieItem)
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