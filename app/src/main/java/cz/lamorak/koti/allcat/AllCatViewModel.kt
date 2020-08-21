package cz.lamorak.koti.allcat

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.*
import cz.lamorak.koti.Cat
import cz.lamorak.koti.service.ApiBuilder
import cz.lamorak.koti.service.CatApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class AllCatViewModel(private val catApi: CatApi) : ViewModel() {

    fun getAllCats(): Flow<PagingData<Cat>> {
        return Pager(PagingConfig(enablePlaceholders = false, pageSize = 100), initialKey = 0) {
            object : PagingSource<Int, Cat>() {

                override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Cat> {
                    val page = params.key ?: 0
                    val cats = catApi.getAll(page = page)
                    val prevKey = if (page == 0) null else page - 1
                    return LoadResult.Page(data = cats, prevKey = prevKey, nextKey = page + 1)
                }
            }
        }.flow
    }
}