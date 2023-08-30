package smk.adzikro.moviezone.core.data.repositories

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import smk.adzikro.moviezone.core.data.source.local.LocalDataSource
import smk.adzikro.moviezone.core.data.source.local.entity.MovieItem
import smk.adzikro.moviezone.core.data.source.local.entity.RemoteKeys
import smk.adzikro.moviezone.core.data.source.remote.api.ApiParams
import smk.adzikro.moviezone.core.data.source.remote.api.ApiService
import smk.adzikro.moviezone.core.utils.DataMapper

@OptIn(ExperimentalPagingApi::class)
class MovieRemoteMediator(
    private val local: LocalDataSource,
    private val remote: ApiService,
    private val hashMap: HashMap<String, String>
) : RemoteMediator<Int, MovieItem>() {

    override suspend fun initialize(): InitializeAction =
        InitializeAction.LAUNCH_INITIAL_REFRESH

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, MovieItem>
    ): MediatorResult {
        val page = when (loadType) {
            LoadType.REFRESH -> {
                val remoteKeys = getRemoteKeyClosestToCurrentPosition(state)
                remoteKeys?.nextKey?.minus(1) ?: INITIAL_PAGE_INDEX
            }
            LoadType.PREPEND -> {
                val remoteKeys = getRemoteKeyForFirstItem(state)
                val prevKey = remoteKeys?.prevKey
                    ?: return MediatorResult.Success(endOfPaginationReached = remoteKeys != null)
                prevKey
            }
            LoadType.APPEND -> {
                val remoteKeys = getRemoteKeysForLastItem(state)
                val nextKey = remoteKeys?.nextKey
                    ?: return MediatorResult.Success(endOfPaginationReached = remoteKeys != null)
                nextKey
            }
        }
        val hashMapAll = HashMap<String,String>()
        hashMapAll[ApiParams.PAGE] = page.toString()
        hashMapAll.putAll(hashMap)
        return try {
            val responseData = remote.getMovies(hashMapAll)
            val endOfPaginationReached = responseData.results.isEmpty()
            local.getDatabase().withTransaction {
                if (loadType == LoadType.REFRESH) {
                    local.getRemoteKeysDao().deleteRemoteKeys()
                   // local.getDatabase().getMovieDao().deleteAll()
                }

                val prevKey = if (page == 1) null else page - 1
                val nextKey = if (endOfPaginationReached) null else page + 1
                val keys = responseData.results.map {
                    RemoteKeys(id = it.id, prevKey = prevKey, nextKey = nextKey)
                }
                local.getRemoteKeysDao().insertAll(keys)
                val data = DataMapper.mapResponseToEntity(responseData.results)
                local.getDatabase().getMovieDao().insertData(data)
            }
            MediatorResult.Success(endOfPaginationReached)
        } catch (e: Exception) {
            MediatorResult.Error(e)
        }
    }
    private suspend fun getRemoteKeyForFirstItem(state: PagingState<Int, MovieItem>): RemoteKeys? {
        return state.pages.firstOrNull { it.data.isNotEmpty() }?.data?.firstOrNull()?.let {
            local.getRemoteKeysDao().getRemoteKeysId(it.id)
        }
    }

    private suspend fun getRemoteKeysForLastItem(state: PagingState<Int, MovieItem>): RemoteKeys? {
        return state.pages.lastOrNull { it.data.isNotEmpty() }?.data?.lastOrNull()?.let {
            local.getRemoteKeysDao().getRemoteKeysId(it.id)
        }
    }

    private suspend fun getRemoteKeyClosestToCurrentPosition(state: PagingState<Int, MovieItem>): RemoteKeys? {
        return state.anchorPosition?.let {
            state.closestItemToPosition(it)?.id?.let { id ->
                local.getRemoteKeysDao().getRemoteKeysId(id)
            }
        }
    }

    private companion object {
        const val INITIAL_PAGE_INDEX = 1
    }

}