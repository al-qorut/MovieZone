package smk.adzikro.moviezone.core.data.source.local.room

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow
import smk.adzikro.moviezone.core.data.source.local.entity.MovieItem

@Dao
interface MovieDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertData(data: List<MovieItem>)


    @Query("SELECT * FROM movie where favorite=1")
    fun getFavoMovie(): Flow<List<MovieItem>>

    @Query("SELECT * FROM movie")
    fun getMovie(): Flow<List<MovieItem>>

    @Query("SELECT * FROM movie")
    fun getAllMovie(): PagingSource<Int, MovieItem>

    @Update
    fun update(movie: MovieItem)

    @Query("DELETE FROM movie")
    suspend fun deleteAll()
}