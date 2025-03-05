package com.dluche.luchedroidchat.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.dluche.luchedroidchat.data.network.NetworkDataSource
import com.dluche.luchedroidchat.data.pagingsource.UserPagingSource
import com.dluche.luchedroidchat.model.User
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val networkDataSource: NetworkDataSource
) : UserRepository {
    override fun getUsers(limit: Int): Flow<PagingData<User>> {
        return Pager(
            config = PagingConfig(
                pageSize = limit,
                enablePlaceholders = false,
                //Por padrão a lib padding3 sempre carrega o valor do limit x3,
                // pois é o comportamento do valor em initialLoadSize.
                // Para mudar esse comportamento , basta definir o initialLoadSize com o tamanho do limit

            ),
            pagingSourceFactory = {
                UserPagingSource(
                    networkDataSource = networkDataSource
                )
            }
        ).flow
    }
}