package com.dluche.luchedroidchat.data.pagingsource

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.dluche.luchedroidchat.data.mapper.asDomainModel
import com.dluche.luchedroidchat.data.network.NetworkDataSource
import com.dluche.luchedroidchat.data.network.model.PaginationParams
import com.dluche.luchedroidchat.model.User
import javax.inject.Inject

class UserPagingSource@Inject constructor (
    private val networkDataSource: NetworkDataSource
): PagingSource<Int, User>() {
    override fun getRefreshKey(state: PagingState<Int, User>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(state.config.pageSize) ?: anchorPage?.nextKey?.minus(state.config.pageSize)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, User> {
        return try {
            val offset: Int = params.key ?: 10
            val user = networkDataSource.getUser(
                PaginationParams(
                    offset = offset.toString(),
                    limit = params.loadSize.toString()
                )
            ).asDomainModel()

            LoadResult.Page(
                data = user,
                prevKey = null,
                nextKey = offset + params.loadSize
            )
        } catch (e: Exception){
            LoadResult.Error(e)
        }
    }
}