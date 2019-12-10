package com.philipgurr.smartshoppinglist.domain.usecases

import com.philipgurr.smartshoppinglist.domain.ShoppingList
import com.philipgurr.smartshoppinglist.repository.Repository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GetListsUseCase @Inject constructor(
    private val repository: Repository
) {

    suspend fun getShoppingList(name: String) = withContext(Dispatchers.Default) {
        repository.getList(name)
    }

    suspend fun getShoppingLists() = withContext(Dispatchers.Default) {
        repository.getAllLists().sortedBy { it.created }
    }
}